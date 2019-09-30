package br.com.annahas.ultrassom.bc;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;

import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.annahas.ultrassom.constants.TipoAlgoritmoEnum;
import br.com.annahas.ultrassom.dao.UltrassomDAO;
import br.com.annahas.ultrassom.dto.UltrassomDTO;
import br.com.annahas.ultrassom.entity.Ultrassom;
import br.com.annahas.ultrassom.util.UltrassomUtil;

public class UltrassomBC extends AbstractBusiness<Ultrassom, BigDecimal> {
	
	private static List<Ultrassom> listaProcessamento = new ArrayList<>();
	
	private static final String LOCK = "###" + UltrassomBC.class.toString() + "###";
	
	private static boolean funcaoIniciada = false;
	
	public List<Ultrassom> findAll() {
		return ((UltrassomDAO) dao).findAll();
	}

	public List<UltrassomDTO> findByCodigoUsuario(BigDecimal codigoUsuario) {
		List<Ultrassom> ultrassomList = ((UltrassomDAO) dao).findByCodigoUsuario(codigoUsuario);
		List<UltrassomDTO> dtoList = new ArrayList<UltrassomDTO>();
		ultrassomList.stream().forEach(ultrassom -> {
			dtoList.add(new UltrassomDTO(ultrassom));
		});
		return dtoList;
	}

	@Transactional
	public void salvaUltrassom(BigDecimal codigoUsuario, BigDecimal codigoAlgoritmo, BigDecimal altura, BigDecimal largura, final MultipartFormDataInput inputData) throws IOException, SQLException {
		
		
		String conteudo = UltrassomUtil.multipartToString(inputData);
		
		if (conteudo == null) {
			throw new DemoiselleRestException("Erro no arquivo enviado", Status.BAD_REQUEST.getStatusCode());
		}
		
//		System.out.println(conteudo);
		
		Ultrassom ultrassom = new Ultrassom();
		ultrassom.setCodigoUsuario(codigoUsuario);
		ultrassom.setSinal(conteudo);
		ultrassom.setAltura(altura);
		ultrassom.setLargura(largura);
		ultrassom.setCodigoTipoAlgoritmo(codigoAlgoritmo);
		
		synchronized (listaProcessamento) {
			listaProcessamento.add(ultrassom);
		}
		if (!funcaoIniciada) {
			synchronized (LOCK) {
				if (!funcaoIniciada) {
					funcaoIniciada = true;
					new Thread() {
						public void run() {
							while(true) {
								Ultrassom item = null;
								synchronized (listaProcessamento) {
									if (listaProcessamento.size() > 0) {
										item = listaProcessamento.remove(0);
									}
								}
								if (item != null) {
									String[] sinaisStr = item.getSinal().split("\n");
									double[] sinais = new double[sinaisStr.length];
									for (int i = 0; i < sinaisStr.length; i++) {
										sinais[i] = Double.parseDouble(sinaisStr[i]);
									}
									/**
									 *  decidi utilizar a notação vec_X e mat_X para conseguir dar os nomes das variáveis
									 *  	igual como foi apresentado, para melhor visualização do que está acontecendo. 
									 */
									DMatrixRMaj vec_g = new DMatrixRMaj(sinais);
									DMatrixRMaj vec_f = new DMatrixRMaj(sinais.length);
									DMatrixRMaj mat_H = new DMatrixRMaj(sinais.length, sinais.length);
									
									int i = 0;
									
									if (item.getCodigoTipoAlgoritmo().compareTo(TipoAlgoritmoEnum.ALGORITMO_1.getCodigo()) == 0 ) {
										DMatrixRMaj vec_aux = new DMatrixRMaj();
										CommonOps_DDRM.mult(mat_H, vec_f, vec_aux);
										
										DMatrixRMaj vec_r = new DMatrixRMaj();
										
										CommonOps_DDRM.subtract(vec_g, vec_aux, vec_r);
										
										DMatrixRMaj vec_p = new DMatrixRMaj();
										
										CommonOps_DDRM.multTransA(mat_H, vec_r, vec_p);
										
										
										double epsum = 1.0;
										
										DMatrixRMaj mat_inner_r = new DMatrixRMaj();
										DMatrixRMaj mat_inner_r1 = new DMatrixRMaj();
										DMatrixRMaj mat_inner_p = new DMatrixRMaj();
										
										DMatrixRMaj vec_f1 = new DMatrixRMaj();
										DMatrixRMaj vec_r1 = new DMatrixRMaj();
										DMatrixRMaj vec_p1 = new DMatrixRMaj();
										
										double alpha = 0.0;
										double beta = 0.0;
										
										// no final do loop, f final estará em vec_f1
										while (epsum > 0.0001) {
											i++;
											
											// alpha(i) = rT(i)*r(i) / pT(i) * p(i)
											CommonOps_DDRM.multInner(vec_p, mat_inner_p); // pT(i) * p(i)
											CommonOps_DDRM.multInner(vec_r, mat_inner_r); // rT(i)*r(i)
											//alpha = ? mat_inner_r / mat_inner_p
											
											// f(i+1) = f(i) + alpha(i) * p(i)
											CommonOps_DDRM.add(vec_f, alpha, vec_p, vec_f1);
											
											// r(i+1) = r(i) - alpha(i) * H * p(i)
											vec_r1 = vec_r.copy(); // r(i+1) = r(i)
											CommonOps_DDRM.multAdd(-alpha, mat_H, vec_p, vec_r1); // r(i+1) = r(i+1) - alpha(i) * H * p(i)
											
											// epsum = ||r(i+1)||2 -||r(i)||2 
											epsum = NormOps_DDRM.normP2(vec_r1) - NormOps_DDRM.normP2(vec_r);
											
											if (epsum <= 0.0001) {
												break;
											}
											
											// beta(i) = rT(i+1)*r(i+1) / rT(i)*r(i)
											CommonOps_DDRM.multInner(vec_r1, mat_inner_r1); // rT(i+1)*r(i+1)
											//beta = ? mat_inner_r1 / mat_inner_r
											
											// p(i+1) = HT * r(i+1) + beta(i) * p(i)
											CommonOps_DDRM.multTransA(mat_H, vec_r1, vec_p1); // p(i+1) = HT * r(i+1)
											CommonOps_DDRM.addEquals(vec_p1, beta, vec_p); // p(i+1) = p(i+1) + beta * p(i)
											
											vec_r = vec_r1.copy(); // r(i) = r(i+1)
											vec_p = vec_p1.copy(); // p(i) = p(i+1)
											vec_f = vec_f1.copy(); // f(i) = f(i+1)
											
										}
									} else if (item.getCodigoTipoAlgoritmo().compareTo(TipoAlgoritmoEnum.ALGORITMO_2.getCodigo()) == 0 ) {
										/**
										 * Fazer reconstrucao do algoritmo 2 aqui.
										 * 
										 * 
										 */
									}
									
									item.setNumeroIteracoes(BigDecimal.valueOf(i));
									
									
								}
							}
						}
					}.start();
				}
			}
		}
		((UltrassomDAO)dao).persist(ultrassom);
	}
	
	
	
}



package br.com.annahas.ultrassom.bc;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;

import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.annahas.ultrassom.dao.UltrassomDAO;
import br.com.annahas.ultrassom.dto.UltrassomCadastroDTO;
import br.com.annahas.ultrassom.dto.UltrassomDTO;
import br.com.annahas.ultrassom.entity.Ultrassom;
import br.com.annahas.ultrassom.thread.InfinityCalc;
import br.com.annahas.ultrassom.util.UltrassomUtil;

public class UltrassomBC extends AbstractBusiness<Ultrassom, BigDecimal> {
	
	private static List<BigDecimal> listaProcessamento = new ArrayList<>();
	
	private static final String LOCK = "###" + UltrassomBC.class.toString() + "###";
	
	private static boolean funcaoIniciada = false;
	
	@Inject private Instance<InfinityCalc> infinityCalc;
	
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
	public BigDecimal salvaUltrassom(BigDecimal codigoUsuario, BigDecimal codigoAlgoritmo, BigDecimal altura, BigDecimal largura, final MultipartFormDataInput inputData) throws IOException, SQLException {
		
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
		
		
		((UltrassomDAO)dao).persist(ultrassom);
		return ultrassom.getCodigo();
	}
	
	public void addListaProcessamento(BigDecimal codigo) {
		synchronized (listaProcessamento) {
			listaProcessamento.add(codigo);
		}
	}
	
	
	public void startThread() {
		if (!funcaoIniciada) {
			synchronized (LOCK) {
				if (!funcaoIniciada) {
					funcaoIniciada = true;
					new Thread("infinita") {
						public void run() {
							while(true) {
								BigDecimal codigoItem = null;
								synchronized (listaProcessamento) {
									if (listaProcessamento.size() > 0) {
										codigoItem = listaProcessamento.remove(0);
									}
								}
								if (codigoItem != null) {
									infinityCalc.get().calculosUpdate(codigoItem);
								}
							}
						}
					}.start();
				}
			}
		}
	}
	
	@Transactional
	public UltrassomCadastroDTO getUltrassomDTO(BigDecimal codigoUltrassom) {
		Ultrassom item = find(codigoUltrassom);
		return new UltrassomCadastroDTO(item);
	}
	
	@Transactional
	public void updateUltrassom(UltrassomCadastroDTO dto) {
		Ultrassom item = find(dto.getCodigo());
		item.update(dto);
	}
	
}



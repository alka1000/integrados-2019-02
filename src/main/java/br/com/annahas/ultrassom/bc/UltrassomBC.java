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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

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
	public void salvaUltrassom(BigDecimal codigoUsuario, final MultipartFormDataInput inputData) throws IOException, SQLException {
		
		
		String conteudo = UltrassomUtil.multipartToString(inputData);
		
		if (conteudo == null) {
			throw new DemoiselleRestException("Erro no arquivo enviado", Status.BAD_REQUEST.getStatusCode());
		}
		
//		System.out.println(conteudo);
		
		Ultrassom ultrassom = new Ultrassom();
		ultrassom.setCodigoUsuario(codigoUsuario);
		ultrassom.setSinal(conteudo);
		
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
									/**
									 * Fazer reconstrucao aqui.
									 * 
									 * 
									 */
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



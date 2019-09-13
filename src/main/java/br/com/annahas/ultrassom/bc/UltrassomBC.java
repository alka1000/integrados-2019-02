package br.com.annahas.ultrassom.bc;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.demoiselle.jee.crud.AbstractBusiness;
import org.demoiselle.jee.rest.exception.DemoiselleRestException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.annahas.ultrassom.dao.UltrassomDAO;
import br.com.annahas.ultrassom.dto.UltrassomDTO;
import br.com.annahas.ultrassom.entity.Ultrassom;
import br.com.annahas.ultrassom.util.UltrassomUtil;

public class UltrassomBC extends AbstractBusiness<Ultrassom, BigDecimal> {
	
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

	public void salvaUltrassom(BigDecimal codigoUsuario, final MultipartFormDataInput inputData) throws IOException, SQLException {
		
		
		String conteudo = UltrassomUtil.multipartToString(inputData);
		
		if (conteudo == null) {
			throw new DemoiselleRestException("Erro no arquivo enviado", Status.BAD_REQUEST.getStatusCode());
		}
		
		System.out.println(conteudo);
		
		Ultrassom ultrassom = new Ultrassom();
		ultrassom.setCodigoUsuario(codigoUsuario);
		
		/**
		 * Fazer reconstrucao aqui.
		 * 
		 * 
		 */
		
//		persist(ultrassom);
	}
	
	
	
}



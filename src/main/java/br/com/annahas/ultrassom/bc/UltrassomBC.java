package br.com.annahas.ultrassom.bc;


import java.math.BigDecimal;
import java.util.List;

import org.demoiselle.jee.crud.AbstractBusiness;

import br.com.annahas.ultrassom.dao.UltrassomDAO;
import br.com.annahas.ultrassom.entity.Ultrassom;

public class UltrassomBC extends AbstractBusiness<Ultrassom, BigDecimal> {
	
	public List<Ultrassom> findAll() {
		return ((UltrassomDAO) dao).findAll();
	}
	
}



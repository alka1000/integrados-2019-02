package br.com.annahas.ultrassom.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.demoiselle.jee.crud.AbstractDAO;

import br.com.annahas.ultrassom.entity.Ultrassom;

public class UltrassomDAO extends AbstractDAO<Ultrassom, BigDecimal> {

	@PersistenceContext(unitName = "ultrassomPU")
	private EntityManager manager;
	
	@Override
	protected EntityManager getEntityManager() {
		return this.manager;
	}
	
	public List<Ultrassom> findAll() {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Ultrassom> query = builder.createQuery(Ultrassom.class);
		Root<Ultrassom> from = query.from(Ultrassom.class);
		query.select(from);
		TypedQuery<Ultrassom> q = getEntityManager().createQuery(query);
		return q.getResultList();
	}

}


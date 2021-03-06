package br.edu.ifpb.caju.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.caju.model.Colegiado;

public class DAOColegiado extends DAO<Colegiado> {
	@SuppressWarnings("unchecked")
	public List<Colegiado> findAtivos() {
		Query q = getManager().createQuery("select c from Colegiado c where c.ativo = :ativo");
		q.setParameter("ativo", true);
		return q.getResultList();
	}
	
	public void updateAtivo() {
		Query q = getManager().createQuery("UPDATE Colegiado c SET c.ativo = :ativo, c.dataFim = :data WHERE c.ativo = 'true'");
		q.setParameter("ativo", false);
		q.setParameter("data", new Date());
		q.executeUpdate();
	}
	
	public Colegiado findById(int id){
		Query q = getManager().createQuery("select * from Colegiado c where c.id = :id");
		q.setParameter("id",id);
		return (Colegiado) q.getSingleResult();
	}

}

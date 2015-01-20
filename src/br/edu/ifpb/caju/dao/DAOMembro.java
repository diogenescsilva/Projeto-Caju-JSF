package br.edu.ifpb.caju.dao;

import java.util.List;

import javax.persistence.Query;

import br.edu.ifpb.caju.model.Membro;

public class DAOMembro extends DAO<Membro>{
	
	public Membro findByNome(String text) {
		Query q = getManager().createQuery("select m from Membro m where m.nome like :atributo" );
		q.setParameter("atributo", text);
		return (Membro) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Membro> findAllAtivos() {
		Query q = getManager().createQuery("select m from Membro m where m.ativo = :atributo" );
		q.setParameter("atributo", true);
		return q.getResultList();
	}


}

package br.edu.ifpb.caju.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifpb.caju.dao.DAO;
import br.edu.ifpb.caju.dao.DAOReuniao;
import br.edu.ifpb.caju.model.Reuniao;

@ManagedBean
@ViewScoped
public class SistemaReuniao implements SistemaReuniaoInterface{

	private DAOReuniao dao;
	
//	private Reuniao reuniao = new Reuniao();
	
	public SistemaReuniao() {
		this.dao = new DAOReuniao();
	}
	
	@Override
	public void cadastraReuniao(Reuniao reuniao) {
		DAO.open();
		DAO.begin();
		this.dao.persist(reuniao);
		DAO.commit();
		DAO.close();
		
	}

	@Override
	public void removeReuniao(Reuniao reuniao) {
		DAO.open();
		DAO.begin();
		this.dao.remove(reuniao);
		DAO.commit();
		DAO.close();
		
	}

	@Override
	public void editaReuniao(Reuniao reuniao) {
		DAO.open();
		DAO.begin();
		this.dao.merge(reuniao);
		DAO.commit();
		DAO.close();
		
	}

	
	
	@Override
	public List<Reuniao> getAllReunioes() {
		DAO.open();
		DAO.begin();
		List<Reuniao> reunioes = this.dao.findAll();
		DAO.commit();
		DAO.close();
		return reunioes;
	}


	
}

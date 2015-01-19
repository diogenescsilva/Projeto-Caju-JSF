package br.edu.ifpb.caju.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.edu.ifpb.caju.dao.DAO;
import br.edu.ifpb.caju.dao.DAOMembro;
import br.edu.ifpb.caju.dao.DAOPresidente;
import br.edu.ifpb.caju.model.Membro;
import br.edu.ifpb.caju.model.Presidente;


@ManagedBean
//@ViewScoped
public  class SistemaMembro implements SistemaMembroInterface{

	private static final String METODO_CRIPTOGRAFIA = "MD5";
	private DAOMembro dao;
	private DAOPresidente daoP;
	private Membro membro = new Membro();
	private Membro selectedMembro;
	private Presidente presidente;
	
	
	public SistemaMembro() {
		this.dao = new DAOMembro();
		this.daoP =  new DAOPresidente();
	}
	
	@Override
	public String criptografarSenha(String senha) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(METODO_CRIPTOGRAFIA);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	        md.update(senha.getBytes());
	        BigInteger hash = new BigInteger(1, md.digest());
	        return hash.toString(16);
	}

	@Override
	public Membro verificarLogin(String login, String senha) {
		String pass = new String(senha);
		
		Membro user = null;
		DAO.open();
		DAO.begin();
		try{
			user = (Presidente) daoP.findByLogin(login);
			DAO.close();
			if(((Presidente)user).getSenha().equals(criptografarSenha(pass))){
				
				return user;
				
			}
		}catch(Exception e){
			return null;
		}
		DAO.close();
		return null;
	}

	@Override
	public String cadastraMembro() {
		DAO.open();
		DAO.begin();
		if(membro.getPerfil().equals("Presidente")) {
			presidente = new Presidente();
			presidente.setNome(membro.getNome());
			presidente.setEmail(membro.getEmail());
			presidente.setTelefone(membro.getTelefone());
			presidente.setPerfil(membro.getPerfil());
			presidente.setAtivo(true);
			this.daoP.updateAtivo();
			this.daoP.persist(this.presidente);
			this.presidente = new Presidente();
			DAO.commit();
			DAO.close();
			return "index?faces-redirect=true";
			
		}
		
		
		//this.dao.persist(this.membro);
		this.dao.merge(this.membro);
		DAO.commit();
		DAO.close();
		this.membro = new Membro();
		return "index?faces-redirect=true";
		
	}

	@Override
	public void removeMembro(Membro membro) {
		DAO.open();
		DAO.begin();
		this.dao.remove(membro);
		DAO.commit();
		DAO.close();
		
	}

	@Override
	public String editaMembro(Membro membro) {
		DAO.open();
		DAO.begin();
		this.dao.merge(membro);
		DAO.commit();
		DAO.close();
		
		this.membro = new Membro();
		
		return "index?faces-redirect=true";
		
	}

	@Override
	public List<Membro> getAllMembros() {
		DAO.open();
		DAO.begin();
		List<Membro> r = this.dao.findAll();
		DAO.close();
		return r;
	}

	@Override
	public Membro getMembroPorNome(String text) {
		DAO.open();
		DAO.begin();
		Membro m = this.dao.findByNome(text);
		DAO.close();
		return m;
	}
	
	@Override
	public Membro getMembroPorId(long id) {
		DAO.open();
		DAO.begin();
		Membro m = null;
		DAO.close();
		return m;
	}

	public Presidente getMembroPorLogin(String login) {
		DAO.open();
		DAO.begin();
		Presidente m = this.daoP.findByLogin(login);
		DAO.close();
		return m;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	
//	public String setSelectedMembro(Membro membro) {
//		this.selectedMembro = membro;
//		return "editar.xhtml";//huehwuehwu
//	}
	
	public Membro  getSelectedMembro() {
		return this.selectedMembro;
	}
	

	
	public String editar(){
		//JSFUtils.flashScope().put("bean", this);
		
		return "/editar";
//		?faces-redirect=true

	}
	
	private static class JSFUtils {
		   public static Flash flashScope(){
			return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
		   }
	}
	
	public Boolean getIsPresidente() {
		if(membro.getPerfil().equals("Presidente")) {
			return true;
		}else{
			return false;
		}
	}

	public Presidente getPresidente() {
		return presidente;
	}

	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}

	
	

}

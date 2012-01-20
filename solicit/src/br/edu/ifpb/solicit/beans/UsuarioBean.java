package br.edu.ifpb.solicit.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Service;

import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.support.Md5Calculator;

@ManagedBean(name="usuarioBean")
@Service("usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;
	@ManagedProperty(value="#{md5Calculator}")
	private Md5Calculator md5Calculator;

	private String matricula;
	private String senha;
	private String novaSenha;

	private Servidor usuario;

	public UsuarioBean() {}

	public String login() {
		List<Servidor> loginResult = (List<Servidor>) basicJpaRepository.queryFind("select s from Servidor s where s.matricula = ?1 and s.md5 = ?2", new Object[] {this.matricula, md5Calculator.md5(this.senha)});

		if(loginResult.size() != 0) {
			this.usuario = loginResult.get(0);
			
			this.afterPropertiesSet();
			
			return "main";
		}
		else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, SuporteTexto.MATRICULA_OU_SENHA_ERRADOS, null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			this.afterPropertiesSet();
			return null;
		}
	}
	
	public String logoff() {
		try {
			this.usuario = null;
			this.afterPropertiesSet();
			return "logoff";
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
	
	public void mudarSenha() {
		String md5 = md5Calculator.md5(senha);
		
		List<Servidor> resultList = basicJpaRepository.queryFind("select s from Servidor s where s.md5 = ?1", new Object[] {md5});
		
		if(resultList.size() != 0) {
			usuario = resultList.get(0);
			usuario.setMd5(md5Calculator.md5(novaSenha));
			basicJpaRepository.merge(usuario);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", SuporteTexto.GRAVACAO_SUCESSO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			this.afterPropertiesSet();
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", SuporteTexto.SENHA_ANTIGA_ERRADA);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public Md5Calculator getMd5Calculator() {
		return md5Calculator;
	}
	public void setMd5Calculator(Md5Calculator md5Calculator) {
		this.md5Calculator = md5Calculator;
	}
	public Servidor getUsuario() {
		return usuario;
	}
	public void setUsuario(Servidor usuario) {
		this.usuario = usuario;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		this.matricula = "";
		this.senha = "";
		this.novaSenha = "";
	}
}

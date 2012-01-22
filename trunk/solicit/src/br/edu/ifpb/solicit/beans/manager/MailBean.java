package br.edu.ifpb.solicit.beans.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.edu.ifpb.solicit.beans.UsuarioBean;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.support.MailServer;

@ManagedBean(name="mailBean")
@RequestScoped
public class MailBean {
	@ManagedProperty(value="#{usuarioBean}")
	private UsuarioBean usuarioBean;
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;

	private Map<String, MailServer> servidoresEmail;

	private String host;
	private String senha = "";
	private String assunto = "";
	private String corpoMensagem = "";

	private List<Servidor> servidoresSetor;
	private List<String> destinatarios;

	public MailBean() {}

	public void enviar() {
		HtmlEmail email = new HtmlEmail();

		try {
			// ENDEREÇO SMTP DO HOST
			email.setHostName(servidoresEmail.get(host).getHost());
			// PORTA HOST
			email.setSmtpPort(servidoresEmail.get(host).getPort());
			// TLS
			email.setTLS(servidoresEmail.get(host).isSecure());
			// SSL
			email.setSSL(servidoresEmail.get(host).isSecure());
			// AUTENTICAÇÃO CLIENTE
			email.setAuthentication(this.usuarioBean.getUsuario().getEmail(), senha);
			// REMETENTE
			email.setFrom(this.usuarioBean.getUsuario().getEmail());
			// ASSUNTO
			email.setSubject(assunto);
			// CORPO DO E-MAIL
			email.setHtmlMsg(corpoMensagem);
			// CHARSET
			email.setCharset("UTF-8");

			// DESTINATARIOS
			for(String destinatario : destinatarios)
				email.addTo(destinatario);

			email.send();
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "E-mail(s) enviado(s) corretamente.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (EmailException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Verifique se sua senha está correta ou se o seu servidor de e-mail está disponível para mala direta com o administrador do sistema.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public Map<String, MailServer> getServidoresEmail() {
		return servidoresEmail;
	}
	public void setServidoresEmail(Map<String, MailServer> servidoresEmail) {
		this.servidoresEmail = servidoresEmail;
	}
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}
	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getCorpoMensagem() {
		return corpoMensagem;
	}
	public void setCorpoMensagem(String corpoMensagem) {
		this.corpoMensagem = corpoMensagem;
	}
	public List<Servidor> getServidoresSetor() {
		return servidoresSetor;
	}
	public void setServidoresSetor(List<Servidor> servidoresSetor) {
		this.servidoresSetor = servidoresSetor;
	}
	public List<String> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<String> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public void configureServers() {
		this.servidoresEmail = new HashMap<String, MailServer>();

		// SERVIDORES DE E-MAIL
		this.servidoresEmail.put("gmail.com", new MailServer("smtp.gmail.com", 587, true));
		this.servidoresEmail.put("hotmail.com", new MailServer("smtp.live.com", 25, true));
		this.servidoresEmail.put("live.com", new MailServer("smtp.live.com", 25, true));
		this.servidoresEmail.put("yahoo.com.br", new MailServer("smtp.mail.yahoo.com.br", 25, true));
		this.servidoresEmail.put("yahoo.com", new MailServer("smtp.mail.yahoo.com", 25, true));
	}

	@PostConstruct
	public void afterPropertiesSet() {
		this.configureServers();
		this.host = (this.usuarioBean.getUsuario().getEmail().split("@"))[1].toLowerCase();
		this.servidoresSetor = basicJpaRepository.queryFind("select s from Servidor s where s.setor = ?1 order by s.nome", new Object[] {this.usuarioBean.getChefeSetor()});
	}
}
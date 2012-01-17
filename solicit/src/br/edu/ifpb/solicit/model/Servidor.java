package br.edu.ifpb.solicit.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Servidor {
	@Id
	private String matricula;
	private String nome;
	private String cpf;
	private String email;
	private String md5;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="servidor")
	private List<Solicitacao> solicitacoes;
	@OneToOne
	private Setor setor;
	private int nivelAcesso;

	public Servidor() {}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}
	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public int getNivelAcesso() {
		return nivelAcesso;
	}
	
	/**
	 * @param nivelAcesso
	 * NÍVEL 0 => USUARIO
	 * NÍVEL 1 => DOCENTE
	 * NÍVEL 2 => ADMINISTRADOR
	 */
	public void setNivelAcesso(int nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
	
	public void adicionarSolicitacao(Solicitacao solicitacao) {
		if(solicitacoes == null)
			solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(solicitacao);
	}
}

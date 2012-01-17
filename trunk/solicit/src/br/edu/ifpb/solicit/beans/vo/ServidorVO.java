package br.edu.ifpb.solicit.beans.vo;

import java.io.Serializable;
import java.util.List;

public class ServidorVO implements Serializable {
	private String matricula = "";
	private String nome = "";
	private String cpf = "";
	private String email = "";
	private String senha = "";
	private List<String> solicitacoes = null;
	private String setorId = "";
	private int nivelAcesso = 0;

	public ServidorVO() {}

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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<String> getSolicitacoes() {
		return solicitacoes;
	}
	public void setSolicitacoes(List<String> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
	public String getSetorId() {
		return setorId;
	}
	public void setSetorId(String setorId) {
		this.setorId = setorId;
	}
	public int getNivelAcesso() {
		return nivelAcesso;
	}
	public void setNivelAcesso(int nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
}

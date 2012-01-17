package br.edu.ifpb.solicit.beans.vo;

import java.io.Serializable;

public class SolicitacaoVO implements Serializable {
	private String servidor = "";
	private String matriculaServidor = "";
	private String tipoSolicitacaoId = "";
	private String descricao = "";
	private String setorId = "";
	private String aval = "";

	public SolicitacaoVO() {}

	public String getServidor() {
		return servidor;
	}
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}
	public String getMatriculaServidor() {
		return matriculaServidor;
	}
	public void setMatriculaServidor(String matriculaServidor) {
		this.matriculaServidor = matriculaServidor;
	}
	public String getTipoSolicitacaoId() {
		return tipoSolicitacaoId;
	}
	public void setTipoSolicitacaoId(String tipoSolicitacaoId) {
		this.tipoSolicitacaoId = tipoSolicitacaoId;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSetorId() {
		return setorId;
	}
	public void setSetorId(String setorId) {
		this.setorId = setorId;
	}
	public String getAval() {
		return aval;
	}
	public void setAval(String aval) {
		this.aval = aval;
	}
}
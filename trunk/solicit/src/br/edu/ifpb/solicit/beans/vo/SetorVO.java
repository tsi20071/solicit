package br.edu.ifpb.solicit.beans.vo;

import java.io.Serializable;

public class SetorVO implements Serializable {
	private String descricao = "";
	private String chefeId = "";

	public SetorVO() {}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getChefeId() {
		return chefeId;
	}
	public void setChefeId(String chefeId) {
		this.chefeId = chefeId;
	}
}

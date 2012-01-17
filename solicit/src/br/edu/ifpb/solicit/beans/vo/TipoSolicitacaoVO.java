package br.edu.ifpb.solicit.beans.vo;

import java.io.Serializable;

public class TipoSolicitacaoVO implements Serializable {
	private String descricao = "";

	public TipoSolicitacaoVO() {}

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

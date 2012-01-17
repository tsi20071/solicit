package br.edu.ifpb.solicit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoSolicitacao {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String descricao;
	
	public TipoSolicitacao() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

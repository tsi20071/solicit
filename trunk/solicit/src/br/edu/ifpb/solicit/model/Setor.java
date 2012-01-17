package br.edu.ifpb.solicit.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Setor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String descricao;
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
	private Servidor chefe;

	public Setor() {}

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
	public Servidor getChefe() {
		return chefe;
	}
	public void setChefe(Servidor chefe) {
		this.chefe = chefe;
	}
}

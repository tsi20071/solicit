package br.edu.ifpb.solicit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Solicitacao {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Date dataCadastro;
	@OneToOne
	private TipoSolicitacao tipoSolicitacao;
	@Lob
	private String descricao;
	@OneToOne
	private Setor setor;
	@ManyToOne
	private Servidor servidor;
	@Lob
	private String aval;
	
	public Solicitacao() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}
	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public Servidor getServidor() {
		return servidor;
	}
	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
	public String getAval() {
		return aval;
	}
	public void setAval(String aval) {
		this.aval = aval;
	}
}

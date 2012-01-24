package br.edu.ifpb.solicit.beans.vo;

public class RelatorioVO {
	private int setorId;
	private String setorDescricao;
	private Long solicitacoesHomologadas;
	private Long solicitacoesEmAberto;

	public RelatorioVO() {}

	public RelatorioVO(int setorId, String setorDescricao, Long solicitacoesHomologadas, Long solicitacoesEmAberto) {
		this();
		this.setorId = setorId;
		this.setorDescricao = setorDescricao;
		this.solicitacoesHomologadas = solicitacoesHomologadas;
		this.solicitacoesEmAberto = solicitacoesEmAberto;
	}

	public int getSetorId() {
		return setorId;
	}
	public void setSetorId(int setorId) {
		this.setorId = setorId;
	}
	public String getSetorDescricao() {
		return setorDescricao;
	}
	public void setSetorDescricao(String setorDescricao) {
		this.setorDescricao = setorDescricao;
	}
	public Long getSolicitacoesHomologadas() {
		return solicitacoesHomologadas;
	}
	public void setSolicitacoesHomologadas(Long solicitacoesHomologadas) {
		this.solicitacoesHomologadas = solicitacoesHomologadas;
	}
	public Long getSolicitacoesEmAberto() {
		return solicitacoesEmAberto;
	}
	public void setSolicitacoesEmAberto(Long solicitacoesEmAberto) {
		this.solicitacoesEmAberto = solicitacoesEmAberto;
	}
}
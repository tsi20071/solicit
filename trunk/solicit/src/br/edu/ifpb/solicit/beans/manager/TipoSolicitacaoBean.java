package br.edu.ifpb.solicit.beans.manager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpb.solicit.beans.SuporteTexto;
import br.edu.ifpb.solicit.beans.vo.TipoSolicitacaoVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.TipoSolicitacao;

@ManagedBean(name="tipoSolicitacaoBean")
@RequestScoped
public class TipoSolicitacaoBean implements Serializable {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;

	private TipoSolicitacaoVO itemVO;
	private TipoSolicitacao item;

	public TipoSolicitacaoBean() {}

	public void gravar() {
		try {
			item = new TipoSolicitacao();
			processarVO();

			basicJpaRepository.persist(item);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", SuporteTexto.GRAVACAO_SUCESSO);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			this.afterPropertiesSet();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void processarVO() throws Exception {
		item.setDescricao(itemVO.getDescricao());
	}



	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public TipoSolicitacao getItem() {
		return item;
	}
	public void setItem(TipoSolicitacao item) {
		this.item = item;
	}
	public TipoSolicitacaoVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(TipoSolicitacaoVO itemVO) {
		this.itemVO = itemVO;
	}
	
	

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new TipoSolicitacaoVO();
	}
}

package br.edu.ifpb.solicit.beans.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpb.solicit.beans.SuporteTexto;
import br.edu.ifpb.solicit.beans.vo.SetorVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.model.Setor;

@ManagedBean(name="setorBean")
@RequestScoped
public class SetorBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;

	private SetorVO itemVO;
	private Setor item;

	private List<Servidor> itens;
	
	private List<Servidor> servidores;

	public SetorBean() {}

	public void gravar() {
		try {
			item = new Setor();
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
		
		if (basicJpaRepository.queryFind("select s from Setor s where s.descricao like ?1", new Object[] {itemVO.getDescricao()}).size() > 0)
			throw new RuntimeException("Setor já existe.");
	}



	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public Setor getItem() {
		return item;
	}
	public void setItem(Setor item) {
		this.item = item;
	}
	public SetorVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(SetorVO itemVO) {
		this.itemVO = itemVO;
	}
	public List<Servidor> getItens() {
		return itens;
	}
	public void setItens(List<Servidor> itens) {
		this.itens = itens;
	}
	public List<Servidor> getServidores() {
		return servidores;
	}
	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new SetorVO();
		itens = basicJpaRepository.queryFind("select s from Setor s where not s.id = 1 order by s.descricao");
		servidores = basicJpaRepository.queryFind("select s from Servidor s where s not in (select t.chefe from Setor t)");
	}
}

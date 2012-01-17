package br.edu.ifpb.solicit.beans.client;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.solicit.beans.SuporteTexto;
import br.edu.ifpb.solicit.beans.UsuarioBean;
import br.edu.ifpb.solicit.beans.vo.SolicitacaoVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Solicitacao;

@ManagedBean(name="solicitacaoSetorBean")
@ViewScoped
public class SolicitacaoSetorBean implements Serializable {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;
	@ManagedProperty(value="#{usuarioBean}")
	private UsuarioBean usuarioBean;

	private SolicitacaoVO itemVO;
	private Solicitacao item;

	private List<Solicitacao> itens;

	public SolicitacaoSetorBean() {}

	@Transactional
	public void atualizar() {
		try {
			processarVO();

			basicJpaRepository.merge(item);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", SuporteTexto.GRAVACAO_SUCESSO);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			this.afterPropertiesSet();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void processarVO() throws Exception {
		item.setAval(itemVO.getAval());
	}

	public void preencherVO() {
		itemVO.setServidor(item.getServidor().getNome());
		itemVO.setMatriculaServidor(item.getServidor().getMatricula());
		itemVO.setDescricao(item.getDescricao());
		itemVO.setSetorId(String.valueOf(item.getId()));
		itemVO.setTipoSolicitacaoId(String.valueOf(item.getTipoSolicitacao()));
		itemVO.setAval(item.getAval());
	}

	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}

	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public SolicitacaoVO getItemVO() {
		return itemVO;
	}

	public void setItemVO(SolicitacaoVO itemVO) {
		this.itemVO = itemVO;
	}

	public Solicitacao getItem() {
		return item;
	}

	public void setItem(Solicitacao item) {
		this.item = item;
	}

	public List<Solicitacao> getItens() {
		return itens;
	}

	public void setItens(List<Solicitacao> itens) {
		this.itens = itens;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new SolicitacaoVO();

		// Pega as solicitações do setor do usuário da sessão (no caso, o chefe)
		itens = basicJpaRepository.queryFind("select s from Solicitacao s where s.setor = ?1", new Object[] {usuarioBean.getUsuario().getSetor()});
	}
}

package br.edu.ifpb.solicit.beans.client;

import java.util.Date;
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
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.model.Setor;
import br.edu.ifpb.solicit.model.Solicitacao;
import br.edu.ifpb.solicit.model.TipoSolicitacao;

@ManagedBean(name="solicitacaoBean")
@ViewScoped
public class SolicitacaoBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;
	@ManagedProperty(value="#{usuarioBean}")
	private UsuarioBean usuarioBean;

	private SolicitacaoVO itemVO;
	private Solicitacao item;
	
	private List<Solicitacao> itens;
	
	private List<TipoSolicitacao> tiposSolicitacoes;
	private List<Setor> setores;

	public SolicitacaoBean() {}

	@Transactional
	public void gravar() {
		try {
			Servidor usuario = basicJpaRepository.find(Servidor.class, usuarioBean.getUsuario().getMatricula());
			
			item = new Solicitacao();
			processarVO();
			
			item.setServidor(usuario);
			
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
		item.setTipoSolicitacao(basicJpaRepository.find(TipoSolicitacao.class, Integer.parseInt(itemVO.getTipoSolicitacaoId())));
		item.setDescricao(itemVO.getDescricao());
		item.setDataCadastro(new Date());
		item.setSetor(basicJpaRepository.find(Setor.class, Integer.parseInt(itemVO.getSetorId())));
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
	
	public List<TipoSolicitacao> getTiposSolicitacoes() {
		return tiposSolicitacoes;
	}
	
	public void setTiposSolicitacoes(List<TipoSolicitacao> tiposSolicitacoes) {
		this.tiposSolicitacoes = tiposSolicitacoes;
	}
	
	public List<Setor> getSetores() {
		return setores;
	}
	
	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}
	
	public List<Solicitacao> getItens() {
		return itens;
	}
	
	public void setItens(List<Solicitacao> itens) {
		this.itens = itens;
	}
	
	public void atualizarItens() {
		List<Solicitacao> itensNovos = basicJpaRepository.queryFind("select s from Solicitacao s where s.servidor = ?1 order by s.id desc", new Object[] {usuarioBean.getUsuario()});
		
		boolean flag = false;
		
		for(int i=0; i<itens.size(); i++) {
			if(itens.get(i).getAval().length() != itensNovos.get(i).getAval().length()) {
				flag = true;
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atualização!", "A solicitação " + itensNovos.get(i).getId() + " foi homologada.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				itens = itensNovos;
				break;
			}
		}	
	}

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new SolicitacaoVO();
		tiposSolicitacoes = basicJpaRepository.queryFind("select ts from TipoSolicitacao ts order by ts.descricao asc");
		setores = basicJpaRepository.queryFind("select s from Setor s order by s.descricao asc");
		
		itens = basicJpaRepository.queryFind("select s from Solicitacao s where s.servidor = ?1 order by s.id desc", new Object[] {usuarioBean.getUsuario()});
	}
}

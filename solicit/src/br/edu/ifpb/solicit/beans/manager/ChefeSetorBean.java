package br.edu.ifpb.solicit.beans.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpb.solicit.beans.SuporteTexto;
import br.edu.ifpb.solicit.beans.vo.ChefeSetorVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.model.Setor;

@ManagedBean(name="chefeSetorBean")
@RequestScoped
public class ChefeSetorBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;

	private ChefeSetorVO itemVO;
	private Setor item;

	private List<Servidor> setores;
	private List<Servidor> servidores;

	private String informacaoChefe;

	public ChefeSetorBean() {}

	public void gravar() {
		try {
			processarVO();

			basicJpaRepository.merge(item);

			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", SuporteTexto.GRAVACAO_SUCESSO + this.informacaoChefe);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			this.afterPropertiesSet();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void processarVO() throws Exception {
		item = basicJpaRepository.find(Setor.class, Integer.parseInt(itemVO.getSetorId()));
		Servidor chefe = basicJpaRepository.find(Servidor.class, itemVO.getChefeId());

		if(item.getChefe() == null) {
			chefe.setNivelAcesso(1);
			item.setChefe(chefe);

			this.avaliarChefiaSetorAnterior(chefe);

			this.informacaoChefe = " " + chefe.getNome() + " é o novo chefe de " + item.getDescricao() + " e está agora com nível de acesso superior.";
		}
		else if(!item.getChefe().getMatricula().equals(basicJpaRepository.find(Servidor.class, itemVO.getChefeId()).getMatricula())) {
			Servidor chefeAnterior = item.getChefe();
			chefeAnterior.setNivelAcesso(0);
			chefeAnterior = basicJpaRepository.merge(chefeAnterior);

			chefe.setNivelAcesso(1);
			item.setChefe(chefe);

			this.avaliarChefiaSetorAnterior(chefe);

			this.informacaoChefe = " " + chefeAnterior.getNome() + " deixou de ser chefe de " + item.getDescricao() + " e está agora com nível de acesso de usuário comum.";
		}
		else {
			throw new RuntimeException(item.getChefe().getNome() + " já é chefe de " + item.getDescricao() + ".");
		}
	}

	// SETA NULL EM CHEFE DE UM SETOR ANTERIOR PARA EVITAR CHEFE EM 2 SETORES
	public void avaliarChefiaSetorAnterior(Servidor chefe) {
		List<Setor> listaSetorAnterior = basicJpaRepository.queryFind("select s from Setor s where s.chefe = ?1", new Object[] {chefe});
		if(listaSetorAnterior.size() != 0) {
			Setor setorAnterior = listaSetorAnterior.get(0);
			setorAnterior.setChefe(null);
			basicJpaRepository.merge(setorAnterior);
		}
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
	public ChefeSetorVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ChefeSetorVO itemVO) {
		this.itemVO = itemVO;
	}
	public List<Servidor> getSetores() {
		return setores;
	}
	public void setSetores(List<Servidor> setores) {
		this.setores = setores;
	}
	public List<Servidor> getServidores() {
		return servidores;
	}
	public void setServidores(List<Servidor> servidores) {
		this.servidores = servidores;
	}
	public String getInformacaoChefe() {
		return informacaoChefe;
	}
	public void setInformacaoChefe(String informacaoChefe) {
		this.informacaoChefe = informacaoChefe;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new ChefeSetorVO();

		setores = basicJpaRepository.queryFind("select s from Setor s order by s.descricao");
		servidores = basicJpaRepository.queryFind("select s from Servidor s where not s.nivelAcesso = 2");
	}
}

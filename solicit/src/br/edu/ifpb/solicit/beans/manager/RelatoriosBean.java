package br.edu.ifpb.solicit.beans.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import br.edu.ifpb.solicit.beans.vo.RelatorioVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Setor;

@ManagedBean(name="relatoriosBean")
@ViewScoped
public class RelatoriosBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;

	private PieChartModel pizzaGeral;
	private PieChartModel pizzaSetor;

	private int indiceSetor;
	private List<Setor> setores;
	private Map<String,RelatorioVO> informacoesSetores;

	public RelatoriosBean() {}

	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}

	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}

	public PieChartModel getPizzaGeral() {
		return pizzaGeral;
	}

	public void setPizzaGeral(PieChartModel pizzaGeral) {
		this.pizzaGeral = pizzaGeral;
	}

	public PieChartModel getPizzaSetor() {
		return pizzaSetor;
	}

	public void setPizzaSetor(PieChartModel pizzaSetor) {
		this.pizzaSetor = pizzaSetor;
	}

	public int getIndiceSetor() {
		return indiceSetor;
	}
	
	public void setIndiceSetor(int indiceSetor) {
		this.indiceSetor = indiceSetor;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public Map<String, RelatorioVO> getInformacoesSetores() {
		return informacoesSetores;
	}

	public void setInformacoesSetores(Map<String, RelatorioVO> informacoesSetores) {
		this.informacoesSetores = informacoesSetores;
	}

	public void configurarPizzaGeral() {
		this.pizzaGeral = new PieChartModel();

		Long solicitacoesHomologadas = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where length(s.aval) > 1").get(0);
		Long solicitacoesEmAberto = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where length(s.aval) = 0").get(0);

		this.pizzaGeral.set("Homologadas", solicitacoesHomologadas);
		this.pizzaGeral.set("Em aberto", solicitacoesEmAberto);
	}

	public void configurarInformacoesSetores() {
		informacoesSetores = new HashMap<String, RelatorioVO>();

		for(int i=0; i<setores.size(); i++) {
			Long solicitacoesHomologadas = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where s.setor = ?1 and length(s.aval) > 1", new Object[] {setores.get(i)}).get(0);
			Long solicitacoesEmAberto = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where s.setor = ?1 and length(s.aval) = 0", new Object[] {setores.get(i)}).get(0);

			informacoesSetores.put(setores.get(i).getDescricao(), new RelatorioVO(setores.get(i).getId(), setores.get(i).getDescricao(), solicitacoesHomologadas, solicitacoesEmAberto));
		}
	}

	public void configurarPizzaSetor() {
		pizzaSetor = new PieChartModel();
		
		RelatorioVO relatorioVO = informacoesSetores.get(basicJpaRepository.find(Setor.class, indiceSetor).getDescricao());

		this.pizzaSetor.set("Homologadas", relatorioVO.getSolicitacoesHomologadas());
		this.pizzaSetor.set("Em aberto", relatorioVO.getSolicitacoesEmAberto());
	}
	
	public List<RelatorioVO> getInformacoesSetoresAsList() {
		return new ArrayList<RelatorioVO>(informacoesSetores.values());
	}

	@PostConstruct
	public void afterPropertiesSet() {
		this.configurarPizzaGeral();
		
		this.setores = basicJpaRepository.queryFind("select s from Setor s order by s.descricao");
		this.indiceSetor = setores.get(0).getId();
		
		this.configurarInformacoesSetores();
		this.configurarPizzaSetor();
	}
}

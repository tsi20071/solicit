package br.edu.ifpb.solicit.beans.manager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Setor;

@ManagedBean(name="relatoriosBean")
@RequestScoped
public class RelatoriosBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;
	
	private PieChartModel generalPie;
	private PieChartModel departmentPie;

	public RelatoriosBean() {}

	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public PieChartModel getGeneralPie() {
		return generalPie;
	}
	public void setGeneralPie(PieChartModel generalPie) {
		this.generalPie = generalPie;
	}
	public PieChartModel getDepartmentPie() {
		return departmentPie;
	}
	public void setDepartmentPie(PieChartModel departmentPie) {
		this.departmentPie = departmentPie;
	}
	
	public void configureGeneralPie() {
		Long solicitacoesHomologadas = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where length(s.aval) > 1").get(0);
		Long solicitacoesEmAberto = (Long) basicJpaRepository.queryFind("select count(s) from Solicitacao s where length(s.aval) = 0").get(0);
		
		this.generalPie = new PieChartModel();
		
		this.generalPie.set("Homologadas", solicitacoesHomologadas);
		this.generalPie.set("Em aberto", solicitacoesEmAberto);
	}
	
	public void configureDepartmentPie() {
		Long setores = basicJpaRepository.count(Setor.class);
		
		this.departmentPie = new PieChartModel();
		
		for(int i=1; i<=setores; i++) {
			Setor setor = basicJpaRepository.find(Setor.class, i);
			this.departmentPie.set(setor.getDescricao(), (Long) basicJpaRepository
					.queryFind("select count(s) from Solicitacao s where s.setor = ?1", new Object[] {setor}).get(0));
		}
	}
	
	@PostConstruct
	public void afterPropertiesSet() {
		this.configureGeneralPie();
		this.configureDepartmentPie();
	}
}

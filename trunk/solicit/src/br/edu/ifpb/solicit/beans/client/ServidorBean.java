package br.edu.ifpb.solicit.beans.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpb.solicit.beans.SuporteTexto;
import br.edu.ifpb.solicit.beans.vo.ServidorVO;
import br.edu.ifpb.solicit.database.api.BasicJpaRepository;
import br.edu.ifpb.solicit.model.Servidor;
import br.edu.ifpb.solicit.model.Setor;
import br.edu.ifpb.solicit.support.Md5Calculator;

@ManagedBean(name="servidorBean")
@RequestScoped
public class ServidorBean {
	@ManagedProperty(value="#{repositoryService}")
	private BasicJpaRepository basicJpaRepository;
	@ManagedProperty(value="#{md5Calculator}")
	private Md5Calculator md5Calculator;

	private ServidorVO itemVO;
	private Servidor item;

	private List<Setor> setores;

	public ServidorBean() {}

	public void gravar() {
		try {
			item = new Servidor();
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
		Servidor servidor = basicJpaRepository.find(Servidor.class, itemVO.getMatricula());
		if (servidor != null)
			throw new RuntimeException("Matrícula " + servidor.getMatricula() + " já cadastrada para " + servidor.getNome() + ". Verifique se o número da matrícula está correto.");
		
		item.setMatricula(itemVO.getMatricula());
		item.setNome(itemVO.getNome());
		item.setCpf(itemVO.getCpf());
		item.setEmail(itemVO.getEmail());
		item.setMd5(md5Calculator.md5(itemVO.getSenha()));
		item.setSetor(basicJpaRepository.find(Setor.class, Integer.parseInt((itemVO.getSetorId()))));
	}

	public BasicJpaRepository getBasicJpaRepository() {
		return basicJpaRepository;
	}
	public void setBasicJpaRepository(BasicJpaRepository basicJpaRepository) {
		this.basicJpaRepository = basicJpaRepository;
	}
	public Servidor getItem() {
		return item;
	}
	public void setItem(Servidor item) {
		this.item = item;
	}
	public ServidorVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ServidorVO itemVO) {
		this.itemVO = itemVO;
	}
	public List<Setor> getSetores() {
		return setores;
	}
	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}
	public Md5Calculator getMd5Calculator() {
		return md5Calculator;
	}
	public void setMd5Calculator(Md5Calculator md5Calculator) {
		this.md5Calculator = md5Calculator;
	}

	@PostConstruct
	public void afterPropertiesSet() {
		itemVO = new ServidorVO();
		setores = basicJpaRepository.queryFind("select s from Setor s where not s.id = 1 order by s.descricao asc");
	}
}
package control;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import model.Contrato;
import model.DAOContrato;
import model.DAOImovel;
import model.DAOInquilino;
import model.IDAO;
import model.Imovel;
import model.Inquilino;
import model.ModelException;
import view.IViewer;
import view.IViewerSalvaContrato;
import view.JanelaContratos;
import view.JanelaExcluirContrato;
import view.JanelaSalvaContrato;

public class CtrlManterContratos implements ICtrlManterContratos {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaContrato jContrato;
	
	private Contrato contratoAtual;
	
	private IDAO<Contrato> dao = DAOContrato.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterContratos(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaContratos(this);
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if(!this.emExecucao)
			return false;

		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterContrato();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		
		IDAO<Imovel> daoImovel = DAOImovel.getSingleton();
		Set<Imovel> imoveis = new TreeSet<Imovel>();
				for(Imovel i: daoImovel.getListaObjs()){
					if(i.isStatus()==true)
						imoveis.add(i);
				}
		
		IDAO<Inquilino> daoInquilino = DAOInquilino.getSingleton();
		Set<Inquilino> inquilinos = daoInquilino.getListaObjs();
		
		try {
			this.jContrato = new JanelaSalvaContrato(this,imoveis,inquilinos);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean incluir(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inqui) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Contrato novo = new Contrato(duracao, dataInicio, percentProprietario, valorAluguel,imo,inqui);
		imo.setStatus(false);
		novo.disponivel(false);
		
		dao.salvar(novo);

		this.jContrato.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jContrato.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.contratoAtual = dao.recuperar(pos);
	
		Set<Imovel> imoveis = new TreeSet<Imovel>();
		imoveis.add(this.contratoAtual.getImovel());
		Set<Inquilino> inquilinos = new TreeSet<Inquilino>();
		inquilinos.add(this.contratoAtual.getInquilino());
		
		try {
			this.jContrato = new JanelaSalvaContrato(this,imoveis,inquilinos);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jContrato.atualizarCampos(
				this.contratoAtual.getDuracao(), 
				this.contratoAtual.getDataInicio(), 
				this.contratoAtual.getPercentProprietario(), 
				this.contratoAtual.getValorAluguel(), 
				this.contratoAtual.getImovel(), 
				this.contratoAtual.getInquilino());
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jContrato.setVisible(false);
			this.contratoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inqui) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.contratoAtual.setDuracao(duracao);
		this.contratoAtual.setDataInicio(dataInicio);
		this.contratoAtual.setPercentProprietario(percentProprietario);
		this.contratoAtual.setValorAluguel(valorAluguel);
		this.contratoAtual.setImovel(imo);
		this.contratoAtual.setInquilino(inqui);

		dao.atualizar(this.contratoAtual);

		this.jContrato.setVisible(false);
		this.atualizarInterface();
		this.contratoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.contratoAtual = dao.recuperar(pos);
		new JanelaExcluirContrato(this, this.contratoAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.contratoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.contratoAtual);
		this.contratoAtual.setInquilino(null);
		this.contratoAtual.disponivel(true);
		this.contratoAtual.setImovel(null);

		this.atualizarInterface();
		this.contratoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Contrato contrato = dao.recuperar(i);
			this.jCadastro.incluirLinha(contrato);
		}
	}

}

package control;

import java.util.Date;

import model.Contrato;
import model.DAOContrato;
import model.IDAO;
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
		this.jContrato = new JanelaSalvaContrato(this);
		return true;
	}
	
	@Override
	public boolean incluir(int duracao, Date dataInicio, int percentProprietario, float valorAluguel) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Contrato novo = new Contrato(duracao, dataInicio, percentProprietario, valorAluguel);

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
		this.jContrato = new JanelaSalvaContrato(this);
		this.jContrato.atualizarCampos(this.contratoAtual.getDuracao(), this.contratoAtual.getDataInicio(), this.contratoAtual.getPercentProprietario(), this.contratoAtual.getValorAluguel());
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

	public boolean alterar(int duracao, Date dataInicio, int percentProprietario, float valorAluguel) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.contratoAtual.setDuracao(duracao);
		this.contratoAtual.setDataInicio(dataInicio);
		this.contratoAtual.setPercentProprietario(percentProprietario);
		this.contratoAtual.setValorAluguel(valorAluguel);

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

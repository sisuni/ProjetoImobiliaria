package control;

import model.Boleto;
import model.Contrato;
import model.DAOBoleto;
import model.IDAO;
import model.ModelException;
import view.IViewer;
import view.IViewerSalvaBoleto;
import view.JanelaBoletos;
import view.JanelaExcluirBoleto;
import view.JanelaSalvaBoleto;

public class CtrlManterBoletos implements ICtrlManterBoletos {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaBoleto jBoleto;
	
	private Boleto boletoAtual;
	
	private IDAO<Boleto> dao = DAOBoleto.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterBoletos(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaBoletos(this);
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
		this.ctrlPrg.terminarCasoDeUsoManterBoleto();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jBoleto = new JanelaSalvaBoleto(this);
		return true;
	}
	
	@Override
	public boolean incluir(int dataVencimento, Contrato contrato) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Boleto novo = new Boleto(dataVencimento, contrato);

		dao.salvar(novo);

		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jBoleto.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.boletoAtual = dao.recuperar(pos);
		this.jBoleto = new JanelaSalvaBoleto(this);
		this.jBoleto.atualizarCampos(this.boletoAtual.getDataVencimento(), this.boletoAtual.getContrato());
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jBoleto.setVisible(false);
			this.boletoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean alterar(int dataVencimento, Contrato contrato) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.boletoAtual.setDataVencimento(dataVencimento);
		this.boletoAtual.setContrato(contrato);

		dao.atualizar(this.boletoAtual);

		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.boletoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.boletoAtual = dao.recuperar(pos);
		new JanelaExcluirBoleto(this, this.boletoAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.boletoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.boletoAtual);

		this.atualizarInterface();
		this.boletoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Boleto boleto = dao.recuperar(i);
			this.jCadastro.incluirLinha(boleto);
		}
	}	

}

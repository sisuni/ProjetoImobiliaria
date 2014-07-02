package control;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import model.Boleto;
import model.Cliente;
import model.Cobra;
import model.Contrato;
import model.DAOBoleto;
import model.DAOContrato;
import model.IDAO;
import model.ModelException;
import model.Taxa;
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

	private ICtrlManterCobrancas ctrlCobra;

	private IViewer jCadastro;

	private IViewerSalvaBoleto jBoleto;

	private Boleto boletoAtual;

	private IDAO<Boleto> dao = DAOBoleto.getSingleton();

	private IDAO<Contrato> daoContrato = DAOContrato.getSingleton();

	private boolean emExecucao;

	private Operacao operacao;

	private float valorTotal = 0;

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
		if (this.emExecucao)
			return false;

		this.jCadastro = new JanelaBoletos(this);
		// this.dao.getListaObjs().removeAll(this.dao.getListaObjs());
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if (!this.emExecucao)
			return false;

		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterBoleto();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;

		Set<Contrato> contratos = daoContrato.getListaObjs();

		try {
			this.jBoleto = new JanelaSalvaBoleto(this, contratos,false);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.iniciarCasoDeUsoManterCobrancas();
		this.jBoleto.selecionaContrato();
		return true;
	}

	@Override
	public boolean incluir(Date dataVencimento, Contrato contrato)
			throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Boleto novo = new Boleto(dataVencimento, contrato);

		for (Cobra c : this.ctrlCobra.getCobrancas()) {
			if (c.getBoleto() == null) {
				novo.addCobranca(c);
			}
		}

		dao.salvar(novo);
		this.terminarCasoDeUsoManterCobrancas();
		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if (this.operacao == Operacao.INCLUSAO) {
			this.jBoleto.setVisible(false);
			this.ctrlCobra = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.boletoAtual = dao.recuperar(pos);

		Set<Contrato> contratos = daoContrato.getListaObjs();
		try {
			this.jBoleto = new JanelaSalvaBoleto(this, contratos,true);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jBoleto.atualizarCampos(this.boletoAtual.getDataVencimento(),
				this.boletoAtual.getContrato(),
				this.boletoAtual.getValorTotal());
		this.iniciarCasoDeUsoManterCobrancas();
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if (this.operacao == Operacao.ALTERACAO) {
			this.jBoleto.setVisible(false);
			this.boletoAtual = null;
			this.ctrlCobra = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean alterar(Date dataVencimento) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO)
			return false;

		this.boletoAtual.setDataVencimento(dataVencimento);

		dao.atualizar(this.boletoAtual);

		this.terminarCasoDeUsoManterCobrancas();
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

		this.iniciarExclusaoCobrancas();
		this.operacao = Operacao.EXCLUSAO;
		this.boletoAtual = dao.recuperar(pos);
		new JanelaExcluirBoleto(this, this.boletoAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if (this.operacao == Operacao.EXCLUSAO) {

			this.boletoAtual = null;
			this.ctrlCobra = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO)
			return false;

		if (!(this.ctrlCobra.getCobrancas().isEmpty())) {
			for (Cobra c : this.ctrlCobra.getCobrancas()) {
				if (c.getBoleto() == this.boletoAtual) {
					c.setBoleto(null);
					this.boletoAtual.removeCobranca(c);
				}
			}
		}
		dao.remover(this.boletoAtual);
		this.boletoAtual.setContrato(null);
		this.atualizarInterface();
		this.boletoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		if (!(dao.getListaObjs().isEmpty())) {
			for (int i = 0; i < dao.getNumObjs(); i++) {
				Boleto boleto = dao.recuperar(i);
				this.jCadastro.incluirLinha(boleto);
			}
		}
	}

	/********** Métodos para Controlar Cobrancas *********/
	@Override
	public boolean iniciarCasoDeUsoManterCobrancas() {
		this.ctrlCobra = new CtrlManterCobrancas(this);
		return this.ctrlCobra.iniciar();
	}

	@Override
	public boolean iniciarIncluirCobranca() {
		return this.ctrlCobra.iniciarIncluir();
	}

	@Override
	public boolean iniciarAlterarCobranca(int pos) {
		return this.ctrlCobra.iniciarAlterar(pos);
	}

	@Override
	public boolean iniciarExcluirCobranca(int pos) {
		return this.ctrlCobra.iniciarExcluir(pos);
	}

	@Override
	public IViewerSalvaBoleto getJanela() {
		return this.jBoleto;
	}

	@Override
	public Boleto getBoleto() {
		return this.boletoAtual;
	}

	@Override
	public boolean terminarCasoDeUsoManterCobrancas() {
		return this.ctrlCobra.terminar();
	}

	public void iniciarExclusaoCobrancas() {
		this.ctrlCobra = new CtrlManterCobrancas(this);
		this.ctrlCobra.recuperarDAO();
	}

	/********** Fim dos Métodos para Controlar Cobrancas *********/

	@Override
	public void selecionarContrato(Contrato c) throws ModelException {

		this.iniciarCasoDeUsoManterCobrancas();
		this.ctrlCobra.removerCobrancasBoletosNulos();
		
		if (this.dao.getListaObjs().isEmpty()) {
			this.ctrlCobra.iniciarCasoDeUsoIncluirAluguel(c.getValorAluguel());
		} else {
			this.ctrlCobra.iniciarCasoDeUsoIncluirAluguel(c.getValorAluguel());
		}
	}

	public void atribuirValor(float valor) {
		if (!(valor == 0))
			valorTotal += valor;
		else
			valorTotal = 0;

		this.jBoleto.setValorTotal(valorTotal);
	}
}

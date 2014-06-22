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
	
	private ICtrlManterTaxas ctrlTax;
	
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
		IDAO<Contrato> daoContrato = DAOContrato.getSingleton();
		Set<Contrato> contratos = daoContrato.getListaObjs();
		
		try {
			this.jBoleto = new JanelaSalvaBoleto(this, contratos);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.iniciarCasoDeUsoManterTaxas();
		this.jBoleto.selecionaContrato();
		return true;
	}
	
	@Override
	public boolean incluir(Date dataVencimento, Contrato contrato) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Boleto novo = new Boleto(dataVencimento, contrato);

		for (Taxa t : this.ctrlTax.getTaxas()){
			for(Cobra c : t.getCobrancas()){
				if(c.getBoleto() == null);
				novo.addCobranca(c);
			}
		}
		
		dao.salvar(novo);
		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		this.terminarCasoDeUsoManterTaxas();
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
		IDAO<Contrato> daoContrato = DAOContrato.getSingleton();
		Set<Contrato> contratos = daoContrato.getListaObjs();
		try {
			this.jBoleto = new JanelaSalvaBoleto(this,contratos);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.iniciarCasoDeUsoManterTaxas();
		this.jBoleto.atualizarCampos(
				this.boletoAtual.getDataVencimento(),
				this.boletoAtual.getContrato());
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
	public boolean alterar(Date dataVencimento, Contrato contrato) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.boletoAtual.setDataVencimento(dataVencimento);
		this.boletoAtual.setContrato(contrato);

		dao.atualizar(this.boletoAtual);

		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.boletoAtual = null;
		
		this.terminarCasoDeUsoManterTaxas();
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

		if(!(this.ctrlTax.getTaxas().isEmpty())){
			for(Taxa t : this.ctrlTax.getTaxas()){
				for(Cobra c : t.getCobrancas()){
					if(c.getBoleto() == this.boletoAtual)
						this.boletoAtual.removeCobranca(c);
				}
			}
		}
		
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

	/********** Métodos para Controlar Taxas *********/
	@Override
	public boolean iniciarCasoDeUsoManterTaxas(){
		this.ctrlTax = new CtrlManterTaxas(this);
		return this.ctrlTax.iniciar();
	}
	@Override
	public boolean iniciarIncluirTaxa(){
		return this.ctrlTax.iniciarIncluir();
	}
	@Override
	public void cancelarIncluirTaxa(){
		this.ctrlTax.cancelarIncluir();
	}
	@Override
	public boolean iniciarAlterarTaxa(int pos){
		return this.ctrlTax.iniciarAlterar(pos);
	}
	@Override
	public void cancelarAlterarTaxa(){
		this.ctrlTax.cancelarAlterar();
	}
	@Override
	public boolean iniciarExcluirTaxa(int pos){
		return this.ctrlTax.iniciarExcluir(pos);
	}
	@Override
	public void cancelarExcluirTaxa(){
		this.ctrlTax.cancelarExcluir();
	}
	@Override
	public IViewerSalvaBoleto getJanela(){
		return this.jBoleto;
	}
	@Override
	public Boleto getBoleto(){
		return this.boletoAtual;
	}
	@Override
	public boolean terminarCasoDeUsoManterTaxas(){
		return this.ctrlTax.terminar();
	}

	@Override
	public void selecionarContrato(Contrato contrato) {
		this.ctrlTax.selecionarContrato(contrato);
	}
}

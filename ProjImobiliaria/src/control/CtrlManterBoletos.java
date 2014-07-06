package control;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import model.Boleto;
import model.Cobra;
import model.Contrato;
import model.DAOBoleto;
import model.DAOCobra;
import model.DAOContrato;
import model.DAOTaxa;
import model.IDAO;
import model.ModelException;
import model.Taxa;
import view.IViewer;
import view.IViewerSalvaBoleto;
import view.IViewerSalvaCobranca;
import view.JanelaBoletos;
import view.JanelaExcluirBoleto;
import view.JanelaExcluirCobranca;
import view.JanelaSalvaBoleto;
import view.JanelaSalvaCobranca;

public class CtrlManterBoletos implements ICtrlManterBoletos {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO		 , EXCLUSAO			, ALTERACAO			, DISPONIVEL,
		INCLUSAO_COBRANCA, EXCLUSAO_COBRANCA, ALTERACAO_COBRANCA;
	}

	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;

	private IViewerSalvaBoleto jBoleto;
	
	private IViewerSalvaCobranca jCobranca;

	private Boleto boletoAtual = null;

	private Cobra cobraAtual = null;
	
	private Set<Cobra> listaCobrancas;
	
	private IDAO<Taxa> daoTaxa = DAOTaxa.getSingleton();

	private IDAO<Boleto> daoBoleto = DAOBoleto.getSingleton();

	private IDAO<Contrato> daoContrato = DAOContrato.getSingleton();
	
	private DAOCobra daoCobra = (DAOCobra) DAOCobra.getSingleton();

	private boolean emExecucao;
	
	private boolean emExecucaoCobrancas;

	private Operacao operacao;
	
	private Operacao exOperacao;

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
		this.exOperacao = Operacao.INCLUSAO;

		Set<Contrato> contratos = daoContrato.getListaObjs();

		try {
			this.jBoleto = new JanelaSalvaBoleto(this, contratos,false);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.iniciarCobrancas();
		this.jBoleto.selecionaContrato();
		return true;
	}

	@Override
	public boolean incluir(Date dataVencimento, Contrato contrato)
			throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Boleto novo = new Boleto(dataVencimento, contrato);

		for (Cobra c : this.listaCobrancas) {
			if (c.getBoleto() == null) {
				novo.addCobranca(c);
			}
		}

		daoBoleto.salvar(novo);
		this.terminarCobrancas();
		this.jBoleto.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if (this.operacao == Operacao.INCLUSAO) {
			this.jBoleto.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
			this.exOperacao = null;
		}
	}
	
	@Override
	public boolean iniciarAlterar(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.exOperacao = Operacao.ALTERACAO;
		this.boletoAtual = daoBoleto.recuperar(pos);

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
		this.iniciarCobrancas();
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if (this.operacao == Operacao.ALTERACAO) {
			this.jBoleto.setVisible(false);
			this.boletoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
			this.exOperacao = null;
		}
	}

	@Override
	public boolean alterar(Date dataVencimento) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO)
			return false;

		this.boletoAtual.setDataVencimento(dataVencimento);

		daoBoleto.atualizar(this.boletoAtual);

		this.terminarCobrancas();
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
		this.boletoAtual = daoBoleto.recuperar(pos);
		new JanelaExcluirBoleto(this, this.boletoAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if (this.operacao == Operacao.EXCLUSAO) {
			this.boletoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO)
			return false;

		if (!(this.listaCobrancas.isEmpty())) {
			for (Cobra c : this.listaCobrancas) {
				if (c.getBoleto() == this.boletoAtual)
					this.boletoAtual.removeCobranca(c);
			}
		}
		daoBoleto.remover(this.boletoAtual);
		this.boletoAtual.setContrato(null);
		this.atualizarInterface();
		this.boletoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		if (!(daoBoleto.getListaObjs().isEmpty())) {
			for (int i = 0; i < daoBoleto.getNumObjs(); i++) {
				Boleto boleto = daoBoleto.recuperar(i);
				this.jCadastro.incluirLinha(boleto);
			}
		}
	}

	/********** Métodos para Controlar Cobrancas *********/
	
	@Override
	public boolean iniciarCobrancas() {
		if (this.emExecucaoCobrancas)
			return false;

		if (!(daoCobra.getListaObjs().isEmpty())) {
			this.listaCobrancas = new TreeSet<Cobra>(daoCobra.getListaObjs());
		} else {
			this.listaCobrancas = new TreeSet<Cobra>();
		}
		
		this.atualizarInterfaceCobranca();
		this.emExecucaoCobrancas = true;
		this.operacao = this.exOperacao;
		return true;
	}

	@Override
	public boolean terminarCobrancas() {
		if (!this.emExecucaoCobrancas)
			return false;

		this.daoCobra.setListaObjs(this.listaCobrancas);
		this.emExecucaoCobrancas = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarIncluirCobranca(){
		if(this.operacao != this.exOperacao)
			return false;
		
		this.operacao = Operacao.INCLUSAO_COBRANCA;
		Set<Taxa> taxas = daoTaxa.getListaObjs();
		this.jCobranca = new JanelaSalvaCobranca(this, taxas);
		return true;
	}
	
	@Override
	public boolean incluirCobranca(float valor, Taxa t) throws ModelException{
		if(this.operacao != Operacao.INCLUSAO_COBRANCA)
			return false;
		
		Cobra novo = new Cobra(valor,t, this.boletoAtual);
		
		this.listaCobrancas.add(novo);
		if(this.jCobranca != null)
			this.jCobranca.setVisible(false);
		this.atualizarInterfaceCobranca();
		this.operacao = this.exOperacao;
		return true;
	}
	
	@Override
	public void cancelarIncluirCobranca(){
		if(this.operacao == Operacao.INCLUSAO_COBRANCA){
			this.jCobranca.setVisible(false);
			this.operacao = this.exOperacao;
		}
	}

	@Override
	public boolean iniciarAlterarCobranca(int pos){
		if (this.operacao != this.exOperacao)
			return false;
		
		this.operacao = Operacao.ALTERACAO_COBRANCA;
		int i = 0;
		for (Cobra c : this.listasCobrancasDoBoleto(this.boletoAtual)){
			if (i++ == pos)
				this.cobraAtual = c;
		}
		
		this.jCobranca = new JanelaSalvaCobranca(this, null);
		this.jCobranca.atualizarCampos(this.cobraAtual.getValor(),this.cobraAtual.getTaxa());
		return true;
	}
	
	@Override
	public boolean alterarCobranca(float valor, Taxa t) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO_COBRANCA)
			return false;

		this.cobraAtual.setValor(valor);
		this.cobraAtual.setTaxa(t);

		this.jCobranca.setVisible(false);
		this.atualizarInterfaceCobranca();
		this.cobraAtual = null;
		this.operacao = this.exOperacao;
		return true;
	}
	
	@Override
	public void cancelarAlterarCobranca(){
		if(this.operacao == Operacao.ALTERACAO_COBRANCA){
			this.jCobranca.setVisible(false);
			this.cobraAtual = null;
			this.operacao = this.exOperacao;
		}
	}
	
	@Override
	public boolean iniciarExcluirCobranca(int pos){
		if (this.operacao != this.exOperacao)
			return false;
		
		this.operacao = Operacao.EXCLUSAO_COBRANCA;
		int i = 0;
		for (Cobra c : this.listasCobrancasDoBoleto(this.boletoAtual)){
			if (i++ == pos)
				this.cobraAtual = c;
		}
		new JanelaExcluirCobranca(this,this.cobraAtual);
		return true;
	}
	
	@Override
	public boolean excluirCobranca() throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO_COBRANCA)
			return false;

		if (!(this.listaCobrancas.isEmpty())) {
			for (Cobra c : this.listaCobrancas){
				if (c.getBoleto() == this.boletoAtual)
					c.setBoleto(null);
			}
		}
		
		this.listaCobrancas.remove(this.cobraAtual);
		this.cobraAtual.setTaxa(null);
		this.atualizarInterfaceCobranca();
		this.cobraAtual = null;
		this.operacao = this.exOperacao;
		return true;
	}

	@Override
	public void cancelarExcluirCobranca(){
		if (this.operacao == Operacao.EXCLUSAO_COBRANCA){
			this.cobraAtual = null;
			this.operacao = this.exOperacao;
		}
	}
	
	@Override
	public void removerCobrancasBoletosNulos() {
		for (Iterator<Cobra> c = this.listaCobrancas.iterator(); c.hasNext();) {
			Cobra cobra = c.next();
			if (cobra.getBoleto() == null)
				c.remove();
		}

		
		Taxa taxa = null;
		for(Taxa t : this.daoTaxa.getListaObjs()){
			if(t.getNome().equals("ALUGUEL"))
				taxa = t;
		}
		
		if(taxa != null){
			for(Cobra cobranca : taxa.getCobrancas()){
				if(cobranca.getBoleto() == null)
					taxa.removeCobranca(cobranca);
			}
		}
	}
	
	@Override
	public void atualizarInterfaceCobranca() {
		this.atribuirValor(0);
		this.jBoleto.limpar();

		for (Cobra c : this.listaCobrancas) {
			if (c.getTaxa().getNome().equals("ALUGUEL")
					&& c.getValor() == this.jBoleto.getContrato()
							.getValorAluguel() && c.getBoleto()==null) {
				this.jBoleto.incluirLinha(c);
				this.atribuirValor(c.getValor());
			} else if (this.boletoAtual != null) {
				if (c.getBoleto() == this.boletoAtual) {
					this.jBoleto.incluirLinha(c);
					this.atribuirValor(c.getValor());
				}
			} else {
				if (c.getBoleto() == null) {
					this.jBoleto.incluirLinha(c);
					this.atribuirValor(c.getValor());
				}
			}
		}
	}
	
	@Override
	public Set<Cobra> listasCobrancasDoBoleto(Boleto b) {
		Set<Cobra> listaCobras = new TreeSet<Cobra>();
		if (b == null) {
			for (Cobra c : this.listaCobrancas)
				if (c.getBoleto() == null)
					listaCobras.add(c);
		} else {
			for (Cobra c : this.listaCobrancas) {
				if (c.getBoleto().equals(b))
					listaCobras.add(c);
			}
		}
		return listaCobras;
	}
	
/***** FIM DOS MÉTODOS PARA CONTROLAR COBRANÇA ******/
	
	@Override
	public void selecionarContrato(Contrato c) throws ModelException {

		this.iniciarCobrancas();
		this.removerCobrancasBoletosNulos();
		
		Taxa taxa = null;
		for(Taxa t : this.daoTaxa.getListaObjs()){
			if(t.getNome().equals("ALUGUEL"))
				taxa = t;
		}
		
		if(taxa == null){
			taxa = new Taxa("ALUGUEL", "Aluguel do Imóvel");
			this.daoTaxa.getListaObjs().add(taxa);
			this.operacao = Operacao.INCLUSAO_COBRANCA;
			this.boletoAtual = null;
			this.incluirCobranca(c.getValorAluguel(), taxa);
		}else{
			this.operacao = Operacao.INCLUSAO_COBRANCA;
			this.boletoAtual = null;
			this.incluirCobranca(c.getValorAluguel(), taxa);
		}
	}
	
	@Override
	public void atribuirValor(float valor) {
		if (!(valor == 0))
			valorTotal += valor;
		else
			valorTotal = 0;

		this.jBoleto.setValorTotal(valorTotal);
	}
}

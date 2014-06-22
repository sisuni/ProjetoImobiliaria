package control;

import java.util.Set;
import java.util.TreeSet;

import model.Boleto;
import model.Cobra;
import model.Contrato;
import model.DAOTaxa;
import model.ModelException;
import model.Taxa;
import view.IViewerSalvaBoleto;
import view.IViewerSalvaTaxa;
import view.JanelaExcluirFuncionario;
import view.JanelaSalvaTaxa;

public class CtrlManterTaxas implements ICtrlManterTaxas{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlManterBoletos ctrlBol;
	
	private IViewerSalvaBoleto jBoleto;
	
	private IViewerSalvaTaxa jTaxa;
	
	private Taxa taxaAtual;
	
	private Set<Taxa> listaTaxas = new TreeSet<Taxa>();
	
	private DAOTaxa dao = (DAOTaxa) DAOTaxa.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	

	public CtrlManterTaxas(ICtrlManterBoletos b) {
		this.ctrlBol = b;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.recuperarDAO();
		this.jBoleto = this.ctrlBol.getJanela();
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if(!this.emExecucao)
			return false;

		this.persisteDAO();
		this.jBoleto = null;
		this.ctrlBol = null;
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.INCLUSAO;
		this.jTaxa = new JanelaSalvaTaxa(this);
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jTaxa.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean incluir(String nome, String descricao, float valor) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;
		Cobra cobranca = null;
		Taxa taxa = new Taxa(nome, descricao);
		if(ctrlBol.getBoleto() == null)
			cobranca = new Cobra(valor, taxa, null);
		else{
			cobranca = new Cobra(valor,taxa,ctrlBol.getBoleto());
		}
		
		
		listaTaxas.add(taxa);
		this.jTaxa.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		Set<Taxa> lista = this.recuperarPeloBoleto(this.ctrlBol.getBoleto());
		int i = 0;
		for(Taxa t : lista){
			if(i++ == pos)
				this.taxaAtual = t;
		}
		
		float valor = 0;
		for(Cobra c : this.taxaAtual.getCobrancas()){
			if(c.getTaxa() == this.taxaAtual)
				valor = c.getValor();
		}
							
		this.jTaxa = new JanelaSalvaTaxa(this);	
		this.jTaxa.atualizarCampos(
				this.taxaAtual.getNome(),
				this.taxaAtual.getDescricao(),
				valor);
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jTaxa.setVisible(false);
			this.taxaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String nome, String descricao, float valor) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.taxaAtual.setNome(nome);
		this.taxaAtual.setDescricao(descricao);

		dao.atualizar(this.taxaAtual);

		this.jTaxa.setVisible(false);
		this.atualizarInterface();
		this.taxaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.taxaAtual = dao.recuperar(pos);
		new JanelaExcluirFuncionario(this, this.taxaAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.taxaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.taxaAtual);
		
		this.atualizarInterface();
		this.taxaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public void selecionarContrato(Contrato c){
		Taxa t = new Taxa("Aluguel", "Aluguel do Imóvel");		
		Cobra cobra;		
		
		if(this.ctrlBol.getBoleto() == null)
			cobra = new Cobra(c.getValorAluguel(), t, null);
		else
			cobra = new Cobra(c.getValorAluguel(), t, this.ctrlBol.getBoleto());
							
		this.listaTaxas.add(t);
		atualizarInterface();
	}
	
	@Override
	public void atualizarInterface() {
		this.jBoleto.limpar();

		for(Taxa t : this.getTaxas()){
			for(Cobra c : t.getCobrancas()){
				if(c.getBoleto() == this.ctrlBol.getBoleto()){
					this.jBoleto.setValorTotal(c.getValor());
					this.jBoleto.incluirLinha(t);
				}
			}
		}
	}
	
	 public Set<Taxa> getTaxas(){
		 return this.listaTaxas;
	 }
	
	public Set<Taxa> recuperarPeloBoleto(Boleto b){
		Set<Taxa> listaTaxBoletos = new TreeSet<Taxa>();
		for(Taxa t : this.listaTaxas){
			if(t.getCobrancas().equals(b.getCobrancas()))
				listaTaxBoletos.add(t);
		}
		return listaTaxBoletos;
	}
	
	public void recuperarDAO(){
		if(!(dao.getListaObjs().isEmpty())){
			this.listaTaxas = new TreeSet<Taxa>(dao.getListaObjs());
		}
	}
	
	public void persisteDAO(){
		this.dao.setListaObjs(this.listaTaxas);
	}
	

}

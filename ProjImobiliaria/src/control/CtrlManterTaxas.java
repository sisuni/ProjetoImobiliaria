package control;

import java.util.Set;
import java.util.TreeSet;

import model.Cobra;
import model.DAOTaxa;
import model.ModelException;
import model.Taxa;
import view.IViewerSalvaBoleto;
import view.IViewerSalvaTaxa;
import view.JanelaExcluirTaxa;
import view.JanelaSalvaTaxa;

public class CtrlManterTaxas implements ICtrlManterTaxas{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlManterCobrancas ctrlCobra;
	
	private IViewerSalvaBoleto jBoleto;
	
	private IViewerSalvaTaxa jTaxa;
	
	private Taxa taxaAtual;
	
	private Set<Taxa> listaTaxas = new TreeSet<Taxa>();
	
	private DAOTaxa dao = (DAOTaxa) DAOTaxa.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	

	public CtrlManterTaxas(ICtrlManterCobrancas c) {
		this.ctrlCobra = c;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.recuperarDAO();
		this.jBoleto = this.ctrlCobra.getJanela();
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
	public boolean incluir(String nome, String descricao, float valor) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;
	
		if(! this.procurarTaxa(nome))
			this.taxaAtual = new Taxa(nome,descricao);	
		
		
		if(! this.listaTaxas.contains(this.taxaAtual))
			this.listaTaxas.add(this.taxaAtual);
		
			
		this.jTaxa.setVisible(false);
		this.ctrlCobra.iniciarIncluir();
		this.ctrlCobra.incluir(valor, this.taxaAtual);
		this.taxaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	public boolean procurarTaxa(String nome){
		for(Taxa t : this.getTaxas()){
			if(t.getNome().equals(nome)){
				this.taxaAtual = t;
				return true;
			}
		}
		return false;
	}
		
	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jTaxa.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(Cobra c) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.taxaAtual = c.getTaxa();
		float valor = c.getValor();
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
			this.ctrlCobra.cancelarAlterar();
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String nome, String descricao, float valor) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.taxaAtual.setNome(nome);
		this.taxaAtual.setDescricao(descricao);
		this.ctrlCobra.alterar(valor, taxaAtual);

		this.jTaxa.setVisible(false);
		this.taxaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(Cobra c) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.taxaAtual = c.getTaxa();
		new JanelaExcluirTaxa(this, this.taxaAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.taxaAtual = null;
			this.ctrlCobra.cancelarExcluir();
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		if(this.taxaAtual.getCobrancas().size()<=1)
			this.listaTaxas.remove(this.taxaAtual);
	
		this.taxaAtual = null;
		this.ctrlCobra.excluir(null);
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	
	
	/*******Métodos para o funcionamento do alguel********/
	@Override
	public boolean iniciarIncluirAluguel(){
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		return true;
	}
	
	public boolean incluirAluguel(float valor) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;
		Taxa taxa = null;
			
		if(!(this.getTaxas().isEmpty())){
			for(Taxa t : this.getTaxas()){
				if(t.getNome().equals("ALUGUEL"))
					taxa = t;
			}
		} else {
			taxa = new Taxa("ALUGUEL","Aluguel do Imóvel");
			listaTaxas.add(taxa);
		}
		
		
		this.ctrlCobra.iniciarIncluirAluguel();
		this.ctrlCobra.incluirAluguel(valor, taxa);
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	/*******Fim dos Métodos para o funcionamento do alguel********/
	
	
	@Override
	public Set<Taxa> getTaxas(){
		 return this.listaTaxas;
	 }
	
	public void recuperarDAO(){
		this.listaTaxas = new TreeSet<Taxa>(dao.getListaObjs());
	}
	
	public void persisteDAO(){
		this.dao.setListaObjs(this.listaTaxas);
	}

	
	/** PRECISO DE POLIMORFISMO DE MÉTODOS COM MUDANÇA NOS PARAMETROS */
	@Override
	public boolean iniciarAlterar(int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void atualizarInterface() {
		// TODO Auto-generated method stub
		
	}

}

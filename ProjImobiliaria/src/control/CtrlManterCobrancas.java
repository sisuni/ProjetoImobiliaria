package control;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import model.Boleto;
import model.Cobra;
import model.DAOCobra;
import model.IDAO;
import model.ModelException;
import model.Taxa;
import view.IViewer;
import view.IViewerSalvaBoleto;

public class CtrlManterCobrancas implements ICtrlManterCobrancas {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}

	private ICtrlManterBoletos ctrlBol;

	private ICtrlManterTaxas ctrlTax;

	private IViewerSalvaBoleto jBoleto;

	private Cobra cobrancaAtual;

	private Set<Cobra> listaCobrancas = new TreeSet<Cobra>();

	private DAOCobra dao = (DAOCobra) DAOCobra.getSingleton();

	private boolean emExecucao;

	private Operacao operacao;

	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterCobrancas(ICtrlManterBoletos b) {
		this.ctrlBol = b;
	}

	@Override
	public boolean iniciar() {
		if (this.emExecucao)
			return false;

		this.recuperarDAO();
		this.jBoleto = this.ctrlBol.getJanela();
		this.ctrlTax = new CtrlManterTaxas(this);
		this.ctrlTax.iniciar();
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if (!this.emExecucao)
			return false;

		this.ctrlTax.terminar();
		this.persisteDAO();
		this.jBoleto = null;
		this.ctrlTax = null;
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.iniciar();
		this.ctrlTax.iniciarIncluir();
		return true;
	}

	@Override
	public boolean incluir(float valor, Taxa taxa) throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Cobra novo = new Cobra(valor, taxa, this.ctrlBol.getBoleto());

		this.listaCobrancas.add(novo);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		this.ctrlTax.terminar();
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if (this.operacao == Operacao.INCLUSAO) {
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;

		int i = 0;
		for (Cobra c : this.listasCobrancasDoBoleto(this.ctrlBol.getBoleto())) {
			if (i++ == pos)
				this.cobrancaAtual = c;
		}
		this.ctrlTax.iniciarAlterar(this.cobrancaAtual);
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if (this.operacao == Operacao.ALTERACAO) {
			this.cobrancaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean alterar(float valor, Taxa taxa) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO)
			return false;

		this.cobrancaAtual.setValor(valor);
		this.cobrancaAtual.setTaxa(taxa);

		this.atualizarInterface();
		this.cobrancaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		int i = 0;
		for (Cobra c : this.listasCobrancasDoBoleto(this.ctrlBol.getBoleto())) {
			if (i++ == pos)
				this.cobrancaAtual = c;
		}
		this.ctrlTax.iniciarExcluir(this.cobrancaAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if (this.operacao == Operacao.EXCLUSAO) {
			this.cobrancaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir(Taxa t) throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO)
			return false;

		this.listaCobrancas.remove(this.cobrancaAtual);
		this.atualizarInterface();
		this.cobrancaAtual.setTaxa(t);
		this.cobrancaAtual.setBoleto(null);
		this.cobrancaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	/**** Métodos para aluguel *************/
	@Override
	public void iniciarCasoDeUsoIncluirAluguel(float valor)
			throws ModelException {
		this.ctrlTax.iniciarIncluirAluguel();
		this.ctrlTax.incluirAluguel(valor);
	}

	public boolean iniciarIncluirAluguel() {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		return true;
	}

	public boolean incluirAluguel(float valor, Taxa taxa) throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Cobra novo = new Cobra(valor, taxa, null);
		this.listaCobrancas.add(novo);

		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		this.terminar();
		return true;
	}
	
	@Override
	public void removerCobrancasBoletosNulos() {

		for (Iterator<Cobra> c = this.getCobrancas().iterator(); c.hasNext();) {
			Cobra cobra = c.next();
			if (cobra.getBoleto() == null) {
				c.remove();
				this.getCobrancas().remove(cobra);
			}
		}
	}

	/**** Fim dos Métodos para aluguel *************/

	@Override
	public void atualizarInterface() {
		this.ctrlBol.atribuirValor(0);
		this.jBoleto.limpar();

		for (Cobra c : this.listaCobrancas) {
			if (c.getTaxa().getNome().equals("ALUGUEL")
					&& c.getValor() == this.jBoleto.getContrato()
							.getValorAluguel() && c.getBoleto()==null) {
				this.jBoleto.incluirLinha(c);
				this.ctrlBol.atribuirValor(c.getValor());
			} else if (this.ctrlBol.getBoleto() != null) {
				if (c.getBoleto() == this.ctrlBol.getBoleto()) {
					this.jBoleto.incluirLinha(c);
					this.ctrlBol.atribuirValor(c.getValor());
				}
			} else {
				if (c.getBoleto() == null) {
					this.jBoleto.incluirLinha(c);
					this.ctrlBol.atribuirValor(c.getValor());
				}
			}
		}
	}

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

	@Override
	public Set<Cobra> getCobrancas() {
		return this.listaCobrancas;
	}

	@Override
	public IViewerSalvaBoleto getJanela() {
		return this.jBoleto;
	}

	@Override
	public void recuperarDAO() {
		if (!(dao.getListaObjs().isEmpty())) {
			this.listaCobrancas = new TreeSet<Cobra>(dao.getListaObjs());
		}
	}

	@Override
	public void persisteDAO() {
		this.dao.setListaObjs(this.listaCobrancas);
	}

	/** Preciso de Polimorfismo em método com mudança de parametros */
	@Override
	public boolean excluir() throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

}

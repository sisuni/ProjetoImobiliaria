package control;

import model.DAOTaxa;
import model.IDAO;
import model.ModelException;
import model.Taxa;
import view.IViewer;
import view.IViewerSalvaTaxa;
import view.JanelaExcluirTaxa;
import view.JanelaSalvaTaxa;
import view.JanelaTaxas;

public class CtrlManterTaxas implements ICtrlManterTaxas {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}

	private ICtrlPrograma ctrlPrg;

	private IViewer jCadastro;
	
	private IViewerSalvaTaxa jTaxa;

	private Taxa taxaAtual;

	private IDAO<Taxa> dao = DAOTaxa.getSingleton();

	private boolean emExecucao;

	private Operacao operacao;

	public CtrlManterTaxas(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}

	@Override
	public boolean iniciar() {
		if (this.emExecucao)
			return false;

		this.jCadastro = new JanelaTaxas(this);
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
		this.ctrlPrg.terminarCasoDeUsoManterTaxas();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jTaxa = new JanelaSalvaTaxa(this);
		return true;
	}

	@Override
	public boolean incluir(String nome, String descricao) throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Taxa novo = new Taxa(nome,descricao);
		dao.salvar(novo);
		this.atualizarInterface();
		this.jTaxa.setVisible(false);
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if (this.operacao == Operacao.INCLUSAO) {
			this.jTaxa.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.taxaAtual = dao.recuperar(pos);
		this.jTaxa = new JanelaSalvaTaxa(this);
		this.jTaxa.atualizarCampos(this.taxaAtual.getNome(),
				this.taxaAtual.getDescricao());
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if (this.operacao == Operacao.ALTERACAO) {
			this.jTaxa.setVisible(false);
			this.taxaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String nome, String descricao) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO)
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
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.taxaAtual = dao.recuperar(pos);
		new JanelaExcluirTaxa(this, this.taxaAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if (this.operacao == Operacao.EXCLUSAO) {
			this.taxaAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO)
			return false;

		if (this.taxaAtual.getCobrancas().size() <= 1)
			dao.remover(this.taxaAtual);

		this.taxaAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

//	/******* Métodos para o funcionamento do alguel ********/
//	@Override
//	public boolean iniciarIncluirAluguel() {
//		if (this.operacao != Operacao.DISPONIVEL)
//			return false;
//
//		this.operacao = Operacao.INCLUSAO;
//		return true;
//	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();
		
		for(int i = 0; i< dao.getNumObjs(); i++){
			Taxa taxa = dao.recuperar(i);
			this.jCadastro.incluirLinha(taxa);
		}
	}

}

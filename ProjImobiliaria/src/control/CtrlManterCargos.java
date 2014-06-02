package control;

import model.Cargo;
import model.DAOCargo;
import model.IDAO;
import model.ModelException;
import view.IViewer;
import view.IViewerSalvaCargo;
import view.JanelaCargos;
import view.JanelaExcluirCargo;
import view.JanelaSalvaCargo;

public class CtrlManterCargos implements ICtrlManterCargos{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaCargo jCargo;
	
	private Cargo cargoAtual;
	
	private IDAO<Cargo> dao = DAOCargo.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterCargos(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaCargos(this);
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
		this.ctrlPrg.terminarCasoDeUsoManterCargo();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jCargo = new JanelaSalvaCargo(this);
		return true;
	}
	
	@Override
	public boolean incluir(int nivel, String nome) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Cargo novo = new Cargo(nome, nivel);

		dao.salvar(novo);

		this.jCargo.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jCargo.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.cargoAtual = dao.recuperar(pos);
		this.jCargo = new JanelaSalvaCargo(this);
		this.jCargo.atualizarCampos(this.cargoAtual.getNivel(), this.cargoAtual.getNome());
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jCargo.setVisible(false);
			this.cargoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(int nivel, String nome) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.cargoAtual.setNivel(nivel);
		this.cargoAtual.setNome(nome);

		dao.atualizar(this.cargoAtual);

		this.jCargo.setVisible(false);
		this.atualizarInterface();
		this.cargoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.cargoAtual = dao.recuperar(pos);
		this.operacao = Operacao.EXCLUSAO;
		JanelaExcluirCargo jExcluirCargo = new JanelaExcluirCargo(this);
		
		if(this.cargoAtual.getFuncionarios().isEmpty())
			return jExcluirCargo.ExcluirCargo(this.cargoAtual);
		else
			return jExcluirCargo.ExcluirTudo(this.cargoAtual, this.cargoAtual.getFuncionarios().size());
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.cargoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.cargoAtual);

		this.atualizarInterface();
		this.cargoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Cargo cargo = dao.recuperar(i);
			this.jCadastro.incluirLinha(cargo);
		}
	}

	

}

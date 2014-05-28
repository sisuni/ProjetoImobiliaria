package control;

import model.Cargo;
import model.DAOFuncionario;
import model.Funcionario;
import model.IDAO;
import model.ModelException;
import view.IViewer;
import view.IViewerSalvaFuncionario;
import view.JanelaExcluirFuncionario;
import view.JanelaFuncionario;
import view.JanelaSalvaFuncionario;

public class CtrlManterFuncionarios implements ICtrlManter{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaFuncionario jFuncionario;
	
	private Funcionario funcionarioAtual;
	
	private IDAO<Funcionario> dao = DAOFuncionario.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterFuncionarios(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaFuncionario(this);
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
		this.jFuncionario = new JanelaSalvaFuncionario(this);
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jFuncionario.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean incluir(String nome, String login, String senha, Cargo cargo) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Funcionario novo = new Funcionario(nome, login, senha, cargo);

		dao.salvar(novo);

		this.jFuncionario.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.funcionarioAtual = dao.recuperar(pos);
		this.jFuncionario = new JanelaSalvaFuncionario(this);
		this.jFuncionario.atualizarCampos(
				this.funcionarioAtual.getNome(),
				this.funcionarioAtual.getLogin(), 
				this.funcionarioAtual.getSenha(),
				this.funcionarioAtual.getCargo()
				);
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jFuncionario.setVisible(false);
			this.funcionarioAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String nome, String login, String senha, Cargo cargo) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.funcionarioAtual.setNome(nome);
		this.funcionarioAtual.setLogin(login);
		this.funcionarioAtual.setSenha(senha);
		this.funcionarioAtual.setCargo(cargo);

		dao.atualizar(this.funcionarioAtual);

		this.jFuncionario.setVisible(false);
		this.atualizarInterface();
		this.funcionarioAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.funcionarioAtual = dao.recuperar(pos);
		new JanelaExcluirFuncionario(this, this.funcionarioAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.funcionarioAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.funcionarioAtual);

		this.atualizarInterface();
		this.funcionarioAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Funcionario funcionario = dao.recuperar(i);
			this.jCadastro.incluirLinha(funcionario);
		}
	}

}

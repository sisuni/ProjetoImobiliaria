package control;

import java.util.Set;

import model.Cargo;
import model.DAOCargo;
import model.DAOFuncionario;
import model.Funcionario;
import model.IDAO;
import model.ModelException;
import view.IViewer;
import view.IViewerAcesso;
import view.IViewerSalvaFuncionario;
import view.JanelaAcesso;
import view.JanelaExcluirFuncionario;
import view.JanelaFuncionario;
import view.JanelaSalvaFuncionario;

public class CtrlManterFuncionarios implements ICtrlManterFuncionarios{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL, ACESSO;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerAcesso jAcesso;
	
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
		
		IDAO<Cargo> daoCargo = DAOCargo.getSingleton();
		Set<Cargo> cargos = daoCargo.getListaObjs();
		
		this.jFuncionario = new JanelaSalvaFuncionario(this, cargos);
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
		IDAO<Cargo> daoCargo = DAOCargo.getSingleton();
		Set<Cargo> cargos = daoCargo.getListaObjs();
		
		this.jFuncionario = new JanelaSalvaFuncionario(this, cargos);
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
		this.funcionarioAtual.setCargo(null);

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
	
	@Override
	public boolean iniciarAcesso(){
		if(this.emExecucao)
			return false;
		
		this.verificarAdmin();
		this.operacao = Operacao.ACESSO;
		this.jAcesso = new JanelaAcesso(this);
		return true;
	}
	
	public void verificarAdmin(){
		for(Funcionario f : dao.getListaObjs()){
			if(f.getNome().equals("Administrador") || f.getLogin().equals("admin"))
				return;
		}
		
		Funcionario novo = new Funcionario("Administrador", "admin", "admin", this.verificarCargo());
		dao.salvar(novo);
	}
	
	public Cargo verificarCargo(){
		IDAO<Cargo> daoCargo = DAOCargo.getSingleton();
		
		for(Cargo c : daoCargo.getListaObjs()){
			if(c.getNome().equals("Administrador"))
				return c;
		}
		
		Cargo novo = null;
		try {
			novo = new Cargo("Administrador", 1);
		} catch (ModelException e) {
			e.printStackTrace();
		}
		daoCargo.salvar(novo);
		return novo;
	}
	
	@Override
	public boolean acessar(String usuario, String senha) throws ModelException{
		if(this.operacao != Operacao.ACESSO)
			return false;
		
		this.funcionarioAtual = this.validarUsuario(usuario);
		this.validarSenha(senha);
		
		this.jAcesso.setVisible(false);
		this.ctrlPrg.iniciarMenu(funcionarioAtual);
		this.funcionarioAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	public Funcionario validarUsuario(String usuario) throws ModelException{
		for(Funcionario f : dao.getListaObjs()){
			if(f.getLogin().equals(usuario))
				return f;
		}
		this.jAcesso.limpar();
		throw new ModelException("Não há nenhum funcionário com este usuário!");
	}
	
	public boolean validarSenha(String senha) throws ModelException{
		if(! this.funcionarioAtual.getSenha().equals(senha)){
			this.jAcesso.limpar();
			throw new ModelException(this.funcionarioAtual.getNome()+" sua senha está incorreta!");
		}
		return true;
	}
	
	@Override
	public void cancelarAcesso(){
		if(this.operacao == Operacao.ACESSO){
			this.jAcesso.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
			this.ctrlPrg.terminarAcesso();
		}
	}

}

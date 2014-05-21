package control;

import model.Cargo;
import model.DAOCargo;
import model.IDAO;
import model.ModelException;
import view.IViewerCargos;
import view.IViewerSalvaCargo;
import view.JanelaCargos;

public class CtrlManterCargo implements ICtrlManterCargo{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	/**
	 * Referência para o controlador do programa.
	 */
	private ICtrlPrograma ctrlPrg;
	
	/**
	 * Referência para a janela do cadastro de Cargos
	 */
	private IViewerCargos jCadastro;
	
	/**
	 * Referência para a janela Cargo que permitirá a 
	 * inclusão e alteração do Cargo
	 */
	private IViewerSalvaCargo jCargo;
	
	/**
	 * Referência para o objeto Cargo sendo manipulado
	 */
	private Cargo cargoAtual;
	
	/**
	 * Referência para o objeto DaoCargo 
	 */
	private IDAO<Cargo> dao = DAOCargo.getSingleton();

	/**
	 * Atributo indicando se o caso de uso está ou não em execução
	 */
	private boolean emExecucao;
	
	/**
	 * Atributo que indica qual operação está em curso
	 */
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe CtrlManterPrograma
	 */
	public CtrlManterCargo(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		// Se já está em execução, não é necessário solicitar novamente a execução do caso de uso
		if(this.emExecucao)
			return false;
		// Crio e abro a janela de cadastro
		this.jCadastro = new JanelaCargos(this);
		// Atualizo a interface
		this.atualizarInterface();
		// Guardo a informação que o caso de uso está em execuão
		this.emExecucao = true;
		// Indico que o controlador de caso de uso está disponível
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarIncluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelarIncluir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean incluir(int Nivel, String nome) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelarAlterar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean alterar(int Nivel, String nome) throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelarExcluir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean excluir() throws ModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void atualizarInterface() {
		// Limpa a tabela da janela
		this.jCadastro.limpar();
		// Para cada objeto Cargo presente no DAO
		for(int i = 0; i < dao.getNumObjs(); i++) {
			// Recupero um objeto Departamento
			Cargo cargo = dao.recuperar(i);
			// Coloco uma linha na tabela
			this.jCadastro.incluirLinha(cargo);
		}
	}

}

package control;

import model.Cargo;
import model.DAOCargo;
import model.IDAO;
import model.ModelException;
import view.IViewerCargos;
import view.IViewerSalvaCargo;

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
	private CtrlPrograma ctrlPrg;
	
	/**
	 * Referência para a janela do cadastro de Cargos
	 */
	private IViewerCargos jCadastro;
	
	/**
	 * Referência para a janela Departamento que permitirá a 
	 * inclusão e alteração do Departamento
	 */
	private IViewerSalvaCargo jDepartamento;
	
	/**
	 * Referência para o objeto Departamento sendo manipulado
	 */
	private Cargo cargoAtual;
	
	/**
	 * Referência para o objeto DaoDepartamento 
	 */
	private IDAO dao = DAOCargo.getSingleton();

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

	@Override
	public boolean iniciar() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

}

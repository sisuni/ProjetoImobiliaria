package control;

import model.DAOTelefone;
import model.IDAO;
import model.ModelException;
import model.Telefone;

public class CtrlManterTelefone implements ICtrlManter{
	
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	private ICtrlPrograma ctrlPrg;
	
	private Telefone telefoneAtual;
	
	private IDAO<Telefone> dao = DAOTelefone.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	public CtrlManterTelefone(ICtrlPrograma p){
		this.ctrlPrg = p;
	}
	
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
	public boolean iniciarAlterar(int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancelarAlterar() {
		// TODO Auto-generated method stub
		
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

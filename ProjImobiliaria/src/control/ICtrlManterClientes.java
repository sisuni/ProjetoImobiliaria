package control;

import java.util.Set;

import model.Cliente;
import model.Telefone;
import view.IViewerSalvaCliente;

public interface ICtrlManterClientes extends ICtrlManter{
	public abstract boolean iniciarCasoDeUsoManterTelefone(IViewerSalvaCliente jc);
	
	public abstract boolean iniciarIncluirTelefone();
	
	public abstract void cancelarIncluirTelefone();
	
	public abstract boolean iniciarAlterarTelefone(int pos);
	 
	public abstract void cancelarAlterarTelefone();
	
	public abstract boolean iniciarExcluirTelefone(int pos);
 
	public abstract void cancelarExcluirTelefone();
	
	public abstract boolean terminarCasoDeUsoManterTelefone() ;
		
	public abstract IViewerSalvaCliente getJanela();
	
	public abstract Set<Telefone> getTelefones();
	
	public abstract Cliente getCliente();

	
}

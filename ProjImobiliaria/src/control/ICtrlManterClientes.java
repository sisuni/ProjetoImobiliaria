package control;

import java.util.Set;

import model.Telefone;
import view.IViewerSalvaCliente;

public interface ICtrlManterClientes extends ICtrlManter{
	public boolean iniciarCasoDeUsoManterTelefone();
	
	public boolean iniciarIncluirTelefone();
	
	public void cancelarIncluirTelefone();
	
	public boolean iniciarAlterarTelefone(int pos);
	
	public void cancelarAlterarTelefone();
	
	public boolean iniciarExcluirTelefone(int pos);

	public void cancelarExcluirTelefone();
	
	public boolean terminarCasoDeUsoManterTelefone() ;
	
	public void setJanela(IViewerSalvaCliente j);
	
	public IViewerSalvaCliente getJanela();
	
	public Set<Telefone> getTelefones();

	
}

package control;

import java.util.Set;

import model.Cliente;
import model.ModelException;
import model.Telefone;

public interface ICtrlManterTelefones extends ICtrlManter {

	 public abstract boolean incluir(String tipo, String numero) throws ModelException;

	 public abstract boolean alterar(String tipo, String numero) throws ModelException;
	 
	 public Set<Telefone> getTelefones();
	 
}

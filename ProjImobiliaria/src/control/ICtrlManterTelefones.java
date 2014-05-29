package control;

import model.Cliente;
import model.ModelException;

public interface ICtrlManterTelefones extends ICtrlManter {

	 public abstract boolean incluir(String tipo, String numero, Cliente cliente) throws ModelException;

	 public abstract boolean alterar(String tipo, String numero, Cliente cliente) throws ModelException;


}

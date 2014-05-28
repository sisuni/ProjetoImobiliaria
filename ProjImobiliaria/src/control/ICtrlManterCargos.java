package control;

import model.ModelException;

public interface ICtrlManterCargos extends ICtrlManter {

	 public abstract boolean incluir(int nivel, String nome) throws ModelException;

	 public abstract boolean alterar(int nivel, String nome) throws ModelException;


}

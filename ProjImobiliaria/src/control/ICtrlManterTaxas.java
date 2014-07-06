package control;

import model.ModelException;

public interface ICtrlManterTaxas extends ICtrlManter {

	 public abstract boolean incluir(String nome, String descricao) throws ModelException;

	 public abstract boolean alterar(String nome, String descricao) throws ModelException;  
}

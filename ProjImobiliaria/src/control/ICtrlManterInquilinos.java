package control;

import model.ModelException;

public interface ICtrlManterInquilinos extends ICtrlManterClientes {

	 public abstract boolean incluir(String nome, String cpf, String email, String endereco, String endAnteriorCompleto) throws ModelException;

	 public abstract boolean alterar(String nome, String cpf, String email, String endereco, String endAnteriorCompleto) throws ModelException;


}

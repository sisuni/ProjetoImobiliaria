package control;

import model.ModelException;

public interface ICtrlManterProprietarios extends ICtrlManterClientes {

	 public abstract boolean incluir(String nome, String cpf, String email, String endereco, String banco, String agencia, String conta) throws ModelException;

	 public abstract boolean alterar(String nome, String cpf, String email, String endereco, String banco, String agencia, String conta) throws ModelException;


}

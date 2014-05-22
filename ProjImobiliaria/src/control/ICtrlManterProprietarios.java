package control;

import model.ModelException;

public interface ICtrlManterProprietarios {
	public abstract boolean iniciar();

	public abstract boolean terminar();
	
	public abstract boolean iniciarIncluir();

	public abstract void cancelarIncluir();

	public abstract boolean incluir(String nome, String cpf, String email, String endereco, String banco, int agencia, String conta) throws ModelException;

	public abstract boolean iniciarAlterar(int pos);

	public abstract void cancelarAlterar();

	public abstract boolean alterar(String nome, String cpf, String email, String endereco, String banco, int agencia, String conta) throws ModelException;

	public abstract boolean iniciarExcluir(int pos);

	public abstract void cancelarExcluir();

	public abstract boolean excluir() throws ModelException;

	public abstract void atualizarInterface();
}

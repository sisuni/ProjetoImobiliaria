package control;

import model.ModelException;

public interface ICtrlManterCargos {
	public abstract boolean iniciar();

	public abstract boolean terminar();
	
	public abstract boolean iniciarIncluir();

	public abstract void cancelarIncluir();

	public abstract boolean incluir(int Nivel, String nome) throws ModelException;

	public abstract boolean iniciarAlterar(int pos);

	public abstract void cancelarAlterar();

	public abstract boolean alterar(int Nivel, String nome) throws ModelException;

	public abstract boolean iniciarExcluir(int pos);

	public abstract void cancelarExcluir();

	public abstract boolean excluir() throws ModelException;

	public abstract void atualizarInterface();
}

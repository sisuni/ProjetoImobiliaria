package control;

import model.ModelException;

public interface ICtrlManter {
	public abstract boolean iniciar();

	public abstract boolean terminar();
	
	public abstract boolean iniciarIncluir();

	public abstract void cancelarIncluir();

	/**
	 * Lembrar de implementar manualmente esses métodos
	 * public abstract boolean incluir(int Nivel, String nome) throws ModelException;
	 * @param pos
	 * @return
	 */

	public abstract boolean iniciarAlterar(int pos);

	public abstract void cancelarAlterar();

	/**
	 * Lembrar de implementar manualmente esses métodos
	 * public abstract boolean alterar(int Nivel, String nome) throws ModelException;
	 * @param pos
	 * @return
	 */

	public abstract boolean iniciarExcluir(int pos);

	public abstract void cancelarExcluir();

	public abstract boolean excluir() throws ModelException;

	public abstract void atualizarInterface();
}

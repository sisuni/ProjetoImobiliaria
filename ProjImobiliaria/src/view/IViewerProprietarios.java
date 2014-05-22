package view;

import control.ITabelavel;

public interface IViewerProprietarios {
	
	public abstract void limpar();

	public abstract void incluirLinha(ITabelavel objeto);

	public abstract void executarIncluir();

	public abstract void executarExcluir();

	public abstract void executarAlterar();

	public abstract void executarTerminar();
	
	public abstract void setVisible(boolean flag);
}

package view;

import control.ITabelavel;

public interface IViewerSalvaCliente {
	
	public void limpar();
	public void incluirLinha(ITabelavel objeto);
	public void executarIncluirTelefone();
	public void executarExcluirTelefone();
	public void executarAlterarTelefone();
	
	public void setVisible(boolean flag);
}

package view;

import model.Cargo;

public interface IViewerSalvaFuncionario {
	
	public abstract void atualizarCampos(String nome, String login, String senha, Object cargo);

	public void setVisible(boolean flag);
}

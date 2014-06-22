package view;

import model.Cargo;

public interface IViewerSalvaTaxa {
	
	public abstract void atualizarCampos(String nome, String descricao, float valor);

	public void setVisible(boolean flag);
}

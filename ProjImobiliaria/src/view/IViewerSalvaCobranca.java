package view;

import model.Taxa;

public interface IViewerSalvaCobranca {
	
	public abstract void atualizarCampos(float valor, Taxa t);

	public void setVisible(boolean flag);
}

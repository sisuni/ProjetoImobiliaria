package view;

import model.Taxa;

public interface IViewerSalvaCobranca {
	
	public abstract void atualizarCampos(Taxa taxa, float valor);

	public void setVisible(boolean flag);
}

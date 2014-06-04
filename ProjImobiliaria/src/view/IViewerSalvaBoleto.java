package view;

import model.Contrato;

public interface IViewerSalvaBoleto {
	
	public abstract void atualizarCampos(int dataVencimento, Contrato contrato);

	public void setVisible(boolean flag);
}

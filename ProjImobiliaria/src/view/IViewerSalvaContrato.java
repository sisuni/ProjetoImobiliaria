package view;

import java.util.Date;

public interface IViewerSalvaContrato {
	
	public abstract void atualizarCampos(int duracao, Date dataInicio, int percentProprietario, float valorAluguel);

	public void setVisible(boolean flag);
}

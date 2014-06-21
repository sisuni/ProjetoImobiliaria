package view;

import java.util.Date;

import model.Imovel;
import model.Inquilino;

public interface IViewerSalvaContrato {
	
	public abstract void atualizarCampos(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inq);

	public void setVisible(boolean flag);
}

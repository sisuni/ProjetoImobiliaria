package control;

import java.text.ParseException;

import model.DAOProprietario;
import model.IDAO;
import model.ModelException;
import model.Proprietario;
import view.IViewer;
import view.IViewerSalvaProprietario;
import view.JanelaExcluirProprietario;
import view.JanelaProprietarios;
import view.JanelaSalvaProprietario;

public abstract class CtrlManterClientes implements ICtrlManterClientes {
	private ICtrlManter ctrlTel;
	
	@Override
	public boolean iniciarCasoDeUsoManterTelefone() {
		this.ctrlTel = new CtrlManterTelefones(this);
		return this.ctrlTel.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterTelefone() {
		return true;
	}

}

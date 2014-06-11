package control;

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

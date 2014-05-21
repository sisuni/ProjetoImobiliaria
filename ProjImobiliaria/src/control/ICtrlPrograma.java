package control;

public interface ICtrlPrograma {

	public abstract void iniciar();

	public abstract void terminar();

	public abstract boolean iniciarCasoDeUsoManterCargo();

	public abstract boolean terminarCasoDeUsoManterCargo();

	public abstract boolean iniciarCasoDeUsoManterFuncionario();
	
	public abstract boolean terminarCasoDeUsoManterFuncionario();
	
	public abstract boolean iniciarCasoDeUsoManterProprietarios();
	
	public abstract boolean terminarCasoDeUsoManterProprietarios();
	
	public abstract boolean iniciarCasoDeUsoManterImovel();
	
	public abstract boolean terminarCasoDeUsoManterImovel();
	
	public abstract boolean iniciarCasoDeUsoManterInquilino();
	
	public abstract boolean terminarCasoDeUsoManterInquilino();
	
	public abstract boolean iniciarCasoDeUsoManterContrato();
	
	public abstract boolean terminarCasoDeUsoManterContrato();
	
	public abstract boolean iniciarCasoDeUsoManterTaxas();
	
	public abstract boolean terminarCasoDeUsoManterTaxas();
	
	public abstract boolean iniciarCasoDeUsoManterBoleto();
	
	public abstract boolean terminarCasoDeUsoManterBoleto();

	public abstract boolean iniciarCasoDeUsoManterTelefone();
	
	public abstract boolean terminarCasoDeUsoManterTelefone();
}

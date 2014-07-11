package control;

import model.Funcionario;

public interface ICtrlPrograma {

	public abstract void iniciar();

	public abstract void terminar();

	public abstract boolean iniciarCasoDeUsoManterBoleto();
	
	public abstract boolean terminarCasoDeUsoManterBoleto();

	public abstract boolean iniciarCasoDeUsoManterCargo();

	public abstract boolean terminarCasoDeUsoManterCargo();

	public abstract boolean iniciarCasoDeUsoManterContrato();
	
	public abstract boolean terminarCasoDeUsoManterContrato();
	
	public abstract boolean iniciarCasoDeUsoManterFuncionario();
	
	public abstract boolean terminarCasoDeUsoManterFuncionario();
	
	public abstract boolean iniciarCasoDeUsoManterImovel();
	
	public abstract boolean terminarCasoDeUsoManterImovel();
	
	public abstract boolean iniciarCasoDeUsoManterInquilino();
	
	public abstract boolean terminarCasoDeUsoManterInquilino();
	
	public abstract boolean iniciarCasoDeUsoManterProprietarios();
	
	public abstract boolean terminarCasoDeUsoManterProprietarios();
		
	public abstract boolean iniciarCasoDeUsoManterTaxas();
	
	public abstract boolean terminarCasoDeUsoManterTaxas();
	
	public abstract boolean iniciarAcesso();
	
	public abstract boolean terminarAcesso();
	
	public abstract boolean iniciarMenu(Funcionario usuario);
	
	public abstract boolean iniciarAlterarSenha();
	
	public abstract boolean terminarAlterarSenha();
	
}

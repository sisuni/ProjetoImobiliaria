package control;

import java.util.Set;

import model.Cargo;
import model.Contrato;
import model.ModelException;
import model.Taxa;

public interface ICtrlManterTaxas extends ICtrlManter {

	 public abstract boolean incluir(String nome, String descricao, float valor) throws ModelException;

	 public abstract boolean alterar(String nome, String descricao, float valor) throws ModelException;

	 public abstract Set<Taxa> getTaxas();
	 
	 public abstract void selecionarContrato(Contrato c);
	 
}

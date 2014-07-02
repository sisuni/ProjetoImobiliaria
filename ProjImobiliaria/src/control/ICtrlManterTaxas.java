package control;

import java.util.Set;

import model.Cobra;
import model.ModelException;
import model.Taxa;

public interface ICtrlManterTaxas extends ICtrlManter {

	 public abstract boolean incluir(String nome, String descricao, float valor) throws ModelException;

	 public abstract boolean alterar(String nome, String descricao, float valor) throws ModelException;
	
	 public abstract boolean iniciarIncluirAluguel();
	 
	 public abstract boolean incluirAluguel(float valor) throws ModelException;
	 
	 public abstract boolean iniciarAlterar(Cobra c);
	 
	 public abstract boolean iniciarExcluir(Cobra c);
	 
	 public abstract Set<Taxa> getTaxas();
	 	  
}

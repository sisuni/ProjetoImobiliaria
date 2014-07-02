package control;

import java.util.Set;

import view.IViewerSalvaBoleto;
import model.Boleto;
import model.Cobra;
import model.ModelException;
import model.Taxa;

public interface ICtrlManterCobrancas extends ICtrlManter {

	 public abstract boolean incluir(float valor, Taxa taxa) throws ModelException;
	 
	 public abstract boolean alterar(float valor, Taxa taxa) throws ModelException;
	 
	 public abstract boolean excluir(Taxa t) throws ModelException;
	 
	 
	 
	 public abstract Set<Cobra> getCobrancas();
	 
	 public abstract IViewerSalvaBoleto getJanela();
	 
	 public abstract Set<Cobra> listasCobrancasDoBoleto(Boleto b);
	 
	 
	 public abstract void iniciarCasoDeUsoIncluirAluguel(float valor) throws ModelException;
	 
	 public abstract boolean iniciarIncluirAluguel();
	 
	 public abstract boolean incluirAluguel(float valor, Taxa taxa) throws ModelException;
	 
	 public abstract void removerCobrancasBoletosNulos();
	 
	 
	 public abstract void recuperarDAO();
	 
	 public abstract void persisteDAO();
}

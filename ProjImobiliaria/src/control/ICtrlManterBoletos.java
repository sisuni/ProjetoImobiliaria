package control;

import java.util.Date;

import model.Boleto;
import model.Contrato;
import model.ModelException;
import view.IViewerSalvaBoleto;

public interface ICtrlManterBoletos extends ICtrlManter {

	 public abstract boolean incluir(Date dataVencimento, Contrato contrato) throws ModelException;

	 public abstract boolean alterar(Date dataVencimento, Contrato contrato) throws ModelException;
	 
	 public abstract boolean iniciarCasoDeUsoManterTaxas();
	 
	 public abstract boolean iniciarIncluirTaxa();
	 
	 public abstract void cancelarIncluirTaxa();
		
	 public abstract boolean iniciarAlterarTaxa(int pos);
		
	 public abstract void cancelarAlterarTaxa();
		
	 public abstract boolean iniciarExcluirTaxa(int pos);
		
	 public abstract void cancelarExcluirTaxa();
		
	 public abstract boolean terminarCasoDeUsoManterTaxas();
	 
	 public abstract IViewerSalvaBoleto getJanela();
	 
	 public abstract Boleto getBoleto();
	 
	 public void selecionarContrato(Contrato c);
}

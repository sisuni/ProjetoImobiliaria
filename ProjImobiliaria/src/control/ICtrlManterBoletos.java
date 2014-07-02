package control;

import java.util.Date;

import model.Boleto;
import model.Contrato;
import model.ModelException;
import view.IViewerSalvaBoleto;

public interface ICtrlManterBoletos extends ICtrlManter {

	 public abstract boolean incluir(Date dataVencimento, Contrato contrato) throws ModelException;

	 public abstract boolean alterar(Date dataVencimento) throws ModelException;
	 
	 public abstract boolean iniciarCasoDeUsoManterCobrancas();
	 
	 public abstract boolean iniciarIncluirCobranca();
	 	
	 public abstract boolean iniciarAlterarCobranca(int pos);
		
	 public abstract boolean iniciarExcluirCobranca(int pos);
				
	 public abstract boolean terminarCasoDeUsoManterCobrancas();
	 
	 public abstract IViewerSalvaBoleto getJanela();
	 
	 public abstract Boleto getBoleto();
	 
	 public abstract void selecionarContrato(Contrato c) throws ModelException;
	 
	 public abstract void atribuirValor(float valor);
}

package control;

import model.Contrato;
import model.ModelException;

public interface ICtrlManterBoletos extends ICtrlManter {

	 public abstract boolean incluir(int dataVencimento, Contrato contrato) throws ModelException;

	 public abstract boolean alterar(int dataVencimento, Contrato contrato) throws ModelException;


}

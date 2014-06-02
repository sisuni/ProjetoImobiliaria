package control;

import java.util.Date;

import model.ModelException;

public interface ICtrlManterContratos extends ICtrlManter {

	 public abstract boolean incluir(int duracao, Date dataInicio, int percentProprietario, float valorAluguel) throws ModelException;

	 public abstract boolean alterar(int duracao, Date dataInicio, int percentProprietario, float valorAluguel) throws ModelException;


}

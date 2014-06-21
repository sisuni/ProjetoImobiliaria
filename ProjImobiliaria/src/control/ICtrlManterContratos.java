package control;

import java.util.Date;

import model.Imovel;
import model.Inquilino;
import model.ModelException;

public interface ICtrlManterContratos extends ICtrlManter {

	 public abstract boolean incluir(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inqui) throws ModelException;

	 public abstract boolean alterar(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inqui) throws ModelException;


}

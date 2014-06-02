package control;

import model.ModelException;
import model.Proprietario;

public interface ICtrlManterImoveis extends ICtrlManter {

	 public abstract boolean incluir(String uf, String cidade, String bairro, String logradouro, int numero, String complemento, float valorBase, String dimensoes, int qtdQuartos, String descricao, String finalidade, String tipo, boolean status, Proprietario proprietario) throws ModelException;

	 public abstract boolean alterar(String uf, String cidade, String bairro, String logradouro, int numero, String complemento, float valorBase, String dimensoes, int qtdQuartos, String descricao, String finalidade, String tipo, boolean status, Proprietario proprietario) throws ModelException;


}

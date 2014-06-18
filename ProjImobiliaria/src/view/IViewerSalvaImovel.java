package view;

import model.Proprietario;

public interface IViewerSalvaImovel {
	
	public abstract void atualizarCampos(String uf, String cidade, String bairro, String logradouro, int numero, 
										String complemento, float valorBase, String dimensoes, int qtdQuartos, 
										String descricao, String finalidade, String tipo, boolean status, 
										Proprietario proprietario);

	public void setVisible(boolean flag);
}

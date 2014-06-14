package view;

public interface IViewerSalvaProprietario extends IViewerSalvaCliente{
	
	public abstract void atualizarCampos(String nome, String cpf, String email, String endereco, String banco, String agencia, String conta);

}

package view;

public interface IViewerSalvaProprietario {
	
	public abstract void atualizarCampos(String nome, String cpf, String email, String endereco, String banco, String agencia, String conta);

	public void setVisible(boolean flag);
}

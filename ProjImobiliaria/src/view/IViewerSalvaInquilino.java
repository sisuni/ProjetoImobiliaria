package view;

public interface IViewerSalvaInquilino {
	
	public abstract void atualizarCampos(String nome, String cpf, String email, String endereco, String endAnteriorCompleto);

	public void setVisible(boolean flag);
}

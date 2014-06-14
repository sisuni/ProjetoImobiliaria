package view;

public interface IViewerSalvaInquilino extends IViewerSalvaCliente{
	
	public abstract void atualizarCampos(String nome, String cpf, String email, String endereco, String endAnteriorCompleto);

}

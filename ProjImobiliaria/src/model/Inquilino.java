package model;

public class Inquilino extends Cliente {
	
	private String endAnteriorCompleto;

	public Inquilino(int cod, String nome, String cpf, String rg, String email,
			String uf, String cidade, String bairro, String logradouro,
			int numero, String complemento, String endAnteriorCompleto) {
		super(cod, nome, cpf, rg, email, uf, cidade, bairro, logradouro, numero, complemento);
		this.endAnteriorCompleto = endAnteriorCompleto;
	}

	public String getEndAnteriorCompleto() {
		return endAnteriorCompleto;
	}

	public void setEndAnteriorCompleto(String endAnteriorCompleto) {
		this.endAnteriorCompleto = endAnteriorCompleto;
	}	

}

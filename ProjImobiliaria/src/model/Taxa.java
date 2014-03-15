package model;

public class Taxa {
	private String nome;
	private String descrição;
	
	public Taxa(String nome,String descrição){
		this.descrição = descrição;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrição() {
		return descrição;
	}

	public void setDescrição(String descrição) {
		this.descrição = descrição;
	}

}


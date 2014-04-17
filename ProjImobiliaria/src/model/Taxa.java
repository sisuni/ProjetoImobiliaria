package model;

public class Taxa {
	private String nome;
	private String descricao;
	
	public Taxa(String nome,String descricao){
		this.descricao = descricao;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getdescricao() {
		return descricao;
	}

	public void setdescricao(String descricao) {
		this.descricao = descricao;
	}

}


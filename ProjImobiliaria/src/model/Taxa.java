package model;

public class Taxa {
	private String nome;
	private String descricao;
	
	public Taxa(String nome,String descricao){
		this.setDescricao(descricao);
		this.setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}


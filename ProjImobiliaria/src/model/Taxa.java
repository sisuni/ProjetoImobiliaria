package model;

public class Taxa {
	private String nome;
	private String descri��o;
	
	public Taxa(String nome,String descri��o){
		this.descri��o = descri��o;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescri��o() {
		return descri��o;
	}

	public void setDescri��o(String descri��o) {
		this.descri��o = descri��o;
	}

}


package model;

public class Cargo {

	private String nome;
	private int nivel;
	
	public Cargo (String nome, int nivel){
		this.nome = nome;
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}	
	
}

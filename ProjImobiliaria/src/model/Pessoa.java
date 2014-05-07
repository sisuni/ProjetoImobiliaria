package model;

public abstract class Pessoa {

	private int cod;
	private String nome;

	public Pessoa(int cod, String nome) {
		this.setCod(cod);
		this.setNome(nome);
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

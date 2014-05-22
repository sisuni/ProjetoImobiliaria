package model;

import java.io.Serializable;

import control.ITabelavel;

public abstract class Pessoa implements Serializable, ITabelavel {

	private String nome;

	public Pessoa(String nome) {
		this.setNome(nome);
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

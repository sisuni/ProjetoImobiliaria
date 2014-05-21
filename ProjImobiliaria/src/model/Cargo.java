package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Cargo implements Serializable, ITabelavel, Comparable<Cargo> {

	private String nome;
	private int nivel;
	private Set<Funcionario> listaFuncionarios;
		
	public Cargo (String nome, int nivel){
		this.setNome(nome);
		this.setNivel(nivel);
		this.listaFuncionarios = new TreeSet<Funcionario>();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNivel() {
		return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public Set<Funcionario> getFuncionarios(){
		return this.listaFuncionarios;
	}
	
	public void addFuncionario(Funcionario novoFunc){
		if(this.listaFuncionarios.contains(novoFunc))
			return;
		
		this.listaFuncionarios.add(novoFunc);
		novoFunc.setCargo(this);
	}
	
	public void removerFuncionario(Funcionario exFunc){
		if(! this.listaFuncionarios.contains(exFunc))
			return;
		
		this.listaFuncionarios.remove(exFunc);
		exFunc.setCargo(null);
	}

	@Override
	public int compareTo(Cargo c) {
		return this.nome.compareTo(c.nome);
	}

	@Override
	/**
	 * Dados para serem exibidos no data grid
	 */
	public Object[] getData() {
		return new Object[]{this.nivel, this.nome, this.listaFuncionarios.size()};
	}
	
	/** 
	 * Implementação do método toString que retorna uma String
	 * que descreve o objeto Cargo
	 */
	public String toString() {
		return this.nome;
	}

}

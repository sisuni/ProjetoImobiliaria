package model;

import java.util.Set;
import java.util.TreeSet;

public class Cargo {

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
}

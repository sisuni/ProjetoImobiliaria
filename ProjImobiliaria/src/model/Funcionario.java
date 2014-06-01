package model;

import java.io.Serializable;

import control.ITabelavel;

public class Funcionario extends Pessoa implements Serializable, ITabelavel, Comparable<Funcionario> {

	private String login;
	private String senha;
	private Cargo cargo;
	
	public Funcionario(String nome, String login, String senha, Cargo cargo) {
		super(nome);
		this.setLogin(login);
		this.setSenha(senha);
		this.setCargo(cargo);
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Cargo getCargo(){
		return this.cargo;
	}
	
	public void setCargo(Cargo cargo){
		if (this.cargo == cargo)
			return;
		
		if(cargo == null){
			Cargo antigo = this.cargo;
			this.cargo = null;
			antigo.removerFuncionario(this);
		}else{
			if(this.cargo !=null)
				this.cargo.removerFuncionario(this);
			this.cargo = cargo;
			cargo.addFuncionario(this);
		}
	}

	@Override
	public Object[] getData() {
		return new Object[]{this.getNome(), this.login, this.cargo.getNome()};
	}

	@Override
	public int compareTo(Funcionario f) {
		return this.getNome().compareTo(f.getNome());
	}
	
	public String toString() {
		return this.getNome();
	}

}

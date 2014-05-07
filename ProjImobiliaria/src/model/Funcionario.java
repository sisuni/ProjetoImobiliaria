package model;

public class Funcionario extends Pessoa {

	private String login;
	private String senha;
	private Cargo cargo;
	
	public Funcionario(int cod, String nome, String login, String senha, Cargo cargo) {
		super(cod, nome);
		this.setLogin(login);
		this.setSenha(senha);
		this.setCargo(cargo);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
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

}

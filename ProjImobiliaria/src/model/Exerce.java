package model;

public class Exerce {
	
	private String funcao;
	private Cargo cargo;
	private Funcionario funcionario;
	
	public Exerce(String funcao){
		this.funcao = funcao;
		this.setCargo(cargo);
		this.setFuncionario(funcionario);
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}

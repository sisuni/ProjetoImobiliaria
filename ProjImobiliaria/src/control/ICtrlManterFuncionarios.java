package control;

import model.Cargo;
import model.Funcionario;
import model.ModelException;

public interface ICtrlManterFuncionarios extends ICtrlManter {

	 public abstract boolean incluir(String nome, String login, String senha, Cargo cargo) throws ModelException;

	 public abstract boolean alterar(String nome, String login, String senha, Cargo cargo) throws ModelException;
	 
	 public abstract boolean iniciarAcesso();		
	 
	 public abstract boolean acessar(String usuario, String senha) throws ModelException;
	 
	 public abstract void cancelarAcesso();
	 
	 public abstract boolean iniciarAlterarSenha(Funcionario func);
	 
	 public abstract boolean alterarSenha(Funcionario func, String senhaAtual, String novaSenha, String repNovaSenha) throws ModelException;
	 
	 public abstract void cancelarAlterarSenha();


}

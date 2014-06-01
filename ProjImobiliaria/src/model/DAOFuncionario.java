package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOFuncionario implements IDAO<Funcionario>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	/**
	 * Referência para a única instância da classe que deverá existir
	 */
	private static IDAO<Funcionario> singleton;
	/**
	 * Referência para o Set que apontará para todos os objetos 
	 * guardados pelo DAO
	 */
	private Set<Funcionario> listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOFuncionario() {
		// Aloco memória para o array
		this.listaObjs = new TreeSet<Funcionario>();
	}
	
	/**
	 * Método para retornar a única instância existente do DAO
	 * @return
	 */
	public static IDAO<Funcionario> getSingleton() {
		if(DAOFuncionario.singleton == null)
			DAOFuncionario.singleton = new DAOFuncionario();
		return DAOFuncionario.singleton;
	}
	
	@Override
	public boolean salvar(Funcionario novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Funcionario obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Funcionario obj){
		return true;
	}
	
	@Override
	public Funcionario recuperar(int pos){
		int i = 0;
		for(Funcionario d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}
	
	@Override
	public Funcionario recuperarPelaChave(Object nivel){
		for(Funcionario f : this.listaObjs)
			if(nivel.equals(f.getLogin()))
				return f;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Funcionario> getListaObjs() {
		return this.listaObjs;
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) 
			throws IOException, ClassNotFoundException {
		// Recupera o array de objetos
		this.listaObjs = (Set<Funcionario>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) 
			throws IOException {
		// Salva o array de objetos
		oos.writeObject(this.listaObjs);
	}
}

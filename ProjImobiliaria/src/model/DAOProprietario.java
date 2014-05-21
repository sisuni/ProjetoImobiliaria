package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOProprietario implements IDAO<Proprietario>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	/**
	 * Refer�ncia para a única instância da classe que deverá existir
	 */
	private static IDAO<Proprietario> 	singleton;
	/**
	 * Refer�ncia para o Set que apontará para todos os objetos 
	 * guardados pelo DAO
	 */
	private Set<Proprietario> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOProprietario() {
		// Aloco memória para o array
		this.listaObjs = new TreeSet<Proprietario>();
	}
	
	/**
	 * Método para retornar a única instância existente do DAO
	 * @return
	 */
	public static IDAO<Proprietario> getSingleton() {
		if(DAOProprietario.singleton == null)
			DAOProprietario.singleton = new DAOProprietario();
		return DAOProprietario.singleton;
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#salvar(dados.Departamento)
	 */
	@Override
	public boolean salvar(Proprietario novo){
		return this.listaObjs.add(novo);
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#remover(dados.Departamento)
	 */
	@Override
	public boolean remover(Proprietario obj){
		return this.listaObjs.remove(obj);
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#atualizar(dados.Departamento)
	 */
	@Override
	public boolean atualizar(Proprietario obj){
		return true;
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#recuperar(int)
	 */
	@Override
	public Proprietario recuperar(int pos){
		int i = 0;
		for(Proprietario d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAO#recuperarPelaSigla(java.lang.String)
	 */
	@Override
	public Proprietario recuperarPelaChave(Object cpf){
		for(Proprietario p : this.listaObjs)
			if(p.getCpf().equals(cpf))
				return p;
		return null;
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#getNumObjs()
	 */
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	/* (non-Javadoc)
	 * @see dados.IDAODepartamento#getListaObjs()
	 */
	@Override
	public Proprietario[] getListaObjs() {
		return (Proprietario[])this.listaObjs.toArray();
	}

	/* (non-Javadoc)
	 * @see dados.ISerializarDAO#recuperarObjetos(java.io.ObjectInputStream)
	 */
	@Override
	public void recuperarObjetos(ObjectInputStream ois) 
			throws IOException, ClassNotFoundException {
		// Recupera o array de objetos
		this.listaObjs = (Set<Proprietario>)ois.readObject();
	}

	/* (non-Javadoc)
	 * @see dados.ISerializarDAO#salvarObjetos(java.io.ObjectOutputStream)
	 */
	@Override
	public void salvarObjetos(ObjectOutputStream oos) 
			throws IOException {
		// Salva o array de objetos
		oos.writeObject(this.listaObjs);
	}
}

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
	private static IDAO<Proprietario> 	singleton;

	private Set<Proprietario> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOProprietario() {
		this.listaObjs = new TreeSet<Proprietario>();
	}
	
	public static IDAO<Proprietario> getSingleton() {
		if(DAOProprietario.singleton == null)
			DAOProprietario.singleton = new DAOProprietario();
		return DAOProprietario.singleton;
	}
	
	@Override
	public boolean salvar(Proprietario novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Proprietario obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Proprietario obj){
		return true;
	}
	
	@Override
	public Proprietario recuperar(int pos){
		int i = 0;
		for(Proprietario d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}

	@Override
	public Proprietario recuperarPelaChave(Object cpf){
		for(Proprietario p : this.listaObjs)
			if(p.getCpf().equals(cpf))
				return p;
		return null;
	}

	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}

	@Override
	public Proprietario[] getListaObjs() {
		return (Proprietario[])this.listaObjs.toArray();
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Proprietario>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}
}

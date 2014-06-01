package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOCargo implements IDAO<Cargo>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Cargo> 	singleton;

	private Set<Cargo> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOCargo() {
		this.listaObjs = new TreeSet<Cargo>();
	}
	
	public static IDAO<Cargo> getSingleton() {
		if(DAOCargo.singleton == null)
			DAOCargo.singleton = new DAOCargo();
		return DAOCargo.singleton;
	}
	
	@Override
	public boolean salvar(Cargo novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Cargo obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Cargo obj){
		return true;
	}
	
	@Override
	public Cargo recuperar(int pos){
		int i = 0;
		for(Cargo d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}
	
	@Override
	public Cargo recuperarPelaChave(Object nivel){
		for(Cargo p : this.listaObjs)
			if(nivel.equals(p.getNivel()))
				return p;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Cargo> getListaObjs() {
		return this.listaObjs;
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Cargo>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}
}

package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOInquilino implements IDAO<Inquilino>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Inquilino> singleton;

	private Set<Inquilino> listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOInquilino() {
		this.listaObjs = new TreeSet<Inquilino>();
	}
	
	public static IDAO<Inquilino> getSingleton() {
		if(DAOInquilino.singleton == null)
			DAOInquilino.singleton = new DAOInquilino();
		return DAOInquilino.singleton;
	}
	
	@Override
	public boolean salvar(Inquilino novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Inquilino obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Inquilino obj){
		return true;
	}
	
	@Override
	public Inquilino recuperar(int pos){
		int i = 0;
		for(Inquilino d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}
	
	@Override
	public Inquilino recuperarPelaChave(Object nivel){
		for(Inquilino p : this.listaObjs)
			if(nivel.equals(p.getCpf()))
				return p;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Inquilino[] getListaObjs() {
		return (Inquilino[])this.listaObjs.toArray();
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Inquilino>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}
}

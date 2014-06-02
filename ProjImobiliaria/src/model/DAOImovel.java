package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOImovel implements IDAO<Imovel>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Imovel> 	singleton;

	private Set<Imovel> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOImovel() {
		this.listaObjs = new TreeSet<Imovel>();
	}
	
	public static IDAO<Imovel> getSingleton() {
		if(DAOImovel.singleton == null)
			DAOImovel.singleton = new DAOImovel();
		return DAOImovel.singleton;
	}
	
	@Override
	public boolean salvar(Imovel novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Imovel obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Imovel obj){
		return true;
	}
	
	@Override
	public Imovel recuperar(int pos){
		int i = 0;
		for(Imovel im : this.listaObjs)
			if(i++ == pos)
				return im;
		return null;
	}
	
	@Override
	public Imovel recuperarPelaChave(Object nivel){
		for(Imovel i : this.listaObjs)
			if(nivel.equals(i.getDescricao()))
				return i;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Imovel> getListaObjs() {
		return this.listaObjs;
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Imovel>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}
}

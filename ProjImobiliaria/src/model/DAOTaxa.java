package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOTaxa implements IDAO<Taxa>, IDAOSerializavel {
	
	private static IDAO<Taxa> singleton;
	
	private Set<Taxa>	listaObjs;
	
	private DAOTaxa(){
		this.listaObjs = new TreeSet<Taxa>();
	}
	
	public static IDAO<Taxa> getSingleton(){
		if(DAOTaxa.singleton == null)
			DAOTaxa.singleton = new DAOTaxa();
		return DAOTaxa.singleton;
	}
	
	@Override
	public boolean salvar(Taxa novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Taxa obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Taxa obj){
		return true;
	}
	
	@Override
	public Taxa recuperar(int pos){
		int i=0;
		for(Taxa t : this.listaObjs)
			if(i++ == pos)
				return t;
		return null;
	}
	
	@Override
	public Taxa recuperarPelaChave(Object nome){
		for(Taxa t : this.listaObjs)
			if(t.getNome().equals(nome))
				return t;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Taxa> getListaObjs(){
		return this.listaObjs;
	}
	
	public void setListaObjs(Set<Taxa> novaLista){
		this.listaObjs = novaLista;
	}
	
	@Override
	public void recuperarObjetos(ObjectInputStream ois)
			throws IOException, ClassNotFoundException{
		this.listaObjs = (Set<Taxa>)ois.readObject();
	}
	
	@Override
	public void salvarObjetos(ObjectOutputStream oos)
			throws IOException{
		oos.writeObject(this.listaObjs);
	}	
}

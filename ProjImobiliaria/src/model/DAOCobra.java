package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOCobra implements IDAO<Cobra>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Cobra> 	singleton;

	private Set<Cobra> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOCobra() {
		this.listaObjs = new TreeSet<Cobra>();
	}
	
	public static IDAO<Cobra> getSingleton() {
		if(DAOCobra.singleton == null)
			DAOCobra.singleton = new DAOCobra();
		return DAOCobra.singleton;
	}
	
	@Override
	public boolean salvar(Cobra novo) {
		return this.listaObjs.add(novo);
	}

	@Override
	public boolean remover(Cobra obj) {
		return this.listaObjs.remove(obj);
	}

	@Override
	public boolean atualizar(Cobra obj) {
		return true;
	}
	
	@Override
	public Cobra recuperar(int pos){
		int i = 0;
		for(Cobra c : this.listaObjs)
			if(i++ == pos)
				return c;
		return null;
	}
	
	@Override
	public Cobra recuperarPelaChave(Object taxa){
		for(Cobra c : this.listaObjs)
			if(taxa.equals(c.getTaxa()))
				return c;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Cobra> getListaObjs() {
		return this.listaObjs;
	}

	public void setListaObjs(Set<Cobra> novaLista){
		this.listaObjs = novaLista;
	}
	
	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Cobra>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}

}

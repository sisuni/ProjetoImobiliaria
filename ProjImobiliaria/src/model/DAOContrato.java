package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOContrato implements IDAO<Contrato>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Contrato> 	singleton;

	private Set<Contrato> 			listaObjs;
	
	//
	// MétodoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOContrato() {
		this.listaObjs = new TreeSet<Contrato>();
	}
	
	public static IDAO<Contrato> getSingleton() {
		if(DAOContrato.singleton == null)
			DAOContrato.singleton = new DAOContrato();
		return DAOContrato.singleton;
	}
	
	@Override
	public boolean salvar(Contrato novo){
		return this.listaObjs.add(novo);
	}
	
	@Override
	public boolean remover(Contrato obj){
		return this.listaObjs.remove(obj);
	}
	
	@Override
	public boolean atualizar(Contrato obj){
		return true;
	}
	
	@Override
	public Contrato recuperar(int pos){
		int i = 0;
		for(Contrato c : this.listaObjs)
			if(i++ == pos)
				return c;
		return null;
	}
	
	@Override
	public Contrato recuperarPelaChave(Object imovel){
		for(Contrato c : this.listaObjs)
			if(imovel.equals(c.getImovel()))
				return c;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Contrato> getListaObjs() {
		return this.listaObjs;
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Contrato>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}
}

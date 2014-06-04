package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOBoleto implements IDAO<Boleto>, IDAOSerializavel {
	//
	// ATRIBUTOS
	//
	private static IDAO<Boleto> 	singleton;

	private Set<Boleto> 			listaObjs;
	
	//
	// MÃ©todoS
	//
	/**
	 * Construtor privado do DAO
	 */
	private DAOBoleto() {
		this.listaObjs = new TreeSet<Boleto>();
	}
	
	public static IDAO<Boleto> getSingleton() {
		if(DAOBoleto.singleton == null)
			DAOBoleto.singleton = new DAOBoleto();
		return DAOBoleto.singleton;
	}
	
	@Override
	public boolean salvar(Boleto novo) {
		return this.listaObjs.add(novo);
	}

	@Override
	public boolean remover(Boleto obj) {
		return this.listaObjs.remove(obj);
	}

	@Override
	public boolean atualizar(Boleto obj) {
		return true;
	}
	
	@Override
	public Boleto recuperar(int pos){
		int i = 0;
		for(Boleto d : this.listaObjs)
			if(i++ == pos)
				return d;
		return null;
	}
	
	@Override
	public Boleto recuperarPelaChave(Object contrato){
		for(Boleto b : this.listaObjs)
			if(contrato.equals(b.getContrato()))
				return b;
		return null;
	}
	
	@Override
	public int getNumObjs(){
		return this.listaObjs.size();
	}
	
	@Override
	public Set<Boleto> getListaObjs() {
		return this.listaObjs;
	}

	@Override
	public void recuperarObjetos(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.listaObjs = (Set<Boleto>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) throws IOException {
		oos.writeObject(this.listaObjs);
	}

}

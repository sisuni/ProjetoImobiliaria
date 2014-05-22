package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

public class DAOTelefone implements IDAO<Telefone>, IDAOSerializavel{

	private static IDAO<Telefone> singleton;
	
	private Set<Telefone> listaObjs;
	
	private DAOTelefone(){
		this.listaObjs = new TreeSet<Telefone>();
	}
	
	public static IDAO<Telefone> getSingleton(){
		if(DAOTelefone.singleton == null)
			DAOTelefone.singleton = new DAOTelefone();
		return DAOTelefone.singleton;
	}
	
	@Override
	public void recuperarObjetos(ObjectInputStream ois) 
			throws IOException,	ClassNotFoundException {
		this.listaObjs = (Set<Telefone>)ois.readObject();
	}

	@Override
	public void salvarObjetos(ObjectOutputStream oos) 
			throws IOException {
		oos.writeObject(this.listaObjs);		
	}

	@Override
	public boolean salvar(Telefone novo) {
		return this.listaObjs.add(novo);
	}

	@Override
	public boolean remover(Telefone obj) {
		return this.listaObjs.remove(obj);
	}

	@Override
	public boolean atualizar(Telefone obj) {
		return true;
	}

	@Override
	public Telefone recuperar(int posicao) {
		int i = 0;
		for(Telefone t: this.listaObjs)
			if(i++ == posicao)
				return t;
		return null;
	}

	@Override
	public Telefone recuperarPelaChave(Object chave) {
		for(Telefone t: this.listaObjs)
			if(chave.equals(t.getNumero()))
				return t;
		return null;
	}

	@Override
	public int getNumObjs() {
		return this.listaObjs.size();
	}

	@Override
	public Telefone[] getListaObjs() {
		return (Telefone[])this.listaObjs.toArray();
	}

}

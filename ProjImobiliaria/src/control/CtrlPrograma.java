package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.DAOProprietario;
import model.IDAOSerializavel;
import view.JanelaPrincipal;


public class CtrlPrograma {
	
	private CtrlManterProprietarios ctrlProprietario;
	private JanelaPrincipal jPrincipal;
	
	public CtrlPrograma() {
		this.ctrlProprietario = new CtrlManterProprietarios();
	}
	
	public void iniciar(){
		// Cria e apresenta a janela principal
		this.jPrincipal = new JanelaPrincipal(this);
		IDAOSerializavel daoProprietario = (IDAOSerializavel) DAOProprietario.getSingleton();
		
		//
		// Recuperação dos objetos serializados no arquivo c:/dados.dat
		//
		try {
			// Abrindo o arquivo para leitura binária
			FileInputStream fis = new FileInputStream("dados.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Solicitação para os DAOs gerenciarem os objetos recuperados do arquivo
			daoProprietario.recuperarObjetos(ois);
			// Fechando o arquivo 
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo dados.dat não encontrado");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}

	public void terminar(){
		// Recuperando os DAOs do sistema
		IDAOSerializavel daoProprietario = (IDAOSerializavel)DAOProprietario.getSingleton();

		try {
			// Abrindo o arquivo c:/dados.dat para escrita
			FileOutputStream fos = new FileOutputStream("dados.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// Salvando os objetos gerenciados pelos DAOs
			daoProprietario.salvarObjetos(oos);
			// Fechando e salvando o arquivo
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		// Método estático da classe System que encerra o programa
		System.exit(0);
	}
	/**
	 * vamos apagar esta classe para criar o model depois
	 */
	public static void main(String[] args) {
		
		CtrlPrograma prg = new CtrlPrograma();
		prg.iniciar();

	}

}

package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.DAOProprietario;
import model.IDAOSerializavel;
import view.IViewerPrincipal;
import view.JanelaPrincipal;


public class CtrlPrograma implements ICtrlPrograma{
	
	private ICtrlManterProprietario ctrlProprietario;
	private ICtrlManterCargo ctrlCargo;
	private IViewerPrincipal jPrincipal;
	
	public CtrlPrograma() {
		//this.ctrlProprietario = new CtrlManterProprietarios(this);
		//this.ctrlCargo = new CtrlManterCargo(this);
	}
	
	public void iniciar(){
		// Cria e apresenta a janela principal
		this.jPrincipal = new JanelaPrincipal(this);
		IDAOSerializavel daoProprietario = (IDAOSerializavel) DAOProprietario.getSingleton();
		
		//
		// Recuperação dos objetos serializados no arquivo c:/base.bin
		//
		try {
			// Abrindo o arquivo para leitura binária
			FileInputStream fis = new FileInputStream("base.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			// Solicitação para os DAOs gerenciarem os objetos recuperados do arquivo
			daoProprietario.recuperarObjetos(ois);
			// Fechando o arquivo 
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo base.bin não encontrado");
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
			// Abrindo o arquivo c:/base.bin para escrita
			FileOutputStream fos = new FileOutputStream("base.bin");
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

	@Override
	public boolean iniciarCasoDeUsoManterCargo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterCargo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterFuncionario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterFuncionario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterProprietarios() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterProprietarios() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterImovel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterImovel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterInquilino() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterInquilino() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterContrato() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterContrato() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterTaxas() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterTaxas() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterBoleto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterBoleto() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterTelefone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterTelefone() {
		// TODO Auto-generated method stub
		return false;
	}

}

package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.DAOFuncionario;
import model.DAOInquilino;
import model.DAOProprietario;
import model.DAOTelefone;
import model.IDAOSerializavel;
import view.IViewerPrincipal;
import view.JanelaPrincipal;


public class CtrlPrograma implements ICtrlPrograma{
	
	private ICtrlManter ctrlCargo;
	private ICtrlManter ctrlFuncionario;
	private ICtrlManter ctrlInquilino;
	private ICtrlManter ctrlProprietario;
	private ICtrlManter ctrlTelefone;
	private IViewerPrincipal jPrincipal;
	
	public CtrlPrograma() {
		this.ctrlCargo = new CtrlManterCargos(this);
		this.ctrlFuncionario= new CtrlManterFuncionarios(this);
		this.ctrlInquilino = new CtrlManterInquilinos(this);
		this.ctrlProprietario = new CtrlManterProprietarios(this);
		this.ctrlTelefone = new CtrlManterTelefones(this);
	}
	
	public void iniciar(){
		this.jPrincipal = new JanelaPrincipal(this);
		IDAOSerializavel daoProprietario = (IDAOSerializavel) DAOProprietario.getSingleton();
		
		//
		// Recuperação dos objetos serializados no arquivo c:/base.bin
		//
		try {
			FileInputStream fis = new FileInputStream("base.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			daoProprietario.recuperarObjetos(ois);
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
		IDAOSerializavel daoCargo = (IDAOSerializavel)DAOProprietario.getSingleton();
		IDAOSerializavel daoFuncionario = (IDAOSerializavel)DAOFuncionario.getSingleton();
		IDAOSerializavel daoInquilino = (IDAOSerializavel)DAOInquilino.getSingleton();
		IDAOSerializavel daoProprietario = (IDAOSerializavel)DAOProprietario.getSingleton();
		IDAOSerializavel daoTelefone = (IDAOSerializavel)DAOTelefone.getSingleton();

		try {
			FileOutputStream fos = new FileOutputStream("base.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// Persistindo os estados das classes no arquino serializado
			daoCargo.salvarObjetos(oos);
			daoFuncionario.salvarObjetos(oos);
			daoInquilino.salvarObjetos(oos);
			daoProprietario.salvarObjetos(oos);
			daoTelefone.salvarObjetos(oos);
			
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 	

		System.exit(0);
	}

	@Override
	public boolean iniciarCasoDeUsoManterCargo() {
		return this.ctrlCargo.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterCargo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterFuncionario() {
		return this.ctrlFuncionario.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterFuncionario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterProprietarios() {
		return this.ctrlProprietario.iniciar();
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
		return this.ctrlInquilino.iniciar();
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

	/**
	 * vamos apagar esta classe para criar o model depois
	 */
	public static void main(String[] args) {
		CtrlPrograma prg = new CtrlPrograma();
		prg.iniciar();
	}

}

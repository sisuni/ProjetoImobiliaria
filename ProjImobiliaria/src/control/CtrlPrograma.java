package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.DAOBoleto;
import model.DAOCargo;
import model.DAOContrato;
import model.DAOFuncionario;
import model.DAOImovel;
import model.DAOInquilino;
import model.DAOProprietario;
import model.DAOTelefone;
import model.IDAOSerializavel;
import view.IViewerPrincipal;
import view.JanelaPrincipal;


public class CtrlPrograma implements ICtrlPrograma{
	
	private ICtrlManter ctrlCargo;
	private ICtrlManter ctrlContrato;
	private ICtrlManter ctrlFuncionario;
	private ICtrlManter ctrlImovel;
	private ICtrlManter ctrlInquilino;
	private ICtrlManter ctrlProprietario;
	private ICtrlManter ctrlBoleto;
	private IViewerPrincipal jPrincipal;
	
	public CtrlPrograma() {
		this.ctrlCargo			= new CtrlManterCargos(this);
		this.ctrlContrato		= new CtrlManterContratos(this);
		this.ctrlFuncionario	= new CtrlManterFuncionarios(this);
		this.ctrlImovel			= new CtrlManterImoveis(this);
		this.ctrlInquilino		= new CtrlManterInquilinos(this);
		this.ctrlProprietario	= new CtrlManterProprietarios(this);
		this.ctrlBoleto			= new CtrlManterBoletos(this);

	}
	
	public void iniciar(){
		this.jPrincipal = new JanelaPrincipal(this);
		IDAOSerializavel daoCargo			= (IDAOSerializavel) DAOCargo.getSingleton();
		IDAOSerializavel daoContrato		= (IDAOSerializavel) DAOContrato.getSingleton();
		IDAOSerializavel daoFuncionario		= (IDAOSerializavel) DAOFuncionario.getSingleton();
		IDAOSerializavel daoImovel			= (IDAOSerializavel) DAOImovel.getSingleton();
		IDAOSerializavel daoInquilino		= (IDAOSerializavel) DAOInquilino.getSingleton();
		IDAOSerializavel daoProprietario	= (IDAOSerializavel) DAOProprietario.getSingleton();
		IDAOSerializavel daoTelefone 		= (IDAOSerializavel) DAOTelefone.getSingleton();
		IDAOSerializavel daoBoleto			= (IDAOSerializavel) DAOBoleto.getSingleton();
		
		//
		// Recuperação dos objetos serializados no arquivo c:/base.bin
		//
		try {
			FileInputStream fis = new FileInputStream("base.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			daoCargo.recuperarObjetos(ois);
			daoContrato.recuperarObjetos(ois);
			daoFuncionario.recuperarObjetos(ois);
			daoImovel.recuperarObjetos(ois);
			daoInquilino.recuperarObjetos(ois);
			daoProprietario.recuperarObjetos(ois);
			daoTelefone.recuperarObjetos(ois);
			daoBoleto.recuperarObjetos(ois);
			
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
		IDAOSerializavel daoCargo			= (IDAOSerializavel)DAOCargo.getSingleton();
		IDAOSerializavel daoContrato		= (IDAOSerializavel)DAOContrato.getSingleton();
		IDAOSerializavel daoFuncionario		= (IDAOSerializavel)DAOFuncionario.getSingleton();
		IDAOSerializavel daoImovel			= (IDAOSerializavel)DAOImovel.getSingleton();
		IDAOSerializavel daoInquilino		= (IDAOSerializavel)DAOInquilino.getSingleton();
		IDAOSerializavel daoProprietario	= (IDAOSerializavel)DAOProprietario.getSingleton();
		IDAOSerializavel daoTelefone 		= (IDAOSerializavel) DAOTelefone.getSingleton();
		IDAOSerializavel daoBoleto			= (IDAOSerializavel)DAOBoleto.getSingleton();

		try {
			FileOutputStream fos = new FileOutputStream("base.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			// Persistindo os estados das classes no arquino serializado
			daoCargo.salvarObjetos(oos);
			daoContrato.salvarObjetos(oos);
			daoFuncionario.salvarObjetos(oos);
			daoImovel.salvarObjetos(oos);
			daoInquilino.salvarObjetos(oos);
			daoProprietario.salvarObjetos(oos);
			daoTelefone.salvarObjetos(oos);
			daoBoleto.salvarObjetos(oos);
			
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
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterCobra() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterCobra() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean iniciarCasoDeUsoManterContrato() {
		return this.ctrlContrato.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterContrato() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterFuncionario() {
		return this.ctrlFuncionario.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterFuncionario() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterImovel() {
		return this.ctrlImovel.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterImovel() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterInquilino() {
		return this.ctrlInquilino.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterInquilino() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterProprietarios() {
		return this.ctrlProprietario.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterProprietarios() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterTaxas() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean terminarCasoDeUsoManterTaxas() {
		return true;
	}

	@Override
	public boolean iniciarCasoDeUsoManterBoleto() {
		return this.ctrlBoleto.iniciar();
	}

	@Override
	public boolean terminarCasoDeUsoManterBoleto() {
		return true;
	}

	/**
	 * vamos apagar esta classe para criar o model depois
	 */
	public static void main(String[] args) {
		CtrlPrograma prg = new CtrlPrograma();
		prg.iniciar();
	}

}

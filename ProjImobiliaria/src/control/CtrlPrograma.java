package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.DAOBoleto;
import model.DAOCargo;
import model.DAOCobra;
import model.DAOContrato;
import model.DAOFuncionario;
import model.DAOImovel;
import model.DAOInquilino;
import model.DAOProprietario;
import model.DAOTaxa;
import model.DAOTelefone;
import model.Funcionario;
import model.IDAOSerializavel;
import view.IViewerPrincipal;
import view.JanelaPrincipal;


public class CtrlPrograma implements ICtrlPrograma{
	
	private ICtrlManter ctrlCargo;
	private ICtrlManter ctrlContrato;
	private ICtrlManterFuncionarios ctrlFuncionario;
	private ICtrlManter ctrlImovel;
	private ICtrlManter ctrlInquilino;
	private ICtrlManter ctrlProprietario;
	private ICtrlManter ctrlBoleto;
	private ICtrlManter ctrlTaxa;
	private IViewerPrincipal jPrincipal;
	private Funcionario funcionarioLogado;
	
	public CtrlPrograma() {
		this.ctrlCargo			= new CtrlManterCargos(this);
		this.ctrlContrato		= new CtrlManterContratos(this);
		this.ctrlFuncionario	= new CtrlManterFuncionarios(this);
		this.ctrlImovel			= new CtrlManterImoveis(this);
		this.ctrlInquilino		= new CtrlManterInquilinos(this);
		this.ctrlProprietario	= new CtrlManterProprietarios(this);
		this.ctrlBoleto			= new CtrlManterBoletos(this);
		this.ctrlTaxa			= new CtrlManterTaxas(this);

	}
	
	public void iniciar(){

		this.iniciarAcesso();
		IDAOSerializavel daoCargo			= (IDAOSerializavel) DAOCargo.getSingleton();
		IDAOSerializavel daoContrato		= (IDAOSerializavel) DAOContrato.getSingleton();
		IDAOSerializavel daoFuncionario		= (IDAOSerializavel) DAOFuncionario.getSingleton();
		IDAOSerializavel daoImovel			= (IDAOSerializavel) DAOImovel.getSingleton();
		IDAOSerializavel daoInquilino		= (IDAOSerializavel) DAOInquilino.getSingleton();
		IDAOSerializavel daoProprietario	= (IDAOSerializavel) DAOProprietario.getSingleton();
		IDAOSerializavel daoTelefone 		= (IDAOSerializavel) DAOTelefone.getSingleton();
		IDAOSerializavel daoBoleto			= (IDAOSerializavel) DAOBoleto.getSingleton();
		IDAOSerializavel daoTaxa			= (IDAOSerializavel) DAOTaxa.getSingleton();
		IDAOSerializavel daoCobra			= (IDAOSerializavel) DAOCobra.getSingleton();
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
			daoTaxa.recuperarObjetos(ois);
			daoCobra.recuperarObjetos(ois);
			
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
		IDAOSerializavel daoTaxa			= (IDAOSerializavel) DAOTaxa.getSingleton();
		IDAOSerializavel daoCobra			= (IDAOSerializavel) DAOCobra.getSingleton();
		
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
			daoTaxa.salvarObjetos(oos);
			daoCobra.salvarObjetos(oos);
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
		return this.ctrlTaxa.iniciar();
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
	

	public boolean iniciarAcesso(){
		funcionarioLogado = null;
		return this.ctrlFuncionario.iniciarAcesso();
	}
	
	public boolean terminarAcesso(){
		System.exit(0);
		return true;
	}
	
	public boolean iniciarMenu(Funcionario usuario){
		this.funcionarioLogado = usuario;
		this.jPrincipal = new JanelaPrincipal(this);
		this.jPrincipal.setUsuario(this.funcionarioLogado);
		return true;
	}
	
	public boolean iniciarAlterarSenha(){
		return this.ctrlFuncionario.iniciarAlterarSenha(this.funcionarioLogado);
	}
	
	public boolean terminarAlterarSenha(){
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

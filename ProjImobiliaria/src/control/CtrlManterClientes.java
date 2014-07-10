package control;

import java.util.Set;

import model.Cliente;
import model.Telefone;
import view.IViewerSalvaCliente;


public abstract class CtrlManterClientes implements ICtrlManterClientes {
	
	private ICtrlManterTelefones ctrlTel;
	
	private IViewerSalvaCliente jCliente;
	
	private Cliente cliente;
	
	public boolean iniciarCasoDeUsoManterTelefone(IViewerSalvaCliente jc){
		this.ctrlTel = new CtrlManterTelefones(this);
		this.jCliente = jc;
		return this.ctrlTel.iniciar();
	}
	
	public  boolean iniciarIncluirTelefone(){
		return this.ctrlTel.iniciarIncluir();
	}
	
	public  void cancelarIncluirTelefone(){
		this.ctrlTel.cancelarIncluir();
	}
	
	public  boolean iniciarAlterarTelefone(int pos){
		return this.ctrlTel.iniciarAlterar(pos);
	}
	 
	public  void cancelarAlterarTelefone(){
		this.ctrlTel.cancelarAlterar();
	}
	
	public  boolean iniciarExcluirTelefone(int pos){
		return this.ctrlTel.iniciarExcluir(pos);
	}
 
	public  void cancelarExcluirTelefone(){
		this.ctrlTel.cancelarExcluir();
	}
	
	public boolean terminarCasoDeUsoManterTelefone(){
		return ctrlTel.terminar();
	}
		
	public IViewerSalvaCliente getJanela(){
		return this.jCliente;
	}
	
	public Set<Telefone> getTelefones(){
		return this.ctrlTel.getTelefones();
	}
	
	public void setCliente(Cliente c){
		this.cliente = c;
	}
	
	public Cliente getCliente(){
		return this.cliente;
	}
	
	public void atualizarListaTelefones(){
		this.ctrlTel.atualizarInterface();
	}	
	
}

package control;

import java.util.Set;

import model.ModelException;
import model.Telefone;
import view.IViewerSalvaCliente;

public abstract class CtrlManterClientes implements ICtrlManterClientes {
	private ICtrlManterTelefones ctrlTel;
	private IViewerSalvaCliente janela;
	
	@Override
	public boolean iniciarCasoDeUsoManterTelefone() {
		this.ctrlTel = new CtrlManterTelefones(this);
		return this.ctrlTel.iniciar();
	}
	
	@Override
	public boolean iniciarIncluirTelefone(){
		//this.iniciarCasoDeUsoManterTelefone();
		return this.ctrlTel.iniciarIncluir();
	}
	
	@Override
	public void cancelarIncluirTelefone(){
		this.ctrlTel.cancelarIncluir();
	}
	
	@Override
	public boolean iniciarAlterarTelefone(int pos){
		//this.iniciarCasoDeUsoManterTelefone();
		return this.ctrlTel.iniciarAlterar(pos);
	}
	
	@Override
	public void cancelarAlterarTelefone(){
		this.ctrlTel.cancelarAlterar();
	}
	
	@Override
	public boolean iniciarExcluirTelefone(int pos){
		//this.iniciarCasoDeUsoManterTelefone();
		return this.ctrlTel.iniciarExcluir(pos);
	}
	
	@Override
	public void cancelarExcluirTelefone(){
		this.ctrlTel.cancelarExcluir();
	}
	
	@Override
	public boolean terminarCasoDeUsoManterTelefone() {
		return true;
	}
	
	@Override
	public void setJanela(IViewerSalvaCliente j){
		if(j!=null)
			this.janela = j;
		else
			this.janela = null;
	}

	public IViewerSalvaCliente getJanela(){
		return this.janela;
	}
	
	@Override
	public Set<Telefone> getTelefones(){
		if(this.ctrlTel.getTelefones().isEmpty())
			return this.ctrlTel.getTelefones();
		
		return null;
	}
}

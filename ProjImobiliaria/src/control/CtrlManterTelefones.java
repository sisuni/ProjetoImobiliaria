package control;

import java.text.ParseException;
import java.util.Set;
import java.util.TreeSet;

import model.Cliente;
import model.DAOTelefone;
//import model.IDAO;
import model.ModelException;
import model.Telefone;
import view.IViewerSalvaCliente;
import view.IViewerSalvaTelefone;
import view.JanelaExcluirTelefone;
import view.JanelaSalvaTelefone;


public class CtrlManterTelefones implements ICtrlManterTelefones {

	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlManterClientes ctrlCli;
	
	private IViewerSalvaCliente jCliente;
	
	private IViewerSalvaTelefone jTelefone;

	private Telefone telefoneAtual;
	
	private Set<Telefone> listaTelefones;
	
	private DAOTelefone dao = (DAOTelefone) DAOTelefone.getSingleton();
	
	private boolean emExecucao;
	
	private Operacao operacao;
	
	
	public CtrlManterTelefones(ICtrlManterClientes c){
		this.ctrlCli = c;
	}
	
	@Override
	public boolean iniciar(){
		if(this.emExecucao)
			return false;
		
		this.jCliente = ctrlCli.getJanela();
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean terminar(){
		if(!this.emExecucao)
			return false;
		
		this.jCliente = null;
		this.ctrlCli = null;
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarIncluir(){
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.INCLUSAO;
		try {
			this.jTelefone = new JanelaSalvaTelefone(this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public void cancelarIncluir(){
		if(this.operacao == Operacao.INCLUSAO){
			this.jTelefone.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean incluir(String tipo, String numero) throws ModelException{
		if(this.operacao != Operacao.INCLUSAO)
			return false;
		
		Telefone novo;
		if(ctrlCli.getCliente() == null)
			 novo = new Telefone(tipo,numero,null);
		else
			 novo = new Telefone(tipo,numero,ctrlCli.getCliente());
		
		dao.salvar(novo);
		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarAlterar(int pos){
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.ALTERACAO;
		
		Set<Telefone> lista = dao.recuperarPeloCliente(ctrlCli.getCliente());
		int i = 0;
		for(Telefone t : lista){
			if(i++ == pos)
				this.telefoneAtual = t;
		}
					
		try {
			this.jTelefone = new JanelaSalvaTelefone(this);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jTelefone.atualizarCampos(this.telefoneAtual.getTipo(), this.telefoneAtual.getNumero());
		return true;
	}
	
	@Override
	public void cancelarAlterar(){
		if(this.operacao == Operacao.ALTERACAO){
			this.jTelefone.setVisible(false);
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean alterar(String tipo, String numero) throws ModelException{
		if(this.operacao != Operacao.ALTERACAO)
			return false;
		
		this.telefoneAtual.setTipo(tipo);
		this.telefoneAtual.setNumero(numero);
				
		dao.atualizar(this.telefoneAtual);
		
		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarExcluir(int pos){
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.EXCLUSAO;
			
		Set<Telefone> lista = dao.recuperarPeloCliente(ctrlCli.getCliente());
		int i = 0;
		for(Telefone t : lista){
			if(i++ == pos)
				this.telefoneAtual = t;
		}
		
		new JanelaExcluirTelefone(this, this.telefoneAtual);
		return true;
	}
	
	@Override
	public void cancelarExcluir(){
		if(this.operacao == Operacao.EXCLUSAO){
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean excluir(){
		if(this.operacao != Operacao.EXCLUSAO)
			return false;
		
		ctrlCli.getCliente().removeTelefone(this.telefoneAtual);
		dao.remover(this.telefoneAtual);
		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public void atualizarInterface(){
		this.jCliente.limpar();
		
		for(Telefone t : dao.getListaObjs()){
			if(!(t.getCliente() == null)){
				if(t.getCliente() == ctrlCli.getCliente())
					this.jCliente.incluirLinha(t);
			}
		}
	}
	
	@Override
	 public Set<Telefone> getTelefones(){
		 return this.listaTelefones;
	 }
}

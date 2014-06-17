package control;

import java.text.ParseException;
import java.util.Set;
import java.util.TreeSet;

import model.Cliente;
import model.DAOTelefone;
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
	
	private Set<Telefone> listaTelefones = new TreeSet<Telefone>();
		
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
				
		this.recuperarDAO();
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
		
		this.persisteDAO();
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
		
		listaTelefones.add(novo);
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
		
		Set<Telefone> lista = this.recuperarPeloCliente(ctrlCli.getCliente());
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
		
		Set<Telefone> lista = this.recuperarPeloCliente(ctrlCli.getCliente());
		int i = 0;
		for(Telefone t : listaTelefones){
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
		this.listaTelefones.remove(this.telefoneAtual);
		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public void atualizarInterface(){
		this.jCliente.limpar();
		
		for(Telefone t : this.getTelefones()){
			if (t.getCliente() == ctrlCli.getCliente())
				this.jCliente.incluirLinha(t);
		}
	}
	
	@Override
	 public Set<Telefone> getTelefones(){
		 return this.listaTelefones;
	 }
	
	public Set<Telefone> recuperarPeloCliente(Cliente c){
		Set<Telefone> listaTelClientes = new TreeSet<Telefone>();
		for(Telefone t : this.listaTelefones){
			if(t.getCliente().equals(c))
				listaTelClientes.add(t);
		}
		return listaTelClientes;
	}
	
	public void recuperarDAO(){
		if(!(dao.getListaObjs().isEmpty())){
			this.listaTelefones = new TreeSet<Telefone>(dao.getListaObjs());
		}
	}
	
	public void persisteDAO(){
		this.dao.setListaObjs(this.listaTelefones);
	}
	
}

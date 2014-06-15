package control;

import java.text.ParseException;

import model.Cliente;
import model.DAOProprietario;
import model.IDAO;
import model.ModelException;
import model.Proprietario;
import model.Telefone;
import view.IViewer;
import view.IViewerSalvaProprietario;
import view.JanelaExcluirProprietario;
import view.JanelaProprietarios;
import view.JanelaSalvaProprietario;

public class CtrlManterProprietarios extends CtrlManterClientes implements ICtrlManterProprietarios {
	
	
	private enum Operacao{
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
		
	private IViewer jCadastro;
	
	private IViewerSalvaProprietario jProprietario;
	
	private Proprietario proprietarioAtual;
	
	private IDAO<Proprietario> dao = DAOProprietario.getSingleton();
	
	private boolean emExecucao;
	
	private Operacao operacao;
	
	public CtrlManterProprietarios(ICtrlPrograma p){
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar(){
		if(this.emExecucao)
			return false;
		
		this.jCadastro = new JanelaProprietarios(this);
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean terminar(){
		if(!this.emExecucao)
			return false;
		
		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterProprietarios();
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
			this.jProprietario = new JanelaSalvaProprietario(this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.iniciarCasoDeUsoManterTelefone(jProprietario);
		return true;
	}
	
	@Override
	public void cancelarIncluir(){
		if(this.operacao == Operacao.INCLUSAO){
			this.jProprietario.setVisible(false);
			this.setCliente(null);
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean incluir(String nome, String cpf, String email, 
	String endereco, String banco, String agencia, String conta) throws ModelException{
		
		if(this.operacao != Operacao.INCLUSAO)
			return false;
		
		Proprietario novo = new Proprietario(nome, cpf, email, endereco, 
												banco, agencia, conta);
		
		for (Telefone t : this.getTelefones()){
			if(t.getCliente() == null)
				novo.addTelefone(t);
		}
		
		this.setCliente(novo);
		dao.salvar(novo);
		this.jProprietario.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		this.terminarCasoDeUsoManterTelefone();
		return true;
	}
	
	@Override
	public boolean iniciarAlterar(int pos){
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.ALTERACAO;
		this.proprietarioAtual = dao.recuperar(pos);
		try {
			this.jProprietario = new JanelaSalvaProprietario(this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setCliente(this.proprietarioAtual);
		this.iniciarCasoDeUsoManterTelefone(jProprietario);
		this.atualizarListaTelefones();
		this.jProprietario.atualizarCampos(this.proprietarioAtual.getNome(),
				this.proprietarioAtual.getCpf(), this.proprietarioAtual.getEmail(),
				this.proprietarioAtual.getEndereco(), this.proprietarioAtual.getBanco(), 
				this.proprietarioAtual.getAgencia(), this.proprietarioAtual.getConta());
		
		return true;
	}
	
	@Override
	public void cancelarAlterar(){
		if(this.operacao == Operacao.ALTERACAO){
			this.jProprietario.setVisible(false);
			this.proprietarioAtual = null;
			this.setCliente(null);
			this.atualizarListaTelefones();
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean alterar(String nome, String cpf, String email, 
	String endereco, String banco, String agencia, String conta) throws ModelException{
		
		if(this.operacao != Operacao.ALTERACAO)
			return false;
		
		this.proprietarioAtual.setNome(nome);
		this.proprietarioAtual.setCpf(cpf);
		this.proprietarioAtual.setEmail(email);
		this.proprietarioAtual.setEndereco(endereco);
		this.proprietarioAtual.setBanco(banco);
		this.proprietarioAtual.setAgencia(agencia);
		this.proprietarioAtual.setConta(conta);
		
		dao.atualizar(this.proprietarioAtual);
	
		this.jProprietario.setVisible(false);
		this.atualizarInterface();
		this.proprietarioAtual = null;
		
		this.terminarCasoDeUsoManterTelefone();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarExcluir(int pos){
		
		if(this.operacao != Operacao.DISPONIVEL)
			return false;
		
		this.operacao = Operacao.EXCLUSAO;
		this.proprietarioAtual = dao.recuperar(pos);
		new JanelaExcluirProprietario(this,this.proprietarioAtual);
		return true;
	}
	
	@Override
	public void cancelarExcluir(){
		if(this.operacao == Operacao.EXCLUSAO){
			this.proprietarioAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	@Override
	public boolean excluir(){
		if(this.operacao != Operacao.EXCLUSAO)
			return false;
		
		dao.remover(this.proprietarioAtual);
		this.atualizarInterface();
		this.proprietarioAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public void atualizarInterface(){
		this.jCadastro.limpar();
		
		for(int i = 0; i < dao.getNumObjs(); i++){
			Proprietario pro = dao.recuperar(i);
			this.jCadastro.incluirLinha(pro);
		}
	}
	
}

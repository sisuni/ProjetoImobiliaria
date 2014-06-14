package control;

import java.text.ParseException;
import java.util.Set;
import java.util.TreeSet;

import model.Cliente;
import model.DAOTelefone;
import model.IDAO;
import model.ModelException;
import model.Proprietario;
import model.Telefone;
import view.IViewerSalvaCliente;
import view.IViewerSalvaTelefone;
import view.JanelaExcluirTelefone;
import view.JanelaSalvaTelefone;

public class CtrlManterTelefones implements ICtrlManterTelefones {
	
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	private ICtrlManterClientes ctrl;
	
	private IViewerSalvaCliente jCliente;
	
	private IViewerSalvaTelefone jTelefone;
	
	private Telefone telefoneAtual;
		
	private IDAO<Telefone> dao = DAOTelefone.getSingleton();
	
	private boolean emExecucao;
	
	private Operacao operacao;
	
	public CtrlManterTelefones(ICtrlManterClientes ctrl){
		this.ctrl = ctrl;
	}
		
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCliente = ctrl.getJanela();
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if(!this.emExecucao)
			return false;

		this.ctrl.terminarCasoDeUsoManterTelefone();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
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
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jTelefone.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean incluir(String tipo, String numero, Cliente cliente) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Telefone novo = new Telefone(tipo, numero, null);
		dao.salvar(novo);
		
		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.telefoneAtual = dao.recuperar(pos);
		try {
			this.jTelefone = new JanelaSalvaTelefone(this);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.jTelefone.atualizarCampos(
				this.telefoneAtual.getTipo(),
				this.telefoneAtual.getNumero());
		
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jTelefone.setVisible(false);
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	public boolean alterar(String tipo, String numero, Cliente cliente) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.telefoneAtual.setTipo(tipo);
		this.telefoneAtual.setNumero(numero);
		this.telefoneAtual.setCliente(cliente);
		
		dao.atualizar(this.telefoneAtual);

		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.telefoneAtual = dao.recuperar(pos);
		new JanelaExcluirTelefone(this, this.telefoneAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.telefoneAtual);

		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCliente.limpar();

		if (this.operacao == Operacao.INCLUSAO){
			for(int i = 0; i < dao.getNumObjs(); i++) {
				Telefone telCliente = dao.recuperar(i);
				this.jCliente.incluirLinha(telCliente);
			}
		}else{
			for(int i = 0; i < dao.getNumObjs(); i++) {
				Telefone telCliente = dao.recuperar(i);
				if(this.telefoneAtual.getCliente().equals(telCliente.getCliente()))
					this.jCliente.incluirLinha(telCliente);
			}
		}
	}
	
	@Override
	public Set<Telefone> getTelefones(){
		return dao.getListaObjs();
	}
	
}

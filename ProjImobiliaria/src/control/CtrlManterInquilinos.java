package control;

import model.Cargo;
import model.DAOInquilino;
import model.IDAO;
import model.Inquilino;
import model.ModelException;
import view.IViewer;
import view.IViewerSalvaInquilino;
import view.JanelaExcluirCargo;
import view.JanelaInquilinos;
import view.JanelaSalvaCargo;
import view.JanelaSalvaInquilino;

public class CtrlManterInquilinos implements ICtrlManter{
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaInquilino jInquilino;
	
	private Inquilino inquilinoAtual;
	
	private IDAO<Inquilino> dao = DAOInquilino.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterInquilinos(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaInquilinos(this);
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if(!this.emExecucao)
			return false;

		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterInquilino();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jInquilino = new JanelaSalvaInquilino(this);
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jInquilino.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean incluir(String nome, String cpf, String email, String endereco, String endAnteriorCompleto) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Inquilino novo = new Inquilino(nome, cpf, email, endereco, endAnteriorCompleto);

		dao.salvar(novo);

		this.jInquilino.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.inquilinoAtual = dao.recuperar(pos);
		this.jInquilino = new JanelaSalvaInquilino(this);
		this.jInquilino.atualizarCampos(
				this.inquilinoAtual.getNome(),
				this.inquilinoAtual.getCpf(),
				this.inquilinoAtual.getEmail(),
				this.inquilinoAtual.getEndereco(),
				this.inquilinoAtual.getEndAnteriorCompleto()
				);
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jInquilino.setVisible(false);
			this.inquilinoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String nome, String cpf, String email, String endereco, String endAnteriorCompleto) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.inquilinoAtual.setNome(nome);
		this.inquilinoAtual.setCpf(cpf);
		this.inquilinoAtual.setEmail(email);
		this.inquilinoAtual.setEndereco(endereco);
		this.inquilinoAtual.setEndAnteriorCompleto(endAnteriorCompleto);

		dao.atualizar(this.inquilinoAtual);

		this.jInquilino.setVisible(false);
		this.atualizarInterface();
		this.inquilinoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.inquilinoAtual = dao.recuperar(pos);
		new JanelaExcluirCargo(this, this.inquilinoAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.inquilinoAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.inquilinoAtual);

		this.atualizarInterface();
		this.inquilinoAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Inquilino inquilino = dao.recuperar(i);
			this.jCadastro.incluirLinha(inquilino);
		}
	}

}

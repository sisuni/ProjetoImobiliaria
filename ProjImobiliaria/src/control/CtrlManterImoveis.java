package control;

import model.DAOImovel;
import model.IDAO;
import model.Imovel;
import model.ModelException;
import model.Proprietario;
import view.IViewer;
import view.IViewerSalvaImovel;
import view.JanelaCargos;
import view.JanelaExcluirImovel;
import view.JanelaSalvaImovel;

public class CtrlManterImoveis implements ICtrlManterImoveis {
	//
	// ATRIBUTOS
	//
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}

	private ICtrlPrograma ctrlPrg;

	private IViewer jCadastro;

	private IViewerSalvaImovel jImovel;

	private Imovel imovelAtual;

	private IDAO<Imovel> dao = DAOImovel.getSingleton();

	private boolean emExecucao;

	private Operacao operacao;

	//
	// MÉTODOS
	//
	/**
	 * Construtor da classe
	 */
	public CtrlManterImoveis(ICtrlPrograma p) {
		this.ctrlPrg = p;
	}

	@Override
	public boolean iniciar() {
		if (this.emExecucao)
			return false;

		this.jCadastro = new JanelaCargos(this);
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if (!this.emExecucao)
			return false;

		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterCargo();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jImovel = new JanelaSalvaImovel(this);
		return true;
	}

	@Override
	public boolean incluir(String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao,
			String finalidade, String tipo, boolean status,
			Proprietario proprietario) throws ModelException {
		if (this.operacao != Operacao.INCLUSAO)
			return false;

		Imovel novo = new Imovel(uf, cidade, bairro, logradouro, numero,
				complemento, valorBase, dimensoes, qtdQuartos, descricao,
				finalidade, tipo, status, proprietario);

		dao.salvar(novo);

		this.jImovel.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if (this.operacao == Operacao.INCLUSAO) {
			this.jImovel.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean iniciarAlterar(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.imovelAtual = dao.recuperar(pos);
		this.jImovel = new JanelaSalvaImovel(this);
		this.jImovel
				.atualizarCampos(
						this.imovelAtual.getUf(),
						this.imovelAtual.getCidade(),
						this.imovelAtual.getBairro(),
						this.imovelAtual.getLogradouro(),
						this.imovelAtual.getNumero(),
						this.imovelAtual.getComplemento(),
						this.imovelAtual.getValorBase(),
						this.imovelAtual.getDimensoes(),
						this.imovelAtual.getQtdQuartos(),
						this.imovelAtual.getDescricao(),
						this.imovelAtual.getFinalidade(),
						this.imovelAtual.getTipo(),
						this.imovelAtual.isStatus(),
						this.imovelAtual.getProprietario());
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if (this.operacao == Operacao.ALTERACAO) {
			this.jImovel.setVisible(false);
			this.imovelAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean alterar(String uf, String cidade, String bairro, String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao, String finalidade, String tipo, boolean status, Proprietario proprietario) throws ModelException {
		if (this.operacao != Operacao.ALTERACAO)
			return false;

		this.imovelAtual.setUf(uf);
		this.imovelAtual.setCidade(cidade);
		this.imovelAtual.setBairro(bairro);
		this.imovelAtual.setLogradouro(logradouro);
		this.imovelAtual.setNumero(numero);
		this.imovelAtual.setComplemento(complemento);
		this.imovelAtual.setValorBase(valorBase);
		this.imovelAtual.setDimensoes(dimensoes);
		this.imovelAtual.setQtdQuartos(qtdQuartos);
		this.imovelAtual.setDescricao(descricao);
		this.imovelAtual.setFinalidade(finalidade);
		this.imovelAtual.setTipo(tipo);
		this.imovelAtual.setStatus(status);
		this.imovelAtual.setProprietario(proprietario);

		dao.atualizar(this.imovelAtual);

		this.jImovel.setVisible(false);
		this.atualizarInterface();
		this.imovelAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if (this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.imovelAtual = dao.recuperar(pos);
		new JanelaExcluirImovel(this, this.imovelAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if (this.operacao == Operacao.EXCLUSAO) {
			this.imovelAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if (this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.imovelAtual);

		this.atualizarInterface();
		this.imovelAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for (int i = 0; i < dao.getNumObjs(); i++) {
			Imovel imovel = dao.recuperar(i);
			this.jCadastro.incluirLinha(imovel);
		}
	}

}

package model;

public class Imovel {
	
	private int cod;
	private String uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	private int numero;
	private String complemento;
	private float valorBase;
	private String dimensoes;
	private int qtdQuartos;
	private String descricao;
	private String finalidade;
	private boolean status;
	private Proprietario proprietario;
	private Tipo tipo;
	
	public Imovel(int cod, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao,
			String finalidade, boolean status, Proprietario proprietario, Tipo tipo) {
		this.cod = cod;
		this.uf = uf;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.valorBase = valorBase;
		this.dimensoes = dimensoes;
		this.qtdQuartos = qtdQuartos;
		this.descricao = descricao;
		this.finalidade = finalidade;
		this.status = status;
		this.setProprietario(proprietario);
		this.tipo = tipo;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public float getValorBase() {
		return valorBase;
	}

	public void setValorBase(float valorBase) {
		this.valorBase = valorBase;
	}

	public String getDimensoes() {
		return dimensoes;
	}

	public void setDimensoes(String dimensoes) {
		this.dimensoes = dimensoes;
	}

	public int getQtdQuartos() {
		return qtdQuartos;
	}

	public void setQtdQuartos(int qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	/**
	 * M�todo que garante o relacionamento entre o Imovel e o Proprietario
	 * seguindo as probilidades eventuais:
	 * @param proprietario
	 */
	public void setProprietario(Proprietario proprietario) {
		// Se a situa��o atual j� estiver igual, n�o precisa fazer nada
		if(this.proprietario == proprietario)
			return;

		if(proprietario != null){
			
			// Se j� pertencer a outro departamento remover primeiro
			if(this.proprietario != null){
				this.proprietario.removeImovel(this);
				this.proprietario = proprietario;
			}else{
				if(this.proprietario == null){
					this.proprietario = proprietario;
					this.proprietario.inserirImovel(this);
				}
			}

		}else{
			
			// Somente nulifica se possuir algum proprietario
			if(this.proprietario != null){
				Proprietario antigo = this.proprietario;
				this.proprietario = null;
				antigo.removeImovel(this);
			}
	
		}
	}

	public Tipo getTipo() {
		return tipo;
	}

	
	/**
	 * M�todo que garante o relacionamento entre o Imovel e o Tipo
	 * seguindo as probilidades eventuais:
	 * @param proprietario
	 */
	public void setTipo(Tipo tipo) {
		// Se a situa��o atual j� estiver igual, n�o precisa fazer nada
		if(this.tipo == tipo)
			return;

		if(tipo != null){
			
			// Se j� pertencer a outro departamento remover primeiro
			if(this.tipo != null){
				this.tipo.removeImovel(this);
				this.tipo = tipo;
			}else{
				if(this.tipo == null){
					this.tipo = tipo;
					this.tipo.inserirImovel(this);
				}
			}

		}else{
			
			// Somente nulifica se possuir algum proprietario
			if(this.tipo != null){
				Tipo antigo = this.tipo;
				this.proprietario = null;
				antigo.removeImovel(this);
			}
	
		}
	}
	

}

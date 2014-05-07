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
	private String tipo;
	private boolean status;
	private Proprietario proprietario;
		
	public Imovel(int cod, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao,
			String finalidade, String tipo, boolean status, Proprietario proprietario) {
		this.setCod(cod);
		this.setUf(uf);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.setValorBase(valorBase);
		this.setDimensoes(dimensoes);
		this.setQtdQuartos(qtdQuartos);
		this.setDescricao(descricao);
		this.setFinalidade(finalidade);
		this.setStatus(status);
		this.setProprietario(proprietario);
		this.setTipo(tipo);
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

	public String getTipo() {
		return this.tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * Método que garante o relacionamento entre o Imovel e Proprietario
	 * seguindo as probilidades eventuais:
	 * @param proprietario
	 */
	public void setProprietario(Proprietario proprietario) {
		// Se a situação atual já estiver igual, não precisa fazer nada
		if(this.proprietario == proprietario)
			return;

		if(proprietario != null){
			
			// Se já pertencer a outro Proprietario remover primeiro
			if(this.proprietario != null){
				this.proprietario.removeImovel(this);
				this.proprietario = proprietario;
			}else{
				if(this.proprietario == null){
					this.proprietario = proprietario;
					this.proprietario.addImovel(this);
				}
			}

		}else{
			
			// Somente nulifica se possuir algum Proprietario
			if(this.proprietario != null){
				Proprietario antigo = this.proprietario;
				this.proprietario = null;
				antigo.removeImovel(this);
			}
	
		}
	}

}

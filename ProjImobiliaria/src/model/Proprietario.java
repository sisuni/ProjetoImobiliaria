package model;

public class Proprietario extends Cliente {
	
	private String banco;
	private int agencia;
	private String conta;
	private String nomeTitular;
	private String cpfTitular;
	private Imovel[] listaImoveis;
	public static final int NUM_MAX_IMOVEIS = 10;
	
	public Proprietario(int cod, String nome, String cpf, String rg,
			String email, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, String banco,
			int agencia, String conta, String nomeTitular, String cpfTitular) {
		super(cod, nome, cpf, rg, email, uf, cidade, bairro, logradouro, numero, complemento);
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.nomeTitular = nomeTitular;
		this.cpfTitular = cpfTitular;
		this.listaImoveis = new Imovel[NUM_MAX_IMOVEIS];
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getCpfTitular() {
		return cpfTitular;
	}

	public void setCpfTitular(String cpfTitular) {
		this.cpfTitular = cpfTitular;
	}
	
	// Somente deve existir o get para esta lista pq o set é pelo método inserirImovel();
	public Imovel[] getListaImoveis() {
		return listaImoveis;
	}

	public static int getNumMaxImoveis() {
		return NUM_MAX_IMOVEIS;
	}

	

}

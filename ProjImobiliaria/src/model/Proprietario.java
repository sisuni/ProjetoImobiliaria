package model;

import java.util.Set;
import java.util.TreeSet;

public class Proprietario extends Cliente {
	
	private String banco;
	private int agencia;
	private String conta;
	private Set<Imovel> listaImoveis;
	private int numImoveis;
	
	public Proprietario(int cod, String nome, String cpf, String rg,
			String email, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, String banco,
			int agencia, String conta) {
		super(cod, nome, cpf, rg, email, uf, cidade, bairro, logradouro, numero, complemento);
		this.setBanco(banco);
		this.setAgencia(agencia);
		this.setConta(conta);
		this.listaImoveis = new TreeSet<Imovel>();
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

	// Somente deve existir o get para esta lista pq o set é pelo método inserirImovel();
	public Set<Imovel> getListaImoveis() {
		return listaImoveis;
	}

	public int getNumImoveis() {
		return numImoveis;
	}

	/**
	 * Método para inclusão de imoveis no array listaImoveis
	 * @param imovel
	 */
	public void addImovel(Imovel novoImovel){
		if (this.listaImoveis.contains(novoImovel))
			return;
		this.listaImoveis.add(novoImovel);
		novoImovel.setProprietario(this);
		
	}
	
	/**
	 * Método para exclusão de imoveis do array listaImoveis
	 * @param exImovel
	 */
	public void removeImovel(Imovel exImovel){
		if (! this.listaImoveis.contains(exImovel))
			return;
		this.listaImoveis.remove(exImovel);
		exImovel.setProprietario(null);

	}

}

package model;

import java.util.Set;
import java.util.TreeSet;

public class Proprietario extends Cliente {
	
	private String banco;
	private int agencia;
	private String conta;
	private Set<Imovel> listaImoveis;
	private int numImoveis; //Ainda não sei pra que server esse atributo
	
	public Proprietario(int cod, String nome, String cpf, String rg,
			String email, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, String banco,
			int agencia, String conta) throws ModelException{
		super(cod, nome, cpf, rg, email, uf, cidade, bairro, logradouro, numero, complemento);
		this.setBanco(banco);
		this.setAgencia(agencia);
		this.setConta(conta);
		this.listaImoveis = new TreeSet<Imovel>();
	}

	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getAgencia() {
		return this.agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return this.conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public Set<Imovel> getImoveis() {
		return this.listaImoveis;
	}

	public int getNumImoveis() {
		return this.numImoveis;
	}

	public void addImovel(Imovel novoImovel){
		if (this.listaImoveis.contains(novoImovel))
			return;
		this.listaImoveis.add(novoImovel);
		novoImovel.setProprietario(this);
		
	}
	
	public void removeImovel(Imovel exImovel){
		if (! this.listaImoveis.contains(exImovel))
			return;
		this.listaImoveis.remove(exImovel);
		exImovel.setProprietario(null);

	}

}

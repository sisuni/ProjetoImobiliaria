package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Proprietario extends Cliente implements Serializable, ITabelavel{
	
	private String banco;
	private String agencia;
	private String conta;
	private Set<Imovel> listaImoveis;
	
	public Proprietario(String nome, String cpf, String email, String endereco, String banco, String agencia, String conta) throws ModelException{
		super(nome, cpf, email, endereco);
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

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
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

	@Override
	public Object[] getData() {
		return new Object[]{
				this.getNome(), 
				this.getCpf(), 
				this.getEmail(), 
				this.getEndereco(), 
				this.banco, 
				this.agencia, 
				this.conta,
				this.getTelefones().size(),
				this.listaImoveis.size()
		};
	}

	/** 
	 * Implementação do método toString que retorna uma String
	 * que descreve o objeto Proprietario
	 */
	public String toString() {
		return this.getNome() + " - " + this.getCpf();
	}

}

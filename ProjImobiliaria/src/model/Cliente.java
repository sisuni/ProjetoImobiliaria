package model;

import java.util.Set;
import java.util.TreeSet;

public abstract class Cliente extends Pessoa {

	private String cpf;
	private String rg;
	private String email;
	private String uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	private int numero;
	private String complemento;
	private Set<Telefone> listaTelefones;
	
	public Cliente(int cod, String nome, String cpf, String rg, String email,
			String uf, String cidade, String bairro, String logradouro,
			int numero, String complemento) {
		super(cod, nome);
		this.setCpf(cpf);
		this.setRg(rg);
		this.setEmail(email);
		this.setUf(uf);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.listaTelefones = new TreeSet<Telefone>();
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public Set<Telefone> getTelefones(){
		return this.listaTelefones;
	}

	public void addTelefone(Telefone novoTel) {
		if (this.listaTelefones.contains(novoTel))
			return;
		this.listaTelefones.add(novoTel);
		novoTel.setCliente(this);
		
	}

	public void removeTelefone(Telefone antigoTel) {
		if(! this.listaTelefones.contains(antigoTel))
			return;
		this.listaTelefones.remove(antigoTel);
		antigoTel.setCliente(null);
	}

}

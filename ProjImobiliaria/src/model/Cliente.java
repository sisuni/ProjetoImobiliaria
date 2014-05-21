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
	
	public Cliente(String nome, String cpf, String rg, String email,
			String uf, String cidade, String bairro, String logradouro,
			int numero, String complemento) throws ModelException{
		super(nome);
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

	public static boolean validarCPF(String cpf) {
		if (cpf == null)
			return false;

		int c1 = 0, c2 = 0, dv1, dv2, i, j = 0;

		for (i = 1; i <= 9; i++) {
			c1 += i * Integer.parseInt(String.valueOf(cpf.charAt(j)));
			j++;
		}

		dv1 = c1 % 11;
		if (dv1 == 10)
			dv1 = 0;

		j = 0;

		for (i = 0; i <= 9; i++) {
			c2 += i * Integer.parseInt(String.valueOf(cpf.charAt(j)));
			j++;
		}

		dv2 = c2 % 11;
		if (dv2 == 10)
			dv2 = 0;

		if ((dv1 == Integer.parseInt(String.valueOf(cpf.charAt(9))) && (dv2 == Integer
				.parseInt(String.valueOf(cpf.charAt(10))))))
			return true;
		else
			return false;
	}
	
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) throws ModelException{
		if(validarCPF(cpf))
			this.cpf = cpf;
		else
			throw new ModelException("CPF inválido: " + cpf);
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

	public void removeTelefone(Telefone exTel) {
		if(! this.listaTelefones.contains(exTel))
			return;
		this.listaTelefones.remove(exTel);
		exTel.setCliente(null);
	}

}

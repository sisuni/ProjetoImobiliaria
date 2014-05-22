package model;

import java.util.Set;
import java.util.TreeSet;

public abstract class Cliente extends Pessoa {

	private String cpf;
	private String email;
	private String endereco;
	private Set<Telefone> listaTelefones;
	
	public Cliente(String nome, String cpf, String email, String endereco) throws ModelException{
		super(nome);
		this.setCpf(cpf);
		this.setEmail(email);
		this.setEndereco(endereco);
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

		if ((dv1 == Integer.parseInt(String.valueOf(cpf.charAt(9))) && (dv2 == Integer.parseInt(String.valueOf(cpf.charAt(10))))))
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
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

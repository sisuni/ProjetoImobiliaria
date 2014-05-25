package model;

import java.io.Serializable;

import control.ITabelavel;

public class Telefone  implements Serializable, ITabelavel {
	
	private String tipo;
	private String numero;
	private Cliente cliente;

	public Telefone(String tipo, String numero, Cliente cliente) {
		this.setTipo(tipo);
		this.setNumero(numero);
		this.setCliente(cliente);
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		
		if (this.cliente == cliente)
			return;
		
		if(cliente == null){
			Cliente antigo = this.cliente;
			this.cliente = null;
			antigo.removeTelefone(this);				
		}else{
			if(this.cliente != null)
				this.cliente.removeTelefone(this);
			this.cliente = cliente;
			cliente.addTelefone(this);
		}
	}

	@Override
	public Object[] getData() {
		return new Object[]{
				this.getTipo(), 
				this.getNumero(), 
		};
	}
	
	public String toString() {
		return this.getNumero();
	}

}

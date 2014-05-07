package model;

public class Telefone {
	
	private String tipo;
	private String numero;
	private Cliente cliente;

	public Telefone(String tipo, String numero, Cliente cliente) {
		this.setTipo(tipo);
		this.setNumero(numero);
		this.setCliente(cliente);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
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

}

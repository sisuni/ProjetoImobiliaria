package model;

public class Cobra {
	private float valor;
	private Taxa taxa;
	private Contrato contrato;

	public Cobra(float valor) {
		this.valor = valor;
		this.setTaxa(taxa);
		this.setValor(valor);
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Taxa getTaxa() {
		return taxa;
	}

	public void setTaxa(Taxa taxa) {
		this.taxa = taxa;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
}

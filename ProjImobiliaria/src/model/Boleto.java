package model;

public class Boleto {
	private int dataVencimento;
	private Contrato contrato;
	
	public Boleto(int dataVencimento){
		this.dataVencimento = dataVencimento ;
		this.setContrato(contrato);
	}

	public int getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(int dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
}


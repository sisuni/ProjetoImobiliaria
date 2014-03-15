package model;

public class Boleto {
	private int dataVencimento;
	
	public Boleto(int dataVencimento){
		this.dataVencimento = dataVencimento ;
		
	}

	public int getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(int dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	
}


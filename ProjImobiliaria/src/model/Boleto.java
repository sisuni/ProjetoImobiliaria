package model;

public class Boleto {
	private int dataVencimento;
	private Contrato contrato;
	
	public Boleto(int dataVencimento){
		this.setDataVencimento(dataVencimento);
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
		if (this.contrato == contrato)
			return;
		if(contrato==null){
			Contrato antigo = this.contrato;
			this.contrato= null;
			antigo.removeBoleto(this);
		}else{
			if(this.contrato !=null)
				this.contrato.removeBoleto(this);
			this.contrato = contrato;
			contrato.addBoleto(this);
			
		}
	}
	
}


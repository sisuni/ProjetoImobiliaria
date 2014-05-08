package model;

public class Cobra {
	private float valor;
	private Taxa taxa;
	private Boleto boleto;

	public Cobra(float valor, Taxa taxa, Boleto boleto) {
		this.setTaxa(taxa);
		this.setValor(valor);
		this.setBoleto(boleto);
	}

	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Taxa getTaxa() {
		return this.taxa;
	}

	public void setTaxa(Taxa taxa) {
		if(this.taxa == taxa)
			return;
		
		if(taxa==null){
			Taxa antiga = this.taxa;
			this.taxa = null;
			antiga.removeCobranca(this);
		}else{
			if(this.taxa !=null)
				this.taxa.removeCobranca(this);
			this.taxa = taxa;
			taxa.addCobranca(this);
		}
	}

	public Boleto getBoleto(){
		return this.boleto;
	}
	
	public void setBoleto(Boleto boleto){
		if(this.boleto == boleto)
			return;
		
		if(boleto == null){
			Boleto antigo = this.boleto;
			this.boleto = null;
			antigo.removeCobranca(this);
		}else{
			if(this.boleto!=null)
				this.boleto.removeCobranca(this);
			this.boleto = boleto;
			boleto.addCobranca(this);
		}
	}
}

package model;

import java.io.Serializable;

import control.ITabelavel;

public class Cobra implements Serializable, ITabelavel, Comparable<Cobra>{
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

	@Override
	public int compareTo(Cobra c) {
		if(c.getBoleto() == null || this.getBoleto() == null)	
			return 1;
		else{
			if((this.getBoleto().compareTo(c.getBoleto())) == 0){
				if(this.getTaxa() == c.getTaxa())
					return 0;
				else
					return 1;			
			}else{
				return 1;
			}
		}
	}

	@Override
	public Object[] getData() {
		return new Object[]{this.taxa.getNome() ,this.taxa.getDescricao(),this.valor};
	}
	
	public String toString(){
		return this.taxa.toString() + this.valor;
	}
}

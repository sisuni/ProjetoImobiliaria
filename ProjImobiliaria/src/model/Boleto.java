package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Boleto implements Serializable, ITabelavel, Comparable<Boleto> {
	private int dataVencimento;
	private Contrato contrato;
	private Set<Cobra> listaCobrancas;
	
	public Boleto(int dataVencimento, Contrato contrato){
		this.setDataVencimento(dataVencimento);
		this.setContrato(contrato);
		this.listaCobrancas = new TreeSet<Cobra>();
	}

	public int getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(int dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public Contrato getContrato() {
		return this.contrato;
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
	
	public Set<Cobra> getCobrancas(){
		return this.listaCobrancas;
	}
	
	public void addCobranca(Cobra novaCobranca){
		if(this.listaCobrancas.contains(novaCobranca))
			return;
		this.listaCobrancas.add(novaCobranca);
		novaCobranca.setBoleto(this);
	}
	
	public void removeCobranca(Cobra exCobranca){
		if(! this.listaCobrancas.contains(exCobranca))
			return;
		this.listaCobrancas.remove(exCobranca);
		exCobranca.setBoleto(null);
	}
	
	public float getValorTotal() {
		float total = this.contrato.getValorAluguel();
		
		for (Cobra taxa : this.listaCobrancas) {
			total += taxa.getValor();
		}
		
		return total;
	}

	@Override
	public int compareTo(Boleto b) {
		return this.contrato.compareTo(b.contrato);
	}

	@Override
	public Object[] getData() {
		return new Object[]{this.dataVencimento, this.contrato.getInquilino().getNome(), this.getValorTotal()};
	}
	
	public String toString() {
		return "Vencimento: " + this.dataVencimento + " - " + 
				"Inquilino: " + this.contrato.getInquilino().getNome() + " - " + 
				"Imóvel: " + this.contrato.getImovel().toString();
	}

}


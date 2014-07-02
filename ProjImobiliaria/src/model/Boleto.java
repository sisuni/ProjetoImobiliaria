package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Boleto implements Serializable, ITabelavel, Comparable<Boleto> {
	
	private Date dataVencimento;
	private SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
	private Contrato contrato;
	private Set<Cobra> listaCobrancas;
	private DecimalFormat formatValor = new DecimalFormat("#,###.00"); 
	
	public Boleto(Date dataVencimento, Contrato contrato) throws ModelException{
		this.setDataVencimento(dataVencimento);
		this.setContrato(contrato);
		this.listaCobrancas = new TreeSet<Cobra>();
	}

	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public boolean validarData(Date data) throws ModelException{
		if(data == null)
			throw new ModelException("Data de Vencimento está em branco!");
		else
			
		return true;
	}
	
	public void setDataVencimento(Date dataVencimento) throws ModelException {
		if(validarData(dataVencimento))
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
		float total = 0;
		
		for (Cobra taxa : this.listaCobrancas) {
			total += taxa.getValor();
		}
		
		return total;
	}

	@Override
	public int compareTo(Boleto b) {
		if (this.contrato.compareTo(b.contrato) == 0) {
			if (this.dataVencimento.equals(b.dataVencimento)) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

	@Override
	public Object[] getData() {
		int num=0;
		for(Cobra cobra :this.listaCobrancas){
			if(cobra.getBoleto() == this)
				num+=1;
		}
		
		return new Object[]{
				this.contrato.toString(),
				formatData.format(this.dataVencimento), 
				num,
				"R$ " + formatValor.format(this.getValorTotal())};
	}
	
	public String toString() {
		return "Vencimento: " + formatData.format(this.dataVencimento)  + " - " + 
				"Inquilino: " + this.contrato.getInquilino().getNome() + " - " + 
				"Imóvel: " + this.contrato.getImovel().toString();
	}

}


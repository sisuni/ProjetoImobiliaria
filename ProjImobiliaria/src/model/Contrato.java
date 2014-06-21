package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Contrato implements Serializable, ITabelavel, Comparable<Contrato> {
	private int duracao;
	private Date dataInicio;
	private int percentImobiliaria;
	private float valorAluguel;
	private Inquilino inquilino;
	private Imovel imovel;
	private Set<Boleto> listaBoletos;
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat formatar_valor = new DecimalFormat("#,###.00"); 

	public Contrato(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo,  Inquilino inqui) {
		this.setDuracao(duracao);
		this.setDataInicio(dataInicio);
		this.setPercentProprietario(percentProprietario);
		this.setValorAluguel(valorAluguel);
		this.setInquilino(inqui);
		this.setImovel(imo);
		this.listaBoletos = new TreeSet<Boleto>();
	}

	public int getDuracao() {
		return this.duracao;
	}	
	
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public int getPercentProprietario() {
		return this.percentImobiliaria;
	}

	public void setPercentProprietario(int percentProprietario) {
		this.percentImobiliaria = percentProprietario;
	}

	public float getValorAluguel() {
		return this.valorAluguel;
	}

	public void setValorAluguel(float valorAluguel) {
		this.valorAluguel = valorAluguel;
	}

	public Inquilino getInquilino() {
		return this.inquilino;
	}

	public void setInquilino(Inquilino inquilino) {
		if(this.inquilino==inquilino)
			return;
		
		if(inquilino==null){
			Inquilino antigo = this.inquilino;
			this.inquilino = null;
			antigo.removeContrato(this);
		}else{
			if(this.inquilino!=null)
				this.inquilino.removeContrato(this);
			this.inquilino = inquilino;
			inquilino.removeContrato(this);
		}
	}

	public Imovel getImovel() {
		return this.imovel;
	}

	public void setImovel(Imovel imovel) {
		if(this.imovel==imovel)
			return;
		if(imovel==null){
			Imovel antigo = this.imovel;
			this.imovel = null;
			antigo.removeContrato(this);
		}else{
			if(this.imovel!=null)
				this.imovel.removeContrato(this);
			this.imovel = imovel;
			imovel.removeContrato(this);
		}
	}

	public Set<Boleto> getBoletos(){
		return this.listaBoletos;
	}
	
	public void addBoleto(Boleto novoBoleto){
		if (this.listaBoletos.contains(novoBoleto))
			return;
		this.listaBoletos.add(novoBoleto);
		novoBoleto.setContrato(this);
	}
	
	public void removeBoleto(Boleto exBoleto){
		if(! this.listaBoletos.contains(exBoleto))
			return;
		this.listaBoletos.remove(exBoleto);
		exBoleto.setContrato(null);
	}
	
	public void disponivel(boolean status){
		this.imovel.setStatus(status);
	}

	@Override
	public int compareTo(Contrato c) {
		return this.imovel.compareTo(c.imovel);
	}

	@Override
	public Object[] getData() {
		return new Object[]{
				this.getImovel(),
				this.getInquilino(),
				this.duracao, 
				formatar.format(this.dataInicio), 
				"R$ " + formatar_valor.format(this.valorAluguel),
				this.listaBoletos.size()};
	}

}

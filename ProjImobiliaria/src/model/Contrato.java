package model;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Contrato {
	private int duracao;
	private Date dataInicio;
	private int percentProprietario;
	private String modelo;
	private float valorAluguel;
	private Inquilino inquilino;
	private Imovel imovel;
	private Set<Boleto> listaBoletos;

	public Contrato(int duracao, Date dataInicio, int percentProprietario, String modelo, float valorAluguel) {
		this.setDuracao(duracao);
		this.setDataInicio(dataInicio);
		this.setPercentProprietario(percentProprietario);
		this.setModelo(modelo);
		this.setValorAluguel(valorAluguel);
		this.setInquilino(inquilino);
		this.setImovel(imovel);
		this.listaBoletos = new TreeSet<Boleto>();
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public int getPercentProprietario() {
		return percentProprietario;
	}

	public void setPercentProprietario(int percentProprietario) {
		this.percentProprietario = percentProprietario;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public float getValorAluguel() {
		return valorAluguel;
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

	public int getduracao() {
		return duracao;
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

}

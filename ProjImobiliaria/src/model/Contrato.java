package model;

import java.util.Date;

public class Contrato {
	private int duracao;
	private Date dataInicio;
	private int percentProprietario;
	private String modelo;
	private float valorAluguel;
	private Inquilino inquilino;
	private Imovel imovel;

	public Contrato(int duracao, Date dataInicio, int percentProprietario, String modelo, float valorAluguel) {
		this.setDuracao(duracao);
		this.setDataInicio(dataInicio);
		this.setPercentProprietario(percentProprietario);
		this.setModelo(modelo);
		this.setValorAluguel(valorAluguel);
		this.setInquilino(inquilino);
		this.setImovel(imovel);

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
		return inquilino;
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
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public int getduracao() {
		return duracao;
	}	

}

package model;

import java.util.Date;

public class Contrato {
	private int dura��o;
	private Date dataInicio;
	private int percentProprietario;
	private String modelo;
	private float valorAluguel;

public Contrato(int dura��o, Date dataInicio, int percentProprietario, String modelo,float valorAluguel){
	this.dura��o = dura��o;
	this.dataInicio = dataInicio;
	this.percentProprietario = percentProprietario;
	this.modelo = modelo;
	this.valorAluguel = valorAluguel;
	
}

public int getDura��o() {
	return dura��o;
}

public void setDura��o(int dura��o) {
	this.dura��o = dura��o;
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

}

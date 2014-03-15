package model;

import java.util.Date;

public class Contrato {
	private int duração;
	private Date dataInicio;
	private int percentProprietario;
	private String modelo;
	private float valorAluguel;

public Contrato(int duração, Date dataInicio, int percentProprietario, String modelo,float valorAluguel){
	this.duração = duração;
	this.dataInicio = dataInicio;
	this.percentProprietario = percentProprietario;
	this.modelo = modelo;
	this.valorAluguel = valorAluguel;
	
}

public int getDuração() {
	return duração;
}

public void setDuração(int duração) {
	this.duração = duração;
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

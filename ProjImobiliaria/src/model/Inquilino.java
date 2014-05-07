package model;

import java.util.Set;
import java.util.TreeSet;

public class Inquilino extends Cliente {
	
	private String endAnteriorCompleto;
	private Set<Contrato> listaContratos;
	
	public Inquilino(int cod, String nome, String cpf, String rg, String email,
			String uf, String cidade, String bairro, String logradouro,
			int numero, String complemento, String endAnteriorCompleto) {
		super(cod, nome, cpf, rg, email, uf, cidade, bairro, logradouro, numero, complemento);
		this.setEndAnteriorCompleto(endAnteriorCompleto);
		this.listaContratos = new TreeSet<Contrato>();
	}

	public String getEndAnteriorCompleto() {
		return endAnteriorCompleto;
	}

	public void setEndAnteriorCompleto(String endAnteriorCompleto) {
		this.endAnteriorCompleto = endAnteriorCompleto;
	}
	
	public Set<Contrato> getListaContrato(){
		return this.listaContratos;
	}
	
	public void addContrato(Contrato novoContrato){
		if(this.listaContratos.contains(novoContrato))
			return;
		this.listaContratos.add(novoContrato);
		novoContrato.setInquilino(this);
	}
	
	public void removeContrato(Contrato exContrato){
		if(! this.listaContratos.contains(exContrato))
			return;
		this.listaContratos.remove(exContrato);
		exContrato.setInquilino(null);
	}

}

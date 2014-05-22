package model;

import java.util.Set;
import java.util.TreeSet;

public class Inquilino extends Cliente {
	
	private String endAnteriorCompleto;
	private Set<Contrato> listaContratos;
	
	public Inquilino(String nome, String cpf, String email, String endereco, String endAnteriorCompleto) throws ModelException{
		super(nome, cpf, email, endereco);
		this.setEndAnteriorCompleto(endAnteriorCompleto);
		this.listaContratos = new TreeSet<Contrato>();
	}

	public String getEndAnteriorCompleto() {
		return this.endAnteriorCompleto;
	}

	public void setEndAnteriorCompleto(String endAnteriorCompleto) {
		this.endAnteriorCompleto = endAnteriorCompleto;
	}
	
	public Set<Contrato> getContratos(){
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

	/**
	 * Dados para serem exibidos no data grid
	 */
	@Override
	public Object[] getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	 * Implementação do método toString que retorna uma String
	 * que descreve o objeto Cargo
	 */
	public String toString() {
		return this.getNome() + " - " + this.getCpf();
	}

}

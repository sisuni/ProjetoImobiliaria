package model;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import control.ITabelavel;

public class Taxa implements Serializable, ITabelavel, Comparable<Taxa>{
	private String nome;
	private String descricao;
	private Set<Cobra> listaCobrancas;
	
	public Taxa(String nome,String descricao){
		this.setDescricao(descricao);
		this.setNome(nome);
		this.listaCobrancas = new TreeSet<Cobra>();
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Set<Cobra> getCobrancas(){
		return this.listaCobrancas;
	}
	
	public void addCobranca(Cobra novaCobranca){
		if(this.listaCobrancas.contains(novaCobranca))
			return;
		this.listaCobrancas.add(novaCobranca);
		novaCobranca.setTaxa(this);
	}
	
	public void removeCobranca(Cobra exCobranca){
		if(! this.listaCobrancas.contains(exCobranca))
			return;
		this.listaCobrancas.remove(exCobranca);
		exCobranca.setTaxa(null);
	}

	@Override
	public int compareTo(Taxa t) {
		return this.getNome().compareTo(t.getNome());
	}

	@Override
	public Object[] getData() {
		return new Object[]{
				this.getNome(),
				this.getDescricao(),
				this.getCobrancas().size()
		};
	}

	public String toString(){
		return "Taxa: " + this.getNome() + " - Boletos:" + this.getCobrancas().size();
	}
}


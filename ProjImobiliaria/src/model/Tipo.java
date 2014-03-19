package model;

public class Tipo {
	
	private String nome;
	private Imovel[] listaImoveis;
	public static final int NUM_MAX_IMOVEIS = 1000;
	
	// Por quest�es de desempenho defini que no sistema poder� existir no m�ximo 100 im�veis de cada tipo definido. 
	// Este gargalo desaparece quando implementa-se o uso das Collections ao inv�s de Arrays
	// por�m o professor solicitou somente o uso de Arrays.
	public Tipo(String nome) {
		this.nome = nome;
		this.listaImoveis = new Imovel[NUM_MAX_IMOVEIS];
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	// Somente deve existir o get para esta lista pq o set � pelo m�todo inserirImovel();
	public Imovel[] getListaImoveis() {
		return listaImoveis;
	}

	public static int getNumMaxImoveis() {
		return NUM_MAX_IMOVEIS;
	}
		
	
	
}

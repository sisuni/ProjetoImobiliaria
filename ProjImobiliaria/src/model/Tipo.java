package model;

public class Tipo {
	
	private String nome;
	private Imovel[] listaImoveis;
	public static final int NUM_MAX_IMOVEIS = 1000;
	
	// Por questões de desempenho defini que no sistema poderá existir no máximo 100 imóveis de cada tipo definido. 
	// Este gargalo desaparece quando implementa-se o uso das Collections ao invés de Arrays
	// porém o professor solicitou somente o uso de Arrays.
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

	// Somente deve existir o get para esta lista pq o set é pelo método inserirImovel();
	public Imovel[] getListaImoveis() {
		return listaImoveis;
	}

	public static int getNumMaxImoveis() {
		return NUM_MAX_IMOVEIS;
	}
		
	
	
}

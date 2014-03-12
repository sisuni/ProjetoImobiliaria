package model;

public class Tipo {
	
	private String nome;
	private Imovel[] listaImoveis;
	private int numImoveis;
	public static final int NUM_MAX_IMOVEIS = 1000;
	
	// Por quest�es de desempenho defini que no sistema poder� existir no m�ximo 100 im�veis de cada tipo definido. 
	// Este gargalo desaparece quando implementa-se o uso das Collections ao inv�s de Arrays
	// por�m o professor solicitou somente o uso de Arrays.
	public Tipo(String nome) {
		super();
		this.nome = nome;
		this.numImoveis = 0;
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

	public int getNumImoveis() {
		return numImoveis;
	}	

	public static int getNumMaxImoveis() {
		return NUM_MAX_IMOVEIS;
	}
	
	/**
	 * M�todo para inclus�o de im�veis no array listaImoveis.
	 * @param novoImovel
	 */
	public void inserirImovel(Imovel novoImovel){
		if(this.numImoveis == this.NUM_MAX_IMOVEIS)
			return;
		else{
			// Caso j� exista na lista n�o fazer nada
			for(int i=0; i < this.numImoveis; i++)
				if(this.listaImoveis[i] == novoImovel)
					return;
			
			this.listaImoveis[this.numImoveis] = novoImovel;
			novoImovel.setTipo(this);
			this.numImoveis++;
		}
		
	}
	
	/**
	 * M�todo para exclus�o de im�veis do array listaImoveis.
	 * @param exImovel
	 */
	public void removeImovel(Imovel exImovel){
		
		for(int i=0; i < this.numImoveis; i++)
			if(this.listaImoveis[i] == exImovel){
				this.listaImoveis[i] = this.listaImoveis[this.numImoveis-1];
				this.listaImoveis[this.numImoveis-1] = null;
				exImovel.setTipo(null);
				this.numImoveis--;
				
				return;
			}

	}
	
	
	
}

package model;

public class Tipo {
	
	private String nome;
	private Imovel[] listaImoveis;
	public static final int NUM_MAX_IMOVEIS = 1000;
	private int numImoveis;
	
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

	public int getNumImoveis() {
		return numImoveis;
	}
		
	/**
	 * Método para inclusão de imoveis no array listaImoveis
	 * @param imovel
	 */
	public void addImovel(Imovel imovel){
		if(this.numImoveis == this.NUM_MAX_IMOVEIS)
			return;
		else{
			// Caso já exista na lista não fazer nada
			for(int i=0; i < this.numImoveis; i++)
				if(this.listaImoveis[i] == imovel)
					return;
			
			this.listaImoveis[this.numImoveis] = imovel;
			imovel.setTipo(this);
			this.numImoveis++;
		}
		
	}
	
	/**
	 * Método para exclusão de imoveis do array listaImoveis
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

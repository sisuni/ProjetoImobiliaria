package model;

import java.util.Set;

public interface IDAO<T> {

	//
	// CONSTANTES
	//
	/**
	 * Define o tamanho m�ximo de objetos que podem ser armazenados
	 */
	public static final int TAMANHO_MAXIMO = 20;

	/**
	 * Salva um objeto 
	 * @param novo
	 * @return
	 */
	public abstract boolean salvar(T novo);

	/**
	 * Remove um objeto
	 * @param obj
	 * @return
	 */
	public abstract boolean remover(T obj);

	/**
	 * Promove a atualiza��o de um objeto
	 * @param obj
	 * @return
	 */
	public abstract boolean atualizar(T obj);

	/**
	 * Recupera um objeto pela posi��o
	 * @param posicao
	 * @return
	 */
	public abstract T recuperar(int posicao);

	/**
	 * Recupera um objeto pela chave
	 * @param sigla
	 * @return
	 */
	public abstract T recuperarPelaChave(Object chave);

	/**
	 * Retorna o n�mero de objetos sendo gerenciados pelo DAO
	 * @return
	 */
	public abstract int getNumObjs();

	/**
	 * Retorna uma c�pia da lista de objetos
	 * @return
	 */
	public abstract Set<T> getListaObjs();
}
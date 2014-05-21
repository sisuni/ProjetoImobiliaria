package control;

/**
 * O conceito de interface em Java tem as seguintes características:
 * 	- Se assemelha a uma classe abstrata
 *  - Que não tem atributos
 *  - E todos os seus métodos são abstratos (ou seja não tem implementação)
 * Uma interface é um CONTRATO DE PRESTAÇÃO DE SERVIÇOS que
 * pode ser REALIZADO por uma ou mais classes.
 * 
 * Neste classe as classes tabeláveis serão aquelas cujo estado 
 * pode ser apresentado em uma tabela
 * @author Alessandro Cerqueira
 */
public interface ITabelavel {
	/**
	 * Retorna um array de objects com os estados dos atributos 
	 * dos objetos
	 * @return
	 */
	public Object[] getData();
}

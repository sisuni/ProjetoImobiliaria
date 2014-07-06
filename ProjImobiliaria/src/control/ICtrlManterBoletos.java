package control;

import java.util.Date;
import java.util.Set;

import model.Boleto;
import model.Cobra;
import model.Contrato;
import model.ModelException;
import model.Taxa;

public interface ICtrlManterBoletos extends ICtrlManter {

	
	/** Métodos pertencentes a inclusão e alteração de Boletos **/
	public abstract boolean incluir(Date dataVencimento, Contrato contrato) throws ModelException;

	public abstract boolean alterar(Date dataVencimento) throws ModelException;	 
	/** Fim dos Métodos pertencentes a inclusão e alteração de Boletos **/
	 
	/** Métodos pertencentes para o controle das cobranças do boleto **/
	public abstract boolean iniciarCobrancas();

	public abstract boolean terminarCobrancas();
		
	public abstract boolean iniciarIncluirCobranca();
		
	public abstract boolean incluirCobranca(float valor, Taxa t) throws ModelException;

	public abstract void cancelarIncluirCobranca();

	public abstract boolean iniciarAlterarCobranca(int pos);
		
	public abstract boolean alterarCobranca(float valor, Taxa t) throws ModelException;
		
	public abstract void cancelarAlterarCobranca();
		
	public abstract boolean iniciarExcluirCobranca(int pos);
		
	public abstract boolean excluirCobranca() throws ModelException;

	public abstract void cancelarExcluirCobranca();
		
	public abstract void removerCobrancasBoletosNulos();
		
	public abstract void atualizarInterfaceCobranca();
			
	public abstract Set<Cobra> listasCobrancasDoBoleto(Boleto b);
	/** Fim dos Métodos pertencentes para o controle das cobranças do boleto **/
	 
	 
	 
	/** Métodos para controlar o Aluguel por dropdown **/
	public abstract void selecionarContrato(Contrato c) throws ModelException;
	 
	public abstract void atribuirValor(float valor);
}

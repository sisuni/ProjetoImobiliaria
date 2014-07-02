package view;

import java.util.Date;

import control.ITabelavel;
import model.Contrato;

public interface IViewerSalvaBoleto {
	
	public abstract void atualizarCampos(Date dataVencimento, Contrato contrato, float valorT);

	public abstract void limpar();
	public abstract void incluirLinha(ITabelavel objeto);
	public abstract void executarIncluirCobranca();
	public abstract void executarExcluirCobranca();
	public abstract void executarAlterarCobranca();
	public abstract void setValorTotal(float valor);
	public abstract float getValorTotal();
	public abstract Contrato getContrato();
	public abstract void selecionaContrato();
	public abstract void setVisible(boolean flag);
	
	public abstract boolean isEhAlteracao();
	public abstract void setEhAlteracao(boolean ehAlteracao);
	
}

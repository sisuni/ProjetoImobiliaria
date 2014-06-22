package view;

import java.util.Date;

import control.ITabelavel;
import model.Contrato;

public interface IViewerSalvaBoleto {
	
	public abstract void atualizarCampos(Date dataVencimento, Contrato contrato);

	public void limpar();
	public void incluirLinha(ITabelavel objeto);
	public void executarIncluirTaxa();
	public void executarExcluirTaxa();
	public void executarAlterarTaxa();
	public void setValorTotal(float valor);
	public void selecionaContrato();
	public void setVisible(boolean flag);
}

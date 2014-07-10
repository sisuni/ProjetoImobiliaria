package view;

import javax.swing.JOptionPane;

import model.ModelException;
import control.ICtrlManterBoletos;

public class JanelaExcluirCobranca {
	/**
	 * Referência para o controlador do caso de uso
	 */
	private ICtrlManterBoletos ctrl;
	
	/**
	 * Opçãoo escolhida pelo usuário
	 */
	private int opcao;
	

	public JanelaExcluirCobranca(ICtrlManterBoletos ct, Object selecionado){
		// Guardo a referência para o controlador de caso de uso
		this.ctrl = ct;
		// Pergunto ao usuário o que ele deseja fazer
		this.opcao = JOptionPane.showConfirmDialog(null, "Deseja remover a Cobrança " + selecionado + "?");
		// Verifica o que o usuário indicou para ser feito
		if(this.opcao == JOptionPane.YES_OPTION)
			try {
				this.ctrl.excluirCobranca();
			} catch (ModelException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		else
			this.ctrl.cancelarExcluirCobranca();
	}
	
	/**
	 * Retorna a opção indicada pelo usuário
	 * @return
	 */
	public int getOpcao(){
		return this.opcao;
	}
}

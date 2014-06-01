package view;

import javax.swing.JOptionPane;

import model.ModelException;
import control.ICtrlManter;

/**
 * Implementação da janela de confirmação de exclusão do Funcionario
 * @author Valdecir
 *
 */
public class JanelaExcluirFuncionario {
	/**
	 * Referência para o controlador do caso de uso
	 */
	private ICtrlManter ctrl;
	
	/**
	 * Opçãoo escolhida pelo usuário
	 */
	private int opcao;
	
	
	/**
	 * Construtor que irá colocar uma janela modal perguntando
	 * se o usuário deseja ou não excluir o Funcionário
	 * @param nome
	 */
	public JanelaExcluirFuncionario(ICtrlManter ct, Object selecionado){
		// Guardo a referência para o controlador de caso de uso
		this.ctrl = ct;
		// Pergunto ao usuário o que ele deseja fazer
		this.opcao = JOptionPane.showConfirmDialog(null, "Deseja remover o(a) Funcionario(a) " + selecionado + "?");
		// Verifica o que o usuário indicou para ser feito
		if(this.opcao == JOptionPane.YES_OPTION)
			try {
				this.ctrl.excluir();
			} catch (ModelException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		else
			this.ctrl.cancelarExcluir();
	}
	
	/**
	 * Retorna a opção indicada pelo usuário
	 * @return
	 */
	public int getOpcao(){
		return this.opcao;
	}
}

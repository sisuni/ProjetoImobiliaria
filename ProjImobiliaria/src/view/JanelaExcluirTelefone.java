package view;

import javax.swing.JOptionPane;

import model.ModelException;
import control.ICtrlManter;

/**
 * Implementação da janela de confirmação de exclusão do Proprietario
 * @author Valdecir
 *
 */
public class JanelaExcluirTelefone {
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
	 * se o usuário deseja ou não excluir o departamento
	 * @param nome
	 */
	public JanelaExcluirTelefone(ICtrlManter t, Object selecionado){
		// Guardo a referência para o controlador de caso de uso
		this.ctrl = t;
		// Pergunto ao usuário o que ele deseja fazer
		this.opcao = JOptionPane.showConfirmDialog(null, "Deseja remover o Telefone " + selecionado + "?");
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

package view;

import javax.swing.JOptionPane;

import model.ModelException;
import control.ICtrlManter;

/**
 * Implementação da janela de confirmação de exclusão do Cargo
 * 
 * @author Valdecir
 * 
 */
public class JanelaExcluirCargo {
	/**
	 * Referência para o controlador do caso de uso
	 */
	private ICtrlManter ctrl;

	/**
	 * Opçãoo escolhida pelo usuário
	 */
	private int opcao;

	/**
	 * Construtor que irá colocar uma janela modal perguntando se o usuário
	 * deseja ou não excluir o Cargo
	 * 
	 * @param nome
	 */
	public JanelaExcluirCargo(ICtrlManter ct) {
		this.ctrl = ct;
	}

	public boolean ExcluirCargo(Object selecionado) {
		this.opcao = JOptionPane.showConfirmDialog(null,
				"Deseja remover o Cargo " + selecionado + "?");
		if (this.opcao == JOptionPane.YES_OPTION)
			try {
				this.ctrl.excluir();
				return true;
			} catch (ModelException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		else
			this.ctrl.cancelarExcluir();
			return false;
	}

	public boolean ExcluirTudo(Object selecionado, int qtdFunc) {
		this.opcao = JOptionPane.showConfirmDialog(null,"O Cargo: "+selecionado+" contém "
				+ qtdFunc + " funcionários. A remoção do cargo removerá também seus funcionários. "
				+ "Deseja Remover o Cargo Selcionado?");
		
		if(this.opcao == JOptionPane.YES_OPTION)
			try{
				this.ctrl.excluir();
				return true;
			} catch (ModelException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
		else
			this.ctrl.cancelarExcluir();
			return false;
	}

	/**
	 * Retorna a opção indicada pelo usuário
	 * 
	 * @return
	 */
	public int getOpcao() {
		return this.opcao;
	}
}

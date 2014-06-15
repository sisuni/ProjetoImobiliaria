package view;

import javax.swing.JOptionPane;

import model.ModelException;
import control.ICtrlManter;


public class JanelaExcluirTelefone {
	
	private ICtrlManter ctrl;
	
	
	private int opcao;
	

	public JanelaExcluirTelefone(ICtrlManter t, Object selecionado){
		
		this.ctrl = t;
		this.opcao = JOptionPane.showConfirmDialog(null, "Deseja remover o Telefone " + selecionado + "?");
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
	

	public int getOpcao(){
		return this.opcao;
	}
}

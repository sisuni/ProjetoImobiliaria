package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.CtrlPrograma;

public class JanelaPrincipal extends JFrame implements  IViewerPrincipal{

	private JPanel contentPane;
	
	private CtrlPrograma ctrlPrg;

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal(CtrlPrograma p) {
		this.ctrlPrg = p;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCargo = new JButton("Cargo");
		btnCargo.addActionListener(new ActionListener() {
			// Método acionado quando o botão "Departamentos" 
			// for pressionado (Método de Callback).
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterCargo();
			}
		});
		btnCargo.setBounds(47, 44, 89, 49);
		contentPane.add(btnCargo);
		
		JButton btnSair = new JButton("sair");
		btnSair.setBounds(195, 44, 89, 49);
		contentPane.add(btnSair);
		
		this.setVisible(true);
	}
}

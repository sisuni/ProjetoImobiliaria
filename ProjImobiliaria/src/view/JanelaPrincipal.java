package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.CtrlPrograma;

public class JanelaPrincipal extends JFrame {

	private JPanel contentPane;
	
	private CtrlPrograma ctrlPrg;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					JanelaPrincipal frame = new JanelaPrincipal();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
	
//				}
//			}
//		});
//	}

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
		
		JButton button = new JButton("? ? ? ?");
		button.setBounds(47, 44, 89, 49);
		contentPane.add(button);
		
		JButton btnSair = new JButton("sair");
		btnSair.setBounds(195, 44, 89, 49);
		contentPane.add(btnSair);
		
		this.setVisible(true);
	}
}

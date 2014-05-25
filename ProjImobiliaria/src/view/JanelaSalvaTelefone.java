package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.ICtrlManter;

public class JanelaSalvaTelefone extends JFrame implements IViewerSalvaTelefone{

	private ICtrlManter ctrl;

	private boolean ehAlteração;
	
	private JPanel contentPane;
	private JTextField txtTipo;
	private JTextField txtNum;
	
	public JanelaSalvaTelefone(ICtrlManter t){
		this.ctrl = t;
		setTitle("Telefone");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(20, 11, 46, 14);
		contentPane.add(lblTipo);
		
		JLabel lblNum = new JLabel("Número:");
		lblNum.setBounds(20, 48, 46, 14);
		contentPane.add(lblNum);
		
		txtTipo = new JTextField();
		txtTipo.setBounds(76, 45, 334, 20);
		contentPane.add(txtTipo);
		txtTipo.setColumns(10);
		
		txtNum = new JTextField();
		txtNum.setBounds(76, 45, 334, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//executarSalvar();
			}
		});
		btnSalvar.setBounds(73, 95, 143, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//executarCancelar();
			}
		});
		btnCancelar.setBounds(256, 95, 154, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	@Override
	public void atualizarCampos(String tipo, String numero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVisible(boolean flag) {
		// TODO Auto-generated method stub
		
	}

}

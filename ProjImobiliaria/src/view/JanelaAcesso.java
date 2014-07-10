package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ModelException;
import control.ICtrlManterFuncionarios;

public class JanelaAcesso extends JFrame implements IViewerAcesso{

	private JPanel contentPane;

	private ICtrlManterFuncionarios ctrlFunc;
		
	private JTextField txtUsuario;
	
	private JPasswordField txtSenha;
	
	public JanelaAcesso(ICtrlManterFuncionarios f) {
		this.ctrlFunc = f;
		setTitle("Acesso ao Sistema");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false); // não maximizar, aumentar
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png")); //colocando icone
		
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(37,10,80,20);
		add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(90,10,120,20);
		add(txtUsuario);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(45,40,80,20);
		add(lblSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(90,40,120,20);
		add(txtSenha);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmar();
			}
		});
		btnConfirmar.setBounds(20,80,100,20);
		add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelar();
			}
		});
		btnCancelar.setBounds(130,80,100,20);
		add(btnCancelar);
		
		getRootPane().setDefaultButton(btnConfirmar);  // Definindo botão Confirmar como botão padrão da tecla 'ENTER'  
		setVisible(true);
	}
	
	public void limpar(){
		this.txtUsuario.setText(null);;
		this.txtSenha.setText(null);;
		this.txtUsuario.grabFocus();
	}
	
	public void confirmar(){
		String usuario = txtUsuario.getText();
		String senha = txtSenha.getText();
		try {
			this.ctrlFunc.acessar(usuario,senha);
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void cancelar(){
		this.ctrlFunc.cancelarAcesso();
	}
}

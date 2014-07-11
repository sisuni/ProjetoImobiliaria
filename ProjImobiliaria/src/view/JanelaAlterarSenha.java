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

import model.Funcionario;
import model.ModelException;
import control.ICtrlManterFuncionarios;

public class JanelaAlterarSenha extends JFrame implements IViewerAltSenha {
	
	private ICtrlManterFuncionarios ctrl;

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenhaAtual;
	private JPasswordField txtSenhaNova;
	private JPasswordField txtRepSenhaNova;
	private Funcionario funcLogado;
	
	public JanelaAlterarSenha(ICtrlManterFuncionarios mf, Funcionario func){
		this.ctrl = mf;
		this.funcLogado = func;
		setTitle("Alterar Senha - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 418, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png")); //colocando icone
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(50, 13, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField(funcLogado.getNome());
		txtNome.setBounds(90, 11, 300, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		txtNome.enable(false);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(50, 50, 46, 14);
		contentPane.add(lblLogin);
		
		txtLogin = new JTextField(funcLogado.getLogin());
		txtLogin.setBounds(90, 47, 150, 20);
		contentPane.add(txtLogin);
		txtLogin.enable(false);
			
		JLabel lblSenha = new JLabel("Senha Atual:");
		lblSenha.setBounds(16 ,87, 80, 14);
	    contentPane.add(lblSenha);
	    
	    txtSenhaAtual = new JPasswordField();
	    txtSenhaAtual.setBounds(90, 85, 150, 20);
	    contentPane.add(txtSenhaAtual);
	    
	    JLabel lblNovaSenha = new JLabel("Nova Senha:");
	    lblNovaSenha.setBounds(16 ,124, 80, 14);
	    contentPane.add(lblNovaSenha);
	    
	    txtSenhaNova = new JPasswordField();
	    txtSenhaNova.setBounds(90, 123, 150, 20);
	    contentPane.add(txtSenhaNova);
	    
	    JLabel lblRepNovaSenha = new JLabel("<html>Repitir<br>Nova Senha:</html>");
	    lblRepNovaSenha.setBounds(16 ,136, 80, 50);
	    contentPane.add(lblRepNovaSenha);
	    
	    txtRepSenhaNova = new JPasswordField();
	    txtRepSenhaNova.setBounds(90, 161, 150, 20);
	    contentPane.add(txtRepSenhaNova);
	    
	    JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(53, 195, 143, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(226, 195, 154, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
		getRootPane().setDefaultButton(btnSalvar);
	}
	
	public void executarSalvar() {
		
		String senhaAtual = txtSenhaAtual.getText();
		String senhaNova = txtSenhaNova.getText();
		String repSenhaNova = txtRepSenhaNova.getText();
		
		try {
			this.ctrl.alterarSenha(this.funcLogado, senhaAtual, senhaNova, repSenhaNova);
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
	}

	public void executarCancelar() {
		ctrl.cancelarAlterarSenha();
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenhaAtual.setText(null);
		txtSenhaNova.setText(null);
		txtRepSenhaNova.setText(null);
		txtSenhaAtual.grabFocus();
	}
	
	@Override
	public void focusSenhaAtual(){
		txtSenhaAtual.grabFocus();
	}
	
	@Override
	public void focusSenhaNova(){
		txtSenhaNova.grabFocus();
	}
}

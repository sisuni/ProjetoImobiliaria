package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Cargo;
import model.ModelException;
import control.ICtrlManterFuncionarios;

public class JanelaSalvaFuncionario extends JFrame implements IViewerSalvaFuncionario{
	
	private ICtrlManterFuncionarios ctrl;

	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblVerSenha;
	private JCheckBox chkVerSenha;
	private JComboBox<Cargo> cmbCargo;
	
	public JanelaSalvaFuncionario(ICtrlManterFuncionarios sc, Set<Cargo> Cargos){
		this.ctrl = sc;
		setTitle("Salvar Funcionário - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 418, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png")); //colocando icone
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 13, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(62, 11, 334, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(20, 50, 46, 14);
		contentPane.add(lblLogin);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(62, 47, 150, 20);
		contentPane.add(txtLogin);
			
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(16 ,87, 46, 14);
	    contentPane.add(lblSenha);
	    
	    txtSenha = new JPasswordField();
	    txtSenha.setBounds(62, 85, 150, 20);
	    contentPane.add(txtSenha);
	    
	    chkVerSenha = new JCheckBox();
		chkVerSenha.setBounds(58,105,110,20);
		chkVerSenha.setOpaque(false);
	    contentPane.add(chkVerSenha);
	    
	    chkVerSenha.addItemListener(new ItemListener(){
	    	public void itemStateChanged(ItemEvent e){
		    	if (e.getStateChange() != ItemEvent.SELECTED){
		    		txtSenha.setEchoChar('•');
		    	}else{
		    		txtSenha.setEchoChar((char) 0);
	    		}
	    	}
	    });
	    
	    lblVerSenha = new JLabel("Mostrar Senha");
	    lblVerSenha.setBounds(80,108,100,14);
	    contentPane.add(lblVerSenha);
	    
	    JLabel lblCargo = new JLabel("Cargo:");
	    lblCargo.setBounds(220, 50, 46, 14);
	    contentPane.add(lblCargo);
	    
	    cmbCargo = new JComboBox<Cargo>();
	    cmbCargo.setBounds(265, 47, 130, 20);
	    contentPane.add(cmbCargo);
	    
	    for(Cargo c : Cargos)
			if(c != null)
				cmbCargo.addItem(c);
		this.setVisible(true);
	    
	    
	    JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(53, 135, 143, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(226, 135, 154, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		
		String nome = txtNome.getText();
		String login = txtLogin.getText();
		String senha = txtSenha.getText();
		Object cargo = cmbCargo.getSelectedItem();
		
		try {
			if(!ehAlteracao)
				ctrl.incluir(nome, login, senha, (Cargo)cargo);
			else
				ctrl.alterar(nome, login, senha, (Cargo)cargo);
		} catch(ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void executarCancelar() {
		if(!ehAlteracao)
			ctrl.cancelarIncluir();
		else
			ctrl.cancelarAlterar();
	}
	
	@Override
	public void atualizarCampos(String nome, String login, String senha, Object cargo) {
		this.txtNome.setText(nome);
		this.txtLogin.setText(login);
		this.txtSenha.setText(senha);
		this.cmbCargo.setSelectedItem(cargo);
		this.ehAlteracao = true;
		this.lblVerSenha.setVisible(false);
		this.chkVerSenha.setVisible(false);
		
	}

}

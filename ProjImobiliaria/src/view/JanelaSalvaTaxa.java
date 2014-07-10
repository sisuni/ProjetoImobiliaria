package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ModelException;
import control.ICtrlManterTaxas;

public class JanelaSalvaTaxa extends JFrame implements IViewerSalvaTaxa{

	private ICtrlManterTaxas ctrl;

	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextArea txtDesc;
	
	
	public JanelaSalvaTaxa(ICtrlManterTaxas tx){
		this.ctrl = tx;
		setTitle("Salva Taxa");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 270, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png")); //colocando icone
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(30, 11, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(80, 9, 180, 20);
		contentPane.add(txtNome);
		
		JLabel lblDesc = new JLabel("Descrição:");
		lblDesc.setBounds(4, 40, 150, 14);
		contentPane.add(lblDesc);
			
		txtDesc = new JTextArea();
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		Box boxDesc = Box.createVerticalBox();
		boxDesc.setBounds(80,40,180,80);
		JScrollPane pane = new JScrollPane(txtDesc);
		boxDesc.add(pane);
		contentPane.add(boxDesc);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(20, 140, 90, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(150, 140, 100, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		String nome = txtNome.getText();
		String descricao = txtDesc.getText();
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(nome.toUpperCase(),descricao);
			else
				ctrl.alterar(nome.toUpperCase(),descricao);
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
	public void atualizarCampos(String nome, String descricao) {
		this.txtNome.setText(nome);
		this.txtDesc.setText(descricao);
		this.ehAlteracao = true;
	}

}

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;

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

	private DecimalFormat format = new DecimalFormat("#,###.00");
	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtValor;
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
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(30, 11, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(70, 9, 180, 20);
		contentPane.add(txtNome);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(30,41,46,14);
		contentPane.add(lblValor);
		
		txtValor = new JTextField();
		txtValor.setBounds(70, 40, 100, 20);
		txtValor.addFocusListener(
				new FocusListener(){
					public void focusGained(FocusEvent e){};
					
					public void focusLost(FocusEvent e){
						if (!e.isTemporary() && isEnabled() ) {
							Float valor  = Float.parseFloat(txtValor.getText());
							txtValor.setText(format.format(valor));
						}
					}
				});
		
		contentPane.add(txtValor);
		
		JLabel lblDesc = new JLabel("Descrição:");
		lblDesc.setBounds(4, 70, 150, 14);
		contentPane.add(lblDesc);
			
		txtDesc = new JTextArea();
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		Box boxDesc = Box.createVerticalBox();
		boxDesc.setBounds(70,70,180,60);
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
		btnCancelar.setBounds(160, 140, 90, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		String nome = txtNome.getText();
		float valor = Float.parseFloat(txtValor.getText().replace(".", "").replace(",", "."));
		String descricao = txtDesc.getText();
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(nome,descricao,valor);
			else
				ctrl.alterar(nome,descricao,valor);
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
	public void atualizarCampos(String nome, String descricao, float valor) {
		this.txtNome.setText(nome);
		this.txtDesc.setText(descricao);
		this.txtValor.setText(format.format(valor));
		this.ehAlteracao = true;
	}

}

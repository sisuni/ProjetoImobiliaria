package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.ModelException;
import control.ICtrlManterTelefones;

public class JanelaSalvaTelefone extends JFrame implements IViewerSalvaTelefone{

	private ICtrlManterTelefones ctrl;

	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JComboBox<String> cmbTipo;
	private JFormattedTextField txtNum;
	
	public JanelaSalvaTelefone(ICtrlManterTelefones t) throws ParseException{
		this.ctrl = t;
		setTitle("Salva Telefone");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 250, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(61, 11, 46, 14);
		contentPane.add(lblTipo);
		
		String[] tipoTel = {"Celular", "Comercial", "Residencial", "Outros"};
		cmbTipo = new JComboBox<String>(tipoTel);
		cmbTipo.setBounds(96, 9, 110, 20);
		contentPane.add(cmbTipo);
		
		JLabel lblNum = new JLabel("Número:");
		lblNum.setBounds(40, 48, 60, 14);
		contentPane.add(lblNum);
		
				
		txtNum = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
		txtNum.setBounds(96, 45, 110, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(20, 80, 90, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(130, 80, 90, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		String tipo = (String) cmbTipo.getSelectedItem();
		String numero = txtNum.getText();
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(tipo, numero, null);
			else
				ctrl.alterar(tipo, numero, null);
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
	public void atualizarCampos(String tipo, String numero) {
		this.cmbTipo.setSelectedItem(tipo);
		this.txtNum.setText(numero);
	}

}

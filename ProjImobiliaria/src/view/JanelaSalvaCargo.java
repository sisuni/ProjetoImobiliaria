package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ModelException;
import control.ICtrlManterCargos;

public class JanelaSalvaCargo extends JFrame implements IViewerSalvaCargo{
	
	private ICtrlManterCargos ctrl;

	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JComboBox<Integer> cmbNivel;
	private JTextField txtNome;
	
	public JanelaSalvaCargo(ICtrlManterCargos sc){
		this.ctrl = sc;
		setTitle("Cargo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNivel = new JLabel("Nível:");
		lblNivel.setBounds(20, 11, 46, 14);
		contentPane.add(lblNivel);
		
		cmbNivel = new JComboBox<Integer>();
		cmbNivel.setBounds(76, 11, 46, 14);
		cmbNivel.addItem(1);
		cmbNivel.addItem(2);
		cmbNivel.addItem(3);
		cmbNivel.addItem(4);
		contentPane.add(cmbNivel);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(20, 48, 46, 14);
		contentPane.add(lblNome);
			
		txtNome = new JTextField();
		txtNome.setBounds(76, 45, 334, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
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
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		int nivel = (int)cmbNivel.getSelectedItem();
		String nome = txtNome.getText();
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(nivel,nome);
			else
				ctrl.alterar(nivel,nome);
		} catch(ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void atualizarCampos(int nivel, String nome) {
		this.cmbNivel.setSelectedItem(nivel);
		this.txtNome.setText(nome);
		this.ehAlteracao = true;
		
	}

}

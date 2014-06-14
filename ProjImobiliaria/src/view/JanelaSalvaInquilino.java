package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.ICtrlManter;
import control.ITabelavel;

public class JanelaSalvaInquilino extends JFrame implements IViewerSalvaInquilino{
	
	private ICtrlManter ctrl;

	private boolean ehAlteração;
	
	private JPanel contentPane;
	private JComboBox cmbNivel;
	private JTextField txtNome;
	
	public JanelaSalvaInquilino(ICtrlManter sc){
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
	public void atualizarCampos(String nome, String cpf, String email,
			String endereco, String endAnteriorCompleto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incluirLinha(ITabelavel objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executarIncluirTelefone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executarExcluirTelefone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executarAlterarTelefone() {
		// TODO Auto-generated method stub
		
	}

}

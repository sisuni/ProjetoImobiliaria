package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.ModelException;
import model.Taxa;
import control.ICtrlManterBoletos;

public class JanelaSalvaCobranca extends JFrame implements IViewerSalvaCobranca{

	private ICtrlManterBoletos ctrl;

	private DecimalFormat format = new DecimalFormat("#,###.00");
	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JComboBox<Taxa> cmbTaxa;
	private JTextField txtValor;
	
	public JanelaSalvaCobranca(ICtrlManterBoletos b, Set<Taxa> listaTaxas){
		this.ctrl = b;
		setTitle("Salva Cobrança");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 270, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLabel lblTaxa = new JLabel("Taxa:");
		lblTaxa.setBounds(30, 11, 46, 14);
		contentPane.add(lblTaxa);
		
		cmbTaxa = new JComboBox<Taxa>();
		if(listaTaxas != null){
			for(Taxa t : listaTaxas){
				cmbTaxa.addItem(t);
			}
		}
		cmbTaxa.setBounds(70, 9, 180, 20);
		contentPane.add(cmbTaxa);
		
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
							if(!(txtValor.getText().isEmpty())){
								Float valor  = Float.parseFloat(txtValor.getText());
								txtValor.setText(format.format(valor));
							}
						}
					}
				});
		
		contentPane.add(txtValor);
			
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
		btnCancelar.setBounds(160, 80, 90, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		Taxa taxa = (Taxa) cmbTaxa.getSelectedItem();
		float valor = Float.parseFloat(txtValor.getText().replace(".", "").replace(",", "."));
		
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluirCobranca(valor,taxa);
			else
				ctrl.alterarCobranca(valor,taxa);
		} catch(ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void executarCancelar() {
		if(!ehAlteracao)
			ctrl.cancelarIncluirCobranca();
		else
			ctrl.cancelarAlterarCobranca();
	}
	
	@Override
	public void atualizarCampos(float valor,Taxa taxa) {
		this.cmbTaxa.setSelectedItem(taxa);
		this.txtValor.setText(format.format(valor));
		this.ehAlteracao = true;
	}

}

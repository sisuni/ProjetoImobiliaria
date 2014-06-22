package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.Imovel;
import model.Inquilino;
import model.ModelException;
import control.ICtrlManterContratos;

public class JanelaSalvaContrato extends JFrame implements IViewerSalvaContrato{
	
	private ICtrlManterContratos ctrl;

	private boolean ehAlteracao;
	
	private static int VALOR_INICIAL=1; // atributo para o JSpiner
	private static int VALOR_MIN=1; // atributo para o JSpiner
	private static int VALOR_MAX=100; // atributo para o JSpiner percentual
	private static int VALOR_MAX_MES=500; // atributo para o JSpiner relacionado o máximo de mês em uma duração
	private static int VALOR_PASSO=1; // atributo para o JSpiner
	
	private DecimalFormat format = new DecimalFormat("#,###.00");
	private SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
	
	private JPanel contentPane;
	private JComboBox<Imovel> cmbImoveis;
	private JComboBox<Inquilino> cmbInquilinos;
	private JSpinner txtDuracao;
	private JFormattedTextField txtDataInicial;
	private JSpinner txtPerc;
	private JTextField txtValAluguel;
	
	public JanelaSalvaContrato(ICtrlManterContratos sc, Set<Imovel> imoveis, Set<Inquilino> inquilinos) throws ParseException{
		this.ctrl = sc;
		setTitle("Salvar Contrato - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		setResizable(false); //não maximizar, aumentar	
		
		JLabel lblImovel = new JLabel("Imóvel:");
		lblImovel.setBounds(20, 18, 50, 14);
		contentPane.add(lblImovel);
		
		cmbImoveis = new JComboBox<Imovel>();
		cmbImoveis.setBounds(76,15,360,20);
		for(Imovel imo : imoveis){
			if(imo != null)
				cmbImoveis.addItem(imo);
		}
		contentPane.add(cmbImoveis);
		
		JLabel lblInquilino = new JLabel("Inquilino:");
		lblInquilino.setBounds(20, 52, 50, 14);
		contentPane.add(lblInquilino);
			
		cmbInquilinos = new JComboBox<Inquilino>();
		cmbInquilinos.setBounds(76,49,360,20);
		for(Inquilino inq : inquilinos){
			if(inq != null)
				cmbInquilinos.addItem(inq);
		}
		
		contentPane.add(cmbInquilinos);
				
		JLabel lblDadosC = new JLabel("Dados do Contrato");
		lblDadosC.setBounds(20, 90, 150, 20);
		lblDadosC.setFont(lblDadosC.getFont().deriveFont(15.0f));
		contentPane.add(lblDadosC);
		
		JLabel lblDuracao = new JLabel("Duração:");
		lblDuracao.setBounds(20,120,80,14);
		contentPane.add(lblDuracao);
		
		SpinnerModel modeloDuracao = new SpinnerNumberModel(VALOR_INICIAL, VALOR_MIN, VALOR_MAX_MES, VALOR_PASSO);
		txtDuracao = new JSpinner(modeloDuracao);
		txtDuracao.setBounds(76,120,50,20);
		contentPane.add(txtDuracao);
		
		JLabel lblMeses = new JLabel("meses");
		lblMeses.setBounds(130,122,50,14);
		contentPane.add(lblMeses);
		
		JLabel lblDataInicial = new JLabel("Data Inicial:");
		lblDataInicial.setBounds(240,122,150,14);
		contentPane.add(lblDataInicial);
		
		txtDataInicial = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtDataInicial.setBounds(310,120,75,20);
		contentPane.add(txtDataInicial);
				
		JLabel lblValAluguel = new JLabel("<html>Valor<br>Aluguel:</html>");
		lblValAluguel.setBounds(20,110,110,100);
		contentPane.add(lblValAluguel);
		
		txtValAluguel = new JTextField();
		txtValAluguel.setBounds(76,160,75,20);
		txtValAluguel.addFocusListener(
				new FocusListener(){
					public void focusGained(FocusEvent e){};
					
					public void focusLost(FocusEvent e){
						if (!e.isTemporary() && isEnabled() ) {
							Float valor  = Float.parseFloat(txtValAluguel.getText());
							txtValAluguel.setText(format.format(valor));
						}
					}
				});
		
		contentPane.add(txtValAluguel);
			
		JLabel lblPerc = new JLabel("Percentual (%):");
		lblPerc.setBounds(220,160,100,14);
		contentPane.add(lblPerc);
		
		SpinnerModel modelo = new SpinnerNumberModel(VALOR_INICIAL, VALOR_MIN, VALOR_MAX, VALOR_PASSO);
		txtPerc = new JSpinner(modelo);
		txtPerc.setBounds(310,160,50,20);
		contentPane.add(txtPerc);
		
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(80, 210, 120, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(240, 210, 120, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		Imovel imo = (Imovel)cmbImoveis.getSelectedItem();
		Inquilino inq = (Inquilino)cmbInquilinos.getSelectedItem();
		int dura = (int) txtDuracao.getValue();
		Date dataI = null;
		try {
			 dataI = formatar.parse(txtDataInicial.getText());
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Data Inválida!");
			e.printStackTrace();
		}
		int percent = (int) txtPerc.getValue();
		float valorAluguel = Float.parseFloat(txtValAluguel.getText().replace(".", "").replace(",", "."));
		
		try {
			if(!ehAlteracao)
				ctrl.incluir(dura,dataI,percent,valorAluguel,imo,inq);
			else
				ctrl.alterar(dura,dataI,percent,valorAluguel,imo,inq);
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
	public void atualizarCampos(int duracao, Date dataInicio, int percentProprietario, float valorAluguel, Imovel imo, Inquilino inq) {
		this.txtDuracao.setValue(duracao);
		this.txtDataInicial.setText(formatar.format(dataInicio));
		this.txtPerc.setValue(percentProprietario);
		this.txtValAluguel.setText(format.format(valorAluguel));
		this.cmbImoveis.setSelectedItem(imo);
		this.cmbInquilinos.setSelectedItem(inq);
		this.ehAlteracao=true;
	}

}

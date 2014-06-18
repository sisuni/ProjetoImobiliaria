package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import model.ModelException;
import model.Proprietario;
import control.ICtrlManterImoveis;

public class JanelaSalvaImovel extends JFrame implements IViewerSalvaImovel{
	
	private ICtrlManterImoveis ctrl;

	private boolean ehAlteracao;
	
	private DecimalFormat format = new DecimalFormat("#,###.00");  //Formatação para o Valor Base
	private static int VALOR_INICIAL=0; // atributo para o JSpiner
	private static int VALOR_MIN=0; // atributo para o JSpiner
	private static int VALOR_MAX=100; // atributo para o JSpiner
	private static int VALOR_PASSO=1; // atributo para o JSpiner
	
	private static final String[] listaTipos = {"Apartamento Padrão","Box/Garagem","Casa Comercial","Casa De Condomínio",
		"Casa De Vlia","Casa Padrão","Chácara","Conjunto Comercial","Fazenda","Flat","Galpão/Depósito","Indústria",
		"Kitchenette","Loft","Loja/Salão","Loteamento","Prédio Inteiro","Sítio","Studio","Terreno Padrão"};
	
	private static final String[] listaUF = {"AL","AM","AP","BA","CE","DF","ES","GO","MA","MG","MS","MT","PA","PB","PE",
											 "PI","PR","RJ","RN","RO","RR","RS","SC","SE","SP","TO"};
	
	/**************** Componentes da View *******************************/
	private JPanel contentPane;
	private JComboBox<Proprietario> cmbProrietarios;
	private JComboBox<String> cmbUF;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtLog;
	private JTextField txtNum;
	private JTextField txtComp;
	private JTextField txtVB;
	private JTextField txtDim;
	private JSpinner txtNQts;
	private JTextArea txtDesc;
	private JRadioButton rbAluguel;
	private JRadioButton rbVenda;
	private ButtonGroup bgFinalidade;
	private JComboBox<String> cmbTipos;

	/**************** Fim dos Componentes da View ***********************/
	
	
	public JanelaSalvaImovel(ICtrlManterImoveis si, Set<Proprietario> Proprietarios){
		this.ctrl = si;
		setTitle("Salvar Imóvel - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 530, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		setResizable(false); //não maximizar, aumentar	
		
		JLabel lblPro = new JLabel("Proprietário:");
		lblPro.setBounds(20, 11, 150, 20);
		lblPro.setFont(lblPro.getFont().deriveFont(15.0f));
		contentPane.add(lblPro);
		
		cmbProrietarios = new JComboBox<Proprietario>();
		cmbProrietarios.setBounds(115, 12, 395, 20);
		contentPane.add(cmbProrietarios);
		
		for(Proprietario p : Proprietarios){
			if(p != null)
				cmbProrietarios.addItem(p);
		}
		
		
		JLabel lblEnd = new JLabel("Endereço");
		lblEnd.setBounds(20, 53, 150, 20);
		lblEnd.setFont(lblEnd.getFont().deriveFont(15.0f));
		contentPane.add(lblEnd);

		JLabel lblUf = new JLabel("UF");
		lblUf.setBounds(30, 83, 46, 14);
		contentPane.add(lblUf);
		
		cmbUF = new JComboBox<String>(listaUF);
		cmbUF.setBounds(50, 81, 46, 20);
		contentPane.add(cmbUF);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(105, 83, 46, 14);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(150, 81, 150, 20);
		contentPane.add(txtCidade);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(310, 83, 46, 14);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(350, 81, 160, 20);
		contentPane.add(txtBairro);
		
		JLabel lblLogradouro = new JLabel("Logradouro");
		lblLogradouro.setBounds(23, 119, 90, 14);
		contentPane.add(lblLogradouro);
		
		txtLog = new JTextField();
		txtLog.setBounds(100, 118, 251, 20);
		contentPane.add(txtLog);
		
		JLabel lblNum = new JLabel("Número");
		lblNum.setBounds(365, 120, 90, 14);
		contentPane.add(lblNum);
		
		txtNum = new JTextField();
		txtNum.setBounds(415, 118, 95, 20);
		contentPane.add(txtNum);
		
		JLabel lblComp = new JLabel("Complemento");
		lblComp.setBounds(10, 150, 90, 14);
		contentPane.add(lblComp);
		
		txtComp = new JTextField();
		txtComp.setBounds(100, 150, 410, 20);
		contentPane.add(txtComp);
		
		JLabel lblDadosI = new JLabel("Dados do Imóvel");
		lblDadosI.setBounds(20, 180, 150, 14);
		lblDadosI.setFont(lblDadosI.getFont().deriveFont(15.0f));
		contentPane.add(lblDadosI);
		
		JLabel lblVB = new JLabel("Valor Base");
		lblVB.setBounds(30, 212, 90, 14);
		contentPane.add(lblVB);
		
		txtVB = new JTextField();
		txtVB.setBounds(100, 210, 100, 20);
		txtVB.addFocusListener(
				new FocusListener(){
					public void focusGained(FocusEvent e){};
					
					public void focusLost(FocusEvent e){
						if (!e.isTemporary() && isEnabled() ) {
							Float valor  = Float.parseFloat(txtVB.getText());
							txtVB.setText(format.format(valor));
						}
					}
				});
		
		contentPane.add(txtVB);
		
		JLabel lblDim = new JLabel("Dimensão");
		lblDim.setBounds(207, 212, 90, 14);
		contentPane.add(lblDim);
		
		txtDim = new JTextField();
		txtDim.setBounds(270, 210, 100, 20);
		contentPane.add(txtDim);
		
		JLabel lblNQts = new JLabel("Nº de Quartos");
		lblNQts.setBounds(380, 212, 90, 14);
		contentPane.add(lblNQts);
		
		SpinnerModel modelo = new SpinnerNumberModel(VALOR_INICIAL, VALOR_MIN, VALOR_MAX, VALOR_PASSO);
		txtNQts = new JSpinner(modelo);
		txtNQts.setBounds(465,210,40,20);
		contentPane.add(txtNQts);
		
		JLabel lblFin = new JLabel("Finalidade");
		lblFin.setBounds(30, 242, 90, 14);
		contentPane.add(lblFin);
		
		rbAluguel = new JRadioButton("Aluguel", true);
		rbAluguel.setBounds(100,240, 70, 20);
		contentPane.add(rbAluguel);
		
		rbVenda = new JRadioButton("Venda", false);
		rbVenda.setBounds(170,240, 65, 20);
		contentPane.add(rbVenda);
		
		bgFinalidade = new ButtonGroup();
		bgFinalidade.add(rbAluguel);
		bgFinalidade.add(rbVenda);
		
		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(240, 243, 90, 14);
		contentPane.add(lblTipo);
				
		cmbTipos = new JComboBox<String>(listaTipos);
		cmbTipos.setBounds(272,241,233,20);
		contentPane.add(cmbTipos);
		
		JLabel lblDesc = new JLabel("Descrição");
		lblDesc.setBounds(30, 270, 90, 14);
		contentPane.add(lblDesc);
		
		txtDesc = new JTextArea();
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		Box boxDesc = Box.createVerticalBox();
		boxDesc.setBounds(100,270,405,85);
		JScrollPane pane = new JScrollPane(txtDesc);
		boxDesc.add(pane);
		contentPane.add(boxDesc);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(100, 370, 143, 23);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(299, 370, 154, 23);
		contentPane.add(btnCancelar);
		
		this.setVisible(true);
		
	}
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		Proprietario proprietario	= (Proprietario) cmbProrietarios.getSelectedItem();
		String uf 					= (String) cmbUF.getSelectedItem();
		String cidade 				= txtCidade.getText();
		String bairro 				= txtBairro.getText();
		String logradouro			= txtLog.getText();
		int numero 					= Integer.parseInt(txtNum.getText());
		String complemento 			= txtComp.getText();
		float valorBase				= Float.parseFloat(txtVB.getText().replace(".", "").replace(",", "."));
		String dimensoes 			= txtDim.getText();
		int qtdQuartos				= (int) txtNQts.getValue();
		String descricao			= txtDesc.getText();
		String tipo					= (String) cmbTipos.getSelectedItem();
		String finalidade;
		if(rbAluguel.getText().equals("Aluguel"))
			finalidade = "Aluguel";
		else
			finalidade = "Venda";
		
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(uf, cidade, bairro, logradouro, numero, 
							complemento, valorBase, dimensoes, qtdQuartos,
							descricao, finalidade, tipo, false, proprietario);
			else
				ctrl.alterar(uf, cidade, bairro, logradouro, numero, 
							complemento, valorBase, dimensoes, qtdQuartos, 
							descricao, finalidade, tipo, false, proprietario);
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
	public void atualizarCampos(String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao,
			String finalidade, String tipo, boolean status,
			Proprietario proprietario) {
			
		this.cmbUF.setSelectedItem(uf);
		this.txtCidade.setText(cidade);
		this.txtBairro.setText(bairro);
		this.txtLog.setText(logradouro);
		this.txtNum.setText(Integer.toString(numero));
		this.txtComp.setText(complemento);
		this.txtVB.setText(format.format(valorBase));
		this.txtNQts.setValue(qtdQuartos);
		this.txtDesc.setText(descricao);
		this.txtDim.setText(dimensoes);
		if(finalidade.equals("Aluguel"))
			rbAluguel.setSelected(true);
		else
			rbVenda.setSelected(true);
		this.cmbTipos.setSelectedItem(tipo);
		this.cmbProrietarios.setSelectedItem(proprietario);
		this.ehAlteracao=true;			
	}

}

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import model.ModelException;
import control.ICtrlManterProprietarios;
import control.ICtrlManterTelefones;
import control.ITabelavel;

public class JanelaSalvaProprietario extends JFrame implements
		IViewerSalvaProprietario {

	private ICtrlManterProprietarios ctrl;
	private ICtrlManterTelefones ctrlTel;

	private boolean ehAlteracao;

	private JPanel contentPane;
	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private JTextField txtEmail;
	private JTextField txtEnd;
	private JComboBox<String> cmbBanco;
	private JTextField txtConta;
	private JTextField txtAgencia;
	private JTable tableNum;

	public JanelaSalvaProprietario(ICtrlManterProprietarios sc, ICtrlManterTelefones tel) throws ParseException {
		this.ctrl = sc;
		this.ctrlTel = tel;
		setTitle("Salva Proprietario - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 420, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel lblDadosC = new JLabel("Dados Cadastrais");
		lblDadosC.setBounds(20, 13, 150, 14);
		lblDadosC.setFont(lblDadosC.getFont().deriveFont(16.0f));
		contentPane.add(lblDadosC);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(30, 43, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(72, 41, 330, 20);
		contentPane.add(txtNome);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(41, 73, 46, 14);
		contentPane.add(lblCpf);

		txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		txtCpf.setBounds(72, 71, 100, 20);
		contentPane.add(txtCpf);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(180, 73, 46, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(220, 71, 181, 20);
		contentPane.add(txtEmail);

		JLabel lblEnd = new JLabel("Endereço:");
		lblEnd.setBounds(9, 101, 80, 20);
		contentPane.add(lblEnd);

		txtEnd = new JTextField();
		txtEnd.setBounds(72, 103, 330, 20);
		contentPane.add(txtEnd);

		JLabel lblDadosP = new JLabel("Dados Financeiros");
		lblDadosP.setBounds(20, 143, 150, 14);
		lblDadosP.setFont(lblDadosP.getFont().deriveFont(16.0f));
		contentPane.add(lblDadosP);

		JLabel lblBanco = new JLabel("Banco:");
		lblBanco.setBounds(30, 173, 46, 14);
		contentPane.add(lblBanco);

		preencherBancos();
		
		JLabel lblAgencia = new JLabel("Agência:");
		lblAgencia.setBounds(21, 210, 90, 14);
		contentPane.add(lblAgencia);

		txtAgencia = new JTextField();
		txtAgencia.setBounds(72, 208, 100, 20);
		contentPane.add(txtAgencia);

		JLabel lblConta = new JLabel("Conta:");
		lblConta.setBounds(180, 210, 46, 14);
		contentPane.add(lblConta);

		txtConta = new JTextField();
		txtConta.setBounds(220, 208, 100, 20);
		contentPane.add(txtConta);
		
		JLabel lblTel = new JLabel("Telefones");
		lblTel.setBounds(20, 245, 150, 14);
		lblTel.setFont(lblTel.getFont().deriveFont(16.0f));
		contentPane.add(lblTel);
		
		/*Inicio da Table*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 270, 380, 120);
		contentPane.add(scrollPane);
		
		tableNum = new JTable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tableNum.getDefaultRenderer(JLabel.class);  
        renderer.setHorizontalAlignment(SwingConstants.CENTER); 
        tableNum.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tipo", "Número"
			}
		));
        tableNum.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableNum.getColumnModel().getColumn(1).setPreferredWidth(220);
			
     // monitorando o evento através de TableModelListener
		tableNum.getModel().addTableModelListener(new TableModelListener() {
		    public void tableChanged(TableModelEvent e) {
		    	tratarModificacaoNaTabela(e);
		    }
		});
		
		scrollPane.setViewportView(tableNum);
		this.setVisible(true);
		/*Fim da Table*/
				
		/*Inicio dos Botões Telefone*/
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarIncluir();
			}
		});
		btnIncluir.setBounds(60, 395, 89, 23);
		btnIncluir.setToolTipText("Incluir Novo Telefone");
		contentPane.add(btnIncluir);
			
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarAlterar();
			}
		});
		btnAlterar.setBounds(162, 395, 89, 23);
		btnAlterar.setToolTipText("Alterar Telefone Selecionado");
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarExcluir();
			}
		});
		btnExcluir.setBounds(263, 395, 89, 23);
		btnExcluir.setToolTipText("Excluir Telefone Selecionado");
		contentPane.add(btnExcluir);
		
		/*Fim dos Botões Telefone*/
		
		
		/*Inicio dos Botões Salva Proprietario*/
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(53, 455, 143, 23);
		btnSalvar.setToolTipText("Salvar Proprietário");
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(216, 455, 154, 23);
		btnCancelar.setToolTipText("Cancelar");
		contentPane.add(btnCancelar);
		
		/*Fim dos Botões Salva Proprietario*/
		
		this.setVisible(true);
	}
	
	/* Inicio dos metódos pertencentes ao telefone*/
	public void limpar() {
		DefaultTableModel dtm = (DefaultTableModel)this.tableNum.getModel();
		while(dtm.getRowCount() > 0)
			dtm.removeRow(0);
	}
	
	public void incluirLinha(ITabelavel objeto) {
		DefaultTableModel dtm = (DefaultTableModel)this.tableNum.getModel();
		dtm.addRow(objeto.getData());
	}
	
	public void executarIncluir() {
		this.ctrlTel.iniciarIncluir();
	}
	
	public void executarExcluir() {
		// Recupero a posição selecionada
		int pos = tableNum.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de exclusão
		ctrlTel.iniciarExcluir(pos);	
	}
	
	public void executarAlterar() {
		// Recupero a posição selecionada
		int pos = tableNum.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de alteração
		ctrlTel.iniciarAlterar(pos);	
	}
	
	/* Fim dos metódos pertencentes ao telefone*/
	
	/*Método para preencher a lista de 124 bancos na combo*/
	public void preencherBancos(){
		String[] listaBancos = {"Banco ABC Brasil S.A.","Banco ABN AMRO S.A.","Banco Alfa S.A.","Banco Alvorada S.A.","Banco Banerj S.A.","Banco Bankpar S.A.",
				"Banco Barclays S.A.","Banco BBM S.A.","Banco Beg S.A.","Banco BGN S.A.","Banco BM&FBOVESPA de Serviços de Liquidação e Custódia S.A","Banco BMG S.A.",
				"Banco BNP Paribas Brasil S.A.","Banco Boavista Interatlântico S.A.","Banco Bonsucesso S.A.","Banco Bracce S.A.","Banco Bradesco BBI S.A.","Banco Bradesco Cartões S.A.",
				"Banco Bradesco Financiamentos S.A.","Banco Bradesco S.A.","Banco BTG Pactual S.A.","Banco Cacique S.A.","Banco Caixa Geral - Brasil S.A.","Banco Cargill S.A.","Banco Caterpillar S.A.",
				"Banco Cifra S.A.","Banco Citibank S.A.","Banco Citicard S.A.","Banco CNH Capital S.A.","Banco Comercial e de Investimento Sudameris S.A.","Banco Confidence de Câmbio S.A.","Banco Cooperativo do Brasil S.A. - BANCOOB",
				"Banco Cooperativo Sicredi S.A.","Banco Credit Agricole Brasil S.A.","Banco Credit Suisse (Brasil) S.A.","Banco CSF S.A.","Banco da Amazônia S.A.","Banco da China Brasil S.A.","Banco Daycoval S.A.","Banco de Lage Landen Brasil S.A.",
				"Banco de Pernambuco S.A. - BANDEPE","Banco de Tokyo-Mitsubishi UFJ Brasil S.A.","Banco Dibens S.A.","Banco do Brasil S.A.","Banco do Estado de Sergipe S.A.","Banco do Estado do Pará S.A.","Banco do Estado do Rio Grande do Sul S.A.",
				"Banco do Nordeste do Brasil S.A.","Banco Fator S.A.","Banco Fiat S.A.","Banco Fibra S.A.","Banco Ficsa S.A.","Banco Fidis S.A.","Banco Finasa BMC S.A.","Banco Ford S.A.","Banco GMAC S.A.","Banco Guanabara S.A.","Banco Honda S.A.",
				"Banco Ibi S.A. - Banco Múltiplo","Banco IBM S.A.","Banco Industrial do Brasil S.A.","Banco Industrial e Comercial S.A.","Banco Indusval S.A.","Banco Investcred UniBanco S.A.","Banco Itaú BBA S.A.","Banco ItaúBank S.A","Banco Itaucard S.A.",
				"Banco Itaucred Financiamentos S.A.","Banco J. P. Morgan S.A.","Banco J. Safra S.A.","Banco John Deere S.A.","Banco Luso Brasileiro S.A.","Banco Mercantil do Brasil S.A.","Banco Mizuho do Brasil S.A.","Banco Modal S.A.","Banco Opportunity S.A.",
				"Banco Original do Agronegócio S.A.","Banco Panamericano S.A.","Banco Paulista S.A.","Banco Pine S.A.","Banco PSA Finance Brasil S.A.","Banco Rabobank International Brasil S.A.","Banco Real S.A.","Banco Rendimento S.A.","Banco Rodobens S.A.",
				"Banco Rural Mais S.A.","Banco Rural S.A.","Banco Safra S.A.","Banco Santander (Brasil) S.A.","Banco Simples S.A.","Banco Société Générale Brasil S.A.","Banco Standard de Investimentos S.A.","Banco Sumitomo Mitsui Brasileiro S.A.","Banco Topázio S.A.",
				"Banco Toyota do Brasil S.A.","Banco Triângulo S.A.","Banco Volkswagen S.A.","Banco Volvo (Brasil) S.A.","Banco Votorantim S.A.","Banco VR S.A.","Banco Western Union do Brasil S.A.","Banco Yamaha Motor S.A.","BANESTES S.A. Banco do Estado do Espírito Santo",
				"Banif-Banco Internacional do Funchal (Brasil)S.A.","Bank of America Merrill Lynch Banco Múltiplo S.A.","BB Banco Popular do Brasil S.A.","BCV - Banco de Crédito e Varejo S.A.","BES Investimento do Brasil S.A. - Banco de Investimento","BNY Mellon Banco S.A.",
				"BPN Brasil Banco Múltiplo S.A.","Brasil Plural S.A. - Banco Múltiplo","BRB - Banco de Brasília S.A.","Caixa Econômica Federal","Citibank S.A.","Concórdia Banco S.A.","Deutsche Bank S.A. - Banco Alemão","Goldman Sachs do Brasil Banco Múltiplo S.A.","Hipercard Banco Múltiplo S.A.",
				"HSBC Bank Brasil S.A. - Banco Múltiplo","ING Bank N.V.","Itaú UniBanco Holding S.A.","Itaú UniBanco S.A.","JPMorgan Chase Bank","Scotiabank Brasil S.A. Banco Múltiplo","Standard Chartered Bank (Brasil) S/A–Bco Invest.","UBS Brasil Banco de Investimento S.A.","UNIBANCO - União de Bancos Brasileiros S.A.",
				"Unicard Banco Múltiplo S.A."};
		cmbBanco = new JComboBox<String>(listaBancos);
		cmbBanco.setBounds(72, 172, 330, 18);
		contentPane.add(cmbBanco);
	}
	/*Fim do Método para preencher a lista de 124 bancos na combo*/
	
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields
		String nome = txtNome.getText();
		String cpf = txtCpf.getText().replace(".","").replace("-", "");
		String email = txtEmail.getText();
		String endereco = txtEnd.getText();
		String banco = (String) cmbBanco.getSelectedItem();
		String conta = txtConta.getText();
		String agencia = txtAgencia.getText();
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(nome,cpf,email,endereco,banco,agencia,conta);
			else
				ctrl.alterar(nome,cpf,email,endereco,banco,agencia,conta);
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
	public void atualizarCampos(String nome, String cpf, String email,
			String endereco, String banco, String agencia, String conta) {
		// TODO Auto-generated method stub
		this.txtNome.setText(nome);
		this.txtCpf.setText(cpf);
		this.txtEmail.setText(email);
		this.txtEnd.setText(endereco);
		this.cmbBanco.setSelectedItem(banco);
		this.txtAgencia.setText(agencia);
		this.txtConta.setText(conta);
		this.ehAlteracao = true;

	}
	
	public void tratarModificacaoNaTabela(TableModelEvent e) {
        if(e.getType() != TableModelEvent.UPDATE)
        	return;
		int linha = e.getFirstRow();
        int coluna = e.getColumn();
 
        TableModel model = (TableModel) e.getSource();
 
        System.out.println("Você alterou a linha " + linha + ", coluna " + coluna);
        System.out.println("Valor da célula alterada: " + model.getValueAt(linha, coluna));
	}
}

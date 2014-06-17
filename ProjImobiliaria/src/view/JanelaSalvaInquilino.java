package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
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
import control.ICtrlManterInquilinos;
import control.ITabelavel;

public class JanelaSalvaInquilino extends JFrame implements IViewerSalvaInquilino{
	
	private ICtrlManterInquilinos ctrl;

	private boolean ehAlteracao;
	
	private JPanel contentPane;
	private JTextField txtNome;
	private JFormattedTextField txtCpf;
	private JTextField txtEmail;
	private JTextField txtEnd;
	private JTextField txtEndAnt;
	private JTable tableNum;


	
	public JanelaSalvaInquilino(ICtrlManterInquilinos sc) throws ParseException{
		this.ctrl = sc;
		setTitle("Salva Inquílino - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 420, 490);
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
		
		JLabel lblDadosP = new JLabel("Dados Anteriores");
		lblDadosP.setBounds(20, 143, 150, 14);
		lblDadosP.setFont(lblDadosP.getFont().deriveFont(16.0f));
		contentPane.add(lblDadosP);
		
		JLabel lblEndAnt = new JLabel("<html>Endereço<br>Anterior:<html>");
		lblEndAnt.setBounds(9, 143, 100, 80);
		contentPane.add(lblEndAnt);
		
		txtEndAnt = new JTextField();
		txtEndAnt.setBounds(72, 173, 330, 20);
		contentPane.add(txtEndAnt);
		
		
		JLabel lblTel = new JLabel("Telefones");
		lblTel.setBounds(20, 215, 150, 14);
		lblTel.setFont(lblTel.getFont().deriveFont(16.0f));
		contentPane.add(lblTel);
		
		/*Inicio da Table*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 240, 380, 120);
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
		/************Fim da Table***********/
				
		/*****************Inicio dos Botões Telefone*******************/
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarIncluirTelefone();
			}
		});
		btnIncluir.setBounds(60, 365, 89, 23);
		btnIncluir.setToolTipText("Incluir Novo Telefone");
		contentPane.add(btnIncluir);
			
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarAlterarTelefone();
			}
		});
		btnAlterar.setBounds(162, 365, 89, 23);
		btnAlterar.setToolTipText("Alterar Telefone Selecionado");
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarExcluirTelefone();
			}
		});
		btnExcluir.setBounds(263, 365, 89, 23);
		btnExcluir.setToolTipText("Excluir Telefone Selecionado");
		contentPane.add(btnExcluir);
		
		/***********Fim dos Botões Telefone****************/
		
		/***************Inicio dos Botões Salva Inquilino****************/
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(53, 425, 143, 23);
		btnSalvar.setToolTipText("Salvar Proprietário");
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(216, 425, 154, 23);
		btnCancelar.setToolTipText("Cancelar");
		contentPane.add(btnCancelar);
		/***************Fim dos Botões Salva Inquilino****************/
		
		this.setVisible(true);
		
	}

	@Override
	public void atualizarCampos(String nome, String cpf, String email,
			String endereco, String endAnteriorCompleto) {
		this.txtNome.setText(nome);
		this.txtCpf.setText(cpf);
		this.txtEmail.setText(email);
		this.txtEnd.setText(endereco);
		this.txtEndAnt.setText(endAnteriorCompleto);
		this.ehAlteracao = true;
	}

	@Override
	public void limpar() {
		DefaultTableModel dtm = (DefaultTableModel)this.tableNum.getModel();
		while(dtm.getRowCount() > 0)
			dtm.removeRow(0);
	}

	@Override
	public void incluirLinha(ITabelavel objeto) {
		DefaultTableModel dtm = (DefaultTableModel)this.tableNum.getModel();
		dtm.addRow(objeto.getData());
	}
	
	public void executarSalvar() {
		String nome = txtNome.getText();
		String cpf = txtCpf.getText().replace(".","").replace("-", "");
		String email = txtEmail.getText();
		String endereco = txtEnd.getText();
		String endAnt = txtEndAnt.getText();
		
		// Verifico qual é a operação que estou fazendo
		// e notifico ao controlador
		try {
			if(!ehAlteracao)
				ctrl.incluir(nome, cpf, email, endereco, endAnt);
			else
				ctrl.alterar(nome, cpf, email, endereco, endAnt);
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

	
	/**métodos dos botões telefone **/
	@Override
	public void executarIncluirTelefone() {
		this.ctrl.iniciarIncluirTelefone();
	}

	@Override
	public void executarExcluirTelefone() {
		int pos = tableNum.getSelectedRow();
		if(pos < 0)
			return;
		
		ctrl.iniciarExcluirTelefone(pos);	
		
	}

	@Override
	public void executarAlterarTelefone() {
		int pos = tableNum.getSelectedRow();
		if(pos < 0)
			return;
		
		ctrl.iniciarAlterarTelefone(pos);
	}
	/**fim dos métodos dos botões telefone **/
	
	
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

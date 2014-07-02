package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import model.Cobra;
import model.Contrato;
import model.Imovel;
import model.ModelException;
import model.Taxa;
import control.ICtrlManterBoletos;
import control.ITabelavel;

public class JanelaSalvaBoleto extends JFrame implements IViewerSalvaBoleto {

	private ICtrlManterBoletos ctrl;

	private boolean ehAlteracao;

	private JPanel contentPane;
	private JComboBox<Contrato> cmbContrato;
	private JFormattedTextField txtDataVenc;
	private JLabel txtValorT;
	private JTable tableTax;
	private SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
	private DecimalFormat formatValor = new DecimalFormat("#,###.00");

	public JanelaSalvaBoleto(ICtrlManterBoletos sc, Set<Contrato> contratos, boolean e_alteracao)
			throws ParseException {
		this.ctrl = sc;
		this.setEhAlteracao(e_alteracao);
		setTitle("Salvar Boletos - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 417, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		setResizable(false); // não maximizar, aumentar

		/* Inicio da Table */
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 160, 380, 120);
		contentPane.add(scrollPane);

		tableTax = new JTable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tableTax
				.getDefaultRenderer(JLabel.class);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		tableTax.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Descrição", "Valor" }));
		tableTax.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableTax.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableTax.getColumnModel().getColumn(2).setPreferredWidth(60);

		// monitorando o evento através de TableModelListener
		tableTax.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				tratarModificacaoNaTabela(e);
			}
		});

		scrollPane.setViewportView(tableTax);
		this.setVisible(true);
		/************ Fim da Table ***********/

		JLabel lblContrato = new JLabel("Contrato:");
		lblContrato.setBounds(20, 11, 150, 14);
		lblContrato.setFont(lblContrato.getFont().deriveFont(15.0f));
		contentPane.add(lblContrato);

		cmbContrato = new JComboBox<Contrato>();
		cmbContrato.setBounds(100, 11, 300, 18);
		for (Contrato c : contratos) {
			if (c != null)
				cmbContrato.addItem(c);
		}
		cmbContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selecionaContrato();
			}
		});
		contentPane.add(cmbContrato);

		JLabel lblDadosC = new JLabel("Dados do Boleto");
		lblDadosC.setBounds(20, 48, 150, 20);
		lblDadosC.setFont(lblDadosC.getFont().deriveFont(15.0f));
		contentPane.add(lblDadosC);

		JLabel lblData = new JLabel("<html>Data de<br>Vencimento:</html>");
		lblData.setBounds(22, 42, 100, 100);
		contentPane.add(lblData);

		txtDataVenc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtDataVenc.setBounds(100, 90, 75, 20);
		contentPane.add(txtDataVenc);

		JLabel lblValorT = new JLabel("<html>Valor<br>Total:</html>");
		lblValorT.setBounds(220, 42, 100, 100);
		contentPane.add(lblValorT);

		txtValorT = new JLabel();
		txtValorT.setBounds(260, 85, 140, 20);
		txtValorT.setForeground(Color.blue);
		txtValorT.setFont(txtValorT.getFont().deriveFont(20.0f));
		contentPane.add(txtValorT);

		JLabel lblTaxas = new JLabel("Taxas");
		lblTaxas.setBounds(20, 135, 150, 14);
		lblTaxas.setFont(lblTaxas.getFont().deriveFont(15.0f));
		contentPane.add(lblTaxas);

		/***************** Inicio dos Botões Taxas *******************/
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarIncluirCobranca();
			}
		});
		btnIncluir.setBounds(60, 290, 89, 23);
		btnIncluir.setToolTipText("Incluir Nova Taxa");
		contentPane.add(btnIncluir);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarAlterarCobranca();
			}
		});
		btnAlterar.setBounds(162, 290, 89, 23);
		btnAlterar.setToolTipText("Alterar Taxa Selecionada");
		contentPane.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarExcluirCobranca();
			}
		});
		btnExcluir.setBounds(263, 290, 89, 23);
		btnExcluir.setToolTipText("Excluir Taxa Selecionada");
		contentPane.add(btnExcluir);
		/*********** Fim dos Botões Taxas ****************/

		/***************** Inicio dos Botões Boleto *******************/
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarSalvar();
			}
		});
		btnSalvar.setBounds(20, 340, 143, 23);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarCancelar();
			}
		});
		btnCancelar.setBounds(245, 340, 154, 23);
		contentPane.add(btnCancelar);
		/***************** Fim dos Botões Boleto *******************/
		this.setVisible(true);
	}

	/******** Métodos do Boleto *******************/
	public void executarSalvar() {
		// Recupero os valores digitados nos textfields

		Date dataVencimento = null;
		Contrato contrato = (Contrato) cmbContrato.getSelectedItem();

		try {
			dataVencimento = formatData.parse(txtDataVenc.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			if (!ehAlteracao)
				ctrl.incluir(dataVencimento, contrato);
			else
				ctrl.alterar(dataVencimento);
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void executarCancelar() {
		if (!ehAlteracao)
			ctrl.cancelarIncluir();
		else
			ctrl.cancelarAlterar();
	}
	
	public boolean isEhAlteracao() {
		return ehAlteracao;
	}

	public void setEhAlteracao(boolean ehAlteracao) {
		this.ehAlteracao = ehAlteracao;
	}

	public void selecionaContrato() {
		if(!ehAlteracao){
			if (cmbContrato.getSelectedItem() != null) {
				Contrato c = (Contrato) cmbContrato.getSelectedItem();
				try {
					this.ctrl.selecionarContrato(c);
				} catch (ModelException e) {
					JOptionPane.showMessageDialog(null,
							"Algo de errado aconteceu com este contrato!");
					e.printStackTrace();
				}
			}
		}

	}

	public Contrato getContrato() {
		return (Contrato) this.cmbContrato.getSelectedItem();
	}

	public void setValorTotal(float valorTotal) {
		txtValorT.setText(formatValor.format(valorTotal));
	}

	public float getValorTotal() {
		if (!(this.txtValorT.getText().isEmpty()))
			return Float.parseFloat(txtValorT.getText().replace(".", "")
					.replace(",", "."));
		return 0;
	}

	@Override
	public void atualizarCampos(Date dataVencimento, Contrato contrato,
			float valorT) {
		this.cmbContrato.setSelectedItem(contrato);
		this.txtDataVenc.setText(formatData.format(dataVencimento));
		this.txtValorT.setText(formatValor.format(valorT));
		this.cmbContrato.enable(false);

	}

	/******** Fim dos Métodos do Boleto *******************/

	/******** Métodos da Taxa *******************/
	public void limpar() {
		DefaultTableModel dtm = (DefaultTableModel) this.tableTax.getModel();
		while (dtm.getRowCount() > 0)
			dtm.removeRow(0);
	}

	public void incluirLinha(ITabelavel objeto) {
		DefaultTableModel dtm = (DefaultTableModel) this.tableTax.getModel();
		dtm.addRow(objeto.getData());
	}

	public void tratarModificacaoNaTabela(TableModelEvent e) {
		if (e.getType() != TableModelEvent.UPDATE)
			return;
		int linha = e.getFirstRow();
		int coluna = e.getColumn();

		TableModel model = (TableModel) e.getSource();

		System.out.println("Você alterou a linha " + linha + ", coluna "
				+ coluna);
		System.out.println("Valor da célula alterada: "
				+ model.getValueAt(linha, coluna));
	}

	@Override
	public void executarIncluirCobranca() {
		ctrl.iniciarIncluirCobranca();
	}

	@Override
	public void executarExcluirCobranca() {
		int pos = tableTax.getSelectedRow();
		if (pos < 0)
			return;

		ctrl.iniciarExcluirCobranca(pos);
	}

	@Override
	public void executarAlterarCobranca() {
		int pos = tableTax.getSelectedRow();
		if (pos < 0)
			return;

		ctrl.iniciarAlterarCobranca(pos);
	}

	/******** Fim dos Métodos da Taxa *******************/
}

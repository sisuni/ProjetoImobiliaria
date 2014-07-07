package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import control.ICtrlManterCargos;
import control.ITabelavel;

public class JanelaCargos extends JFrame implements IViewer{
	private JPanel contentPane;
	private ICtrlManterCargos ctrl;
	private JTable table;
	private JTextField txtPesquisa;
	private JComboBox<String> cmbCampo;
	private String pesquisa = "";
	
	public JanelaCargos(ICtrlManterCargos c){
		this.ctrl = c;
		setTitle("Cargos - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 410, 380);
		contentPane = new JPanel();
		setLocationRelativeTo(null);//centralizar janela
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); 
		setResizable(false); //não maximizar, aumentar	
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
		
		
		JLabel lblCampo = new JLabel("Campo:");
		lblCampo.setBounds(28,11,100,20);
		add(lblCampo);
		
		cmbCampo = new JComboBox<String>();
		cmbCampo.addItem("Nome");
		cmbCampo.addItem("Nível");
		cmbCampo.addItem("Nº Func");
		cmbCampo.setBounds(75,13,60,20);
		add(cmbCampo);
		
		JLabel lblPesquisa = new JLabel("Pesquisar:");
		lblPesquisa.setBounds(10,41,100,20);
		add(lblPesquisa);
		
		txtPesquisa = new JTextField();
		txtPesquisa.setBounds(75,43,318,20);
		add(txtPesquisa);
		
		txtPesquisa.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String valor = Character.toString(c);
				
				if(valor.equals("\b")){
					if(pesquisa.length() > 0)
						pesquisa = pesquisa.substring(0,pesquisa.length()-1);
				}else{
					if(cmbCampo.getSelectedItem().equals("Nível") || cmbCampo.getSelectedItem().equals("Nº Func")){
						if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
							getToolkit().beep();
							e.consume();
						}else
							pesquisa += valor;	
					}else
						pesquisa += valor;
				}
				
				procurar((String)cmbCampo.getSelectedItem(), pesquisa);
			}});
			
		/*Inicio dos Botões*/
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarIncluir();
			}
		});
		btnIncluir.setBounds(10, 312, 89, 23);
		contentPane.add(btnIncluir);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarAlterar();
			}
		});
		btnAlterar.setBounds(109, 312, 89, 23);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarExcluir();
			}
		});
		btnExcluir.setBounds(208, 312, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarTerminar();
			}
		});
		btnSair.setBounds(307, 312, 89, 23);
		contentPane.add(btnSair);
		/*Fim dos Botões*/
		
		/*Inicio da Table*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 386, 212);
		contentPane.add(scrollPane);
		
		
		table = new JTable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(JLabel.class);  
        renderer.setHorizontalAlignment(SwingConstants.CENTER); 
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Nível", "NºFunc"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		// monitorando o evento através de TableModelListener
				table.getModel().addTableModelListener(new TableModelListener() {
				    public void tableChanged(TableModelEvent e) {
				    	tratarModificacaoNaTabela(e);
				    }
				});
				
				scrollPane.setViewportView(table);
				this.setVisible(true);
		/*Fim da Table*/
	}
	
	public void limpar() {
		DefaultTableModel dtm = (DefaultTableModel)this.table.getModel();
		while(dtm.getRowCount() > 0)
			dtm.removeRow(0);
	}
	
	public void incluirLinha(ITabelavel objeto) {
		DefaultTableModel dtm = (DefaultTableModel)this.table.getModel();
		dtm.addRow(objeto.getData());
	}
	
	public void executarIncluir() {
		this.ctrl.iniciarIncluir();
	}
	
	public void executarExcluir() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de exclusão
		ctrl.iniciarExcluir(pos);	
	}
	
	public void executarAlterar() {
		// Recupero a posição selecionada
		int pos = table.getSelectedRow();
		// Se a posição for -1, não há ninguém selecionado. Então cancelo
		// a operação
		if(pos < 0)
			return;
		// Informo ao controlador para iniciar o processo de alteração
		ctrl.iniciarAlterar(pos);	
	}
	
	public void executarTerminar() {
		ctrl.terminar();
	}
	
	public void procurar(String campo, String valor){
		ctrl.procurar(campo, valor);
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

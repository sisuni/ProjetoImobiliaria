package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import control.ICtrlManter;
import control.ITabelavel;

public class JanelaContratos extends JFrame implements IViewer{
	private JPanel contentPane;
	private ICtrlManter ctrl;
	private JTable table;
	
	
	public JanelaContratos(ICtrlManter c){
		this.ctrl = c;
		setTitle("Contratos - Imobiliária");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 575, 300);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); //centralizar janela
		setResizable(false); //não maximizar, aumentar		
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
		
		/*Inicio dos Botões*/
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarIncluir();
			}
		});
		btnIncluir.setBounds(109, 232, 89, 23);
		contentPane.add(btnIncluir);
			
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarAlterar();
			}
		});
		btnAlterar.setBounds(208, 232, 89, 23);
		contentPane.add(btnAlterar);
		
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarExcluir();
			}
		});
		btnExcluir.setBounds(307, 232, 89, 23);
		contentPane.add(btnExcluir);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executarTerminar();
			}
		});
		btnSair.setBounds(406, 232, 89, 23);
		contentPane.add(btnSair);
		/*Fim dos Botões*/
		
		/*Inicio da Table*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 550, 212);
		contentPane.add(scrollPane);
		
		
		table = new JTable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(JLabel.class);  
        renderer.setHorizontalAlignment(SwingConstants.CENTER); 
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Imovel", "Inquilino", "Duração", "Data Inicial", "Valor Aluguel", "Boletos"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		
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

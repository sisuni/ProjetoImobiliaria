package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.CtrlPrograma;
import control.ICtrlPrograma;

public class JanelaPrincipal extends JFrame implements  IViewerPrincipal {

	private JPanel contentPane;
	
	private ICtrlPrograma ctrlPrg;
	
	// Cria uma barra de menu para o JFrame
	private JMenuBar menuBar = new JMenuBar();

	/**
	 * Create the frame.
	 */
	public JanelaPrincipal(CtrlPrograma p) {
		this.ctrlPrg = p;
		setTitle("Menu Principal - Imobiliária");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Adiciona a barra de menu ao frame
		setJMenuBar(menuBar);
		
		// Define e adiciona dois menus drop down na barra de menus
		JMenu menu = new JMenu("Controle");
		JMenu cliente = new JMenu("Cliente");
		menuBar.add(menu);
		
		JMenuItem cargo = new JMenuItem("Cargos");
		JMenuItem func = new JMenuItem("Funcionários");
		JMenuItem proprietario = new JMenuItem("Proprietários");
		JMenuItem inquilino = new JMenuItem("Inquilinos");
		JMenuItem imovel = new JMenuItem("Imóveis");
		JMenuItem contra = new JMenuItem("Contratos");
		JMenuItem taxa = new JMenuItem("Taxas");
		JMenuItem boleto = new JMenuItem("Boletos");
		JMenuItem sair = new JMenuItem("Sair");
		ButtonGroup bg = new ButtonGroup();
		
		menu.add(cargo);
		menu.add(func);
		menu.add(cliente);
		cliente.add(proprietario);
		cliente.add(inquilino);
		menu.add(imovel);
		menu.add(taxa);
		menu.add(contra);
		menu.add(boleto);
		menu.addSeparator();
		menu.add(sair);
				
		cargo.addActionListener(new ActionListener() {
			// Método acionado quando o botão "Departamentos" 
			// for pressionado (Método de Callback).
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterCargo();
			}
		});
		
		func.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterFuncionario();
			}
		});
		
		proprietario.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterProprietarios();
			}
		});
		
		inquilino.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterInquilino();
			}
		});
		
		imovel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterImovel();
			}
		});
		
		contra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterContrato();
			}
		});
		
		taxa.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				ctrlPrg.iniciarCasoDeUsoManterTaxas();
			}
		});
	
		boleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Método de Callback).
				ctrlPrg.iniciarCasoDeUsoManterBoleto();
			}
		});
		
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Método de Callback).
				ctrlPrg.terminar();
			}
		});
		
		
		
		this.setVisible(true);
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e){
//				ctrlPrg.terminar();
//				
//			}
//		});
	}

}

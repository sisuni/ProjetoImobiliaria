package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.Funcionario;
import control.ICtrlPrograma;

public class JanelaPrincipal extends JFrame implements IViewerPrincipal {

	private JPanel contentPane;

	private ICtrlPrograma ctrlPrg;

	private JLabel horas, data, usuario; //labels da 'barra de status'

	private String diaSemana[] = { "Domingo", "Segunda-feira", "Terça-feira",
			"Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado" };

	private String meses[] = { "Janeiro", "Fevereiro", "Março", "Abril",
			"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
			"Novembro", "Dezembro" };

	// Cria uma barra de menu para o JFrame
	private JMenuBar menuBar = new JMenuBar();
		
	public JanelaPrincipal(ICtrlPrograma p) {
		this.ctrlPrg = p;
		setTitle("Menu Principal - Imobiliária");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false); // não maximizar, aumentar
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png")); //colocando icone
		
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("img/bg.png"))))); //colocando o background
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*inicio para o funcionamento da 'barra de status'*/
		Container tela = getContentPane(); 
	
		BorderLayout layout = new BorderLayout();
		tela.setLayout(layout);

		Border border = BorderFactory.createLoweredBevelBorder();

		horas = new JLabel("horas", JLabel.CENTER);
		horas.setPreferredSize(new Dimension(200, 20));
		horas.setBorder(border);

		data = new JLabel("data", JLabel.CENTER);
		data.setPreferredSize(new Dimension(350, 20));
		data.setBorder(border);
		
		usuario = new JLabel("Usuário: ADMINISTRADOR", JLabel.CENTER);
		usuario.setPreferredSize(new Dimension(239, 20));
		usuario.setBorder(border);

		JPanel inferior = new JPanel();
		inferior.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
		inferior.add(horas);
		inferior.add(data);
		inferior.add(usuario);
		
		tela.add(inferior, BorderLayout.SOUTH);

		ActionListener tarefa = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				horasData();
			}
		};

		javax.swing.Timer timer = new javax.swing.Timer(1000, tarefa);
		timer.start();
		/*Fim para o funcionamento da 'barra de status'*/

		// Adiciona a barra de menu ao frame
		setJMenuBar(menuBar);

		// Define e adiciona dois menus drop down na barra de menus
		JMenu controle = new JMenu("Controle");
		JMenu cliente = new JMenu("Cliente");
		JMenu relat = new JMenu("Relatórios");
		JMenu ajuda = new JMenu("Ajuda");
		JMenuItem sair = new JMenuItem("Encerrar Sistema");
		
		sair.setMinimumSize(new Dimension(50,22));
		sair.setMaximumSize(new Dimension(115,22));
		
		menuBar.add(controle);
		menuBar.add(relat);
		menuBar.add(ajuda);
		menuBar.add(sair);
		

		JMenuItem cargo = new JMenuItem("Cargos");
		JMenuItem func = new JMenuItem("Funcionários");
		JMenuItem proprietario = new JMenuItem("Proprietários");
		JMenuItem inquilino = new JMenuItem("Inquilinos");
		JMenuItem imovel = new JMenuItem("Imóveis");
		JMenuItem contra = new JMenuItem("Contratos");
		JMenuItem taxa = new JMenuItem("Taxas");
		JMenuItem boleto = new JMenuItem("Boletos");
		
		JMenuItem cred = new JMenuItem("Créditos");
		JMenuItem debo = new JMenuItem("Débitos");
		JMenuItem prop = new JMenuItem("Proprietário");
		JMenuItem imo = new JMenuItem("Imóveis");
		
		JMenuItem alt = new JMenuItem("Alterar Senha");
		JMenuItem sob = new JMenuItem("Sobre o Sistema");
			
		controle.add(cargo);
		controle.add(func);
		controle.addSeparator();
		controle.add(cliente);
		cliente.add(proprietario);
		cliente.add(inquilino);
		controle.add(imovel);
		controle.addSeparator();
		controle.add(contra);
		controle.add(taxa);
		controle.add(boleto);
		
		relat.add(cred);
		relat.add(debo);
		relat.add(prop);
		relat.add(imo);
		
		ajuda.add(alt);
		ajuda.add(sob);
		
		
		cargo.addActionListener(new ActionListener() {
			// Método acionado quando o botão "Departamentos"
			// for pressionado (Método de Callback).
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterCargo();
			}
		});

		func.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterFuncionario();
			}
		});

		proprietario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterProprietarios();
			}
		});

		inquilino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterInquilino();
			}
		});

		imovel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterImovel();
			}
		});

		contra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterContrato();
			}
		});

		taxa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterTaxas();
			}
		});

		boleto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarCasoDeUsoManterBoleto();
			}
		});
	
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.terminar();
			}
		});
		
		alt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ctrlPrg.iniciarAlterarSenha();
			}
		});

		
		 this.addWindowListener(new WindowAdapter() {
		 public void windowClosing(WindowEvent e){
			 int opcao = JOptionPane.showConfirmDialog(null, "Deseja encerrar salvando os dados?");
			 if(opcao == JOptionPane.YES_OPTION)
				 ctrlPrg.terminar();
			 else if(opcao == JOptionPane.NO_OPTION)
				 System.exit(0);
			 else
				 ctrlPrg.iniciar();
				 		
		 }
		 });
		
		this.setVisible(true);
	}

	private void horasData() {
		Calendar agora = Calendar.getInstance();
		int ho = agora.get(Calendar.HOUR_OF_DAY);
		int mi = agora.get(Calendar.MINUTE);
		int se = agora.get(Calendar.SECOND);

		int ds = agora.get(Calendar.DAY_OF_WEEK);
		int dia = agora.get(Calendar.DAY_OF_MONTH);
		int mes = agora.get(Calendar.MONTH);
		int ano = agora.get(Calendar.YEAR);

		horas.setText(formatar(ho % 24) + ":" + formatar(mi) + ":"
				+ formatar(se) + "");

		data.setText(diaSemana[ds - 1] + ", " + formatar(dia) + " de "
				+ meses[mes] + " de " + ano + "");
	}

	private String formatar(int num) {
		DecimalFormat df = new DecimalFormat("00");

		return df.format(num);
	}
	
	@Override
	public void setUsuario(Funcionario usuario){
		this.usuario.setText(usuario.getCargo().toString()+" : "+usuario.getLogin().toUpperCase());
	}

}

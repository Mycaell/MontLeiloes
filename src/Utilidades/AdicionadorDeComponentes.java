package Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;


public class AdicionadorDeComponentes {
	
	public static JTable adicionarTabelaComLeiloesDeUmUsuario(JFrame janela, Usuario usuario, ArrayList<Leilao> leiloes, int x, int y, int c, int a) {
		
		DefaultTableModel modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable tabela = new JTable(modelo);


		if(leiloes.size() != 0) {
			
			modelo.addColumn("Produto");
			modelo.addColumn("Preço");
			modelo.addColumn("Ultimo Lance");
			modelo.addColumn("Fechamento");
			
			for (Leilao leilao : leiloes) {
				
				Date dataDeExpiracao = calcularDataDeExpiracao(leilao.getDataDeCadastro(), leilao.getDias(), leilao.getHoras());
				

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String  dataDeExpiracaoFormatada = formato.format(dataDeExpiracao);
					
				Object[] linha = new Object[] {
						leilao.getNomeDoProduto(),
						leilao.getPreco(),
						leilao.getLanceMinimo(),
						dataDeExpiracaoFormatada,
				};
				modelo.addRow(linha);
				
				tabela.addRowSelectionInterval(0, 0);
			}
				
		}else {
			String texto = null;
			if(usuario.getLeiloesCadastrados().size() == 0 && usuario.getLeiloesGanhos().size() == 0) {
				texto = "cadastrados e nem ganhos";
			}else if(usuario.getLeiloesCadastrados().size() == 0) {
				texto = "cadastrados";
			}else if(usuario.getLeiloesGanhos().size() == 0) {
				texto = "ganhos";
			}
			modelo.addColumn(usuario.getNome()+" não tem leilões "+texto);
		}
			
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(x, y, c, a);
		janela.add(scroll);
	
		return tabela;
	}

	public static JTable adicionarTabelaComLeiloesVendidosDeUmUsuario(JFrame janela, Usuario usuario, int x, int y, int c, int a) {
		
		DefaultTableModel modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable tabela = new JTable(modelo);


		if(usuario.getLeiloesVendidos().size() != 0) {
			
			modelo.addColumn("Produto");
			modelo.addColumn("Preço");
			modelo.addColumn("Ultimo Lance");
			modelo.addColumn("Fechamento");
			modelo.addColumn("Comprador");
			
			
			for (Leilao leilao : usuario.getLeiloesVendidos()) {
				
				Date dataDeExpiracao = calcularDataDeExpiracao(leilao.getDataDeCadastro(), leilao.getDias(), leilao.getHoras());
				

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String  dataDeExpiracaoFormatada = formato.format(dataDeExpiracao);
					
				Object[] linha = new Object[] {
						leilao.getNomeDoProduto(),
						leilao.getPreco(),
						leilao.getLanceMinimo(),
						dataDeExpiracaoFormatada,
						leilao.getGanhador()
				};
				modelo.addRow(linha);
				
				tabela.addRowSelectionInterval(0, 0);
			}
				
		}else {
			modelo.addColumn(usuario.getNome()+" não realizou vendas");
		}
			
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(x, y, c, a);
		janela.add(scroll);
	
		return tabela;
	}
	
	
	public static JTable adicionarTabelaComTodosLeiloesDisponiveis(JFrame janela, int x, int y, int c, int a) {
		
		DefaultTableModel modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable tabela = new JTable(modelo);
	
		modelo.addColumn("Produto");
		modelo.addColumn("Preço");
		modelo.addColumn("Ultimo Lance");
		modelo.addColumn("Fechamento");
		modelo.addColumn("E-mail do Proprietário");

		
		Persistencia p = new Persistencia();
		CentralDeInformacoes cdi = p.recuperarCentral("central");
		
		
		for (Usuario usuario : cdi.getUsuariosCadastrados()) {

			if(usuario.getLeiloesCadastrados() != null) {
				
				for (Leilao leilao : usuario.getLeiloesCadastrados()) {
					
					Date dataDeExpiracao = calcularDataDeExpiracao(leilao.getDataDeCadastro(), leilao.getDias(), leilao.getHoras());
					Date dataAtual = Calendar.getInstance().getTime();
					
					if(dataDeExpiracao.after(dataAtual) && !leilao.isComprado()) {
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						String  dataDeExpiracaoFormatada = formato.format(dataDeExpiracao);
						
						Object[] linha = new Object[] {
								leilao.getNomeDoProduto(),
								leilao.getPreco(),
								leilao.getLanceMinimo(),
								dataDeExpiracaoFormatada,
								usuario.getEmail()
						};
						modelo.addRow(linha);
						tabela.addRowSelectionInterval(0, 0);
					}else {
						if(leilao.getGanhador() == null) {
							Usuario us = cdi.recuperarUsuario(usuario.getEmail());
							us.getLeiloesVendidos().add(leilao);
							if(leilao.getLancesRecebidos().size() != 0 ) {
								Usuario u = cdi.recuperarUsuario(usuario.getEmail());
								
								Leilao leilaoAtualizado = u.recuperarLeilao(leilao);
								leilaoAtualizado.setComprado(true);
								
								Usuario ganhadorPorExpiracao = cdi.recuperarUsuario(leilao.getDonosDosLances().get(leilao.getDonosDosLances().size()-1));
								
								if(ganhadorPorExpiracao != null) {
									leilaoAtualizado.setGanhador(leilao.getDonosDosLances().get(leilao.getDonosDosLances().size()-1));
									
									Usuario ganhador = cdi.recuperarUsuario(leilao.getDonosDosLances().get(leilao.getDonosDosLances().size()-1));
									ganhador.getLeiloesGanhos().add(leilaoAtualizado);
									p.salvarCentral(cdi, "central");
								}
							}
						}
					}
				}
			}
		}
		
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(x, y, c, a);
		janela.add(scroll);
	
		return tabela;
		
	}

	public static Date calcularDataDeExpiracao(Date dataDeCadastro,int dias, int horas) {
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(dataDeCadastro);
		ca.add(Calendar.DATE, +dias);
		ca.add(Calendar.HOUR, +horas);
		
		return ca.getTime();
		
	}
	
	public static Leilao pegarLeilaoSelecionado(String dataDeExpiracao1) {

		Leilao l = null;

		Persistencia p = new Persistencia();
		CentralDeInformacoes cdi = p.recuperarCentral("central");
		
		for (Usuario usuario : cdi.getUsuariosCadastrados()) {

			for (Leilao leilao : usuario.getLeiloesCadastrados()) {
				
				Date dataDeExpiracao2 = calcularDataDeExpiracao(leilao.getDataDeCadastro(), leilao.getDias(), leilao.getHoras());
				
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				String  dataDeExpiracaoFormatada = formato.format(dataDeExpiracao2);
				
				if(dataDeExpiracaoFormatada.equals(dataDeExpiracao1)) {
					l = leilao;
				}
				
			}

		}	
		return l;
	}
	
	public static JLabel adicionarJLabel(JFrame janela, String textoDoLabel, int x, int y, int c, int a) {
		JLabel label = new JLabel(textoDoLabel);
		label.setBounds(x, y, c, a);
		janela.add(label);
		return label;
	}
	
	public static JButton adicionarJButton(JFrame janela, String textoDoBotao, int x, int y, int c, int a) {
		JButton botao = new JButton(textoDoBotao);
		botao.setBounds(x, y, c, a);
		janela.add(botao);
		botao.setFocusPainted(false);
		return botao;
	}
	
	public static JTextField adicionarJTextField(JFrame janela, int x, int y, int c, int a) {
		JTextField textField = new JTextField();
		textField.setBounds(x, y, c, a);
		janela.add(textField);
		return textField;
	}
	
	public static JPasswordField adicionarJPasswordField(JFrame janela, int x, int y, int c, int a) {
		JPasswordField senha = new JPasswordField(); 
		senha.setBounds(x, y, c, a);
		janela.add(senha);
		return senha;
	}
	
	public static JFormattedTextField adicionarJFormattedTextFiel(JFrame janela, String formato, int x, int y, int c, int a ) throws ParseException {
		MaskFormatter mascara = new MaskFormatter(formato);
		JFormattedTextField campo = new JFormattedTextField(mascara);
		campo.setBounds(x, y, c, a);
		janela.add(campo);
		return campo;
	}
	
	public static JTextArea adicionarJTextArea(JFrame janela, int x, int y, int c, int a) {
		JTextArea textArea = new JTextArea();
		textArea.setBounds(x, y, c, a);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		janela.add(textArea);
		return textArea;
	}

	public static JMenuBar adicionarJMenubar(JFrame janela,int x,int y, int c, int a) {
		JMenuBar barraDeMenu = new JMenuBar();
		barraDeMenu.setBounds(x, y, c, a);
		janela.add(barraDeMenu);
		return barraDeMenu;
	}
//	
//	public static JMenu adicionarJMenu(JMenuBar barraDeMenu,String nome) {
//		JMenu menu = new JMenu(nome);
//		barraDeMenu.add(menu);
//		return menu;
//	}

	public static JMenu adicionarJMenuComIconeSemTexo(JMenuBar barraDeMenu,ImageIcon icone) {
		JMenu menu = new JMenu();
		menu.setIcon(icone);
		barraDeMenu.add(menu);
		return menu;
	}
	
	public static JMenuItem adicionarJMenuItem(JMenu menu, String nome) {
		JMenuItem item = new JMenuItem(nome);
		menu.add(item);
		return item;
	}
	
	
	//## Inutil
//	public static JMenuBar adicionarCatracaDeOpcoes(JFrame janela) {
//		JMenuBar barraDeMenu = new JMenuBar();
//		JMenu menu = new JMenu();
//		menu.setIcon(Icones.ICONE_OPCOES);
//		barraDeMenu.add(menu);
//		janela.add(barraDeMenu);
//		return barraDeMenu;
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

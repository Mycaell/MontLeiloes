
/**
 * @author Mycaell
 *
 */

package telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Ouvintes.OuvinteBotaoDetalhes;
import Ouvintes.OuvinteQueLevaDevoltaParaOLogin;
import Ouvintes.OuvinteQueLevaParaTelaDeUsuariosCadastrados;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.GeradorDeRelatorios;
import Utilidades.Icones;
import Utilidades.Persistencia;
import Utilidades.Usuario;

/**
 * classe responsável pela chamada de todas as funcionalidades presentes no sistema. 
 */
 
public class TelaDentroDoPrograma extends TelaPadrao{

//	atributos
	private Usuario usuario;
	private JTable tabelaDosLeiloesDisponiveis;
	
	/**
	 * usuário logado no sistema e que será manipulado em todas as classes do sistema, a partir dessa
	 * 
	 * @param usuario 
	 */
	
	public TelaDentroDoPrograma(Usuario usuario) {
		super("Mont Leilões");
		this.setSize(677,300);
		this.usuario = usuario;
	
//		métodos responsáveis pela atribuição de componentes no JFrame.
		adicionarLabels();
		adicionarBotoes();
		adicionarTabelaDosLeiloesDisponiveis();
		
		this.setVisible(true);
	}

	/**
	 * método responsável pela adição de uma tabela com todos os leilões disponíveis no momento
	 */
	private void adicionarTabelaDosLeiloesDisponiveis() {
		
//		aqui ocorre a chamada do método que adiciona a tabela dos leilões disponíveis
		tabelaDosLeiloesDisponiveis = AdicionadorDeComponentes.adicionarTabelaComTodosLeiloesDisponiveis(this, 10, 88, 651, 132);
		int qtd = 0;
		
//		for usado para a contagem de leilões em vigor do usuário
		for (int i = 0; i < tabelaDosLeiloesDisponiveis.getRowCount(); i++) {
			String dono = (String) tabelaDosLeiloesDisponiveis.getValueAt(i, 4);

			if(dono.equals(usuario.getEmail())) {
				qtd++;
			}
		}
		AdicionadorDeComponentes.adicionarJLabel(this, "Meus leilões em vigor: "+qtd, 10, 235, 260, 20);
	
//		adição de um botoão que está sendo "ouvido" por um ouvinte externo responsável pela exibição de uma tela contendo todos os detalhes de um leilão em específico
		JButton botaoDetalhes = AdicionadorDeComponentes.adicionarJButton(this, "Detalhes", 577, 235, 85, 20);
		botaoDetalhes.addActionListener(new OuvinteBotaoDetalhes(this, usuario, tabelaDosLeiloesDisponiveis));
		
	}

	/**
	 * método responsável pela adição dos JLabel no JFrame
	 */
	
	private void adicionarLabels() {
//		chamada do método que adicona um JLabel
		JLabel L = AdicionadorDeComponentes.adicionarJLabel(this, "Bem Vindo(a) "+usuario.getNome(), 240, 25, 350, 30);
		L.setFont(new Font("Comic Sans", Font.BOLD, 17));
		
		JLabel LL = AdicionadorDeComponentes.adicionarJLabel(this, "Leilões Disponíveis:", 20, 60, 150, 30);
		LL.setFont(new Font("Comic Sans", Font.BOLD, 13));
		
	}

	/**
	 * método responsável pela adição do JMenuBar, JMenu e JMenuItem no JFrame
	 */
	
	private void adicionarBotoes() {
//		chamada do método que adicona um JMenuBar
		JMenuBar barraDeMenu = AdicionadorDeComponentes.adicionarJMenubar(this, 634, 0, 38, 30);
		
//		chamada do método que adiciona um JMenu
		JMenu menu = AdicionadorDeComponentes.adicionarJMenuComIconeSemTexo(barraDeMenu, Icones.ICONE_OPCOES);
		menu.setToolTipText("Opções");
		
//		chamda do método que adicionar um JMenuItem
		JMenuItem perfil = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Ver Perfil");
		JMenuItem alterarSenha = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Alterar Senha");
		JMenuItem minhasVendas = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Minhas Vendas");
		JMenuItem minhasCompras = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Minhas Compras");
		JMenuItem cadastrarLeilao = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Cadastrar Leilão");
		JMenuItem gerarRelatorios = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Gerar Relatórios");
		JMenuItem listarUsuarios = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Listar Usuários");
		JMenuItem excluirConta = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Excluir Conta");
		JMenuItem sair = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Sair");
		
		OuvinteDaCatracaDeOpcoes ouvinteCatracaDeOpcoes = new OuvinteDaCatracaDeOpcoes(this);
		perfil.addActionListener(ouvinteCatracaDeOpcoes);
		alterarSenha.addActionListener(ouvinteCatracaDeOpcoes);
		minhasVendas.addActionListener(ouvinteCatracaDeOpcoes);
		minhasCompras.addActionListener(ouvinteCatracaDeOpcoes);
		cadastrarLeilao.addActionListener(ouvinteCatracaDeOpcoes);
		gerarRelatorios.addActionListener(ouvinteCatracaDeOpcoes);
		listarUsuarios.addActionListener(new OuvinteQueLevaParaTelaDeUsuariosCadastrados(this, usuario));
		excluirConta.addActionListener(ouvinteCatracaDeOpcoes);
		sair.addActionListener(new OuvinteQueLevaDevoltaParaOLogin(this));
	}

	/**
	 * classe responsável por "ouvir" os JMenuItens presentes no JFrame
	 */
	
	private class OuvinteDaCatracaDeOpcoes implements ActionListener{

		private TelaDentroDoPrograma telaDentroDoPrograma;
		
		public OuvinteDaCatracaDeOpcoes(TelaDentroDoPrograma telaDentroDoPrograma) {
			this.telaDentroDoPrograma = telaDentroDoPrograma;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

//			String que representa o texto do JMenuItem que gerou o ActionEvent
			String textoDoMenu = e.getActionCommand();
			
			/**
			 * testes que serão verificados após o lançamento de um ActionEvent
			 */
			
			if(textoDoMenu.equals("Ver Perfil")) {
//				teste responsável pela criação de um objeto do tipo TelaDePerfil que representa a tela de Perfil, onde o usuário pode consultar seu dados cadastrais
				TelaDePerfil telaDePerfil = new TelaDePerfil(usuario);
				telaDePerfil.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Alterar Senha")) {
//				teste responsável pela criação de um objeto do tipo TelaDeAlteracaoDeSenha que representa a tela de alteração de senha do usuário
				TelaDeAlteracaoDeSenha telaDeAlteracaoDeSenha = new TelaDeAlteracaoDeSenha(usuario);
				telaDeAlteracaoDeSenha.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Minhas Vendas")) {
//				teste responsável pela criação de um objeto do tipo TelaDeVendasDoUsuario que representa a tela de vendas realizadas pelo usuário, onde o usuário pode consultar atravez de uma tabela todos os seus leilões vendidos
				TelaDeVendasDoUsuario telaDeVendasDoUsuario = new TelaDeVendasDoUsuario(usuario);
				telaDeVendasDoUsuario.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Minhas Compras")) {
//				teste responsável pela criação de um objeto do tipo TelaDeComprasDoUsuario que representa a tela de compras realizadas pelo usuário,  onde o usuário pode consultar atravez de uma tabela todos os leiloes comprados por ele
				TelaDeComprasDoUsuario telaDeComprasDoUsuario = new TelaDeComprasDoUsuario(usuario);
				telaDeComprasDoUsuario.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Cadastrar Leilão")) {
//				teste responsável pela criação de um objeto do tipo TelaDeCadastroDeLeilao que representa a tela de cadastro de leilão, onde o usuário tem a possibilidade de cadastrar um leilão no sistema
				TelaDeCadastroDeLeilao telaDeCadastroDeLeilao = new TelaDeCadastroDeLeilao(usuario);
				telaDeCadastroDeLeilao.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Gerar Relatórios")) {
//				teste responsável pela geração de relatórios em PDF dos leilões cadastrados e ganhos do usuário
				GeradorDeRelatorios gerarRelatorio = new GeradorDeRelatorios();
				String[] tiposDeRelatorio = {"Todos os meus leilões cadastrados","Minhas compras"};
				
				String relatorioSelecionado = (String) JOptionPane.showInputDialog(telaDentroDoPrograma, "Gerar um relatório em PDF de:", "Gerar Relatório", JOptionPane.PLAIN_MESSAGE, null, tiposDeRelatorio, tiposDeRelatorio[0]);

				try {
					if(relatorioSelecionado.equals("Todos os meus leilões cadastrados")) {
						if(usuario.getLeiloesCadastrados().size() != 0 ) {
							
							String nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"Dê um nome para o arquivo:","nomeação do arquivo",JOptionPane.PLAIN_MESSAGE);
							try {
								do {
									if(!nomeDoArquivo.equals("")) {
										gerarRelatorio.gerarRelatorioDosLeiloesCadastradosPorUmUsuario(usuario, nomeDoArquivo);
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Relatório gerado com sucesso!");
										break;
									}else {
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Preencha o campo!", "Erro", JOptionPane.ERROR_MESSAGE);
										nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"Dê um nome para o arquivo:","nomeação do arquivo",JOptionPane.PLAIN_MESSAGE);
									}									
								}while(true);
							}catch (NullPointerException er) {
							}
						}else {
							JOptionPane.showMessageDialog(telaDentroDoPrograma, "Você não tem leilões cadastrados!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
					}else if(relatorioSelecionado.equals("Minhas compras")) {
						if(usuario.getLeiloesGanhos().size() != 0 ) {
							
							String nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"Dê um nome para o arquivo:","nomeação do arquivo",JOptionPane.PLAIN_MESSAGE);
							try {
								do {
									if(!nomeDoArquivo.equals("")) {
										gerarRelatorio.gerarRelatorioDosLeiloesganhosDeUmUsuario(usuario, nomeDoArquivo);
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Relatório gerado com sucesso!");
										break;
									}else {
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Preencha o campo!", "Erro", JOptionPane.ERROR_MESSAGE);
										nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"Dê um nome para o arquivo:","nomeação do arquivo",JOptionPane.PLAIN_MESSAGE);
									}									
								}while(true);
							}catch (NullPointerException er) {
							}
						}else {
							JOptionPane.showMessageDialog(telaDentroDoPrograma, "Você não ganhou nenhum leilão!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}catch (NullPointerException er) {
				}
				
			}else if(textoDoMenu.equals("Excluir Conta")) {
//				teste responsável pela ação de exclusão de conta do usuário
				int opcao = JOptionPane.showConfirmDialog(telaDentroDoPrograma, "Você tem certeza?","Exclusão de Conta",JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					Persistencia p = new Persistencia();
					CentralDeInformacoes cdi = p.recuperarCentral("central");
					Usuario usuarioExcluido = cdi.recuperarUsuario(usuario.getEmail());
					cdi.getUsuariosCadastrados().remove(usuarioExcluido);
					p.salvarCentral(cdi, "central");
					
					JOptionPane.showMessageDialog(telaDentroDoPrograma, "Conta Excluida!");
					TelaInicial telaInicial = new TelaInicial();
					telaInicial.setLocationRelativeTo(telaDentroDoPrograma);
					telaDentroDoPrograma.dispose();
				}
			}
		}
	}
}

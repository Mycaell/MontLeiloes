
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
 * classe respons�vel pela chamada de todas as funcionalidades presentes no sistema. 
 */
 
public class TelaDentroDoPrograma extends TelaPadrao{

//	atributos
	private Usuario usuario;
	private JTable tabelaDosLeiloesDisponiveis;
	
	/**
	 * usu�rio logado no sistema e que ser� manipulado em todas as classes do sistema, a partir dessa
	 * 
	 * @param usuario 
	 */
	
	public TelaDentroDoPrograma(Usuario usuario) {
		super("Mont Leil�es");
		this.setSize(677,300);
		this.usuario = usuario;
	
//		m�todos respons�veis pela atribui��o de componentes no JFrame.
		adicionarLabels();
		adicionarBotoes();
		adicionarTabelaDosLeiloesDisponiveis();
		
		this.setVisible(true);
	}

	/**
	 * m�todo respons�vel pela adi��o de uma tabela com todos os leil�es dispon�veis no momento
	 */
	private void adicionarTabelaDosLeiloesDisponiveis() {
		
//		aqui ocorre a chamada do m�todo que adiciona a tabela dos leil�es dispon�veis
		tabelaDosLeiloesDisponiveis = AdicionadorDeComponentes.adicionarTabelaComTodosLeiloesDisponiveis(this, 10, 88, 651, 132);
		int qtd = 0;
		
//		for usado para a contagem de leil�es em vigor do usu�rio
		for (int i = 0; i < tabelaDosLeiloesDisponiveis.getRowCount(); i++) {
			String dono = (String) tabelaDosLeiloesDisponiveis.getValueAt(i, 4);

			if(dono.equals(usuario.getEmail())) {
				qtd++;
			}
		}
		AdicionadorDeComponentes.adicionarJLabel(this, "Meus leil�es em vigor: "+qtd, 10, 235, 260, 20);
	
//		adi��o de um boto�o que est� sendo "ouvido" por um ouvinte externo respons�vel pela exibi��o de uma tela contendo todos os detalhes de um leil�o em espec�fico
		JButton botaoDetalhes = AdicionadorDeComponentes.adicionarJButton(this, "Detalhes", 577, 235, 85, 20);
		botaoDetalhes.addActionListener(new OuvinteBotaoDetalhes(this, usuario, tabelaDosLeiloesDisponiveis));
		
	}

	/**
	 * m�todo respons�vel pela adi��o dos JLabel no JFrame
	 */
	
	private void adicionarLabels() {
//		chamada do m�todo que adicona um JLabel
		JLabel L = AdicionadorDeComponentes.adicionarJLabel(this, "Bem Vindo(a) "+usuario.getNome(), 240, 25, 350, 30);
		L.setFont(new Font("Comic Sans", Font.BOLD, 17));
		
		JLabel LL = AdicionadorDeComponentes.adicionarJLabel(this, "Leil�es Dispon�veis:", 20, 60, 150, 30);
		LL.setFont(new Font("Comic Sans", Font.BOLD, 13));
		
	}

	/**
	 * m�todo respons�vel pela adi��o do JMenuBar, JMenu e JMenuItem no JFrame
	 */
	
	private void adicionarBotoes() {
//		chamada do m�todo que adicona um JMenuBar
		JMenuBar barraDeMenu = AdicionadorDeComponentes.adicionarJMenubar(this, 634, 0, 38, 30);
		
//		chamada do m�todo que adiciona um JMenu
		JMenu menu = AdicionadorDeComponentes.adicionarJMenuComIconeSemTexo(barraDeMenu, Icones.ICONE_OPCOES);
		menu.setToolTipText("Op��es");
		
//		chamda do m�todo que adicionar um JMenuItem
		JMenuItem perfil = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Ver Perfil");
		JMenuItem alterarSenha = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Alterar Senha");
		JMenuItem minhasVendas = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Minhas Vendas");
		JMenuItem minhasCompras = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Minhas Compras");
		JMenuItem cadastrarLeilao = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Cadastrar Leil�o");
		JMenuItem gerarRelatorios = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Gerar Relat�rios");
		JMenuItem listarUsuarios = AdicionadorDeComponentes.adicionarJMenuItem(menu, "Listar Usu�rios");
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
	 * classe respons�vel por "ouvir" os JMenuItens presentes no JFrame
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
			 * testes que ser�o verificados ap�s o lan�amento de um ActionEvent
			 */
			
			if(textoDoMenu.equals("Ver Perfil")) {
//				teste respons�vel pela cria��o de um objeto do tipo TelaDePerfil que representa a tela de Perfil, onde o usu�rio pode consultar seu dados cadastrais
				TelaDePerfil telaDePerfil = new TelaDePerfil(usuario);
				telaDePerfil.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Alterar Senha")) {
//				teste respons�vel pela cria��o de um objeto do tipo TelaDeAlteracaoDeSenha que representa a tela de altera��o de senha do usu�rio
				TelaDeAlteracaoDeSenha telaDeAlteracaoDeSenha = new TelaDeAlteracaoDeSenha(usuario);
				telaDeAlteracaoDeSenha.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Minhas Vendas")) {
//				teste respons�vel pela cria��o de um objeto do tipo TelaDeVendasDoUsuario que representa a tela de vendas realizadas pelo usu�rio, onde o usu�rio pode consultar atravez de uma tabela todos os seus leil�es vendidos
				TelaDeVendasDoUsuario telaDeVendasDoUsuario = new TelaDeVendasDoUsuario(usuario);
				telaDeVendasDoUsuario.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Minhas Compras")) {
//				teste respons�vel pela cria��o de um objeto do tipo TelaDeComprasDoUsuario que representa a tela de compras realizadas pelo usu�rio,  onde o usu�rio pode consultar atravez de uma tabela todos os leiloes comprados por ele
				TelaDeComprasDoUsuario telaDeComprasDoUsuario = new TelaDeComprasDoUsuario(usuario);
				telaDeComprasDoUsuario.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Cadastrar Leil�o")) {
//				teste respons�vel pela cria��o de um objeto do tipo TelaDeCadastroDeLeilao que representa a tela de cadastro de leil�o, onde o usu�rio tem a possibilidade de cadastrar um leil�o no sistema
				TelaDeCadastroDeLeilao telaDeCadastroDeLeilao = new TelaDeCadastroDeLeilao(usuario);
				telaDeCadastroDeLeilao.setLocationRelativeTo(telaDentroDoPrograma);
				telaDentroDoPrograma.dispose();
			}else if(textoDoMenu.equals("Gerar Relat�rios")) {
//				teste respons�vel pela gera��o de relat�rios em PDF dos leil�es cadastrados e ganhos do usu�rio
				GeradorDeRelatorios gerarRelatorio = new GeradorDeRelatorios();
				String[] tiposDeRelatorio = {"Todos os meus leil�es cadastrados","Minhas compras"};
				
				String relatorioSelecionado = (String) JOptionPane.showInputDialog(telaDentroDoPrograma, "Gerar um relat�rio em PDF de:", "Gerar Relat�rio", JOptionPane.PLAIN_MESSAGE, null, tiposDeRelatorio, tiposDeRelatorio[0]);

				try {
					if(relatorioSelecionado.equals("Todos os meus leil�es cadastrados")) {
						if(usuario.getLeiloesCadastrados().size() != 0 ) {
							
							String nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"D� um nome para o arquivo:","nomea��o do arquivo",JOptionPane.PLAIN_MESSAGE);
							try {
								do {
									if(!nomeDoArquivo.equals("")) {
										gerarRelatorio.gerarRelatorioDosLeiloesCadastradosPorUmUsuario(usuario, nomeDoArquivo);
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Relat�rio gerado com sucesso!");
										break;
									}else {
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Preencha o campo!", "Erro", JOptionPane.ERROR_MESSAGE);
										nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"D� um nome para o arquivo:","nomea��o do arquivo",JOptionPane.PLAIN_MESSAGE);
									}									
								}while(true);
							}catch (NullPointerException er) {
							}
						}else {
							JOptionPane.showMessageDialog(telaDentroDoPrograma, "Voc� n�o tem leil�es cadastrados!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
					}else if(relatorioSelecionado.equals("Minhas compras")) {
						if(usuario.getLeiloesGanhos().size() != 0 ) {
							
							String nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"D� um nome para o arquivo:","nomea��o do arquivo",JOptionPane.PLAIN_MESSAGE);
							try {
								do {
									if(!nomeDoArquivo.equals("")) {
										gerarRelatorio.gerarRelatorioDosLeiloesganhosDeUmUsuario(usuario, nomeDoArquivo);
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Relat�rio gerado com sucesso!");
										break;
									}else {
										JOptionPane.showMessageDialog(telaDentroDoPrograma, "Preencha o campo!", "Erro", JOptionPane.ERROR_MESSAGE);
										nomeDoArquivo = JOptionPane.showInputDialog(telaDentroDoPrograma,"D� um nome para o arquivo:","nomea��o do arquivo",JOptionPane.PLAIN_MESSAGE);
									}									
								}while(true);
							}catch (NullPointerException er) {
							}
						}else {
							JOptionPane.showMessageDialog(telaDentroDoPrograma, "Voc� n�o ganhou nenhum leil�o!", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}catch (NullPointerException er) {
				}
				
			}else if(textoDoMenu.equals("Excluir Conta")) {
//				teste respons�vel pela a��o de exclus�o de conta do usu�rio
				int opcao = JOptionPane.showConfirmDialog(telaDentroDoPrograma, "Voc� tem certeza?","Exclus�o de Conta",JOptionPane.YES_NO_OPTION);
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

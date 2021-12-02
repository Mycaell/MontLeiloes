package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Exceções.UsuarioNaoExistenteException;
import Ouvintes.OuvinteDeVoltaParaDentroDoPrograma;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Icones;
import Utilidades.Persistencia;
import Utilidades.Usuario;

public class TelaDeUsuariosCadastrados extends TelaPadrao{

	private Usuario usuario;
	private JTable tabela;
	
	private JButton botaoMensagem;
	public TelaDeUsuariosCadastrados(Usuario usuario) {
		super("Usuários Cadastrados");
		this.usuario = usuario;
		
		this.setSize(660, 300);
	
		adicionarTabelaComTodosUsuariosCadastrados();
		adicionarBotoes();
		
		this.setVisible(true);
	}

	private void adicionarBotoes() {
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 85, 235, 70, 20);
		botaoVoltar.addActionListener(new OuvinteDeVoltaParaDentroDoPrograma(this, usuario));
		
		
		OuvinteTelaDeUsuariosCadastrados ouvinte = new OuvinteTelaDeUsuariosCadastrados(this);
		botaoMensagem = AdicionadorDeComponentes.adicionarJButton(this, "Mensagem", 250, 235, 125, 20);
		botaoMensagem.setIcon(Icones.ICONE_GMAIL);
		botaoMensagem.addActionListener(ouvinte);
		JButton botaoListarLeiloes = AdicionadorDeComponentes.adicionarJButton(this, "Listar Leilões", 465, 235, 120, 20);
		botaoListarLeiloes.addActionListener(ouvinte);
		
		
	}

	private void adicionarTabelaComTodosUsuariosCadastrados() {

		DefaultTableModel modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelo.addColumn("Nome");
		modelo.addColumn("Sobrenome");
		modelo.addColumn("Sexo");
		modelo.addColumn("Idade");
		modelo.addColumn("E-mail");
		
		tabela = new JTable(modelo);
		
		Persistencia p = new Persistencia();
		CentralDeInformacoes cdi = p.recuperarCentral("central");
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for (Usuario usuario : cdi.getUsuariosCadastrados()) {
			
			String dataDeNascimento = formato.format(usuario.getDataDeNascimento());
			String[] separaDataDeHoraEMinutos = dataDeNascimento.split(" ");
			String[] dataDividida = separaDataDeHoraEMinutos[0].split("/");
			
			int anoDeNascimento = Integer.parseInt(dataDividida[2]); 
			
			Calendar ca = Calendar.getInstance();
			int anoAtual = ca.get(Calendar.YEAR);
			
			int idade = anoAtual - anoDeNascimento;
			
			Object[] linha = new Object[] {
					usuario.getNome(),
					usuario.getSobrenome(),
					usuario.getSexo(),
					idade,
					usuario.getEmail()
			};
			modelo.addRow(linha);
		}
		
		tabela.addRowSelectionInterval(0, 0);
		
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(0, 0, 655, 220);
		add(scroll);
		
	}

	
	private class OuvinteTelaDeUsuariosCadastrados implements ActionListener{

		private TelaDeUsuariosCadastrados telaDeUsuariosCadastrados;
		
		public OuvinteTelaDeUsuariosCadastrados(TelaDeUsuariosCadastrados telaDeUsuariosCadastrados){
			this.telaDeUsuariosCadastrados = telaDeUsuariosCadastrados;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			String textoDoBotao = e.getActionCommand();
			
			if(tabela.getSelectedRow() == -1) {
				botaoMensagem.setEnabled(false);
			}
			
			if(textoDoBotao.equals("Mensagem")) {
				int linhaSelecionada = tabela.getSelectedRow();
				
				String emailDoDestinatario = (String) tabela.getValueAt(linhaSelecionada, 4);
				
				Persistencia p = new Persistencia();
				CentralDeInformacoes cdi = p.recuperarCentral("central");
				
				Usuario destinatario = cdi.recuperarUsuario(emailDoDestinatario);
				
				TelaDeEmail telaDeEmail = new TelaDeEmail(usuario, destinatario);
				telaDeEmail.setLocationRelativeTo(telaDeUsuariosCadastrados);
				
				
			}else if(textoDoBotao.equals("Listar Leilões")) {
				if(tabela.getSelectedRow() != -1) {

					int linhaSelecionada = tabela.getSelectedRow();
					String emailDoUsuarioSelecionado = (String) tabela.getValueAt(linhaSelecionada, 4);
					
					
					Persistencia p = new Persistencia();
					CentralDeInformacoes cdi = p.recuperarCentral("central");
					
					String senha = null;
					for (Usuario u : cdi.getUsuariosCadastrados()) {
						if(u.getEmail().equals(emailDoUsuarioSelecionado)) {
							senha = u.getSenha();
						}
					}
					
					try {
						Usuario usuarioSelecionado = cdi.recuperarUsuario(emailDoUsuarioSelecionado, senha);
						
						TelaDeListagemDeLeiloesDeUmUsuario telaDeListagemDeLeiloesDeUmUsuario = new TelaDeListagemDeLeiloesDeUmUsuario(usuario, usuarioSelecionado);
						telaDeListagemDeLeiloesDeUmUsuario.setLocationRelativeTo(telaDeUsuariosCadastrados);
						telaDeUsuariosCadastrados.dispose();
						
						
					}catch (UsuarioNaoExistenteException er) {

					}
				}
			}
		}
		
	}
	
}


package telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Exceções.CampoVazioException;
import Exceções.UsuarioNaoExistenteException;
import Ouvintes.OuvinteDeVoltaParaTelaInicial;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Persistencia;
import Utilidades.Usuario;
import Utilidades.ValidacaoDeSenhaEmailECampos;

public class TelaDeLogin extends TelaPadrao{

	private JTextField email;
	private JPasswordField senha;
	private JLabel recuperarSenha;

	public TelaDeLogin() {
		super("Login");
		
		adicionarLabels();
		adicionarBotoes();
		adicionarFields();
		
		
		this.setVisible(true);
	}	
	
	private void adicionarLabels() {
		AdicionadorDeComponentes.adicionarJLabel(this, "E-mail", 70, 40, 50, 30);		
		AdicionadorDeComponentes.adicionarJLabel(this, "Senha",70, 80, 50, 30);

		recuperarSenha = AdicionadorDeComponentes.adicionarJLabel(this, "Recuperar Senha", 100, 118, 100, 20);
		recuperarSenha.addMouseListener(new OuvinteLabelRecuperarSenha(this));	
		recuperarSenha.setForeground(Color.blue);
	}
	
	private void adicionarBotoes() {
		JButton botaoEntrar = AdicionadorDeComponentes.adicionarJButton(this, "Entrar", 248, 115, 70, 20);
		botaoEntrar.addActionListener(new OuvinteTelaDeLogin(this));
		
		JButton botaoCadastrar = AdicionadorDeComponentes.adicionarJButton(this, "Cadastrar-se", 200, 200, 120, 20);
		botaoCadastrar.addActionListener(new OuvinteTelaDeLogin(this));
		
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 100, 200, 90, 20);
		botaoVoltar.addActionListener(new OuvinteDeVoltaParaTelaInicial(this));
		
	}
	
	private void adicionarFields() {
		email = AdicionadorDeComponentes.adicionarJTextField(this, 120, 45, 200, 20);
		senha = AdicionadorDeComponentes.adicionarJPasswordField(this, 120, 85, 200, 20);
		
	}
	
	private class OuvinteTelaDeLogin implements ActionListener {

		
		private TelaDeLogin telaDeLogin;
		
		public OuvinteTelaDeLogin(TelaDeLogin telaDeLogin) {
			this.telaDeLogin = telaDeLogin;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			String botao = e.getActionCommand();
			
			if(botao.equals("Cadastrar-se")){
				TelaDeCadastro telaDeCadastro = new TelaDeCadastro();
				telaDeCadastro.setLocationRelativeTo(telaDeLogin);
				telaDeLogin.dispose();
			}else if(botao.equals("Entrar")) {

				try {
					
					String emailDigitado = email.getText();
					String senhaDigitada = new String(senha.getPassword());
					
					ValidacaoDeSenhaEmailECampos.validarCampo(emailDigitado);
					ValidacaoDeSenhaEmailECampos.validarCampo(senhaDigitada);

					
					CentralDeInformacoes cdi = new CentralDeInformacoes();
					Persistencia p = new Persistencia();
					
					cdi = p.recuperarCentral("central");
					
					
					try {
						Usuario usuario = cdi.recuperarUsuario(emailDigitado,senhaDigitada);

						TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(usuario);
						telaDentroDoPrograma.setLocationRelativeTo(telaDeLogin);
						telaDeLogin.dispose();
						
					}catch (UsuarioNaoExistenteException er) {
						JOptionPane.showMessageDialog(telaDeLogin, er.getMessage());
					}
				}catch (CampoVazioException er) {
					JOptionPane.showMessageDialog(telaDeLogin,er.getMessage(),"Campo vázio",JOptionPane.ERROR_MESSAGE);
				}

				
			}
		}
		
	}
	
	private class OuvinteLabelRecuperarSenha implements MouseListener{

		private TelaDeLogin telaDeLogin;
		
		public OuvinteLabelRecuperarSenha(TelaDeLogin telaDeLogin) {
			this.telaDeLogin = telaDeLogin;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			TelaDeRecuperacaoDeSenha telaDeRecuperacaoDeSenha = new TelaDeRecuperacaoDeSenha();
			telaDeRecuperacaoDeSenha.setLocationRelativeTo(telaDeLogin);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			recuperarSenha.setText("<html><u>Recuperar Senha</u></html>");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			recuperarSenha.setText("Recuperar Senha");			
		}
	}
	
	
	
}

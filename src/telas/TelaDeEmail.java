package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import Exceções.CampoVazioException;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.EnviadorDeEmail;
import Utilidades.Usuario;
import Utilidades.ValidacaoDeSenhaEmailECampos;

public class TelaDeEmail extends TelaPadrao{

	
	private JTextField TFdestinatario;
	private JTextField assunto;
	private JTextArea mensagem;
	private JPasswordField senhaGmail;
	
	private Usuario remetente;
	private Usuario destinatario;
	
	public TelaDeEmail(Usuario remetente, Usuario destinatario) {
		super("E-mail");

		this.remetente = remetente;
		this.destinatario = destinatario;
	
		adicionarComponentes();
	
		this.setVisible(true);
	}

	private void adicionarComponentes() {
		
		JLabel l1 = AdicionadorDeComponentes.adicionarJLabel(this, "Para", 10, 17, 30, 20);
		
		TFdestinatario = AdicionadorDeComponentes.adicionarJTextField(this, 40, 17, 220, 20);
		TFdestinatario.setText(destinatario.getEmail());
		TFdestinatario.setEnabled(false);
		
		JLabel l2 = AdicionadorDeComponentes.adicionarJLabel(this, "Assunto", 10, 45, 60, 20);
		
		assunto = AdicionadorDeComponentes.adicionarJTextField(this, 60, 45, 310, 20);
		
		JLabel l3 = AdicionadorDeComponentes.adicionarJLabel(this, "Mensagem", 10, 70, 70, 20);
		
		mensagem = new JTextArea();
		JScrollPane scroll = new JScrollPane(mensagem);
		scroll.setBounds(20, 95, 350, 90);
		add(scroll);
		
		JLabel l4 = AdicionadorDeComponentes.adicionarJLabel(this, "Minha senha do Gmail", 10, 200, 135, 20);
		
		senhaGmail = AdicionadorDeComponentes.adicionarJPasswordField(this, 140, 200, 230, 20);
		
		OuvinteTelaDeEmail ouvinteTelaDeEmail = new OuvinteTelaDeEmail(this);
		JButton botaoEnviar = AdicionadorDeComponentes.adicionarJButton(this, "Enviar", 100, 233, 90, 20);
		botaoEnviar.addActionListener(ouvinteTelaDeEmail);
		JButton botaoCancelar = AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 200, 233, 90, 20);
		botaoCancelar.addActionListener(ouvinteTelaDeEmail);
	}
	
	private class OuvinteTelaDeEmail implements ActionListener{
		
		private TelaDeEmail telaDeEmail;
		
		public OuvinteTelaDeEmail(TelaDeEmail telaDeEmail) {
			this.telaDeEmail = telaDeEmail;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String textoDoBotao = e.getActionCommand();

			if(textoDoBotao.equals("Enviar")) {
				
				try {
					ValidacaoDeSenhaEmailECampos.validarCampo(assunto.getText());
					ValidacaoDeSenhaEmailECampos.validarCampo(mensagem.getText());
					
					String senhaGmailDigitada = new String(senhaGmail.getPassword());
					ValidacaoDeSenhaEmailECampos.validarCampo(senhaGmailDigitada);
					
					EnviadorDeEmail enviador = new EnviadorDeEmail();
					enviador.enviarEmail(senhaGmailDigitada, remetente.getEmail(), destinatario.getEmail(), assunto.getText(), mensagem.getText());
					
					JOptionPane.showMessageDialog(telaDeEmail, "e-mail enviado!");
					telaDeEmail.dispose();
				}catch (CampoVazioException er) {
					JOptionPane.showMessageDialog(telaDeEmail, er.getMessage(), "Campo Vazio", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}else if(textoDoBotao.equals("Cancelar")) {
				telaDeEmail.dispose();
			}
			
		}
		
	}
	
	
}

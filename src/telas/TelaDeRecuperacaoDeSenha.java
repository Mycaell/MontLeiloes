package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Exceções.CampoVazioException;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.EnviadorDeEmail;
import Utilidades.Persistencia;
import Utilidades.Usuario;

public class TelaDeRecuperacaoDeSenha extends TelaPadrao {

	private JTextField email;
	
	public TelaDeRecuperacaoDeSenha() {
		super("Recuperação de Senha");

		this.setSize(400,170);
		adicionarComponentes();
	
		this.setVisible(true);
	}

	private void adicionarComponentes() {
		
		JLabel l1 = new JLabel("Informe o e-mail utilizado no cadastro da sua conta:");
		l1.setBounds(20, 20, 300, 20);
		add(l1);
	

		email = AdicionadorDeComponentes.adicionarJTextField(this, 20, 45, 290, 20);
		
		
		OuvinteTelaDeRecuperacaoDeSenha ouvinte = new OuvinteTelaDeRecuperacaoDeSenha(this);
		JButton botaoOk = AdicionadorDeComponentes.adicionarJButton(this, "OK", 100, 90, 80, 20);
		add(botaoOk);
		
		JButton botaoCancelar = AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 200, 90, 90, 20);
		add(botaoCancelar);

		botaoOk.addActionListener(ouvinte);
		botaoCancelar.addActionListener(ouvinte);
	}
	
	private class OuvinteTelaDeRecuperacaoDeSenha implements ActionListener{
		
		private TelaDeRecuperacaoDeSenha telaDeRecuperacaoDeSenha;
		
		public OuvinteTelaDeRecuperacaoDeSenha(TelaDeRecuperacaoDeSenha telaDeRecuperacaoDeSenha) {
			this.telaDeRecuperacaoDeSenha = telaDeRecuperacaoDeSenha;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			
			String textoDoBotao = e.getActionCommand();
			
			if(textoDoBotao.equals("OK")) {
				
				try {
					if(email.getText().equals("")) {
						throw new CampoVazioException();
					}
					
					Persistencia p = new Persistencia();
					CentralDeInformacoes cdi = p.recuperarCentral("central");
					
					Usuario usuario = cdi.recuperarUsuario(email.getText());
					if(usuario != null) {

						EnviadorDeEmail enviador = new EnviadorDeEmail();
						enviador.enviarEmail("mycaellleiloes", "montleiloesmy@gmail.com", usuario.getEmail(), "Recuperação de Senha", "A sua senha é essa: "+usuario.getSenha());
						
						JOptionPane.showMessageDialog(telaDeRecuperacaoDeSenha, "A sua senha foi enviada para o seu e-mail");
						telaDeRecuperacaoDeSenha.dispose();
						
					}else {
						JOptionPane.showMessageDialog(telaDeRecuperacaoDeSenha, "não existe uma conta com esse e-mail!", "e-mail inválido", JOptionPane.ERROR_MESSAGE);
					}
					
					
				}catch (CampoVazioException er) {
					JOptionPane.showMessageDialog(telaDeRecuperacaoDeSenha, er.getMessage(), "Campo Vazio", JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}else if(textoDoBotao.equals("Cancelar")) {
				telaDeRecuperacaoDeSenha.dispose();
			}
			
		}
		
		
		
		
	}
}

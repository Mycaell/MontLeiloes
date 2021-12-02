package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Exceções.CampoVazioException;
import Exceções.SenhaAtualIncompativelException;
import Exceções.UsuarioNaoExistenteException;
import Ouvintes.OuvinteDeVoltaParaDentroDoPrograma;
import Ouvintes.OuvinteDoOlho;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Icones;
import Utilidades.Persistencia;
import Utilidades.Usuario;
import Utilidades.ValidacaoDeSenhaEmailECampos;

public class TelaDeAlteracaoDeSenha extends TelaPadrao{

	private Usuario usuario;
	private JPasswordField senhaAtual;
	private JPasswordField NovaSenha;
	private JPasswordField confirmacaoDaNovaSenha;

	private JTextField novaSenhaRevelada;
	private JLabel olho;
	
	
	public TelaDeAlteracaoDeSenha(Usuario usuario) {
		super("Alteração de Senha");
		this.usuario = usuario;
		
		adicionarLabels();
		adicionarFields();
		adicionarBotoes();
		
		this.setVisible(true); 
	}

	private void adicionarLabels() {
		AdicionadorDeComponentes.adicionarJLabel(this, "Senha Atual", 20, 20, 75, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Nova Senha",20, 60, 70, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Confirme a nova Senha", 20, 100, 135, 20);
	}

	private void adicionarFields() {
		senhaAtual = AdicionadorDeComponentes.adicionarJPasswordField(this, 100, 20, 200, 20);
		confirmacaoDaNovaSenha = AdicionadorDeComponentes.adicionarJPasswordField(this, 157, 100, 200, 20);
	}

	private void adicionarBotoes() {
		JButton botaoSalvar = AdicionadorDeComponentes.adicionarJButton(this, "Salvar", 100, 175, 80, 20);
		botaoSalvar.addActionListener(new OuvinteBotaoSalvar(this));
		JButton botaoCancelar =AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 200, 175, 85, 20);
		botaoCancelar.addActionListener(new OuvinteDeVoltaParaDentroDoPrograma(this, usuario));
				
	    ImageIcon imgOlho = Icones.ICONE_OLHO_FECHADO;
	    olho = new JLabel(imgOlho);
	    olho.setToolTipText("revelar senha");
	    olho.setBounds(300, 60, 40, 20);
	    add(olho);

	    NovaSenha = AdicionadorDeComponentes.adicionarJPasswordField(this, 100, 60, 200, 20);
	    novaSenhaRevelada = AdicionadorDeComponentes.adicionarJTextField(this, 100, 60, 200, 20);
	    novaSenhaRevelada.setVisible(false);
	    olho.addMouseListener(new OuvinteDoOlho(this,NovaSenha,novaSenhaRevelada,olho));
		
				
	}
	
	private class OuvinteBotaoSalvar implements ActionListener{

		private TelaDeAlteracaoDeSenha telaDeAlteracaoDeSenha;
		
		public OuvinteBotaoSalvar(TelaDeAlteracaoDeSenha telaDeAlteracaoDeSenha) {
			this.telaDeAlteracaoDeSenha = telaDeAlteracaoDeSenha;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String senhaAtualDigitada = new String(senhaAtual.getPassword());
	
			try {
				
				if(senhaAtualDigitada.equals("")) {
					throw new CampoVazioException();
				}
				
				if(!senhaAtualDigitada.equals(usuario.getSenha())) {
					throw new SenhaAtualIncompativelException();
				}

				if(ValidacaoDeSenhaEmailECampos.validarSenhaEConfirmacaoDeSenha(telaDeAlteracaoDeSenha, NovaSenha, confirmacaoDaNovaSenha)) {
					
					Persistencia p = new Persistencia();
					CentralDeInformacoes cdi = p.recuperarCentral("central");
					
					Usuario usuarioAtualizado = cdi.recuperarUsuario(usuario.getEmail(), usuario.getSenha());

					usuarioAtualizado.setSenha(new String(NovaSenha.getPassword()));

					p.salvarCentral(cdi, "central");
					
					JOptionPane.showMessageDialog(telaDeAlteracaoDeSenha, "Senha alterada com sucesso");

					TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(usuarioAtualizado);
					telaDentroDoPrograma.setLocationRelativeTo(telaDeAlteracaoDeSenha);
					telaDeAlteracaoDeSenha.dispose();
				}

			}catch (SenhaAtualIncompativelException er) {
				JOptionPane.showMessageDialog(telaDeAlteracaoDeSenha,er.getMessage(),"Senha Atual inválida",JOptionPane.ERROR_MESSAGE);
			} catch (UsuarioNaoExistenteException er) {

			} catch (CampoVazioException er) {
				JOptionPane.showMessageDialog(telaDeAlteracaoDeSenha,er.getMessage(),"Campo não preenchido",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}

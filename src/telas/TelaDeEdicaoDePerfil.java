package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Exceções.UsuarioNaoExistenteException;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Persistencia;
import Utilidades.Usuario;
import Utilidades.ValidacaoDeSenhaEmailECampos;

public class TelaDeEdicaoDePerfil extends TelaDeCadastro{
	private Usuario usuario;

	public TelaDeEdicaoDePerfil(Usuario usuario) {
		super();

		setTitle("Edição De Perfil");
		this.usuario = usuario;
		
		
		removerComonentes();
		inicializarFields();
		adicionarBotoes();

		this.setVisible(true);
	}
	
	private void removerComonentes() {
		remove(getLabelSenha());
		remove(getSenha());
		remove(getLabelConfirmeSenha());
		remove(getConfirmacaoDeSenha());
		remove(getOBS());
		remove(getBotaoSalvar());
		remove(getBotaoCancelar());
		remove(getOlho());
	}

	private void inicializarFields() {
		getNome().setText(usuario.getNome());
		getSobrenome().setText(usuario.getSobrenome());
		
		if(usuario.getSexo() == 'F') {
			getRadioButtonF().setSelected(true);
		}else {
			getRadioButtonM().setSelected(true);
		}
		
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String dataFormatada = formataData.format(usuario.getDataDeNascimento());
		getDataDeNascimento().setText(dataFormatada);
		
		getEndereço().setText(usuario.getEndereço());
		getTelefone().setText(usuario.getTelefone());
		getCPF().setText(usuario.getCPF());
		getEmail().setText(usuario.getEmail());
		
	}

	private void adicionarBotoes() {
		OuvinteTelaDeEdicaoDePerfil ouvinteTelaDePerfil = new OuvinteTelaDeEdicaoDePerfil(this);
		JButton botaoSalvarEdicao = AdicionadorDeComponentes.adicionarJButton(this, "Salvar Edição", 80, 230, 115, 20);
		botaoSalvarEdicao.addActionListener(ouvinteTelaDePerfil);
		JButton botaoCancelar = AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 210, 230, 90, 20);
		botaoCancelar.addActionListener(ouvinteTelaDePerfil);
	}
	
	
	private class OuvinteTelaDeEdicaoDePerfil implements ActionListener{
		
		private TelaDeEdicaoDePerfil telaDeEdicaoDePerfil;
		
		public OuvinteTelaDeEdicaoDePerfil(TelaDeEdicaoDePerfil telaDeEdicaoDePerfil) {
			this.telaDeEdicaoDePerfil = telaDeEdicaoDePerfil;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String textoDoBotao = e.getActionCommand();
			
			
			if(textoDoBotao.equals("Salvar Edição")) {
				if(ValidacaoDeSenhaEmailECampos.validarDadosDoUsuario(telaDeEdicaoDePerfil, getNome(), getSobrenome(), getDataDeNascimento(), getEndereço(), getTelefone(), getCPF(), getEmail())) {
					
					char sexo = 'F';
					if(!getRadioButtonF().isSelected()) {
						sexo = 'M';
					}
					
					Date dataDeNasc = null;
					SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
					try {
						dataDeNasc = formataData.parse(getDataDeNascimento().getText());
					
						Persistencia p = new Persistencia();
						CentralDeInformacoes cdi = p.recuperarCentral("central");
					
						Usuario usuarioAtualizado = cdi.recuperarUsuario(usuario.getEmail(),usuario.getSenha());

						usuarioAtualizado.setNome(getNome().getText());
						usuarioAtualizado.setSobrenome(getSobrenome().getText());
						usuarioAtualizado.setSexo(sexo);
						usuarioAtualizado.setDataDeNascimento(dataDeNasc);
						usuarioAtualizado.setEndereço(getEndereço().getText());
						usuarioAtualizado.setTelefone(getTelefone().getText());
						usuarioAtualizado.setCPF(getCPF().getText());
						usuarioAtualizado.setEmail(getEmail().getText());
						
						p.salvarCentral(cdi, "central");
						
						JOptionPane.showMessageDialog(telaDeEdicaoDePerfil, "Edição realizada com sucesso");

						TelaDePerfil telaDePerfil = new TelaDePerfil(usuarioAtualizado);
						telaDePerfil.setLocationRelativeTo(telaDeEdicaoDePerfil);
						telaDeEdicaoDePerfil.dispose();
						
					}catch (ParseException er) {
					}catch (UsuarioNaoExistenteException er) {
					}
				}
				
			}else if(textoDoBotao.equals("Cancelar")) {
				TelaDePerfil telaDePerfil = new TelaDePerfil(usuario);
				telaDePerfil.setLocationRelativeTo(telaDeEdicaoDePerfil);
				telaDeEdicaoDePerfil.dispose();
				
			}
		}
	}
	

}

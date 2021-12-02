package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;

import Ouvintes.OuvinteDeVoltaParaDentroDoPrograma;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.Usuario;

public class TelaDePerfil extends TelaPadrao{

	private Usuario usuario;
	
	public TelaDePerfil(Usuario usuario) {
		super("Perfil");
		this.usuario = usuario;
		
		adicionarLabels();
		adicionarBotoes();
		
		this.setVisible(true);
	}

	private void adicionarLabels() {
		AdicionadorDeComponentes.adicionarJLabel(this, "Nome: "+usuario.getNome(), 20, 20, 130, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Sobrenome: "+usuario.getSobrenome(), 190, 20, 200, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Sexo: "+usuario.getSexo(), 20, 50, 100, 20);
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/YYYY");
		AdicionadorDeComponentes.adicionarJLabel(this, "Data de Nasc: "+formataData.format(usuario.getDataDeNascimento()), 190, 50, 210, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Endereço: "+usuario.getEndereço(), 20, 80, 320, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Telefone: "+usuario.getTelefone(), 20, 110, 145, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "CPF: "+usuario.getCPF(), 250, 110, 350, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "E-mail: "+usuario.getEmail(), 20, 140, 340, 20);
		
	}
	
	private void adicionarBotoes() {
		
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 115, 190, 85, 20);
		botaoVoltar.addActionListener(new OuvinteDeVoltaParaDentroDoPrograma(this, usuario));
		
		JButton botaoEditar = AdicionadorDeComponentes.adicionarJButton(this, "Editar", 215, 190, 75, 20);
		botaoEditar.addActionListener(new OuvinteDoBotaoEditar(this));
	}
	
	private class OuvinteDoBotaoEditar implements ActionListener{

		private TelaDePerfil telaDePerfil;
		
		public OuvinteDoBotaoEditar(TelaDePerfil telaDePerfil) {
			this.telaDePerfil = telaDePerfil;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			TelaDeEdicaoDePerfil telaDeEdicaoDePerfil = new TelaDeEdicaoDePerfil(usuario);
			telaDeEdicaoDePerfil.setLocationRelativeTo(telaDePerfil);
			telaDePerfil.dispose();
			
		}
		
	}
	
}

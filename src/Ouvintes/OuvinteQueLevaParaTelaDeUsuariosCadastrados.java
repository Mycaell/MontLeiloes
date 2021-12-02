package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Utilidades.Usuario;
import telas.TelaDeUsuariosCadastrados;

public class OuvinteQueLevaParaTelaDeUsuariosCadastrados implements ActionListener{

	private JFrame janela;
	private Usuario usuario;
	
	public OuvinteQueLevaParaTelaDeUsuariosCadastrados(JFrame janela, Usuario usuario) {
		this.janela = janela;
		this.usuario = usuario;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TelaDeUsuariosCadastrados telaDeUsuariosCadastrados = new TelaDeUsuariosCadastrados(usuario);
		telaDeUsuariosCadastrados.setLocationRelativeTo(janela);
		janela.dispose();
		
	}

}

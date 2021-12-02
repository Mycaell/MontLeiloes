package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import telas.TelaDeLogin;

public class OuvinteQueLevaDevoltaParaOLogin implements ActionListener{

	private JFrame janela;
	
	public OuvinteQueLevaDevoltaParaOLogin(JFrame janela) {
		this.janela = janela;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TelaDeLogin telaDeLogin = new TelaDeLogin();
		telaDeLogin.setLocationRelativeTo(janela);
		janela.dispose();
	}

}

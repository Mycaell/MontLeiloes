package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Utilidades.Usuario;
import telas.TelaDeLeiloesDisponiveis;
import telas.TelaDeListagemDeLeiloesDeUmUsuario;
import telas.TelaDentroDoPrograma;

public class OuvinteDeVoltaParaDentroDoPrograma implements ActionListener {

	private JFrame janela;
	private Usuario usuario;
	
	public OuvinteDeVoltaParaDentroDoPrograma(JFrame janela,Usuario usuario) {
		this.janela = janela;
		this.usuario = usuario;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(usuario != null) {
			TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(usuario);
			telaDentroDoPrograma.setLocationRelativeTo(janela);
			janela.dispose();
		}
		else {
			TelaDeLeiloesDisponiveis telaDeLeiloesDisponiveis = new TelaDeLeiloesDisponiveis();
			telaDeLeiloesDisponiveis.setLocationRelativeTo(janela);
			janela.dispose();
		}
		
		
		
	}
	
	

}

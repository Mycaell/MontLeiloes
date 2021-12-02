package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import telas.TelaInicial;

public class OuvinteDeVoltaParaTelaInicial implements ActionListener {

	private JFrame janela;
	
	public OuvinteDeVoltaParaTelaInicial(JFrame janela) {
		this.janela = janela;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TelaInicial telaInicial = new TelaInicial();
		telaInicial.setLocationRelativeTo(janela);
		janela.dispose();
	}
	
}

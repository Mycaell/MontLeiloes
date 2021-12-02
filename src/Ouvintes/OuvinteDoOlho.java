package Ouvintes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Utilidades.Icones;

public class OuvinteDoOlho implements MouseListener{

	private JFrame janela;
	private JPasswordField senha;
	private JTextField senhaRevelada;
	private JLabel olho;
	
	public OuvinteDoOlho(JFrame jenela,JPasswordField senha, JTextField senhaRevelada, JLabel olho) {
		this.janela = janela;
		this.senha = senha;
		this.senhaRevelada = senhaRevelada;
		this.olho = olho;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		olho.setIcon(Icones.ICONE_OLHO_ABERTO);
		senhaRevelada.setText(new String(senha.getPassword()));
		senhaRevelada.setVisible(true);
		senha.setVisible(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		olho.setIcon(Icones.ICONE_OLHO_FECHADO);
		senhaRevelada.setVisible(false);
		senha.setVisible(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

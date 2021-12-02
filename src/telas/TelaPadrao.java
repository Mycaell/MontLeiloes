package telas;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Utilidades.Icones;

public class TelaPadrao extends JFrame{

	public TelaPadrao(String titulo) {
		super(titulo);
		this.setSize(400,300);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setIconImage(Icones.ICONE_TELA.getImage());
		this.getContentPane().setBackground(Color.lightGray);

		
        // SETA LOOK AND FEEL
        try {
            // AQUI VOCÊ SETA O NOME DA CLASSE REFERENTE A CADA TEMA !
		String tema = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
		
            // AQUI VC SETA O LOOK AND FEEL
            UIManager.setLookAndFeel(tema);
        } catch (InstantiationException | IllegalAccessException  |
                     UnsupportedLookAndFeelException | ClassNotFoundException e) {
            System.out.println("Erro LAF : " + e.getMessage());
        }

		
	}
}

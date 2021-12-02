package telas;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import Ouvintes.OuvinteQueLevaDevoltaParaOLogin;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.Icones;


public class TelaInicial extends TelaPadrao{

	
	private JLabel leiloesDisponiveis;

	public JLabel getLeiloesDisponiveis() {
		return leiloesDisponiveis;
	}

	public void setLeiloesDisponiveis(JLabel leiloesDisponiveis) {
		this.leiloesDisponiveis = leiloesDisponiveis;
	}

	public TelaInicial() {
		super("Inicio");
		adicionarLabels();
		adicionarBotoes();
		this.setVisible(true);
	}
	
	private void adicionarLabels() {
		
		JLabel label = new JLabel(Icones.ICONE_LOGO);
		label.setBounds(100, 10, 190, 140);
		add(label);
		
		leiloesDisponiveis = AdicionadorDeComponentes.adicionarJLabel(this, "Leilões Disponíveis", 240, 230, 120, 20);
		leiloesDisponiveis.addMouseListener(new OuvinteLabelLink(this));	
		leiloesDisponiveis.setForeground(Color.blue);
		
		
	
	}
	
	private void adicionarBotoes() {
		
		JButton botaoRealizarLogin = AdicionadorDeComponentes.adicionarJButton(this, "Realizar Login", 130, 180, 130, 20);
		botaoRealizarLogin.addActionListener(new OuvinteQueLevaDevoltaParaOLogin(this));
	}
	
	private class OuvinteLabelLink implements MouseListener{

		private TelaInicial telaInicial;
		
		public OuvinteLabelLink(TelaInicial telaInicial) {
			this.telaInicial = telaInicial;
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			TelaDeLeiloesDisponiveis teLaDeLeiloesDisponiveis = new TelaDeLeiloesDisponiveis();
			teLaDeLeiloesDisponiveis.setLocationRelativeTo(telaInicial);
			telaInicial.dispose();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			telaInicial.getLeiloesDisponiveis().setText("<html><u>Leilões Disponíveis</u></html>");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			telaInicial.getLeiloesDisponiveis().setText("Leilões Disponíveis");
		}

	}

	
	
	
	public static void main(String[] args) {
		new TelaInicial();
	}
	
}

package Ouvintes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import Utilidades.Usuario;
import telas.TelaDeDetalhesDeLeilao;

public class OuvinteBotaoDetalhes implements ActionListener {

	private JFrame janela;
	private Usuario usuario;
	private JTable tabelaDosLeiloesDisponiveis;
	
	
	public OuvinteBotaoDetalhes(JFrame janela, Usuario usuario, JTable tabelaDosLeiloesDisponiveis) {
		this.janela = janela;
		this.usuario = usuario;
		this.tabelaDosLeiloesDisponiveis = tabelaDosLeiloesDisponiveis;
	}
	

	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(tabelaDosLeiloesDisponiveis.getSelectedRow() != -1) {
			TelaDeDetalhesDeLeilao telaDeDetalhesDeLeilao = new TelaDeDetalhesDeLeilao(usuario,tabelaDosLeiloesDisponiveis);
			telaDeDetalhesDeLeilao.setLocationRelativeTo(janela);
			janela.dispose();
		}else {
			JOptionPane.showMessageDialog(janela,"Não existe um leilão selecionado!","Ação Impossível",JOptionPane.ERROR_MESSAGE);
		}
	}

	
}

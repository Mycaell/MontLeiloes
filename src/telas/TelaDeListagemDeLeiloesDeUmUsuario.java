package telas;


import javax.swing.JButton;
import javax.swing.JTable;

import Ouvintes.OuvinteBotaoDetalhes;
import Ouvintes.OuvinteQueLevaParaTelaDeUsuariosCadastrados;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.Usuario;

public class TelaDeListagemDeLeiloesDeUmUsuario extends TelaPadrao{

	
	private Usuario donoDosLeiloes;
	private Usuario observador;
	
	private JTable tabela;
	
	public TelaDeListagemDeLeiloesDeUmUsuario(Usuario observador,Usuario donoDosLeiloes) {
		super("Leilões de "+donoDosLeiloes.getNome());
		this.setSize(660, 300);
		this.donoDosLeiloes = donoDosLeiloes;
		this.observador = observador;

		adicionarTabela();
		adicionarBotoes();
		
		
		this.setVisible(true);
	}

	private void adicionarTabela() {

	 tabela = AdicionadorDeComponentes.adicionarTabelaComLeiloesDeUmUsuario(this,donoDosLeiloes,donoDosLeiloes.getLeiloesCadastrados(),0,0,655,220);
		
		
	}

	
	private void adicionarBotoes() {
		
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 235, 235, 70, 20);
		botaoVoltar.addActionListener(new OuvinteQueLevaParaTelaDeUsuariosCadastrados(this, observador));
		
		JButton botaoDetalhes = AdicionadorDeComponentes.adicionarJButton(this, "Detalhes",  335, 235, 85, 20);
		botaoDetalhes.addActionListener(new OuvinteBotaoDetalhes(this, observador, tabela));
		
		
	}
	
	
}

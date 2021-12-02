package telas;

import javax.swing.JButton;
import javax.swing.JTable;

import Ouvintes.OuvinteBotaoDetalhes;
import Ouvintes.OuvinteDeVoltaParaTelaInicial;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.Usuario;

public class TelaDeLeiloesDisponiveis extends TelaPadrao{

	private JTable tabelaDosLeiloesDisponiveis;
	private Usuario usuarioNaoLogado;
	
	public TelaDeLeiloesDisponiveis() {
		super("Leilões Disponíveis");
		
		this.setSize(660, 300);
		adicionarComponentes();
	
		this.setVisible(true);
	}
	
	private void adicionarComponentes() {
		
		tabelaDosLeiloesDisponiveis = AdicionadorDeComponentes.adicionarTabelaComTodosLeiloesDisponiveis(this,0,0,655,220);		
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 235, 235, 70, 20);
		botaoVoltar.addActionListener(new OuvinteDeVoltaParaTelaInicial(this));
		
		JButton botaoDetalhes = AdicionadorDeComponentes.adicionarJButton(this, "Detalhes",  335, 235, 85, 20);
		botaoDetalhes.addActionListener(new OuvinteBotaoDetalhes(this, usuarioNaoLogado, tabelaDosLeiloesDisponiveis));
		
	}

	
	
}

package telas;

import javax.swing.JButton;
import javax.swing.JTable;

import Ouvintes.OuvinteBotaoDetalhes;
import Ouvintes.OuvinteDeVoltaParaDentroDoPrograma;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.Usuario;

public class TelaDeComprasDoUsuario extends TelaPadrao{

	private Usuario usuario;
	private JTable tabela;
	
	public TelaDeComprasDoUsuario(Usuario usuario) {
		super("Minhas Compras");
		this.usuario = usuario;
		
		this.setSize(660, 300);
	
		adicionarTabela();
		adicionarBotoes();
		
		this.setVisible(true);
	}

	private void adicionarTabela() {
		tabela =  AdicionadorDeComponentes.adicionarTabelaComLeiloesDeUmUsuario(this, usuario,usuario.getLeiloesGanhos(), 0, 0, 655, 220);
	}
	
	private void adicionarBotoes() {
		
		JButton botaoVoltar = AdicionadorDeComponentes.adicionarJButton(this, "Voltar", 235, 235, 70, 20);
		botaoVoltar.addActionListener(new OuvinteDeVoltaParaDentroDoPrograma(this, usuario));
		
		JButton botaoDetalhes = AdicionadorDeComponentes.adicionarJButton(this, "Detalhes",  335, 235, 85, 20);
		botaoDetalhes.addActionListener(new OuvinteBotaoDetalhes(this, usuario, tabela));
	}

}

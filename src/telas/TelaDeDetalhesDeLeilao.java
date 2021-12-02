package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Exceções.CampoVazioException;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Leilao;
import Utilidades.Persistencia;
import Utilidades.Usuario;

public class TelaDeDetalhesDeLeilao extends TelaDeCadastroDeLeilao{

	private Usuario usuario;
	private JTable tabela;
	
	private Leilao leilaoSelecionado;
	
	public TelaDeDetalhesDeLeilao(Usuario usuario, JTable tabela) {
		super(usuario);
		this.usuario = usuario;	
		this.tabela = tabela;
		
		this.setTitle("Detalhes ");
		this.setSize(400, 380);
		
		inicializarCampos();
		adicionarComponentes();
		
		adicionarTabelaComOsLancesRecebidos();
		
		this.setVisible(true);
	}



	private void adicionarTabelaComOsLancesRecebidos() {

		
		DefaultTableModel modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JTable tabela = new JTable(modelo);
		
		if(leilaoSelecionado.getLancesRecebidos() != null) {
			modelo.addColumn("Lance");
			modelo.addColumn("Dado por");
//			
			for (int i = leilaoSelecionado.getLancesRecebidos().size()-1; i >= 0 ; i--) {
				Object[] linha = {leilaoSelecionado.getLancesRecebidos().get(i), leilaoSelecionado.getDonosDosLances().get(i)};
				modelo.addRow(linha);
			}
		}else {
			modelo.addColumn("Nenhum lance recebido!");
		}
				
		
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(10, 227, 374, 80);
		add(scroll);
	}



	private void inicializarCampos() {
		
		int linhaSelecionada = tabela.getSelectedRow();
		
		String dataDoLeilaoSelecionado = (String) tabela.getValueAt(linhaSelecionada, 3);
		
		leilaoSelecionado = AdicionadorDeComponentes.pegarLeilaoSelecionado(dataDoLeilaoSelecionado);

		JButton botaoExcluir = AdicionadorDeComponentes.adicionarJButton(this, "Excluir", 305, 320, 80, 20);
		botaoExcluir.addActionListener(new OuvinteTelaDeDetalhesDeLeilao(this));
		botaoExcluir.setEnabled(false);
		
		
		OuvinteTelaDeDetalhesDeLeilao ouvinte = new OuvinteTelaDeDetalhesDeLeilao(this);
		JButton botaoDarLance = AdicionadorDeComponentes.adicionarJButton(this, "Dar Lance", 103, 320, 100, 20);
		botaoDarLance.addActionListener(ouvinte);
		JButton botaoComprar = AdicionadorDeComponentes.adicionarJButton(this, "Comprar", 211, 320, 85, 20);
		botaoComprar.addActionListener(ouvinte);
		if(usuario == null) {
			botaoDarLance.setEnabled(false);
			botaoComprar.setEnabled(false);
		}else {
			if(leilaoSelecionado.getDono() != null && leilaoSelecionado.getDono().equals(usuario.getEmail())) {
				botaoExcluir.setEnabled(true);
				botaoDarLance.setEnabled(false);
				botaoComprar.setEnabled(false);
			}
		}
		
		getNomeDoProduto().setEnabled(false);
		getDescricao().setEnabled(false);
		getPreco().setEnabled(false);
		getLanceMinimo().setEnabled(false);
		getQtdDias().setEnabled(false);
		getQtdHoras().setEnabled(false);
		
		getNomeDoProduto().setText(leilaoSelecionado.getNomeDoProduto());
		getDescricao().setText(leilaoSelecionado.getDescricao());
		getPreco().setText(Double.toString(leilaoSelecionado.getPreco()));
		getLanceMinimo().setText(Double.toString(leilaoSelecionado.getLanceMinimo()));
		
		
        Date dataAtual = Calendar.getInstance().getTime();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dataExpiracao = null;
		try {
			dataExpiracao = formato.parse(dataDoLeilaoSelecionado);
		} catch (ParseException e) {
		}

        
        long d = (dataExpiracao.getTime() - dataAtual.getTime()) + 3600000;
        int diasRestantes = (int) (d / 86400000L);
        getQtdDias().setSelectedItem(diasRestantes);

        JTextField dataDeNasc = AdicionadorDeComponentes.adicionarJTextField(this,122, 183, 105, 20);
        dataDeNasc.setText(formato.format(leilaoSelecionado.getDataDeCadastro()));
        dataDeNasc.setEnabled(false);
        add(dataDeNasc);
        
//TENTAR MELHORAR ESSA LOGICA        
        
        
		SimpleDateFormat formatHora = new SimpleDateFormat("HH");
		
		Date dat = Calendar.getInstance().getTime();
		String horasAtualString = formatHora.format(dat);

		String[] dataDoLeilaoSelecionadoDividida = dataDoLeilaoSelecionado.split(" ");
		String horaFinalString = dataDoLeilaoSelecionadoDividida[1].split(":")[0];
			
		int H = Integer.parseInt(horaFinalString) - Integer.parseInt(horasAtualString);
			
		getQtdHoras().setSelectedItem(H);;
		
		getJLprazo().setBounds(20, 155, 60, 20);
		getJLprazo().setText("Expira em ");
		getQtdDias().setBounds(80, 155, 40, 20);
		getJLdias().setText("dias(s),");
		getJLdias().setBounds(124, 155, 65, 20);
		getQtdHoras().setBounds(170, 155, 40, 20);
		getJLhoras().setBounds(213, 155, 60, 20);
		getJLhoras().setText("Hora(s) e");
		
		
		JComboBox<Integer> qtdMinutos = new JComboBox<Integer>();
		qtdMinutos.setBounds(268, 155, 40, 20);
		qtdMinutos.setEnabled(false);
		add(qtdMinutos);
		
		SimpleDateFormat formatoMinuto = new SimpleDateFormat("mm");

		Date da = Calendar.getInstance().getTime();
		String minutosAtualString = formatoMinuto.format(da);
		
		String minutosFinalString = dataDoLeilaoSelecionadoDividida[1].split(":")[1];
		int M = 0;
		if(Integer.parseInt(minutosFinalString) > Integer.parseInt(minutosAtualString)) {
			M = Integer.parseInt(minutosFinalString) - Integer.parseInt(minutosAtualString);
		}
		
//		int minutoMaior = Math.max(Integer.parseInt(minutosFinalString), Integer.parseInt(minutosAtualString));
//		
//		int minutoMenor = Math.min(Integer.parseInt(minutosFinalString), Integer.parseInt(minutosAtualString));
//		
//		int M = minutoMaior - minutoMenor;

		qtdMinutos.addItem(M);
		
		AdicionadorDeComponentes.adicionarJLabel(this, "Minuto(s)", 312, 155, 75, 20);
	
	}



	private void adicionarComponentes() {
		remove(getBotaoCadastrar());
		
		AdicionadorDeComponentes.adicionarJLabel(this, "Data de Cadastro", 20,183, 110, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Ultimos Lances:", 20, 206, 100, 20);
		
		getBotaoCancelar().setText("Voltar");
		getBotaoCancelar().setBounds(10, 320, 85, 20);
		
	}
	
	
	
	private void comprarLeilao(Usuario comprador, Usuario donoDoLeilao, Leilao leilao) {
		
		
		Persistencia p = new Persistencia();
		CentralDeInformacoes cdi = p.recuperarCentral("central");
		
		Usuario usuarioAtualizado = cdi.recuperarUsuario(comprador.getEmail());
		
		Usuario donoDoLeilaoAtualizado = cdi.recuperarUsuario(donoDoLeilao.getEmail());
		
		Leilao leilaoAtualizado = donoDoLeilaoAtualizado.recuperarLeilao(leilao);
		
		donoDoLeilaoAtualizado.getLeiloesVendidos().add(leilaoAtualizado);
		
		leilaoAtualizado.setComprado(true);
		leilaoAtualizado.setGanhador(usuarioAtualizado.getEmail());
		usuarioAtualizado.getLeiloesGanhos().add(leilaoAtualizado);
		
		p.salvarCentral(cdi, "central");

		
		JOptionPane.showMessageDialog(this, "Leilão Comprado!");
		
		TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(usuarioAtualizado);
		telaDentroDoPrograma.setLocationRelativeTo(this);
		this.dispose();
		
	}
	

	private class OuvinteTelaDeDetalhesDeLeilao implements ActionListener{

		private TelaDeDetalhesDeLeilao telaDeDetalhesDeLeilao;
		
		
		public OuvinteTelaDeDetalhesDeLeilao(TelaDeDetalhesDeLeilao telaDeDetalhesDeLeilao) {
			this.telaDeDetalhesDeLeilao = telaDeDetalhesDeLeilao;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {

			String textoDoBotao = e.getActionCommand();
			
			Persistencia p = new Persistencia();
			CentralDeInformacoes cdi = p.recuperarCentral("central");
			
			int linhaSelecionada = tabela.getSelectedRow();
			String dataDoLeilaoSelecionado = (String) tabela.getValueAt(linhaSelecionada, 3);
			leilaoSelecionado = AdicionadorDeComponentes.pegarLeilaoSelecionado(dataDoLeilaoSelecionado);
			
			String emailDoDono = (String) tabela.getValueAt(linhaSelecionada, 4);
			Usuario donoDoLeilao = cdi.recuperarUsuario(emailDoDono);


			
			if(textoDoBotao.equals("Dar Lance")) {
				String lance = JOptionPane.showInputDialog(telaDeDetalhesDeLeilao,"Informe o seu lance","Lance",JOptionPane.PLAIN_MESSAGE);

				double l = 0;
				
				if(lance != null) {
					try {
						if(lance.equals("")) {
							throw new CampoVazioException();
						}
						l = Double.parseDouble(lance);

						
						if(l > leilaoSelecionado.getLanceMinimo() && l < leilaoSelecionado.getPreco()) {
							Leilao lei = donoDoLeilao.recuperarLeilao(leilaoSelecionado);
							for (Leilao le : donoDoLeilao.getLeiloesCadastrados()) {
								if(le.equals(lei)) {
									lei.getLancesRecebidos().add(l);
									lei.getDonosDosLances().add(usuario.getEmail());
									lei.setLanceMinimo(l);
									p.salvarCentral(cdi, "central");
									JOptionPane.showMessageDialog(telaDeDetalhesDeLeilao, "Lance realizado!");
									
									TelaDeDetalhesDeLeilao atualizandoTela = new TelaDeDetalhesDeLeilao(usuario, tabela);
									atualizandoTela.setLocationRelativeTo(telaDeDetalhesDeLeilao);
									dispose();
								}
							}
							
						}else {
							if(l >= leilaoSelecionado.getPreco()) {
								int opcao = JOptionPane.showConfirmDialog(telaDeDetalhesDeLeilao, "Este lance comprará o produto, você está ciente disso?", "Lance", JOptionPane.YES_NO_OPTION);
								if(opcao == JOptionPane.YES_OPTION) {
									comprarLeilao(usuario, donoDoLeilao, leilaoSelecionado);
								}
								
							}else {
								JOptionPane.showMessageDialog(telaDeDetalhesDeLeilao, "O lance minímo é "+leilaoSelecionado.getLanceMinimo(), "Lance minímo não respeitado", JOptionPane.ERROR_MESSAGE);
							}
						}
						
					}catch (CampoVazioException er) {
						JOptionPane.showMessageDialog(telaDeDetalhesDeLeilao,"O campo não pode ficar vazio!","Campo não preenchido",JOptionPane.ERROR_MESSAGE);
					}catch (NumberFormatException er) {
						JOptionPane.showMessageDialog(telaDeDetalhesDeLeilao, "Digite apenas números!", "Lance Inválido", JOptionPane.ERROR_MESSAGE);
					}
					
				}

				
			}else if(textoDoBotao.equals("Comprar")) {
				int opcao = JOptionPane.showConfirmDialog(telaDeDetalhesDeLeilao, "Você tem certeza quer pagar "+leilaoSelecionado.getPreco()+" pelo produto?", "Compra Direta", JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					comprarLeilao(usuario, donoDoLeilao, leilaoSelecionado);
				}

				
			}else if(textoDoBotao.equals("Excluir")) {
				int opcao = JOptionPane.showConfirmDialog(telaDeDetalhesDeLeilao, "Você tem certeza?", "Exclusão de Leilão", JOptionPane.YES_NO_OPTION);
				if(opcao == JOptionPane.YES_OPTION) {
					for (Leilao leilao : donoDoLeilao.getLeiloesCadastrados()) {
						if(leilao.equals(leilaoSelecionado)) {
							donoDoLeilao.excluirLeilao(leilao);
							JOptionPane.showMessageDialog(telaDeDetalhesDeLeilao, "Leilão Excluido!");
							break;
						}
					}

					p.salvarCentral(cdi, "central");
					
					TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(donoDoLeilao);
					telaDentroDoPrograma.setLocationRelativeTo(telaDeDetalhesDeLeilao);
					telaDeDetalhesDeLeilao.dispose();
				}
			}
			
		}
	}
	
	
}



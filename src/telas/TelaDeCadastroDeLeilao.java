package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Exceções.CampoVazioException;
import Exceções.LanceMinimoMaiorQueOPrecoException;
import Exceções.PrazoInvalidoException;
import Exceções.PrecoELanceMinimoIguaisAZeroException;
import Exceções.UsuarioNaoExistenteException;
import Ouvintes.OuvinteDeVoltaParaDentroDoPrograma;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Leilao;
import Utilidades.Persistencia;
import Utilidades.Usuario;

public class TelaDeCadastroDeLeilao extends TelaPadrao{

	private Usuario usuario;
	private JTextField nomeDoProduto;
	private JTextArea descricao;
	private JTextField preco;
	private JTextField lanceMinimo;
	
	private JComboBox<Integer> qtdDias;
	private JComboBox<Integer> qtdHoras;
	
	private JButton botaoCadastrar;
	private JButton botaoCancelar;
	
	private JLabel JLprazo;
	private JLabel JLdias;
	private JLabel JLhoras;
	

		
	
	
	public JLabel getJLprazo() {
		return JLprazo;
	}

	public void setJLprazo(JLabel jLprazo) {
		JLprazo = jLprazo;
	}

	public JLabel getJLdias() {
		return JLdias;
	}

	public void setJLdias(JLabel jLdias) {
		JLdias = jLdias;
	}

	public JLabel getJLhoras() {
		return JLhoras;
	}

	public void setJLhoras(JLabel jLhoras) {
		JLhoras = jLhoras;
	}

	public JTextField getNomeDoProduto() {
		return nomeDoProduto;
	}

	public void setNomeDoProduto(JTextField nomeDoProduto) {
		this.nomeDoProduto = nomeDoProduto;
	}

	public JTextArea getDescricao() {
		return descricao;
	}

	public void setDescricao(JTextArea descricao) {
		this.descricao = descricao;
	}

	public JTextField getPreco() {
		return preco;
	}

	public void setPreco(JTextField preco) {
		this.preco = preco;
	}

	public JTextField getLanceMinimo() {
		return lanceMinimo;
	}

	public void setLanceMinimo(JTextField lanceMinimo) {
		this.lanceMinimo = lanceMinimo;
	}

	public JComboBox<Integer> getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(JComboBox<Integer> qtdDias) {
		this.qtdDias = qtdDias;
	}

	public JComboBox<Integer> getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(JComboBox<Integer> qtdHoras) {
		this.qtdHoras = qtdHoras;
	}

	public JButton getBotaoCadastrar() {
		return botaoCadastrar;
	}

	public void setBotaoCadastrar(JButton botaoCadastrar) {
		this.botaoCadastrar = botaoCadastrar;
	}

	public JButton getBotaoCancelar() {
		return botaoCancelar;
	}

	public void setBotaoCancelar(JButton botaoCancelar) {
		this.botaoCancelar = botaoCancelar;
	}

	public TelaDeCadastroDeLeilao(Usuario usuario) {
		super("Cadastro de Leilão");
		this.usuario = usuario;
		
		adicionarLabels();
		adicionarFields();
		adicionarBotoes();
		adicionarComboBox();
		
		this.setVisible(true);
	}
	
	private void adicionarLabels() {
		AdicionadorDeComponentes.adicionarJLabel(this, "Nome do Produto", 20, 10, 100, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Descrição", 20, 40, 70, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Preço", 20, 125, 40, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Lance Mínimo", 185, 125, 80, 20);
		JLprazo = AdicionadorDeComponentes.adicionarJLabel(this, "Prazo:", 20, 155, 40, 20);
		JLdias = AdicionadorDeComponentes.adicionarJLabel(this, "dias(s)  e ", 105, 155, 70, 20);
		JLhoras = AdicionadorDeComponentes.adicionarJLabel(this, "Hora(s)", 210, 155, 60, 20);
	}

	private void adicionarFields() {
		nomeDoProduto = AdicionadorDeComponentes.adicionarJTextField(this, 123, 10, 180, 20);
		descricao = new JTextArea();
		preco = AdicionadorDeComponentes.adicionarJTextField(this, 60, 125, 110, 20);
		lanceMinimo = AdicionadorDeComponentes.adicionarJTextField(this, 270, 125, 110, 20);

		JScrollPane scroll = new JScrollPane(descricao);
		scroll.setBounds(90, 40, 290, 70);
		add(scroll);
	}
		
	private void adicionarComboBox() {
		
		Integer[] dias = new Integer[31];
		Integer[] horas = new Integer[24];
		
		for (int i = 0; i < dias.length; i++) {
			dias[i] = i;
		}
		
		for (int i = 0; i < horas.length; i++) {
			horas[i] = i;
		}
		
		qtdDias = new JComboBox<Integer>(dias);
		qtdDias.setBounds(60, 155, 40, 20);
		add(qtdDias);
		
		
		qtdHoras = new JComboBox<Integer>(horas);
		qtdHoras.setBounds(165, 155, 40, 20);
		add(qtdHoras);
		
	}

	private void adicionarBotoes() {
		botaoCadastrar = AdicionadorDeComponentes.adicionarJButton(this, "Cadastrar", 95, 200, 95, 20);
		botaoCadastrar.addActionListener(new OuvinteDoBotaoCadastrar(this));
		botaoCancelar = AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 205, 200, 90, 20);
		botaoCancelar.addActionListener(new OuvinteDeVoltaParaDentroDoPrograma(this, usuario));
	}
	
	private class OuvinteDoBotaoCadastrar implements ActionListener{

		private TelaDeCadastroDeLeilao telaDeCadastroDeLeilao;
		
		public OuvinteDoBotaoCadastrar(TelaDeCadastroDeLeilao telaDeCadastroDeLeilao) {
			this.telaDeCadastroDeLeilao = telaDeCadastroDeLeilao;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			double precoDigitado = 0;
			double lanceMinimoDigitado = 0;
			
			int dias = 0;
			int horas = 0;
			
			try {
				if(nomeDoProduto.getText().equals("") || descricao.getText().equals("") || preco.getText().equals("") || lanceMinimo.getText().equals("")) {
					throw new CampoVazioException();
				}
				
				precoDigitado = Double.parseDouble(preco.getText());
				lanceMinimoDigitado = Double.parseDouble(lanceMinimo.getText()); 
				
				if(precoDigitado == 0 || lanceMinimoDigitado == 0) {
					throw new PrecoELanceMinimoIguaisAZeroException();
				}
				
				if(lanceMinimoDigitado >= precoDigitado) {
					throw new LanceMinimoMaiorQueOPrecoException();
				}
				
				dias = (int) qtdDias.getSelectedItem();
				horas = (int) qtdHoras.getSelectedItem();

				if(dias == 0 && horas == 0) {
					throw new PrazoInvalidoException();
				}
				
				Persistencia p = new Persistencia();
				CentralDeInformacoes cdi = p.recuperarCentral("central");
				
				Usuario usuarioAtualizado = cdi.recuperarUsuario(usuario.getEmail(), usuario.getSenha());

					
				Leilao leilao = new Leilao(nomeDoProduto.getText(), descricao.getText(), precoDigitado, lanceMinimoDigitado, dias, horas);
				leilao.setDono(usuario.getEmail());

				usuarioAtualizado.cadastrarNovoLeilao(leilao);
				p.salvarCentral(cdi, "central");
					
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao, "Leilão cadastrado com sucesso!");
				
				TelaDentroDoPrograma telaDentroDoPrograma = new TelaDentroDoPrograma(usuarioAtualizado);
				telaDentroDoPrograma.setLocationRelativeTo(telaDeCadastroDeLeilao);
				telaDeCadastroDeLeilao.dispose();
				
			}catch (CampoVazioException er) {
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao,er.getMessage(),"Campo não preenchido",JOptionPane.ERROR_MESSAGE);
			}catch (NumberFormatException er) {
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao,"Nos campos Preço e Lance Mínimo digite apenas números","Preço e Lance Mínimo inválidos",JOptionPane.ERROR_MESSAGE);
			}catch (PrecoELanceMinimoIguaisAZeroException er) {
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao,er.getMessage(),"Preço e Lance Minimo inválidos",JOptionPane.ERROR_MESSAGE);	
			}catch (LanceMinimoMaiorQueOPrecoException er) {
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao,er.getMessage(),"Lance Mínimo inválido",JOptionPane.ERROR_MESSAGE);
			}catch (PrazoInvalidoException er) {
				JOptionPane.showMessageDialog(telaDeCadastroDeLeilao,"O prazo não pode ser 0 dias e 0 horas",er.getMessage(),JOptionPane.ERROR_MESSAGE);
			} catch (UsuarioNaoExistenteException er) {

			}
		}
	}
}
	


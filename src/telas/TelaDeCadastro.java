package telas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Exceções.UsuarioExistenteException;
import Ouvintes.OuvinteDoOlho;
import Ouvintes.OuvinteQueLevaDevoltaParaOLogin;
import Utilidades.AdicionadorDeComponentes;
import Utilidades.CentralDeInformacoes;
import Utilidades.Icones;
import Utilidades.Persistencia;
import Utilidades.Usuario;
import Utilidades.ValidacaoDeSenhaEmailECampos;


public class TelaDeCadastro  extends TelaPadrao {

	private JTextField nome;
	private JTextField sobrenome;
	private char sexo = 'F';
	private JFormattedTextField dataDeNascimento;
	private JTextField endereço;
	private JFormattedTextField telefone;
	private JFormattedTextField CPF;
	private JTextField email;
	private JPasswordField senha;
	private JPasswordField confirmacaoDeSenha;

	private JButton botaoSalvar;
	private JButton botaoCancelar;
	
	private JLabel olho;
	private JTextField senhaRevelada;
	
	private JRadioButton radioButtonF;
	private JRadioButton radioButtonM;
	
	
	public JLabel getOlho() {
		return olho;
	}

	public JTextField getNome() {
		return nome;
	}

	public JTextField getSobrenome() {
		return sobrenome;
	}

	public JFormattedTextField getDataDeNascimento() {
		return dataDeNascimento;
	}

	public JTextField getEndereço() {
		return endereço;
	}

	public JFormattedTextField getTelefone() {
		return telefone;
	}


	public JFormattedTextField getCPF() {
		return CPF;
	}

	public JTextField getEmail() {
		return email;
	}

	public JPasswordField getSenha() {
		return senha;
	}

	public JPasswordField getConfirmacaoDeSenha() {
		return confirmacaoDeSenha;
	}

	public JRadioButton getRadioButtonF() {
		return radioButtonF;
	}

	public JLabel getOBS() {
		return OBS;
	}
	public JLabel getLabelSenha() {
		return labelSenha;
	}

	public JLabel getLabelConfirmeSenha() {
		return labelConfirmeSenha;
	}
	
	public JButton getBotaoSalvar() {
		return botaoSalvar;
	}

	public JRadioButton getRadioButtonM() {
		return radioButtonM;
	}
	
	public JButton getBotaoCancelar() {
		return botaoCancelar;
	}

	public TelaDeCadastro() {
		super("Cadastro");
		
		adicionarLabels();
		adicionarBotoes();
		adicionarFields();
		adicionarMascaras();
		this.setVisible(true);
	}

	private JLabel OBS;
	private JLabel labelSenha;
	private JLabel labelConfirmeSenha;
	
	private void adicionarLabels() {
		AdicionadorDeComponentes.adicionarJLabel(this, "Nome", 20, 10, 40, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Sobrenome", 190, 10, 70, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Sexo", 20, 40, 40, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Data de Nasc.", 190, 40, 130, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Endereço", 20, 70, 60, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "Telefone", 20, 100, 50, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "CPF", 250, 100, 60, 20);
		AdicionadorDeComponentes.adicionarJLabel(this, "E-mail", 20, 130, 40, 20);
		labelSenha = AdicionadorDeComponentes.adicionarJLabel(this, "Senha", 20, 160, 40, 20);
		labelConfirmeSenha = AdicionadorDeComponentes.adicionarJLabel(this, "Confirme a Senha", 20, 190, 110, 20);
		OBS = AdicionadorDeComponentes.adicionarJLabel(this, "OBS: 4 letras e 4 números", 210, 160, 150, 20);
		OBS.setForeground(Color.BLACK);
		
	}



	private void adicionarBotoes() {
		botaoSalvar = AdicionadorDeComponentes.adicionarJButton(this, "Salvar", 110, 230, 80, 20);
		botaoSalvar.addActionListener(new OuvinteBotaoSalvar(this));
		
		botaoCancelar = AdicionadorDeComponentes.adicionarJButton(this, "Cancelar", 210, 230, 90, 20);
		botaoCancelar.addActionListener(new OuvinteQueLevaDevoltaParaOLogin(this));
		
		radioButtonF = new JRadioButton("F");
		radioButtonF.setBounds(60, 40, 40, 20);
		radioButtonM = new JRadioButton("M");
		radioButtonM.setBounds(100, 40, 45, 20);

	    ButtonGroup grupoRadioSexo = new ButtonGroup();
	    grupoRadioSexo.add(radioButtonF);
	    grupoRadioSexo.add(radioButtonM);
	    
	    radioButtonF.setSelected(true);
	    
	    radioButtonF.setBackground(null);
	    radioButtonM.setBackground(null);
	    
	    add(radioButtonF);
	    add(radioButtonM);
	    
	    ImageIcon imgOlho = Icones.ICONE_OLHO_FECHADO;
	    olho = new JLabel(imgOlho);
	    olho.setToolTipText("revelar senha");
	    olho.setBounds(175, 160, 30, 20);
	    add(olho);
	    
	    senha = AdicionadorDeComponentes.adicionarJPasswordField(this,60, 160, 110, 20);
	    senhaRevelada = AdicionadorDeComponentes.adicionarJTextField(this, 60, 160,110, 20);
	    senhaRevelada.setVisible(false);
	    olho.addMouseListener(new OuvinteDoOlho(this,senha,senhaRevelada,olho));
	    
	}
	
	private void adicionarFields() {
		nome = AdicionadorDeComponentes.adicionarJTextField(this,60, 10, 110, 20);
		sobrenome = AdicionadorDeComponentes.adicionarJTextField(this, 265, 10, 115, 20);
		endereço = AdicionadorDeComponentes.adicionarJTextField(this, 80, 70, 300, 20);
		email = AdicionadorDeComponentes.adicionarJTextField(this, 60, 130, 320, 20);
		email.setText("gmail");
		email.addFocusListener(new OuvinteDeFocoDoEmail(this));
		confirmacaoDeSenha = AdicionadorDeComponentes.adicionarJPasswordField(this, 130, 190, 110, 20);
	}

	private void adicionarMascaras() {
		try {
			dataDeNascimento = AdicionadorDeComponentes.adicionarJFormattedTextFiel(this, "##/##/####", 280, 40, 100, 20);
			CPF = AdicionadorDeComponentes.adicionarJFormattedTextFiel(this, "###.###.###-##", 280, 100, 100, 20);
			telefone = AdicionadorDeComponentes.adicionarJFormattedTextFiel(this, "## ####-####", 75, 100, 110, 20);
		}catch (ParseException e) {

		}
	}
	
	private class OuvinteDeFocoDoEmail implements FocusListener{

		private TelaDeCadastro telaDeCadastro;
		
		public OuvinteDeFocoDoEmail(TelaDeCadastro telaDeCadastro) {
			this.telaDeCadastro = telaDeCadastro;
		}
		
		@Override
		public void focusGained(FocusEvent e) {
			email.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(email.getText().equals("")) {
				email.setText("gmail");
			}
			
		}
		
	}	
	
	private class OuvinteBotaoSalvar implements ActionListener{
		
		private TelaDeCadastro telaDeCadastro;
		
		public OuvinteBotaoSalvar(TelaDeCadastro telaDeCadastro) {
			this.telaDeCadastro = telaDeCadastro;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String botao = e.getActionCommand();
			if(botao.equals("O")) {
				senha.setVisible(true);
			}
			
			
			if(!radioButtonF.isSelected()) {
				sexo = 'M';
			}
			
			
			if(ValidacaoDeSenhaEmailECampos.validarDadosDoUsuario(telaDeCadastro, nome, sobrenome, dataDeNascimento, endereço, telefone, CPF, email) && ValidacaoDeSenhaEmailECampos.validarSenhaEConfirmacaoDeSenha(telaDeCadastro, senha, confirmacaoDeSenha)) {

				SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
				
				String nomeDigitado = nome.getText();
				String sobrenomeDigitado = sobrenome.getText();
				String endereçoDigitado = endereço.getText();
				Date dataDeNasc = null;
				String telefoneDigitado = telefone.getText();
				String CPFDigitado = CPF.getText();
				String emailDigitado = email.getText();
				String senhaDigitada = new String(senha.getPassword()); 	
								
				try {
					
					dataDeNasc = formataData.parse(dataDeNascimento.getText());
					
					Usuario usuario = new Usuario(nomeDigitado,sobrenomeDigitado,sexo,dataDeNasc,endereçoDigitado,telefoneDigitado,CPFDigitado,emailDigitado,senhaDigitada);
					
					Persistencia persistencia = new Persistencia();
					CentralDeInformacoes central = persistencia.recuperarCentral("central");
					
					central.adicionarUsuario(usuario);

					persistencia.salvarCentral(central,"central");
					
					JOptionPane.showMessageDialog(telaDeCadastro, "Usuário cadastrado com sucesso!");
				
					TelaDeLogin telaDeLogin = new TelaDeLogin();
					telaDeLogin.setLocationRelativeTo(telaDeCadastro);
					telaDeCadastro.dispose();
					
				}catch (ParseException er) {
				
				}catch(UsuarioExistenteException er) {
					JOptionPane.showMessageDialog(telaDeCadastro, er.getMessage(), "Mude o seu e-mail", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	
}

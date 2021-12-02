package Utilidades;


import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Exceções.CampoVazioException;
import Exceções.ConfirmacaoDeSenhaInvalidaException;
import Exceções.DataInvalidaException;
import Exceções.EmailNaoPreenchidoException;
import Exceções.EmailUsadoNoCadastroInvalidoException;
import Exceções.SenhaUsadaNoCadastroInvalidaException;

public class ValidacaoDeSenhaEmailECampos {

	public static boolean validarDataDeExpiracaoDeLeilao(Leilao leilao) {
		Date dataDeExpiracao = AdicionadorDeComponentes.calcularDataDeExpiracao(leilao.getDataDeCadastro(), leilao.getDias(), leilao.getHoras());
		
		Date dataAtual = Calendar.getInstance().getTime();

		if(dataDeExpiracao.after(dataAtual)) {
			return true;
		}else {
			return false;
		}
		

	}
	
	public static boolean validaSenha(String senha) throws SenhaUsadaNoCadastroInvalidaException{

		if(senha.length() < 8 || senha.length() > 8) {
			throw new SenhaUsadaNoCadastroInvalidaException();
		}
		
		if(contarQTDDeLetras(senha) != 4) {
			throw new SenhaUsadaNoCadastroInvalidaException();
		}else if(contarQTDDeNumeros(senha) != 4) {
			throw new SenhaUsadaNoCadastroInvalidaException();
		}else {
			return true;
		}

	}
	
	public static boolean ValidaEmail(String email) throws EmailUsadoNoCadastroInvalidoException{
		
		String regex = "[A-Za-z0-9]+@+gmail.com$";	
		
		if(email.matches(regex)) {
			return true;
		}else {
			throw new EmailUsadoNoCadastroInvalidoException();
		}
	}
	
	public static boolean validarCampo(String textoDoCampo) throws CampoVazioException{
		if(textoDoCampo.equals("")) {
			throw new CampoVazioException();
		}else {
			return true;
		}
	}
	
	public static boolean validarData(int dia, int mes, int ano) throws DataInvalidaException{
		if(mes == 2 && dia > 28 || dia < 1 || ano < 1) {
			throw new DataInvalidaException();
		}else if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
			if(dia > 30){
				throw new DataInvalidaException();
			}
		}else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
			if(dia > 31) {
				throw new DataInvalidaException();
			}
		}else if (mes < 1 || mes > 12){
			throw new DataInvalidaException();
		}
		return true;
	}
	
	public static boolean validarSenhaEConfirmacaoDeSenha(JFrame janela, JPasswordField senha, JPasswordField confimacaoDeSenha) {
		
		String senhaDigitada = new String(senha.getPassword()); 	
		String confirmacaoDeSenhaDigitada = new String(confimacaoDeSenha.getPassword());

		try {
			
			if(senhaDigitada.equals("") || confirmacaoDeSenhaDigitada.equals("")) {
				throw new CampoVazioException();
			}
			
			ValidacaoDeSenhaEmailECampos.validaSenha(senhaDigitada);

			if(!senhaDigitada.equals(confirmacaoDeSenhaDigitada)) {
				throw new ConfirmacaoDeSenhaInvalidaException();
			}
			return true;
			
		}catch (CampoVazioException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Campo não preenchido",JOptionPane.ERROR_MESSAGE);
		}catch (SenhaUsadaNoCadastroInvalidaException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Campo inválido",JOptionPane.ERROR_MESSAGE);
		}catch (ConfirmacaoDeSenhaInvalidaException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Confirmação de senha inválida",JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	
	public static boolean validarDadosDoUsuario(JFrame janela, JTextField nome,JTextField sobrenome,JFormattedTextField dataDeNasc, JTextField endereço, JFormattedTextField telefone,
			JFormattedTextField CPF, JTextField email)  {

		
		String nomeDigitado = nome.getText();
		
		String sobrenomeDigitado = sobrenome.getText();
		
		String endereçoDigitado = endereço.getText();
		
		try {
			
			ValidacaoDeSenhaEmailECampos.validarCampo(nomeDigitado);
			ValidacaoDeSenhaEmailECampos.validarCampo(sobrenomeDigitado);
			ValidacaoDeSenhaEmailECampos.validarCampo(endereçoDigitado);
			
			String dataDigitada = dataDeNasc.getText();

			if(dataDigitada.equals("  /  /    ")) {
				throw new CampoVazioException();
			}
			
			String[] data = dataDeNasc.getText().split("/");
			int dia = Integer.parseInt(data[0]);
			int mes = Integer.parseInt(data[1]);
			int ano = Integer.parseInt(data[2]);
			
			validarData(dia, mes, ano);
			
			String telefoneDigitado = telefone.getText();

			if(telefoneDigitado.equals("       -    ")) {
				throw new CampoVazioException();
			}
		
			String CPFDigitado = CPF.getText();
			
			if(CPFDigitado.equals("   .   .   -  ")) {
				throw new CampoVazioException();
			}
			
			String emailDigitado = email.getText();

			if(emailDigitado.equals("gmail")) {
				throw new EmailNaoPreenchidoException();
			}
			
			ValidacaoDeSenhaEmailECampos.ValidaEmail(emailDigitado);
			
			return true;
		
		}catch (CampoVazioException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Campo não preenchido",JOptionPane.ERROR_MESSAGE);
		}catch (DataInvalidaException e) {
			JOptionPane.showMessageDialog(janela, e.getMessage(),"Data Inválida",JOptionPane.ERROR_MESSAGE);
		}catch(EmailNaoPreenchidoException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Campo não preenchido",JOptionPane.ERROR_MESSAGE);
		}catch(EmailUsadoNoCadastroInvalidoException e) {
			JOptionPane.showMessageDialog(janela,e.getMessage(),"Campo inválido",JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
		
	}

	
	
	
//	METODOS INUTEIS
	public static int contarQTDDeNumeros(String texto) {
		int qtdNUM = 0;
		
		for (int i = 0; i < texto.length(); i++) {
			if(Character.isDigit(texto.charAt(i))){
				qtdNUM++;
			}
		}
		return qtdNUM;
	}
	
	public static int contarQTDDeLetras(String texto) {
		int qtdLetras = 0;
		
		for (int i = 0; i < texto.length(); i++) {
			if(Character.isLetter(texto.charAt(i))) {
				qtdLetras++;
			}
		}
		return qtdLetras;
	}
	
	
	
}

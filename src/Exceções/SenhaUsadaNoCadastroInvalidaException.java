package Exce��es;

public class SenhaUsadaNoCadastroInvalidaException extends Exception{

	public SenhaUsadaNoCadastroInvalidaException() {
		super("A senha n�o segue os padroes (4 letras e 4 n�meros)!");
	}
	
}

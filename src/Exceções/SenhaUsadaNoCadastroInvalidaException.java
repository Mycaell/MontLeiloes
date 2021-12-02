package Exceções;

public class SenhaUsadaNoCadastroInvalidaException extends Exception{

	public SenhaUsadaNoCadastroInvalidaException() {
		super("A senha não segue os padroes (4 letras e 4 números)!");
	}
	
}

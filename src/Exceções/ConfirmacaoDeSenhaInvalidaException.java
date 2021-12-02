package Exceções;

public class ConfirmacaoDeSenhaInvalidaException extends Exception{

	public ConfirmacaoDeSenhaInvalidaException() {
		super("A confirmação da senha é incompatível");
	}
	
}

package Exceções;

public class UsuarioNaoExistenteException extends Exception{
	
	public UsuarioNaoExistenteException() {
		super("Você não está cadastrado");
	}

}

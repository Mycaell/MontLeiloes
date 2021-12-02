package Exceções;

public class UsuarioExistenteException extends Exception{

	public UsuarioExistenteException() {
		super("Já existem um usuário cadastrado com esse e-mail");
	}
	
}

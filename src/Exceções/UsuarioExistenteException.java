package Exce��es;

public class UsuarioExistenteException extends Exception{

	public UsuarioExistenteException() {
		super("J� existem um usu�rio cadastrado com esse e-mail");
	}
	
}

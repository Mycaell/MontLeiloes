package Exce��es;

public class UsuarioNaoExistenteException extends Exception{
	
	public UsuarioNaoExistenteException() {
		super("Voc� n�o est� cadastrado");
	}

}

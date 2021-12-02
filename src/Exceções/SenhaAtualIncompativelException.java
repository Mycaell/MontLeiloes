package Exceções;

public class SenhaAtualIncompativelException extends Exception{
	
	public SenhaAtualIncompativelException() {
		super("Senha atual é incompatível!");
	}
}

package Exceções;

public class LanceMinimoMaiorQueOPrecoException extends Exception{

	public LanceMinimoMaiorQueOPrecoException() {
		super("O Lance Mínimo deve ser menor que o Preço!");
	}
	
}

package Exceções;

public class PrecoELanceMinimoIguaisAZeroException extends Exception{

	public PrecoELanceMinimoIguaisAZeroException() {
		super("Preço e Lance Mínimo devem ser maiores que 0!");
	}
	
}

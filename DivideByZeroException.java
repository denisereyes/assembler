package assembler;


@SuppressWarnings("serial")
public class DivideByZeroException extends RuntimeException{
	
	public DivideByZeroException(String str) {
		super(str);
	}
	public DivideByZeroException() {
		
	}   
	
	
}
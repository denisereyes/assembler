package assembler;

@SuppressWarnings("serial")
public class IllegalInstructionException extends RuntimeException{
	
	public IllegalInstructionException(String str) {
		super(str);
	}
	public IllegalInstructionException() {
	
	}    
}
}

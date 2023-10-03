package assembler;

public class Test {  
	public static void main(String[] args) {
		var inst = new Instruction((byte)9, 0);
		System.out.printf("parity check: %d (%s)\n", inst.opcode, "1001");
		try {
			Instruction.checkParity(inst);
			System.out.println("parity check passed");
		}
		catch(ParityCheckException e){
			System.out.println("parity check failed" + e);
		} 
		
		inst = new Instruction((byte)15, 0);
		System.out.printf("parity check: %d (%s)\n", inst.opcode, "1111");
		try {
			Instruction.checkParity(inst);
			System.out.println("parity check passed");
		}
		catch(ParityCheckException e){
			System.out.println("parity check failed" + e);
		}
		
		inst = new Instruction((byte)5, 0);
		System.out.printf("parity check: %d (%s)\n", inst.opcode, "0011");
		try {
			Instruction.checkParity(inst);
			System.out.println("parity check passed");
		}
		catch(ParityCheckException e){
			System.out.println("parity check failed" + e);
		}
	}
		
}
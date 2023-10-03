package assembler;
//import static java.util.Map.entry; // remove if you only have Java 8
import java.util.Map;
import java.util.TreeMap;

public class Instruction {
	byte opcode;
	int arg;
	public static final Map<String, Integer> OPCODES = new TreeMap<>();
	public static final Map<Integer, String> MNEMONICS = new TreeMap<>();
	static {
		OPCODES.put("NOP", 0);
		OPCODES.put("NOT", 1);
		OPCODES.put("HALT", 2);
		OPCODES.put("LOD", 3);
		OPCODES.put("STO", 4);
		OPCODES.put("ADD", 5);
		OPCODES.put("SUB", 6);
		OPCODES.put("MUL", 7);
		OPCODES.put("DIV", 8);
		OPCODES.put("AND", 9);
		OPCODES.put("JUMP", 10);
		OPCODES.put("JMPZ", 11);
		OPCODES.put("CMPL", 12);
		OPCODES.put("CMPZ", 13);
		
		for(String str : OPCODES.keySet()) {
			MNEMONICS.put(OPCODES.get(str), str);
		}
	}	
	
	public Instruction(byte opcode, int arg) {
		this.opcode = opcode;
		this.arg = arg;
	}
	public static boolean noArgument(Instruction instr) {
		boolean bool = false;
		if(instr.opcode < 24) {
			bool = true;
		}
		return bool;
	
	}
	
	static int numOnes(int k) {
		String str = Integer.toUnsignedString(k,2);
		int count = 0;
		for(int i =0; i < str.length(); i++) {
			if(str.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}
	
	static void checkParity(Instruction instr) {
		int op = numOnes(instr.opcode); //Instrction.numOnes(instr.opcode)
		for(int i = 0; i < op; i++) {
			if(op % 2 != 0) throw new ParityCheckException("This instruction is corrupted.");
		} 
	}   
	
	public String getText() { 
		StringBuilder buff = new StringBuilder();
		buff.append(MNEMONICS.get(opcode/8));
		buff.append("  ");
		int flags = opcode & 6;
		if(flags == 2) buff.append('#');
		else if(flags == 4) buff.append('@');
		else if(flags == 6) buff.append('&');
		buff.append(Integer.toString(arg, 16));
		return buff.toString().toUpperCase();
	}
	
	public String getBinHex() {
		StringBuilder buff = new StringBuilder();
		String str = "00000000" + Integer.toString(opcode, 2);
		buff.append(str.substring(str.length()-8));
		buff.append(" ");
		buff.append(Integer.toHexString(arg));
		return buff.toString().toUpperCase();
	}
	
	public String toString() {
		return "Instruction ["+Integer.toString(opcode,2)+", "+Integer.toString(arg, 16)+"]";
	}
	 
	
}
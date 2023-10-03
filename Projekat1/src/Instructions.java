import java.util.*;
import java.util.function.*;

public class Instructions {

    public static final Map<String, Consumer<String>> UNARY_INSTRUCTIONS = new HashMap<>();
    public static final Map<String, BiConsumer<String, String>> BINARY_INSTRUCTIONS = new HashMap<>();

    static {
        BINARY_INSTRUCTIONS.put("ADD", (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)+getValue(strOperand2)) ) );
        BINARY_INSTRUCTIONS.put("SUB", (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)-getValue(strOperand2)) ) );
        BINARY_INSTRUCTIONS.put("MUL", (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)*getValue(strOperand2)) ) );
        BINARY_INSTRUCTIONS.put("DIV", (strOperand1, strOperand2)-> putValue( strOperand1 , (long)(getValue(strOperand1)/getValue(strOperand2)) ) );

        BINARY_INSTRUCTIONS.put("AND", (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)&getValue(strOperand2)) ) );
        BINARY_INSTRUCTIONS.put("OR",  (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)|getValue(strOperand2)) ) );
        BINARY_INSTRUCTIONS.put("XOR", (strOperand1, strOperand2)-> putValue( strOperand1 , (getValue(strOperand1)^getValue(strOperand2)) ) );

        BINARY_INSTRUCTIONS.put("MOV", (strOperand1, strOperand2)-> putValue( strOperand1 , getValue(strOperand2) ));

        UNARY_INSTRUCTIONS.put("NOT", strOperand -> putValue(strOperand, ~getValue(strOperand)) );
        UNARY_INSTRUCTIONS.put("PRINT", strOperand -> {
                                                         if (Registers.REGISTERS.containsKey(strOperand))
                                                             System.out.println(strOperand+ " = " + Registers.getValue(strOperand));
                                                         else
                                                             try{
                                                                 Long address = Long.valueOf(strOperand);
                                                                 System.out.println("["+address+"] = "+ Addresses.getValue(address));
                                                             }catch (NumberFormatException e){
                                                                 e.printStackTrace();
                                                                 throw new AddressingException("Addressing error : "+strOperand+" neither register neither address.");
                                                             }

        } );
        UNARY_INSTRUCTIONS.put("SCAN", strOperand -> {
                                                        System.out.print("Enter value for "+ strOperand+ " : ");
                                                        Scanner sc = new Scanner(System.in);
                                                        Long numberToBeStored = sc.nextLong();
                                                        putValue(strOperand, numberToBeStored);
        });

    }

    public static long getValue(String operand){
        if (Registers.REGISTERS.containsKey(operand))
            return Registers.getValue(operand);
        try{
            Long address = Long.valueOf(operand);
            return Addresses.getValue(address);
        }catch (NumberFormatException e){
            throw new AddressingException("Addressing error : "+operand+" neither register neither address.");
        }
    }

    public static void putValue(String operand, long newValue){
        if (Registers.REGISTERS.containsKey(operand)) {
            Registers.setValue(operand, newValue);
            return;
        }
        try{
            Long address = Long.valueOf(operand);
            Addresses.setValue(address,(byte)newValue);
        }catch (NumberFormatException e){
            throw new AddressingException("Addressing error : "+operand+" neither register neither address.");
        }
    }

}

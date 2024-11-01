import java.util.*;

public class Coder {

    public static final Queue<Long> OPERATOR_ADDRESS_QUEUE = new LinkedList<>();
    public static long MEMORY_START_ADDRESS = 500_000_000_000L;
    public static long distance = 0L;

    public static void codeOperator(String operator){
        switch (operator){
            case "ADD" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "SUB" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "MUL" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)2);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "DIV" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)3);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "AND" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)4);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "OR"  : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)5);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "XOR" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)6);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "MOV" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)7);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "NOT" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)8);    OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "SCAN"  : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)9);  OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
            case "PRINT" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)10); OPERATOR_ADDRESS_QUEUE.offer(MEMORY_START_ADDRESS + distance); distance++; break;
        }
    }

    public static void codeOperand(String operand){
        switch (operand){
            case "RAX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         return;
            case "RBX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                         distance++;
                         return;
            case "RCX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)2);
                         distance++;
                         return;
            case "RDX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)3);
                         distance++;
                         return;
            case "REX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)4);
                         distance++;
                         return;
            case "RFX" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                         distance++;
                         Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)5);
                         distance++;
                         return;
            case "[RAX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)0);
                           distance++;
                           return;
            case "[RBX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           return;
            case "[RCX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)2);
                           distance++;
                           return;
            case "[RDX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)3);
                           distance++;
                           return;
            case "[REX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)4);
                           distance++;
                           return;
            case "[RFX]" : Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)1);
                           distance++;
                           Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)5);
                           distance++;
                           return;
        }
        if (operand.startsWith("[") && operand.endsWith("]")){

            String address = operand.substring(1,operand.length()-1);
            Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)2);
            distance++;
            byte[] temp = address.getBytes();
            for(byte b : temp){
                Addresses.setValue(MEMORY_START_ADDRESS + distance,b);
                distance++;
            }
            Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)'#');
            distance++;
            return;
        }
        else{

            Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)3);
            distance++;

            byte[] temp = operand.getBytes();
            for(byte b : temp){
                Addresses.setValue(MEMORY_START_ADDRESS + distance,b);
                distance++;
            }
            Addresses.setValue(MEMORY_START_ADDRESS + distance,(byte)'#');
            distance++;
            return;
        }
    }

    public static String decodeOperator(byte operator){
        String result = "";
        switch (operator){
            case 0: result = "ADD"; break;
            case 1: result = "SUB"; break;
            case 2: result = "MUL"; break;
            case 3: result = "DIV"; break;
            case 4: result = "AND"; break;
            case 5: result = "OR"; break;
            case 6: result = "XOR"; break;
            case 7: result = "MOV"; break;
            case 8: result = "NOT"; break;
            case 9: result = "SCAN"; break;
            case 10: result = "PRINT"; break;
        }
        return result;
    }

}

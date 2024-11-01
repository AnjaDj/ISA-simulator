import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ByteCodeGenerator {

    public static boolean debuggingMode = false;
    public static final String DEBUG_KEYWORD = "*";
    public static final String DEBUG_KEYWORD_NEXT = "NEXT";
    public static final String DEBUG_KEYWORD_CONTINUE = "CONTINUE";

    public static void codeLine(String line){

        String operator = line.split(" ")[0];
        String operand1 = line.split(" ")[1].split(",")[0];
        String operand2 = (Instructions.BINARY_INSTRUCTIONS.containsKey(operator)) ? line.split(" ")[1].split(",")[1] : null ;

        Coder.codeOperator(operator);
        Coder.codeOperand(operand1);
        if (operand2 != null) Coder.codeOperand(operand2);
    }

    public static void executeLine(){
        Registers.setValue("RIP",Coder.OPERATOR_ADDRESS_QUEUE.poll());
        String operator = Coder.decodeOperator(Addresses.getValue(Registers.getValue("RIP")));

        int startIndexForSecondOperand = 0;
        String operand1 = "", operand2 = "";
        switch (Addresses.getValue(Registers.getValue("RIP")+1)){
            case 0:
                if      ( Addresses.getValue(Registers.getValue("RIP")+2) == 0 ) operand1 = "RAX";
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 1 )  operand1 = "RBX";
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 2 ) operand1 = "RCX";
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 3 ) operand1 = "RDX";
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 4 ) operand1 = "REX";
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 5 ) operand1 = "RFX";
                startIndexForSecondOperand = 3;
                break;
            case 1:
                if      ( Addresses.getValue(Registers.getValue("RIP")+2) == 0 ) operand1 = Registers.getValue("RAX").toString();
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 1 ) operand1 = Registers.getValue("RBX").toString();
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 2 ) operand1 = Registers.getValue("RCX").toString();
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 3 ) operand1 = Registers.getValue("RDX").toString();
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 4 ) operand1 = Registers.getValue("REX").toString();
                else if ( Addresses.getValue(Registers.getValue("RIP")+2) == 5 ) operand1 = Registers.getValue("RFX").toString();
                startIndexForSecondOperand = 3;
                break;
            case 2:
                List<Byte> bytes = new ArrayList<>();
                int i = 2;

                while ( Addresses.getValue(Registers.getValue("RIP")+i) != (byte)'#' ){
                    bytes.add(Addresses.getValue(Registers.getValue("RIP")+i));
                    i++;
                }

                byte[] array = new byte[bytes.size()];
                for(int j = 0; j < array.length; j++) array[j] = bytes.get(j);

                operand1 = new String(array);
                startIndexForSecondOperand = i+1;
                break;
        }
        if (Instructions.BINARY_INSTRUCTIONS.containsKey(operator)){
            switch (Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand)){
                case 0:
                    if      ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 0 ) operand2 = "RAX";
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 1 )  operand2 = "RBX";
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 2 ) operand2 = "RCX";
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 3 ) operand2 = "RDX";
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 4 ) operand2 = "REX";
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 5 ) operand2 = "RFX";
                    break;
                case 1:
                    if      ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 0 ) operand2 = Registers.getValue("RAX").toString();
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 1 ) operand2 = Registers.getValue("RBX").toString();
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 2 ) operand2 = Registers.getValue("RCX").toString();
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 3 ) operand2 = Registers.getValue("RDX").toString();
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 4 ) operand2 = Registers.getValue("REX").toString();
                    else if ( Addresses.getValue(Registers.getValue("RIP")+startIndexForSecondOperand+1) == 5 ) operand2 = Registers.getValue("RFX").toString();
                    break;
                case 2:
                    List<Byte> bytes = new ArrayList<>();
                    int i = startIndexForSecondOperand+1;

                    while ( Addresses.getValue(Registers.getValue("RIP")+i) != (byte)'#' ){
                        bytes.add(Addresses.getValue(Registers.getValue("RIP")+i));
                        i++;
                    }

                    byte[] array = new byte[bytes.size()];
                    for(int j = 0; j < array.length; j++) array[j] = bytes.get(j);

                    operand2 = new String(array);
                    break;
                case 3:
                    List<Byte> bytes2 = new ArrayList<>();
                    int i2 = startIndexForSecondOperand+1;

                    while ( Addresses.getValue(Registers.getValue("RIP")+i2) != (byte)'#' ){
                        bytes2.add(Addresses.getValue(Registers.getValue("RIP")+i2));
                        i2++;
                    }

                    byte[] array2 = new byte[bytes2.size()];
                    for(int j = 0; j < array2.length; j++) array2[j] = bytes2.get(j);

                    String number = new String(array2);

                    Registers.setValue("RGX", Long.valueOf(number));
                    operand2 = "RGX";
                    break;
            }
        }

        if (Instructions.UNARY_INSTRUCTIONS.containsKey(operator)){
            Instructions.UNARY_INSTRUCTIONS.get(operator).accept(operand1);
            return;
        }
        if (Instructions.BINARY_INSTRUCTIONS.containsKey(operator)){
            Instructions.BINARY_INSTRUCTIONS.get(operator).accept(operand1,operand2);
            return;
        }
    }

    public static void debugger(){
        Registers.print();
        System.out.println("Next step? N/C/[?] -> ");   Scanner sc = new Scanner(System.in);    String temp = sc.nextLine();
        while(!DEBUG_KEYWORD_NEXT.equals(temp) && !DEBUG_KEYWORD_CONTINUE.equals(temp)){

            System.out.println("stored value in "+temp+" = "+ Instructions.getValue(temp) );
            temp = sc.nextLine();
        }
        if (DEBUG_KEYWORD_CONTINUE.equals(temp))
            debuggingMode = false;
    }

}

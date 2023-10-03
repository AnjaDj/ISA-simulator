import java.util.*;

public class Registers {

    public static final Map<String, Long> REGISTERS = new HashMap<>();
    static {
        REGISTERS.put("RAX", 0L);
        REGISTERS.put("RBX", 0L);
        REGISTERS.put("RCX", 0L);
        REGISTERS.put("RDX", 0L);
        REGISTERS.put("REX", 0L);
        REGISTERS.put("RFX", 0L);
        REGISTERS.put("RGX", 0L);
        REGISTERS.put("RIP", 0L);

    }
    public static void print() {
        System.out.println("RAX = "+REGISTERS.get("RAX"));
        System.out.println("RBX = "+REGISTERS.get("RBX"));
        System.out.println("RCX = "+REGISTERS.get("RCX"));
        System.out.println("RDX = "+REGISTERS.get("RDX"));
        System.out.println("REX = "+REGISTERS.get("REX"));
        System.out.println("RFX = "+REGISTERS.get("RFX"));
        System.out.println("RIP = "+REGISTERS.get("RIP"));
    }

    public static Long getValue(String register){
        if (REGISTERS.containsKey(register))
            return REGISTERS.get(register);
        else
            throw new AddressingException("ISA Simulator does not define the register "+register);
    }

    public static void setValue(String register, Long value){
        if (REGISTERS.containsKey(register))
            REGISTERS.replace(register,value);
        else
            throw new AddressingException("ISA Simulator does not define the register "+register);
    }
}

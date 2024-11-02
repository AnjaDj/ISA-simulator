public class Analysis {
    public static boolean lineAnalysis(String line){

        try{
            if (ByteCodeGenerator.DEBUG_KEYWORD.equals(line)) return true;
            if (line.isBlank() || line.isEmpty()) return false;

                String[] parts1 = line.split(" ");
                if (parts1.length != 2) return false;
                if (!Instructions.UNARY_INSTRUCTIONS.containsKey(parts1[0]) && !Instructions.BINARY_INSTRUCTIONS.containsKey(parts1[0])) return false;

                String operand1 = parts1[1].split(",")[0];
                if (!Registers.REGISTERS.containsKey(operand1)){
                    if (operand1.startsWith("[") && operand1.endsWith("]")){
                        if (Registers.REGISTERS.containsKey( operand1.substring(1,operand1.length()-1) ) == false)
                            try{
                                Long address = Long.valueOf(operand1.substring(1,operand1.length()-1));
                            }catch (NumberFormatException e){
                                return false;
                            }

                    }else
                        return false;
                }

                if (Instructions.UNARY_INSTRUCTIONS.containsKey(parts1[0]) && parts1[1].split(",").length != 1) return false;
                if (Instructions.BINARY_INSTRUCTIONS.containsKey(parts1[0]) && parts1[1].split(",").length != 2) return false;

            if (Instructions.BINARY_INSTRUCTIONS.containsKey(parts1[0])) {
                String operand2 = parts1[1].split(",")[1];
                if (!Registers.REGISTERS.containsKey(operand2)) {
                    if (operand2.startsWith("[") && operand2.endsWith("]")) {
                        if (Registers.REGISTERS.containsKey(operand2.substring(1, operand2.length() - 1)) == false)
                            try {
                                Long address = Long.valueOf(operand2.substring(1, operand2.length() - 1));
                            } catch (NumberFormatException e) {
                                return false;
                            }

                    } else
                        try {
                            Long address = Long.valueOf(operand2);
                        } catch (NumberFormatException e) {
                            return false;
                        }
                }
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

    }
}

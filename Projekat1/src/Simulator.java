import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Simulator {

    public static File SOURCE_FILE ;
    public static final Queue<String> SOURCE_LINES = new LinkedList<>();

    public static void sourceFileSetter(String source){
        try {
            SOURCE_FILE = new File(source);
            List<String> tempList = Files.readAllLines(SOURCE_FILE.toPath());
            for(String string : tempList)
                SOURCE_LINES.offer(string);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void execute(){
        int currentLine = 0;
        while(!SOURCE_LINES.isEmpty()){

            String line = SOURCE_LINES.poll();
            currentLine++;
            if (!Analysis.lineAnalysis(line))
                throw new AnalysingException("Error in line "+currentLine+" while analysing...");

            if (ByteCodeGenerator.DEBUG_KEYWORD.equals(line) || ByteCodeGenerator.debuggingMode) {
                ByteCodeGenerator.debuggingMode = true;
                ByteCodeGenerator.debugger();
            }
            if (!ByteCodeGenerator.DEBUG_KEYWORD.equals(line)) {
                ByteCodeGenerator.codeLine(line);
                ByteCodeGenerator.executeLine();
            }
        }
    }

    public static void main(String[] args) {
       try{
           sourceFileSetter(args[0]);
           execute();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}

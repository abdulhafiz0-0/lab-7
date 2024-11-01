import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    enum CommandType {
        A_COMMAND,
        C_COMMAND,
        L_COMMAND
    }

    private List<String> commands;
    private int pointer;
    private int LCommandCounter;
    private String thisCommand;

    public Parser() {
    }

    public Parser(String filePath) {
        LCommandCounter = 0;
        initPointer();
        commands = new ArrayList();
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            line = in.readLine();
            while(line!=null){
                line = line.replaceAll("\\s", "").trim();

                if(line.equals("") || line.startsWith("//")){
                    line=in.readLine();
                    continue;
                }

                String[] LineSplit=line.split("//");
                commands.add(LineSplit[0]);
                line=in.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(commands);
    }

    public Boolean hasMoreCommands() {
        return pointer < commands.size() - 1;
    }

    public void addLCommand() {
        LCommandCounter++;
    }

    public int getLCommandCounter() {
        return LCommandCounter;
    }

    public void advance() {
        pointer++;
        this.thisCommand = commands.get(pointer);
    }

    public CommandType commandType() {
        if(thisCommand.startsWith("(")) return CommandType.L_COMMAND;
        if(thisCommand.startsWith("@")) return CommandType.A_COMMAND;
        return CommandType.C_COMMAND;
    }

    public String symbol() {
        try {
            if(commandType()==CommandType.A_COMMAND) return thisCommand.substring(1);
            if(commandType()==CommandType.L_COMMAND) return thisCommand.substring(1, thisCommand.length()-1);
            throw new RuntimeException("line "+ pointer +" is not a valid command");
        } catch (RuntimeException r) {
            System.err.println(r.getMessage());
            return null;
        }
    }

    public static boolean isVar(String str) {
        Pattern pattern = Pattern.compile("^[a-z][a-z0-9_$.]*$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str).matches();
    }

    public String dest() {
        try {
            if(commandType()!=CommandType.C_COMMAND) throw new RuntimeException("line "+ pointer +" is not a valid command (not a C command)");

            if(thisCommand.contains("=")) return thisCommand.split("=")[0];
            return "null";
        } catch (RuntimeException r) {
            System.err.println(r.getMessage());
            return null;
        }
    }

    public String comp() {
        try {
           if(commandType()!=CommandType.C_COMMAND) throw new RuntimeException("line "+ pointer +" is not a valid command (not a C command)");
           String nJump=this.thisCommand.split(";")[0];
           if(thisCommand.contains("=")) return nJump.split("=")[1];
           return nJump;
        } catch (RuntimeException r) {
            System.err.println(r.getMessage());
            return null;
        }
    }

    public String jump() {
        try {
            if(commandType()!=CommandType.C_COMMAND) throw new RuntimeException("line "+ pointer +" is not a valid command (not a C command)");
            if(thisCommand.contains(";")) return thisCommand.split(";")[1];
            return "null";
        } catch (RuntimeException r) {
            System.err.println(r.getMessage());
            return null;
        }
    }


    public void initPointer() {
        pointer = -1;
    }

    public int getPointer() {
        return pointer;
    }
}

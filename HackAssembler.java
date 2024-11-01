import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HackAssembler {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser(args[0]);
            Code code = new Code();
            SymbolTable symbolTable = new SymbolTable();
            BufferedWriter out = new BufferedWriter(new FileWriter(args[0].replace(".asm", ".hack")));

            while(parser.hasMoreCommands()){
                parser.advance();
                if(parser.commandType()== Parser.CommandType.L_COMMAND){
                    symbolTable.addEntry(parser.symbol(), String.valueOf(parser.getPointer()- parser.getLCommandCounter()));
                    parser.addLCommand();
                }

            }
            parser.initPointer();
            while(parser.hasMoreCommands()){
                parser.advance();
                switch(parser.commandType()){
                    case A_COMMAND:
                        String newA=parser.symbol();
                        if(parser.isVar(newA)){
                            if(!symbolTable.contains(newA)) symbolTable.addVarEntry(newA);
                            newA=Code.getAInstructionBinaryString(symbolTable.getAddress(newA));
                        }
                        else newA=Code.getAInstructionBinaryString(newA);

                        out.write(newA);
                        break;
                    case C_COMMAND:
                        String dest=code.dest(parser.dest());
                        String comp=code.comp(parser.comp());
                        String jump=code.jump(parser.jump());
                        out.write("111"+comp+dest+jump);
                        break;
                    case L_COMMAND:
                        continue;
                    default:
                        throw new RuntimeException("Unrecognized command type. Line: "+parser.getPointer());

                }
                out.newLine();
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeWriter {
    private String filename;
    private PrintWriter writeOut;
    private int labelNumber;

    private void out(String line) {
        writeOut.println(line);
    }

    private String nextLabel() {
        return String.valueOf(labelNumber++);
    }

    public CodeWriter(File output) throws FileNotFoundException {
        try {
            writeOut = new PrintWriter(output);
            labelNumber = 0;
        } catch (FileNotFoundException fnf) {
            throw new FileNotFoundException("File not found: " + fnf.getMessage());
        }
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public void writeInit() {
        // TODO: set SP to 256

        // uncomment in project 8
        // writeCall("Sys.init", 0);
    }

    public void writeArithmetic(String operation) {
        out("// " + operation);

        switch (operation.toLowerCase()) {
            case "add":
            case "sub":
            case "and":
            case "or":
                // TODO: implement arithmetic operations
                break;
            case "eq":
            case "lt":
            case "gt":
                // TODO: implement comparison operations
                break;
            case "not":
                // TODO: implement not operation
                break;
            case "neg":
                // TODO: implement negation operation
                break;
        }
    }

    public void writePushPop(CommandType command, String segment, int index) {
        out("// " + command + " " + segment + " " + index);
        if (command == CommandType.C_PUSH) {
            // save value to D
            switch (segment.toLowerCase()) {
                case "pointer":
                    // TODO: save pointer value to D
                    break;
                case "static":
                    // TODO: save static value to D
                    break;
                case "constant":
                    // TODO: save constant value to D
                    break;
                case "temp":
                    // TODO: save temp value to D
                    break;
                default:
                    // TODO: save value from segment to D
                    break;
            }

            // push D to stack
            finishPush();
        } else if (command == CommandType.C_POP) {
            // save address to D
            switch (segment.toLowerCase()) {
                case "pointer":
                    // TODO: save pointer address to D
                    break;
                case "static":
                    // TODO: save static address to D
                    break;
                case "temp":
                    // TODO: save temp address to D
                    break;
                default:
                    // TODO: save address from segment to D
                    break;
            }
            // pop to an address pointed by D
            // TODO: pop to address pointed by D
        }
    }

    private void finishPush() {
        // TODO: push D to stack
    }

    public void writeLabel(String label) {
        out("// C_LABEL " + label);
        // TODO: write label
    }

    public void writeGoto(String label) {
        out("// C_GOTO " + label);
        // TODO: write goto
    }

    public void writeIf(String label) {
        out("// C_IF " + label);
        // TODO: write if
    }

    public void writeCall(String functionName, int numArgs) {
        String label = nextLabel();

        out("// call " + functionName + " " + numArgs);
        // R13 = SP
        // TODO: store SP in R13

        // push return address
        // TODO: push return address

        // push LCL
        // TODO: push LCL

        // push ARG
        // TODO: push ARG

        // push THIS
        // TODO: push THIS

        // push THAT
        // TODO: push THAT

        // ARG = R13 - numArgs
        // TODO: set ARG to R13 - numArgs, where numArgs is the number of arguments and R13 is the current SP

        // LCL = SP
        // TODO: set LCL to SP

        // goto functionName
        // TODO: goto functionName

        // declare return address label
        // TODO: declare return address label
    }

    public void writeReturn() {
        out("// return");

        // store return address in R13 = LCL - 5
        // TODO: store return address in R13

        // store return value *(SP-1) in *ARG
        // TODO: store return value in *ARG

        // restore SP = ARG + 1
        // TODO: restore SP

        // restore THAT = *(LCL - 1); LCL--
        // TODO: restore THAT

        // restore THIS = *(LCL - 1); LCL--
        // TODO: restore THIS

        // restore ARG = *(LCL - 1); LCL--
        // TODO: restore ARG

        // restore LCL=*(LCL - 1)
        // TODO: restore LCL

        // Jump to return address stored in R13
        // TODO: jump to return address
    }

    public void writeFunction(String functionName, int numLocals) {
        writeOut.println("// function " + functionName + numLocals);

        // declare label for function entry
        // TODO: declare label for function entry

        // initialize local variables to 0
        // TODO: initialize local variables to 0
    }

    private String getLabel(String segment) {
        switch (segment.toLowerCase()) {
            case "local":
                return "@LCL";
            case "argument":
                return "@ARG";
            case "this":
                return "@THIS";
            case "that":
                return "@THAT";
            default:
                return null;
        }
    }

    public void close() {
        writeOut.close();
    }
}

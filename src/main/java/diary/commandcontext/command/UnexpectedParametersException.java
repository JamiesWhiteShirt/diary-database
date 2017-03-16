package diary.commandcontext.command;

public class UnexpectedParametersException extends CommandException {
    public UnexpectedParametersException() {
        super("Unexpected parameters");
    }
}

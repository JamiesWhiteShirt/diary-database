package diary.lib.context.command;

public class UnexpectedParametersException extends CommandException {
    public UnexpectedParametersException() {
        super("Unexpected parameters");
    }
}

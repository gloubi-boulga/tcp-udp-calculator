package tcp;

/**
 * @author guho
 */
public enum Command {

    STOP("stop"),
    CANCEL_CALCULUS("cancel"),
    RESTART_CALCULUS("restart");

    private final String action;

    Command(String action) {
        this.action = action;
    }

    public static Command getCommand(final String input) {
        for (Command enumElement : Command.values()) {
            if (enumElement.action.equals(input)) {
                return enumElement;
            }
        }
        return null;
    }
}

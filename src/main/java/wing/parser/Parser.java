package wing.parser;

import wing.command.AddDeadlineCommand;
import wing.command.AddEventCommand;
import wing.command.AddTodoCommand;
import wing.command.Command;
import wing.command.DeleteCommand;
import wing.command.ExitCommand;
import wing.command.FindCommand;
import wing.command.ListCommand;
import wing.command.MarkCommand;
import wing.command.UnmarkCommand;
import wing.exception.WingException;

public class Parser {

    public static Command parse(String userInput) throws WingException {
        if (userInput == null) {
            throw new WingException("Bruh. You gave me nothing.");
        }

        int firstSpaceIndex = userInput.indexOf(" ");
        String firstWord = (firstSpaceIndex > -1) ? userInput.substring(0, firstSpaceIndex) : userInput;

        return switch (firstWord) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(userInput);
            case "unmark" -> new UnmarkCommand(userInput);
            case "todo" -> new AddTodoCommand(userInput);
            case "deadline" -> new AddDeadlineCommand(userInput);
            case "event" -> new AddEventCommand(userInput);
            case "delete" -> new DeleteCommand(userInput);
            case "find" -> new FindCommand(userInput);
            default -> throw new WingException("What talking?");
        };

    }
}

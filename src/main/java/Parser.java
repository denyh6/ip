public class Parser {

    public static String parse(String userInput) throws WingException {
        if (userInput == null) {
            throw new WingException("Bruh. You gave me nothing.");
        }
        int firstSpaceIndex = userInput.indexOf(" ");
        return (firstSpaceIndex > -1) ? userInput.substring(0, firstSpaceIndex) : userInput;
    }
}

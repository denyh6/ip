import java.util.Scanner;

public class Wing {
    public static void main(String[] args) {
        String logo
                = " __  __  __  ____  __ _  ____   \n"
                + "\\   /   /  /|_  _||  | ||  __|_ \n"
                + " \\   /\\   /  _||_ | || || |__  |\n"
                + "  \\_/  \\_/  |____||_|__||______|\n";

        String StartMsg
                = """
                ____________________________________________________________
                Hello! I'm Wing
                What can I do for you?
                ____________________________________________________________""";

        System.out.println("Hello from\n" + logo + StartMsg);
        Scanner in = new Scanner(System.in);
        boolean isTalking = true;
        while (isTalking) {
            String line = in.nextLine();
            if (line.equals("bye")) {
                System.out.println("____________________________________________________________\n"
                        + "Ok. Bye." + "\n____________________________________________________________");
                isTalking = false;
                break;
            }
            System.out.println("____________________________________________________________\n"
                    + line + "\n____________________________________________________________");
        }
    }
}

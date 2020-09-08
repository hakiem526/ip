import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Duke {

    public static String HORIZONTAL_LINE =
            "_________________________________________________________________________________________";
    public static ArrayList<Task> LIST = new ArrayList<>();
    public static String[] COMMANDS = {"todo", "deadline", "event", "list", "done", "bye"};

    public static void main(String[] args) {
        startUpMessage();
        programLoop();

    }

    // Prints start up message upon running
    private static void startUpMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(Duke.HORIZONTAL_LINE);
        System.out.println("Hello I'm Duke\nWhat can I do for you?");
        System.out.println(Duke.HORIZONTAL_LINE);
    }

    // Main program functionality
    private static void programLoop() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String input = sc.nextLine();
            String[] command = input.split(" ");
            System.out.println(Duke.HORIZONTAL_LINE);

            if (command[0].toLowerCase().equals(Duke.COMMANDS[5])) {
                // exit program if user inputs "bye"
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (command[0].toLowerCase().equals(Duke.COMMANDS[3])) {
                // prints all elements of LIST if not empty
                if (Duke.LIST.size() == 0) {
                    continue;
                } else {
                    printList();
                }

            } else if (command[0].toLowerCase().equals(Duke.COMMANDS[4])) {
                // marks task at specified index as done
                try {
                    int index = Integer.valueOf(command[1]);
                    Task current = Duke.LIST.get(index - 1);
                    current.completeTask();
                    System.out.println("Nice! I have marked this task as done:\n\t" + current.printTask());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Please input a valid index");
                }
            } else if (Arrays.asList(Duke.COMMANDS).contains(command[0].toLowerCase())) {
                // add Task to LIST
                try {
                    addTask(input);
                } catch (InvalidInputException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please enter valid input");
                }
            } else {
                // invalid inputs
                System.out.println("Sorry, I don't understand! Please enter valid input.");
            }

            System.out.println(Duke.HORIZONTAL_LINE);
        } // end while loop
    }

    // Adds Task to LIST. Checks inputs and throws exceptions for invalid inputs
    public static void addTask(String input) throws InvalidInputException {
        StringBuilder sb = new StringBuilder();
        String[] splitSpace = input.split(" ");
        Task task;

        if (splitSpace[0].toLowerCase().equals(Duke.COMMANDS[0])) {
            // case: todo
            if (splitSpace.length == 1) {
                // throws exception if input is "todo" (missing task name)
                throw new InvalidInputException("Todo command incomplete");
            }

            for (String str : splitSpace) {
                if (str.toLowerCase().equals(Duke.COMMANDS[0])) {
                    continue;
                } else {
                    sb.append(str + " ");
                }
            } // end for loop
            task = new ToDo(sb.toString());
        } else if (splitSpace[0].toLowerCase().equals(Duke.COMMANDS[1])) {
            // case: deadline
            if (!input.contains(" /by")) {
                // throws exception if invalid input format: does not contain "/by"
                throw new InvalidInputException("Deadline command is missing \"/by\" keyword");
            }

            if (input.split(" /")[0].toLowerCase().equals(Duke.COMMANDS[1])) {
                // throws exception if invalid input format: "deadline /by taskDeadline"
                throw new InvalidInputException("Missing deadline task description");
            }

            if (input.split(" ")[(input.split(" ").length - 1)].equals("/by")) {
                // throws exception if invalid input format: "deadline taskName /by"
                throw new InvalidInputException("Missing task deadline");
            }

            String name = input.split(" /")[0].substring(9);
            String deadline = input.split("/")[1].substring(3);
            task = new Deadline(name, deadline);
        } else {
            // case: event
            if (!input.contains(" /at")) {
                // throws exception if invalid input format: does not contain "/at"
                throw new InvalidInputException("Event command is missing \"/at\" keyword");
            }

            if (input.split(" /")[0].toLowerCase().equals(Duke.COMMANDS[2])) {
                // throws exception if invalid input format: "event /at eventTime"
                throw new InvalidInputException("Missing event task description");
            }

            if (input.split(" ")[(input.split(" ").length - 1)].equals("/at")) {
                // throws exception if invalid input format: "event eventName /at"
                throw new InvalidInputException("Missing event date");
            }

            String name = input.split(" /")[0].substring(6);
            String time = input.split("/")[1].substring(3);
            task = new Event(name, time);
        }

        Duke.LIST.add(task);
        System.out.println("Got it! Task added to list.");
        System.out.println("\t" + task.printTask());
        System.out.println("Now you have " + Duke.LIST.size() + " tasks in your list.");
    }

    // Prints all Tasks in an ordered list
    public static void printList() {
        System.out.println("Here are your tasks: ");
        for (int i = 0; i < Duke.LIST.size(); i++) {
            System.out.println(i + 1 + ". " + Duke.LIST.get(i).printTask());
        }
    }
}
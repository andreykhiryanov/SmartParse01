package com.generation.brain.smartparse01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.File;
import java.util.List;

public class SmartParse {

    public static void main(String[] args) {

        // Getting TextFileHandler from Spring context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        TextFileHandler textFileHandler = context.getBean(TextFileHandler.class);

        System.out.println("-----");
        System.out.println("Hello and welcome to the SmartParse app!");

        // The whole program works in an endless cycle
        // so that it is possible to interrupt its work at any convenient moment.

        // The program will stop, if "run" will be false.
        boolean run = true;

        while (run) {

            System.out.println("-----");

            // Getting the file.
            File file = null;
            while (true) {
                // Inputting path to the file.
                System.out.println("Please, enter the path to your text file on your hard drive,\nor enter \"0\" to exit the program."); // e.g. C:\bookpart.log
                String path = textFileHandler.getInput();

                // Aborting inner cycle, if user want to exit.
                if (path.equals("0")) break;

                // Repeats until a file is found.
                file = textFileHandler.getFile(path);
                if (file != null) {
                    break;
                }
            }

            // Aborting main cycle, if user want to exit.
            if (file==null) {
                System.out.println("Goodbuy!");
                break;
            }

            // Processing the file. Separating of the file into lines, each line individually copied to the list.
            List<String> lines = textFileHandler.fileToArrayList(file);

            // If the file is empty, abort the iteration.
            if (lines.size() == 0) {
                System.out.println("The file is empty!");
                continue;
            }

            // Inner cycle to work with the file with any way.
            while (true) {
                System.out.println("-----");
                // Asking, what to do.
                System.out.println("What do you want to do?");
                System.out.println("Enter \"1\", if you want to see a list of words and how often they appear in the text.");
                System.out.println("Enter \"2\", if you want to see how often a particular word appears in the text.");
                System.out.println("Enter \"3\", if you want to choose a new file.");
                System.out.println("Enter \"0\", if you want to exit the program.");

                // Getting the choice. If user enters String instead int, interrupt this cycle.
                int choice = 0;
                try {
                    choice = Integer.parseInt(textFileHandler.getInput());
                }
                catch (NumberFormatException e) {
                    System.out.println("Wrong choice! Try again...");
                    continue;
                }

                // Processing the choice.
                if (choice == 0) {
                    // Interrupting the program.
                    System.out.println("Goodbuy.");
                    run = false;
                    break;
                }
                else if (choice == 1) {
                    // Processing all lines, souting the result.
                    textFileHandler.processAllLines(lines);
                }
                else if (choice == 2) {
                    // Inputting search word.
                    System.out.println("You are searching:"); // "объект"
                    String searchWord = textFileHandler.getInput();
                    textFileHandler.countQuantity(lines, searchWord);
                }
                else if (choice == 3) {
                    break;
                }
                else System.out.println("Wrong choice! Try again...");
            }

            // The end of the main iteration.
        }

    }

}
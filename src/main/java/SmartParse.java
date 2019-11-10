import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class SmartParse {

    public static void main(String[] args) {

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
                // Inputting path to the log file.
                System.out.println("Please, enter the path to your file on your hard drive,\nor enter \"0\" to exit the program."); // e.g. C:\bookpart.log
                String path = getInput(false);

                // Aborting inner cycle, if user want to exit.
                if (path.equals("0")) break;

                // Repeats until a file is found.
                file = getFile(path);
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
            List<String> lines = fileToArrayList(file);

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
                    choice = Integer.parseInt(getInput(false));
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
                    processAllLines(lines);
                }
                else if (choice == 2) {
                    // Inputting search word.
                    System.out.println("You are searching:"); // "объект"
                    String searchWord = getInput(false);
                    countQuantity(lines, searchWord);
                }
                else if (choice == 3) {
                    break;
                }
                else System.out.println("Wrong choice! Try again...");
            }

            // The end of the main iteration.
        }
    }

    // Just comment.

    public static String getInput(boolean readyToClose) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input;
        while (true) {
            try {
                input = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Input error! Try again!!!!!...");
            }
        }

        // Closing stream.
        if (readyToClose) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return input;
    }

    // Just comment.

    public static File getFile(String path) {

        File file = new File(path);

        if (file.exists() & file.canRead()) {
            System.out.println("File was found.");
            return file;
        }
        else {
            System.out.println("File does not exists! Try again.");
            return null;
        }

    }

    // Just comment.

    public static List<String> fileToArrayList(File file) {

        // Creating new list.
        List<String> lines = new ArrayList<>();

        // Reading our file.
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            fileReader = null;
        }
        BufferedReader reader = new BufferedReader(fileReader);

        // Dividing the text in the file into lines, each line is written separately to the list.
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        // Removing everything except spaces & letters in each line.
        for (int i = 0; i < lines.size(); i++) {
            String temp = lines.get(i);
            temp = temp.replaceAll("[^\\p{L} \n]+", "");
            lines.set(i, temp);
        }

        // Closing the stream.
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    // Just comment.

    public static void processAllLines(List<String> lines) {

        // Creating HashMap<Word, QuantityInText>.
        Map<String, Integer> strings = new HashMap<>();

        // Processing lines in the list.
        for (String line : lines) {

            // Splitting every line on separate words.
            for (String word : line.split(" ")) {

                // All words in lower case.
                if (!word.equals("")) {
                    String wordInLowerCase = word.toLowerCase();
                    // Forming the map.
                    if (strings.containsKey(wordInLowerCase)) {
                        int i = strings.get(wordInLowerCase);
                        strings.put(wordInLowerCase, ++i);
                    }
                    else strings.put(wordInLowerCase, 1);
                }
            }
        }

        // Sorting and souting the result.
        sortAndSout(strings);

    }

    // Just comment.

    public static void sortAndSout(Map<String, Integer> strings) {

        // Sorting.
        Map<String, Integer> result = new LinkedHashMap<>();
        Stream<Map.Entry<String, Integer>> stream = strings.entrySet().stream();
        stream.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(),e.getValue()));

        // Souting.
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            System.out.print("The word \"" + entry.getKey() + "\" appears in the text " + entry.getValue());
            if (entry.getValue() == 1) System.out.println(" time.");
            else System.out.println(" times.");
        }

    }

    // Just comment.

    public static void countQuantity(List<String> lines, String searchWord) {

        int count = 0;

        for (String line : lines) {

            // Splitting every line on separate words.
            for (String word : line.split(" ")) {

                // All words in lower case.
                String wordInLowerCase = word.toLowerCase();
                // Forming the map.

                if (wordInLowerCase.equals(searchWord) & !wordInLowerCase.equals("")) count++;
            }
        }

        if (count == 1) System.out.println("The word \"" + searchWord + "\" occurs " + count + " time in the text.");
        else System.out.println("The word \"" + searchWord + "\" occurs " + count + " times in the text.");
    }

}
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Start {

    private static int countOfSearchWord = 0;

    public static void main(String[] args) {

        // Inputting path to the log file.
        System.out.println("Enter the path to the file:"); // C:\bookpart.log
        String path = getInput();

        // Inputting search word.
        System.out.println("You are searching:"); // "объект"
        String searchWord = getInput();

        // Processing the file. Separating of the file into lines, each line individually copied to the list.
        List<String> lines = fileToArrayList(getFile(path));

        // Processing lines in the list.
        for (String line : lines) {

            if (line.contains(searchWord)) {



            }

        }

        // Souting the result.
        System.out.println("Result!");

    }



    public static String getInput() {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String input;
        while (true) {
            try {
                input = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Input error! Try again...");
            }
        }

        // Closing streams.
        try {
            reader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }



    public static File getFile(String path) {

        File file = new File(path);

        if (file.exists() & file.canRead()) {
            System.out.println("File exists.");
            return file;
        }
        else {
            System.out.println("File does not exists! Try again.");
            return null;
        }

    }



    public static List<String> fileToArrayList(File file) {

        List<String> lines = new ArrayList<>();

        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            fileReader = null;
        }
        BufferedReader reader = new BufferedReader(fileReader);

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return lines;
    }



    public static int getCount(String line) {
        return 0;
    }

}
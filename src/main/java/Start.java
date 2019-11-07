import java.io.*;

public class Start {

    private static String searchWord = "";
    private static int countOfSearchWord = 0;

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();

        // Inputting path to our log file.

        String path = "";
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter path to your file:"); // C:\today.log

        File file;
        while (true) {
            try {
                path = reader1.readLine();
            } catch (IOException e) {
                System.out.println("Input error! Try again.");
                continue;
            }

            file = new File(path);
            if (file.exists()) {
                System.out.println("File exists.");
                break;
            }
            else System.out.println("File does not exists! Try again.");
        }

        BufferedReader reader2 = null;

        try {
            reader2 = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

        // Inputting search word.

        System.out.println("You are searching: ");
        while (true) {
            try {
                searchWord = reader1.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Input error! Try again.");
            }
        }

        // Processing the file. Counting quantity of keywords.

        String s = "";
        try {
            while ((s = reader2.readLine()) != null) {
//                System.out.println(s);
                if (s.toLowerCase().equals(searchWord.toLowerCase())) countOfSearchWord++;
            }
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader2.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        // Souting

        System.out.println("The word " + searchWord + " appears in " + file.getName() + " " + countOfSearchWord + " times!");

    }

}
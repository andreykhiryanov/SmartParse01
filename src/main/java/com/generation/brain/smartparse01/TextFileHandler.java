package com.generation.brain.smartparse01;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class TextFileHandler {

    @Value("${textFileHandler.version}")
    private double version;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @PreDestroy
    public void destroy() {
        // Closing stream.
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInput() {

        String input;
        while (true) {
            try {
                input = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Input error! Try again...");
            }
        }

        return input;
    }

    public File getFile(String path) {

        File file = new File(path);

        if (file.exists() & file.canRead()) {
            System.out.println("The file was found.");
            return file;
        }
        else {
            System.out.println("The file was not found! Try again.");
            return null;
        }

    }

    public List<String> fileToArrayList(File file) {

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

    public void processAllLines(List<String> lines) {

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

    public void sortAndSout(Map<String, Integer> strings) {

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

    public void countQuantity(List<String> lines, String searchWord) {

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
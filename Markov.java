// Devaansh Mann
// Project - Markov.java
// May 7, 2023

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import tools.Controls;

public class Markov {

    public static void main(String[] args) {
        
        Markov M = new Markov("/Users/devaanshmann/Desktop/MarkovAssignment/Markov.txt");

        M.Run();
    }
    
    public int splittingString(String[] arr, String str)
    {

        int count = 0;
        int n = 0;
        Scanner sc = new Scanner(str);
        while (sc.hasNext()) {
            arr[n] = sc.next();
            count++;
            n++;                // i did n++ just to avoid error: outside the array bounds
        }
        return count;
    }

    private String routeOfFile;

    public Markov(String routeOfFile) 
    {
        this.routeOfFile = routeOfFile;
    }

    public void Run() {

        Controls.cls();
        
        HashMap<String, ArrayList<String>> mrkvhash = new HashMap<>();
        
        try 
        {
            File file = new File(routeOfFile);              // takaing a file as an input through "main"

            Scanner input = new Scanner(file);
            
            String row;
            
            String[] arr = new String[1000];
            
            int rowSize;
            
            while (input.hasNextLine())
            {
                row = input.nextLine();
                rowSize = splittingString(arr, row);
                String firstWord, secondWord;
                
                for(int i = 0 ; i < rowSize-1 ; i++)
                {
                    firstWord = arr[i];
                    secondWord = arr[i+1];
                    if(i==0)
                    {
                        if (!mrkvhash.containsKey("__$")) {
                            mrkvhash.put("__$", new ArrayList<String>());
                        }
                        mrkvhash.get("__$").add(firstWord);
                    }
                    ArrayList<String> str = mrkvhash.get(firstWord);
                    if(str == null)
                    {
                        ArrayList<String> s = new ArrayList<String>();
                        s.add(secondWord);
                        mrkvhash.put(firstWord, s);
                    }
                    else
                    {
                        str.add(secondWord);
                    }
                    if (secondWord.contains(".") || secondWord.contains("!") || secondWord.contains("?"))
                    {
                        break;
                    }
                }
            }
            input.close();
        } 

        catch (FileNotFoundException e) 
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        
        // this prints the contents of the map
        for (String key : mrkvhash.keySet()) 
        {
            System.out.print(key + ": ");
            for (String value : mrkvhash.get(key)) 
            {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < 10; i++) 
        {
            String generatedSentence = getSentence(mrkvhash);
            System.out.println( "-->  " + generatedSentence + "\n");
        }    
    }

    public String getSentence(HashMap<String, ArrayList<String>> hash) 
    {
            ArrayList<String> words = new ArrayList<>();
            String firstWord = "__$";
            String nextWord;
            
            while (true) 
            {
                ArrayList<String> possibleNextWords = hash.get(firstWord);

                if (possibleNextWords == null || possibleNextWords.size() == 0) 
                {
                    break;
                }
                
                int randomIndex = (int) (Math.random() * possibleNextWords.size());
                nextWord = possibleNextWords.get(randomIndex);
                words.add(nextWord);

                if (nextWord.contains(".") || nextWord.contains("!") || nextWord.contains("?")) 
                {
                    break;
                }
                firstWord = nextWord;
            }
            StringBuilder strbldr = new StringBuilder();
            for (int i = 0; i < words.size(); i++) 
            {
                if (i != 0) 
                {
                    strbldr.append(" ");
                }
                strbldr.append(words.get(i));
            }
            return strbldr.toString();
    }
    
}

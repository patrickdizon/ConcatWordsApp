/*
    ConcatWordsApp:
    Author: Patrick Dizon 3/6/2016

    This script provides the following results:
    - Longest words,
    - Second longest words
    - The number of longest words

*/


import java.util.*;
import java.util.Deque;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;


class ConcatWordsApp {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ConcatWordsApp lcwApp = new ConcatWordsApp();
        long startTime = System.currentTimeMillis();
        lcwApp.findLongestConcatenatedWords("words.txt");
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("runtime:" + elapsedTime );//, stop - start 
        
    }


    public boolean isConcat(String word, Set wordsDict ) {
        /*
            Function that loops over found suffs
        */
        //Whole word
        Word w = new Word(word, wordsDict);
        //set initial queue to use for looping, faster instead of a list, cause of shifting
        
        Deque<Suf> q = new LinkedList<>();

        for(Suf suf : w.getSuffs()) {
            q.add(suf);
        }
        Iterator it = q.iterator();
        while (it.hasNext()) {
            Suf suf = q.remove();
            if (suf.found == true) {  
                //return True

                return suf.found;
            } else {
                //Check the mids and add to the queue to keep looking
                Word w2 = new Word(suf.suf, wordsDict);
                for(Suf suf2 : w2.getSuffs()) {
                    q.add(suf2);
                }
            }
        }
        return false;

    }

    public void findLongestConcatenatedWords(String file) throws FileNotFoundException, IOException {
        /*
            Function that reads and loops over words.txt file
        */
        List<String> wordList = new ArrayList<>();
        List<String> longestWords = new ArrayList<>(); //list to store longest concatenated words
        List<String> secondLongestWords = new ArrayList<>();; //list to store second longest concatenated words
        int count = 0; //list to store longest concatenated words
        FileInputStream fileStream = new FileInputStream(file);
        BufferedReader buff = new BufferedReader(new InputStreamReader(fileStream));

        String strLine;

        //Read File Line By Line
        while ((strLine = buff.readLine()) != null)   {
            // Print the content on the console
            String str = strLine.replaceAll("\\r\\n|\\r|\\n","").trim();
            if(str.length() > 0) {
                wordList.add(str);
            }
        }

        //Close the input stream
        buff.close();

        //#convert to frozenset for faster lookup.
        Set<String> wordsDict  = new HashSet<String>(); 
        for(int i = 0; i < wordList.size(); i++) {
            wordsDict.add(wordList.get(i));  
        } 

        //reverse the list so that we can eval the top faster
        Collections.sort(wordList, new StringLengthComparator());
    
        //loop through list of words
        for(String word : wordList){ //.subList(0,1)){
            if(isConcat(word, wordsDict)){
                //There maybe 1 or more longest or second longest words,
                //so add to list if the list is empty or the same length
                //as the first word
                if(longestWords.size() == 0 || word.length() == longestWords.get(0).length()){
                    longestWords.add(word);
                } else if (secondLongestWords.size()== 0 || word.length() == secondLongestWords.get(0).length()) {
                    secondLongestWords.add(word);
                }
                //set counter
                count++;
            }
        }

        System.out.println("Longest word is: " + String.join(",", longestWords)); // + .format(longestWords)
        System.out.println("Second longest words: " + String.join(",", secondLongestWords)); //.format(secondLongestWords)
        System.out.println("Total Concatenated words is: " + count); //.format(count)
    }
   
        
}

class StringLengthComparator implements Comparator<String> {

    
    public int compare(String obj1, String obj2) {
       Integer len1 = ((String) obj1).length();
       Integer len2 = ((String) obj2).length();

       if (len1 < len2) {
           return 1;
       } else if (len1 > len2){
           return -1;
       } else {
           return 0;
       }
    }
}

class Suf {
    String suf = "";
    boolean found = false;

    public Suf(String suf, boolean found) {
        this.suf = suf;
        this.found = found;
    }
}

class Word {
    /*
        Word object stores a word that needs to be evaluated by is_concat function.
    */
    String word = ""; //Word to match
    List<String> prefs = new ArrayList<>(); //Possible beginning of words
    List<Suf> suffs = new ArrayList<>(); //Possible ending of words

    public Word(String word, Set wordsDict) {
        this.word = new String(word);
        String pref_word = new String(word);
        //get possible prefixes and suffixes from large to smaller words
        for(int i = 2; i < pref_word.length(); i++) {
            String pref = pref_word.substring(0,i);
            if(wordsDict.contains(pref)) {
                this.prefs.add(pref);
            }
        }
        
        
        for(String pref : this.prefs) {
            //get the suffix
            String suf = pref_word.substring(pref.length());
            //check the wordsDict and length of the suf word
            if(wordsDict.contains(suf) || suf.length() == 0) { 
                this.suffs.add(new Suf(suf,true));
                //break as soon as one is found to speed up the process
                break;
            } else {
                //else store the suf as it may contain a concatenated word
                this.suffs.add(new Suf(suf,false));
            }
        }
    }

    public List<Suf> getSuffs() {
        //Suffs Getter
        return this.suffs;
    }


}

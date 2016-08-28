package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    int wordLength = DEFAULT_WORD_LENGTH;

    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    private HashMap<String,ArrayList<String >> lettersToWord;
    private HashMap<String , ArrayList<String >> sizeOfWords;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        wordSet = new HashSet<>();
        wordList = new ArrayList<>();
        lettersToWord = new HashMap<>();
        sizeOfWords = new HashMap<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordSet.add(word);
            wordList.add(word);

            String sortedWord = sortString(word);

            if(!lettersToWord.containsKey(sortedWord)){
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                lettersToWord.put(sortedWord, list);
            }else{
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
            }

            int wordLength = word.length();

            if(!sizeOfWords.containsKey(wordLength)){
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                sizeOfWords.put(sortedWord, list);
            }else{
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
            }
        }
    }

    public boolean isGoodWord(String word, String base){
        return wordSet.contains(word) && !word.contains(base);
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> list;

        for(char c = 'a'; c <= 'z'; c++){
                String newWord = sortString(word + c);
                if(lettersToWord.containsKey(newWord)){
                    list = lettersToWord.get(newWord);
                    for (String s : list) {
                        if(isGoodWord(s, word)){
                            Log.i("result", " : " + s);
                            result.add(s);
                        }
                    }
                }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String word;
        int index;
        while(true){
            index = random.nextInt(wordList.size());
            word = (String) wordList.get(index);
            if(getAnagramsWithOneMoreLetter(word).size() > MIN_NUM_ANAGRAMS){
                break;
            }
        }
        return word;
    }


    public String sortString(String word){

        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);

        return new String(charArray);

    }
}

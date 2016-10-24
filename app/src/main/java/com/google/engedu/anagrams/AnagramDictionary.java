package com.google.engedu.anagrams;

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
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap< String, ArrayList<String>> lettersToWord = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {

            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)){
                lettersToWord.get(sortedWord).add(word);

            }
            else{
                ArrayList<String > anagramGroup = new ArrayList<>();
                anagramGroup.add(word);
                lettersToWord.put(sortedWord,anagramGroup);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base) ;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<>();
        String sortedWord = sortLetters(targetWord);
        if(lettersToWord.containsKey(sortedWord)){
            result.addAll(lettersToWord.get(sortedWord));
        }


        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        char c,d;
        for (int i = 97; i <= 122 ; i++)
        { for (int j = 97; j <= 122 ; j++)
        {c = (char)i ;
            d = (char) j;
            String OneMoreLetterWords = word.concat(String.valueOf(d));
            String OneMoreLetterWord = OneMoreLetterWords.concat(String.valueOf(c));
            ArrayList<String> anagrams = getAnagrams(OneMoreLetterWord);
            for(String  anagram: anagrams ) {
                if (isGoodWord(anagram, word)) {
                    result.add(anagram);
                }
            }}}
        return result;
    }


    public String pickGoodStarterWord() {

        String randomWord = wordList.get(random.nextInt(wordList.size() - 1));
        ArrayList<String> anagrams = getAnagramsWithOneMoreLetter(sortLetters(randomWord));
        if (anagrams.size() >= MIN_NUM_ANAGRAMS && randomWord.length()==wordLength) {
            if(wordLength < MAX_WORD_LENGTH){
                wordLength = wordLength + 1;}
            else
                wordLength = MAX_WORD_LENGTH;
            return randomWord;

        }
        return pickGoodStarterWord();
    }

    public String sortLetters(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }}


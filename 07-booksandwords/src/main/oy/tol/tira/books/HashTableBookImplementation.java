package oy.tol.tira.books;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


class HashTableBookImplementation implements Book {
    private static final int TABLE_SIZE = 1000;

    private class WordCount {
        String word;
        int count;
        WordCount next;

        WordCount(String word) {
            this.word = word;
            this.count = 1;
            this.next = null;
        }
    }

    private static final int MAX_WORD_LEN = 100;
    private WordCount[] words = null;
    private String bookFile = null;
    private String wordsToIgnoreFile = null;
    private WordFilter filter = null;
    private int uniqueWordCount = 0;
    private int totalWordCount = 0;
    private int ignoredWordsTotal = 0;
    private long loopCount = 0;
    private int collisionCount = 0;

    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        boolean success = false;
        if (checkFile(fileName)) {
            bookFile = fileName;
            if (checkFile(ignoreWordsFile)) {
                wordsToIgnoreFile = ignoreWordsFile;
                success = true;
            }
        }
        if (!success) {
            throw new FileNotFoundException("Cannot find the specified files");
        }
    }

    @Override
    public void countUniqueWords() throws IOException, OutOfMemoryError {
        if (bookFile == null || wordsToIgnoreFile == null) {
            throw new IOException("No file(s) specified");
        }
        
        uniqueWordCount = 0;
        totalWordCount = 0;
        loopCount = 0;
        ignoredWordsTotal = 0;
        words = new WordCount[TABLE_SIZE];
        filter = new WordFilter();
        
        filter.readFile(wordsToIgnoreFile);

        try (FileReader reader = new FileReader(bookFile, StandardCharsets.UTF_8)) {
            int c;
            StringBuilder wordBuilder = new StringBuilder(MAX_WORD_LEN);
            while ((c = reader.read()) != -1) {
                if (Character.isLetter(c)) {
                    wordBuilder.append((char) c);
                } else if (wordBuilder.length() > 0) {
                    processWord(wordBuilder.toString().toLowerCase(Locale.ROOT));
                    wordBuilder.setLength(0);
                }
            }
            if (wordBuilder.length() > 0) {
                processWord(wordBuilder.toString().toLowerCase(Locale.ROOT));
            }
        }
    }

    private void processWord(String word) {
        if (!filter.shouldFilter(word) && word.length() >= 2) {
            addToWords(word);
            totalWordCount++;
        } else {
            ignoredWordsTotal++;
        }
    }

    @Override
    public void report() {
        if (words == null) {
            System.out.println("*** No words to report! ***");
            return;
        }
        System.out.println("Listing words from a file: " + bookFile);
        System.out.println("Ignoring words from a file: " + wordsToIgnoreFile);
        System.out.println("Sorting the results...");
        
        sortWords();
        System.out.println("...sorted.");

        for (int index = 0; index < uniqueWordCount; index++) {
            if (words[index].word.length() == 0) {
                break;
            }
            String word = String.format("%-20s", words[index].word).replace(' ', '.');
            System.out.format("%4d. %s %6d%n", index + 1, word, words[index].count);
        }
        System.out.println("Number of collisions: " + collisionCount);
        System.out.println("Count of words in total: " + totalWordCount);
        System.out.println("Count of unique words:    " + uniqueWordCount);
        System.out.println("Count of words to ignore:    " + filter.ignoreWordCount());
        System.out.println("Ignored words count:      " + ignoredWordsTotal);
        System.out.println("How many times the inner loop rolled: " + loopCount);
    }

    @Override
    public void close() {
        bookFile = null;
        wordsToIgnoreFile = null;
        words = null;
        if (filter != null) {
            filter.close();
        }
        filter = null;
    }

    @Override
    public int getUniqueWordCount() {
        return uniqueWordCount;
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        if (words != null && position >= 0 && position < uniqueWordCount) {
            return words[position].word;
        }
        return null;
    }

    @Override
    public int getWordCountInListAt(int position) {
        if (words != null && position >= 0 && position < uniqueWordCount) {
            return words[position].count;
        }
        return -1;
    }

    private boolean checkFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    private void sortWords() {
        WordCount[] wordArray = new WordCount[uniqueWordCount];
        int index = 0;

        for (int i = 0; i < words.length && index < uniqueWordCount; i++) {
            WordCount node = words[i];
            while (node != null) {
                wordArray[index++] = node;
                node = node.next;
            }
        }

        heapSort(wordArray);

        words = wordArray;
    }

    private void heapSort(WordCount[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            WordCount temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    private void heapify(WordCount[] array, int n, int i) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left].count < array[max].count) {
            max = left;
        }

        if (right < n && array[right].count < array[max].count) {
            max = right;
        }

        if (max != i) {
            WordCount temp = array[i];
            array[i] = array[max];
            array[max] = temp;

            heapify(array, n, max);
        }
    }

    private void addToWords(String word) throws OutOfMemoryError {
        int hash = calcHash(word);
        int index = hash % TABLE_SIZE;

        WordCount current = words[index];
        while (current != null) {
            if (current.word.equals(word)) {
                current.count++;
                return;
            }
            current = current.next;
        }

        WordCount newNode = new WordCount(word);
        newNode.next = words[index];
        words[index] = newNode;
        uniqueWordCount++;
        collisionCount++;
    }

    private int calcHash(String key) {
        int hash = 1;

        for (char c : key.toCharArray()) {
            hash = hash * 31 + c;
        }

        hash = hash % words.length;
        if (hash < 0) {
            hash += words.length;
        }
        return hash;
    }

}
package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie {
    
    private int size;

private final TrieNode<Character> root;

    private int height = 0;

    static class TrieNode<Character> {
        Character c;
        int count;
        //TrieNode<Character>[] children;
        List<TrieNode<Character>> children;
        boolean isWord = false;


        TrieNode(Character c){
            this.c = c;
            count = 0;
            //this.children = new TrieNode[26];
            this.children = new ArrayList<TrieNode<Character>>();
            isWord = false;
        }

        private List<TrieNode<Character>> getChildren(){

            /*List<TrieNode<Character>> childs = new ArrayList<>();
            for(TrieNode<Character> t: children){
                if(t != null)
                    childs.add(t);
            }
            return childs;*/
            return children;
        }
        
        private void addChild(TrieNode<Character> node) {
            children.add(node);
        }

        public boolean isWord(){
            return this.isWord;
        }

        public void setIsWord(boolean word){
            this.isWord = word;
        }

        public Character getChar() {
            return c;
        }
        
    }
    
    
    
    
    /////////////////////////


    public LinkedTrie() {
        this.root = new TrieNode<Character>(' ');
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean put(@Nonnull String word) {

        System.out.println("in put");
        
        height = Math.max(word.length(), height);

        String lowerCaseWord = word.toLowerCase();
        
        
       return putHelper(root,lowerCaseWord);
        
    }

    private boolean putHelper(TrieNode<Character> trieNode, String word){
        
        TrieNode<Character> trie = trieNode;
        char[] chars = word.toCharArray();
        int counter = 0;

        if(word.length() == 0){
            trie.setIsWord(true);
            return true;
        }
        
        while (counter < chars.length) {

            TrieNode<Character> child = getChild(trie,chars[counter]);
            
            if (child == null) {
                child = insertChar(trie, chars[counter]);

                if (counter == chars.length - 1) {
                    child.setIsWord(true);
                    return true;
                }
            } else {
                child = getChild(trie, chars[counter]);
            }
    
            if (counter == chars.length - 1) {
                if (!child.isWord()) child.setIsWord(true);
                return true;
            }
            
            trie = child;
            counter++;
        }
        return false;
    }

    private TrieNode<Character> getChild(TrieNode<Character> trienode, Character c) {

        for (TrieNode<Character> child : trienode.getChildren()) {
            if (child.getChar() == c) {
                return child;
            }
        }
        
        return null;
    }

    private TrieNode<Character> insertChar(TrieNode<Character> trienode, Character c) {
    
        if (trienode.getChar() != null ) System.out.println(trienode.getChar().toString());
        System.out.println(" \\");
        System.out.println("   " + c);
        
        System.out.println("");
        
        TrieNode<Character> next = new TrieNode<Character>(c);
        
        //if (trienode.getChildren().contains(next)) {
        //    return null;
        //}
        
        for (TrieNode<Character> child : trienode.getChildren()){
            if (child.getChar() == c){
                return child;
            }
        }
        
        //trienode.getChildren().add(next);
        trienode.addChild(next);
        size++;
        
        return next;
    }


    @Override
    public int putAll(SortedSet<? extends String> words) {
        int count = 0;
        for(@Nonnull String i: words){
            if(put(i)){
                count++;
            }
        }
        return count;
    }

    @Override
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        
        System.out.println("in getNextN");
        
        if(prefix == null){
            throw new IllegalArgumentException();
        }
        else if (prefix.equals(" ")){
            throw new IllegalArgumentException();
        }
        else{
            String preLower = prefix.toLowerCase();
            TreeSet<String> wordList = new TreeSet<>();
            
            return  getNextNHelper(preLower, wordList, N);
        }
        
    }
    
    
    private SortedSet<String> getNextNHelper(String prefix, TreeSet<String> words, int N){
        
        //if the Trie contains the prefix
        
        if (this.contains(prefix)){
            //first we ned to check to see if word list is less then N
            //then, we can add the prefix to the list of words
            if(words.size() < N){
                words.add(prefix);
            }
        }
        
        TrieNode<Character> traveller = Traverse(root,prefix.toCharArray());
        /*for(int j = 0; j<26; j++ ){
            if(traveller.children[j] != null) {
                getNextNHelper(prefix + traveller.children[j].c, words, N);
            }
        }*/
        
        for (TrieNode<Character> child : traveller.getChildren()){
            if (child.getChar() != null){
                getNextNHelper(prefix + child.getChar(), words,N);
            }
        }
        
        return words;
    }
    
    private TrieNode<Character> Traverse(TrieNode<Character> n, char[] words){
        
        //if the length of the word is 0, then return the node
        if(words.length == 0){
            return n;
        }
        else{
            TrieNode<Character> node;
    
            System.out.println("\nChildren of " + n.getChar() + ":");
            for (TrieNode<Character> child : n.getChildren()) {
                
                System.out.print(child.getChar() + " ");
                
            }
            System.out.println("");
            
            //if (n.children[words[0] - 'a'] == null){
            boolean nContainsWordsAtZero = false;
            TrieNode<Character> somenode = new TrieNode<Character>('.');
            for (TrieNode<Character> child : n.getChildren()) {
                if (child.getChar().charValue() == (words[0])) {
                    nContainsWordsAtZero = true;
                    somenode = child;
                    break;
                }
            }
            
            if (!nContainsWordsAtZero) {
                return null;
            }
            else{
                //node = Traverse(n.children[words[0] - 'a'], Arrays.copyOfRange(words,1,words.length));
                node = Traverse(somenode, Arrays.copyOfRange(words,1,words.length));
                return node;
            }
        }
    }

    
    

    @Override
    public boolean contains(@Nonnull String word) throws IllegalArgumentException {

        return containsHelper(root, word.toLowerCase());
    }

    private boolean containsHelper(TrieNode<Character> node, String word) {
        
        if (node.isWord() && word.equals("")){
            return true;
        }
        else if(word.equals("")){
            return false;
        }
    
        for (TrieNode<Character> child : node.getChildren()) {
            if (child.getChar().charValue() == word.charAt(0)) {
                return containsHelper(child, word.substring(1));
            }
        }
        
        return false;
    }

    
    public static void main(String [] args){
        
        LinkedTrie test = new LinkedTrie();
        
        test.put("hello");
        
        if (test.contains("hell")){
            System.out.println("sweet");
        }
        else{
            System.out.println("shit");
        }
        
//        for (String s : test.getNextN("h", 1)) {
//            System.out.println(s);
//        }
//
        
    }
    
    
    
}

package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

/**
 *  Tests for Trie implementation
 */
public class TrieTest {

    private Trie underTest = new LinkedTrie();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
    }

    @Test
    public void put() throws Exception {
        // Check what happens when adding and contains
        LinkedTrie trie = new LinkedTrie();
        
        trie.put("hello");
        trie.put("nice");
        trie.put("help");
        
        for (String s : trie.getNextN("he",6)) {
            System.out.println(s);
        }
        //trie.getNextN("he",6);
        
    }

    @Test
    public void putAll() throws Exception {

        // make sure it works compared to put
    }

    @Test
    public void getNextN() throws Exception {
        // Make sure you get the results you expect
    }

}
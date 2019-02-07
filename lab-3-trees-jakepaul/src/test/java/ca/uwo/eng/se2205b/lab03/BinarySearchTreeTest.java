package ca.uwo.eng.se2205b.lab03;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for Binary Search Tree
 */
public class BinarySearchTreeTest {

    private final BinarySearchTree<Integer> underTest = new BinarySearchTree<>();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        //BinarySearchTree<Integer> tes
    }

    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
        BinarySearchTree<Integer> testTree = new BinarySearchTree<Integer>();
        System.out.println(testTree.height());
        testTree.put(9);
        System.out.println(testTree.height());
        testTree.put(4);
        System.out.println(testTree.height());
        testTree.remove(4);
        System.out.println(testTree.height());
        
        
    }

    @Test
    public void put() throws Exception {
        // check the return result, adding/removing
        BinarySearchTree<Integer> test = new BinarySearchTree<Integer>();
        Assert.assertTrue(test.put(3));
        Assert.assertTrue(test.put(5));
        Assert.assertTrue(test.remove(5));
        Assert.assertFalse(test.remove(4));
   }

    @Test
    public void remove() throws Exception {
        // Removing nodes, remember the cases


    }


    @Test
    public void iterator() throws Exception {
        // Check the three different types of iteration
    }

    @Test
    public void contains() throws Exception {
        // Actually in the tree, not in..
    }

    @Test
    public void isComplete() throws Exception {
        // Check the null condition, complete, incomplete..
    }

    @Test
    public void isFull() throws Exception {
        // Check the null condition, complete, incomplete..
    }

}
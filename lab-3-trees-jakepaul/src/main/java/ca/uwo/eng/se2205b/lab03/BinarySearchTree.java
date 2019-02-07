package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Binary Search Tree
 */
@ParametersAreNonnullByDefault
public class BinarySearchTree <E extends Comparable<E>> implements Tree<E> {
    
    private int size = 0;
    private BinaryNode<E> root;
    
    /**
     * Internal Node structure used for the BinaryTree
     * @param <E>
     */
    static class BinaryNode<E> implements Tree.Node<E> {
        
        private E elem;           // sorted by key
        private BinaryNode<E> left, right, parent;  // left and right subtrees
        TreeMap<E, BinaryNode<E>> children;
        
    
        BinaryNode(@Nullable E elem, @Nullable BinaryNode<E> parent) {
                this.elem = elem;
                this.parent = parent;
                this.left = null;
                this.right = null;
        }

        @Nullable BinaryNode<E> getLeft() { return left; }

        @Nullable BinaryNode<E> getRight() {
            return right;
        }

        @Nullable BinaryNode<E> getParent() {
            return parent;
        }
    
        @Nullable void setLeft(BinaryNode<E> left) { this.left = left; }
    
        @Nullable void setRight(BinaryNode<E> right) { this.right = right; }
    
        @Nullable void setParent(BinaryNode<E> parent) { this.parent = parent; }

        @Override
        public int size() {
            
            if (this.isLeaf()){
                return 1;
            }
    
            return ((this.getLeft() == null ? 0 : this.getLeft().size())
                    + (this.getRight() == null ? 0 : this.getRight().size() )
                    + 1);
        }

        @Override
        public boolean isEmpty() {
            return elem == null;
        }

        @Override
        public int height() {
            //return height();
            //calculates the height of a given node
            return (1 + Math.max(
                    (this.getLeft() == null ? -1 : this.getLeft().height() ),
                    (this.getRight() == null ? -1 : this.getRight().height())
            ));
        }
        @Override
        public boolean isProper() {
            if (this.getLeft() == null && this.getRight() == null){
                return true;
            }
            if (this.getLeft() != null && this.getRight() != null){
                return true;
            }
            else
                return false;
        }

        @Override
        public boolean isBalanced() {
            int rightheight;
            int leftheight;
    
            if(this.isLeaf()){
                return true;
            }
    
            rightheight = (right == null ? -1 : right.height());
            leftheight = (left == null ? -1 : left.height());
    
            return (Math.abs(rightheight-leftheight) <= 1);
            
        }

        @Override
        public E getElement() throws NullPointerException{
           
            if (elem == null){
                throw new NullPointerException("Element cannot be null");
            }
            
            return elem;
        }

        @Nonnull
        @Override
        public Collection<? extends Node<E>> children() {
    
            List<BinaryNode<E>> children = new ArrayList<>(2);
            
            if (this.getLeft() != null){
                children.add(this.getLeft());
            }
            if (this.getRight() != null){
                children.add(this.getRight());
            }
            
            return children;
            
        }

        @Override
        public boolean isInternal() {
            return !isLeaf();
        }

        @Override
        public boolean isLeaf() {
            return this.getLeft() == null && this.getRight() == null;
        }
        
    }

    // END OF NODE CLASS
    
    
    
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
       
        //calculates the height of a tree(not to sure about parameters)
        if (this.getRoot().height() < 0 ){

            throw new NullPointerException("Height must be positive");

        }
        
        return root.height();
    
    }

    @Override
    public boolean isProper() {
        
        //need to iterate over all nodes to see if they are proper
        /*boolean rproper = isProper(node.left);
        boolean lproper = isProper(node.right);
        
        
        return rproper && lproper;*/
        return isProperRecursion(root);
    }
    
    private boolean isProperRecursion (BinaryNode node)
    {
        if (node == null) {
            return true;
        }
    
        if (node.isLeaf()) {
            return true;
        }
        
        if (!node.isProper()) {
            return false;
        }
        
        return isProperRecursion(node.getLeft()) && isProperRecursion(node.getRight());
    }
        
    
    @Override
    public boolean isBalanced() {
        return root.isBalanced();
        
    }

    @Override
    public Iterator<E> iterator(Traversal how) throws UnsupportedOperationException {
        if (how != Traversal.PreOrder && how != Traversal.PostOrder && how != Traversal.InOrder){
            throw new UnsupportedOperationException("Not a valid traversal");
        } else {
    
            if (how == Traversal.InOrder) {
                return inOrder(root).iterator();
            }
            if (how == Traversal.PreOrder) {
                return preOrder(root).iterator();
            }
            if (how == Traversal.PostOrder) {
                return postOrder(root).iterator();
            }
        }
        
        return null;
    }
    private ArrayList<E> inOrder (BinaryNode<E> node){
    
        ArrayList<E> list = new ArrayList<E>();
    
        if (node.getLeft() != null){
            list.addAll(inOrder(node.getLeft()));
        }
        
        list.add((E)node.getElement());
    
        if (node.getRight() != null){
            list.addAll(inOrder(node.getRight()));
        }
        
        return list;
    }
    
    private ArrayList<E> preOrder (BinaryNode<E> node){
        
        ArrayList<E> list = new ArrayList<E>();
        
        list.add((E)node.getElement());
        
        if (node.getLeft() != null){
            list.addAll(preOrder(node.getLeft()));
        }
        if (node.getRight()!= null){
            list.addAll(preOrder(node.getRight()));
        }
        
        return list;
        
    }
    
    private ArrayList<E> postOrder (BinaryNode<E> node){
    
        ArrayList<E> list = new ArrayList<E>();
        
        if (node.getLeft()!= null){
            list.addAll(postOrder(node.getLeft()));
        }
        if (node.getRight()!= null){
            list.addAll(postOrder(node.getRight()));
        }
        
        list.add((E)node.getElement());
        
        return list;
        
    }
    
    private boolean BinarySearch (BinaryNode<E> node , E element){
        try {
            
           int comp = element.compareTo((E)node.getElement());
            
           if (comp == 0){
                return true;
           }
           else if (comp < 0){
                return BinarySearch(node.getLeft(), element);
           }
           else{
                return BinarySearch(node.getRight(), element);
           }
    
        }
        catch (NullPointerException e) {
            return false;
        }
    }
    
    
    @Override
    public boolean contains(E element) {
       return (root != null && BinarySearch(root , element));
    }
    
    private BinaryNode<E> putBinarySearch (BinaryNode<E> node , E element){
        
//        System.out.println(node == null ? "Node is null!" : "Node is not null");
//        System.out.println(node.getParent() == null ? "Node is root!" : "Node is not root");
//        System.out.println(node.isInternal() ? "Node is Internal!" : "Node is not internal");
//        System.out.println(node.isLeaf() ? "Node is leaf!" : "Node is not leaf");
    
        BinaryNode<E> newNode = new BinaryNode<E>(element, null);
        if (root == null) {
            root = newNode;
            size++;
            return root;
        }
    
        int comp = element.compareTo((E)node.getElement());
        

        if (comp == 0) {
            return node;
        }
        
        if (comp < 0) {
            if (node.getLeft() == null) {
                node.setLeft(newNode);
                newNode.parent = node;
                size++;
                return newNode;
            } else {
                return  putBinarySearch(node.getLeft(), element);
            }
        }
        
        if (comp > 0){
            if (node.getRight() == null) {
                node.setRight(newNode);
                newNode.parent = node;
                size++;
                return newNode;
            } else {
                return putBinarySearch(node.getRight(), element);
            }
        }
        
        return null;
    }

    @Override
    public boolean put(E element) throws UnsupportedOperationException {
       
        if (this.contains(element)){
            return false;
        }
    
        BinaryNode<E> newNode = putBinarySearch(root, element);
    
        return newNode != null;
    }
    
    private BinaryNode<E> removeBinarySearch (BinaryNode<E> node , E element){

        int comp;

        try {
            comp = element.compareTo((E) node.getElement());
        }
        catch(NullPointerException e){
            System.out.println("Exception Caught");
            return null;
        }

        if (comp== 0){
            return node;
        }
        else if (comp < 0 && node.getLeft() != null){
            return removeBinarySearch(node.getLeft(), element);
        }
        else if (comp > 0 && node.getRight() != null){
          return  removeBinarySearch(node.getRight(), element);
        }

        System.out.println("End of RemoveBinarySearch reached");
        return null;
    }

    
    @Override
    public boolean remove(E element) {
        
        this.delete(element);
        return (!this.contains(element));
    }
    
    public void delete(E toDelete)
    {
        root = delete(root, toDelete);
    }
    private BinaryNode<E> delete(BinaryNode<E> p, E toDelete)
    {
        if (p == null)  throw new RuntimeException("cannot delete.");
        else
        if (toDelete.compareTo(p.getElement()) < 0)
            p.left = delete (p.left, toDelete);
        else
        if (toDelete.compareTo(p.getElement())  > 0)
            p.right = delete (p.right, toDelete);
        else
        {
            if (p.left == null) return p.right;
            else
            if (p.right == null) return p.left;
            else
            {
                // get data from the rightmost node in the left subtree
                p.elem = retrieveData(p.left);
                // delete the rightmost node in the left subtree
                p.left =  delete(p.left, p.getElement()) ;
            }
        }
        return p;
    }
    private E retrieveData(BinaryNode<E> p)
    {
        while (p.right != null) p = p.right;
        
        return p.getElement();
    }
    
    private BinaryNode<E> getSuccessor(BinaryNode<E> node) {
        BinaryNode<E> successor = null;
        BinaryNode<E> parent = null;
        BinaryNode<E> current = node.getRight();
        while (current != null) {
            parent = successor;
            successor = current;
            current = current.getLeft();
        }
        if (successor != node.getRight()) {
            parent.left = successor.getRight();
            successor.right = node.getRight();
        }
        return successor;
    }
    
    private BinaryNode<E> getPredecessor(BinaryNode<E> node) {
        BinaryNode<E> predecessor = null;
        BinaryNode<E> parent = null;
        BinaryNode<E> current = node.getLeft();
        while (current != null) {
            parent = predecessor;
            predecessor = current;
            current = current.getRight();
        }
        if (predecessor != node.getLeft()) {
            parent.right = predecessor.getLeft();
            predecessor.left = node.getLeft();
        }
        return predecessor;
    }
    
    private BinaryNode<E> findMin( BinaryNode<E> node ){
        if( node == null )
            return null;
        else if( node.left == null)
            return node;
        return findMin( node.left );
    }
    
    private BinaryNode<E> findMax ( BinaryNode<E> node){
        if( node == null )
            return null;
        else if( node.right == null)
            return node;
        return findMax( node.right );
    }
    @Nullable
    @Override
    public BinaryNode<E> getRoot() {
        return root;
    }
    
    
    private void display(BinaryNode<E> node) {
        if (node != null) {
            display(node.getLeft());
            System.out.print(" " + node.getElement());
            display(node.getRight());
        }
    }
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
    
        t.put(7);
        t.put(9);
        t.put(5);
        //t.put(9);
        t.put(4);
        t.put(6);
        t.put(8);
        t.put(10);
    
        t.display(t.root);
    
        System.out.println("");
        
        t.remove(7);
    
        System.out.println("");
    
        t.display(t.root);
        
        t.remove(5);
    
        System.out.println("");
    
        t.display(t.root);
    }
    
}

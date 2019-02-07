# lab-03-trees
##Q1
1. You can implement a map using a BST under the hood to obtain a structure which is
efficient for certain operations. By iterating over the BST in a specific order (ie inorder)
you are able to store values related to their specific key. 
2. ```put()```, ```remove()```, and ```height()``` all have worst O(n) complexity. For an unbalanced tree 
the complexity remains the same but you can become more specific and say ```put()``` and ```remove()```
both have O(h) complexity. For a balanced tree these methods ALL have O(logn) complexity.

##Q2
1. The complexity of verifying a word of length k is O(k), a HashMap is a better structure because it 
guarentees O(1) lookup time. 
2. A Trie is a specialization of a binary search tree.


See lab document: https://uwoece-se2205b-2017.github.io/labs/03-trees
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marupeke;

/**
 *
 * @author 181326
 */
public class Tree {
    Node root; 
    Node start;
    /**
     * Adds a new node to the tree 
     * @param IntegerValue
     * @param Symbol
     */
    public void add(int value,String symbol) {
        
        Node newNode = new Node(value,symbol);
        if (root == null){
            root = newNode;
            start = newNode;
        }
        else 
        {
            Node current = root;
            Node parent;
            
            while (true){
                parent = current;
                if (current.left == null){
                    parent.left = newNode;
                    break;
                }
                else if(current.right == null){
                    parent.right = newNode;
                    break;
                }
                else 
                    current = current.right;
            }
        }
      
    }   
    /**
     * Traverses the tree structure 
     * @param RootNode  
     */
    public void traverse(Node node) {
        if (node != null) {
            traverse(node.left);
            System.out.print(" " + node.symbol);
            traverse(node.right);
        }
    }
       
}

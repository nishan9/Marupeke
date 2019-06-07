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
public class Node {
    int value;
    String symbol;
    Node left;
    Node right;
    /**
     * Creates a new Node 
     * @param IntegerValue
     * @param Symbol 
     */
    Node(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
        right = null;
        left = null;
    }
}
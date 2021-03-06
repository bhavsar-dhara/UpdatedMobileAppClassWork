package edu.neu.madcourse.dharabhavsar1.ui.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhara on 2/5/2016.
 */
/*public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.children;

            //set leaf node
            if(i==word.length()-1)
                t.isLeaf = true;
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);

        if(t != null && t.isLeaf)
            return true;
        else
            return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }

        return t;
    }
}*/

// Implements the actual Trie
public class Trie {
    // Constructor
    public Trie()   {     root = new TrieNode((char)0);       }

    // Method to insert a new word to Trie
    public void insert(String word)  {

        // Find length of the given word
        int length = word.length();
        TrieNode crawl = root;

        // Traverse through all characters of given word
        for( int level = 0; level < length; level++)
        {
            HashMap<Character,TrieNode> child = crawl.getChildren();
            char ch = word.charAt(level);

            // If there is already a child for current character of given word
            if( child.containsKey(ch))
                crawl = child.get(ch);
            else   // Else create a child
            {
                TrieNode temp = new TrieNode(ch);
                child.put( ch, temp );
                crawl = temp;
            }
        }

        // Set bIsEnd true for last character
        crawl.setIsEnd(true);
    }

    // The main method that finds out the longest string 'input'
    public String getMatchingPrefix(String input)  {
        String result = ""; // Initialize resultant string
        int length = input.length();  // Find length of the input string

        // Initialize reference to traverse through Trie
        TrieNode crawl = root;

        // Iterate through all characters of input string 'str' and traverse
        // down the Trie
        int level, prevMatch = 0;
        for( level = 0 ; level < length; level++ )
        {
            // Find current character of str
            char ch = input.charAt(level);

            // HashMap of current Trie node to traverse down
            HashMap<Character,TrieNode> child = crawl.getChildren();

            // See if there is a Trie edge for the current character
            if( child.containsKey(ch) )
            {
                result += ch;          //Update result
                crawl = child.get(ch); //Update crawl to move down in Trie

                // If this is end of a word, then update prevMatch
                if( crawl.isEnd() )
                    prevMatch = level + 1;
            }
            else  break;
        }

        // If the last processed character did not match end of a word,
        // return the previously matching prefix
        if( !crawl.isEnd() )
            return result.substring(0, prevMatch);

        else return result;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(String str){
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            TrieNode crawl = root;
            Map<Character, TrieNode> children = crawl.getChildren();
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.getChildren();
            }else{
                return null;
            }
        }
        return t;
    }

    private TrieNode root;
}

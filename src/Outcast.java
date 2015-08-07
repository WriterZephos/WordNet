import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

/*
 * WordNet
 * Bryant Morrill
 * Spencer Daniels
 * 
 * 
 */

public class Outcast {
   
	WordNet wn;
	
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet){
		wn = wordnet;
	}
	
	
	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns){
		
		RedBlackBST<Integer,String> bst = new RedBlackBST<>();
		
		for(String s : nouns){
			int distance = 0;
			for(String n : nouns){
				distance += wn.distance(s, n);
			}
			bst.put(distance, s);
		}
		
		return bst.get(bst.max());
	}
	
	
	// see test client below
	public static void main(String[] args) {
	    WordNet wordnet = new WordNet("/synsets.txt","/hypernyms.txt");
	    Outcast outcast = new Outcast(wordnet);
	    
	    String[] outcasts = {"/outcast10.txt","/outcast11.txt","/outcast17.txt","/outcast5.txt","/outcast9.txt"};
	    
	    for (int t = 0; t < outcasts.length; t++) {
	        In in = new In(outcasts[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(outcasts[t] + ": " + outcast.outcast(nouns));
	    }
	}
}

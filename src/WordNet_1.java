import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/*
 * WordNet
 * Bryant Morrill
 * Spencer Daniels
 * 
 * 
 */
public class WordNet_1 {
	
	Digraph G;
	RedBlackBST<String,Stack<Integer>> vertexByNoun;
	RedBlackBST<Integer,String> synsetByVertex;
	SAP sap;

   // constructor takes the name of the two input files
   public WordNet_1(String synsets, String hypernyms){
	   In in = new In(synsets);
	   
	   String line =in.readLine();
	   String[] tokens;
	   String[] words;
	   
	   vertexByNoun = new RedBlackBST<>();
	   synsetByVertex = new RedBlackBST<>();
	   
	   int count = 0;
	   
	   while(line != null){
		   count++;
		   tokens = line.split(",");
		   int vertex = Integer.parseInt(tokens[0]);
		   synsetByVertex.put(vertex, tokens[1]);

		   words = tokens[1].split(" ");
		   
		   for(String s : words){
			   if(!vertexByNoun.contains(s)) vertexByNoun.put(s, new Stack<Integer>());
			   vertexByNoun.get(s).push(vertex);
		   }
		   
		   line = in.readLine();
	   }
	   if(count != synsetByVertex.size()) throw new java.lang.IllegalArgumentException("vertex count is off");
	   
       G = new Digraph(count);
       in = new In(hypernyms);
       
       line = in.readLine();
       
       while(line != null){
    	   tokens = line.split(",");
    	   for(int i = 1 ; i < tokens.length ; i++){
    		   G.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[i]));
    	   }
    	   line = in.readLine();
       }
       
       sap = new SAP(G);
       if(!sap.isRootedDAG()) throw new java.lang.IllegalArgumentException("Not a rooted DAG.");
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
	   return vertexByNoun.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word){
	   return vertexByNoun.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB){
	   if(!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException("Not all of the words entered are in the WordNet.");
	   return sap.length(vertexByNoun.get(nounA), vertexByNoun.get(nounB));
	   
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
	   if(!isNoun(nounA) || !isNoun(nounB)) throw new java.lang.IllegalArgumentException("Not all of the words entered are in the WordNet.");
	   int vertex = sap.ancestor(vertexByNoun.get(nounA), vertexByNoun.get(nounB));
	   return synsetByVertex.get(vertex);
   }

   // do unit testing of this class
   public static void main(String[] args){
	   WordNet wn = new WordNet("/synsets.txt","/hypernyms.txt");
	   while (!StdIn.isEmpty()) {
		   String v = StdIn.readLine();
		   String w = StdIn.readLine();
		   if(!wn.isNoun(v)) {
			   StdOut.printf("%s is not in the WordNet", v);
		   }
		   if(!wn.isNoun(w)) {
			   StdOut.printf("%s is not in the WordNet", w);
		   }
		   int distance   = wn.distance(v, w);
		   String ancestor = wn.sap(v, w);
		   StdOut.printf("distance = %s, ancestor = %s\n", distance, ancestor);
	   }
	   
	   
	   
   }
}
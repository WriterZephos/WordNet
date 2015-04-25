import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SAP {
	
	private boolean dag = false;
	private boolean rootedDag = true;
	private Iterable<Integer> reversePostOrder;
	Digraph G;
	BreadthFirstDirectedPaths[] paths;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G){
	   this.G = G;
	   
	   DirectedCycle dc = new DirectedCycle(G);
	   if (!dc.hasCycle()) dag = true;
	   
	   paths = new BreadthFirstDirectedPaths[G.V()];
	   
	   DepthFirstOrder dfo = new DepthFirstOrder(G);
	   BreadthFirstDirectedPaths bfdp = null;
	   reversePostOrder = dfo.reversePost();
	   
	   for(int i : reversePostOrder){
		  if(bfdp == null) bfdp = paths[i] = new BreadthFirstDirectedPaths(G,i);
		  if(!bfdp.hasPathTo(i)) {
			  rootedDag = false;
			  break;
		  }
	   }
   }

   // is the digraph a directed acyclic graph?
   public boolean isDAG(){
	   return dag;
   }

   // is the digraph a rooted DAG?
   public boolean isRootedDAG(){
	   return rootedDag;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w){
	   if(paths[v] == null) paths[v] = new BreadthFirstDirectedPaths(G,v);
	   if(paths[w] == null) paths[w] = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int current = -1;
	   
	   for(int i : reversePostOrder){
		   if(paths[v].hasPathTo(i) && paths[w].hasPathTo(i)){
			   
			   current = paths[v].distTo(i) + paths[w].distTo(i);
			   
			   if(distance > current || distance == -1) {
				   
				   distance = current;
				   
			   }
		   }
		   
	   }
	   return distance;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w){
	   if(paths[v] == null) paths[v] = new BreadthFirstDirectedPaths(G,v);
	   if(paths[w] == null) paths[w] = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int ancestor = -1;
	   int current = -1;
	   
	   for(int i : reversePostOrder){
		   if(paths[v].hasPathTo(i) && paths[w].hasPathTo(i)){
			   
			   current = paths[v].distTo(i) + paths[w].distTo(i);
			   
			   if(distance > current || distance == -1) {
				   
				   distance = current;
				   ancestor = i;
			   }
		   }
	   }
	   return ancestor;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w){
	   
	   int distance = -1;
	   int current = -1;
	   
	   for(int i : v){
		   
		   for(int j : w){
			   
			   current = length(i,j);
			   if(current != -1 && (distance > current || distance == -1)) {
				   distance = current;
			   } 
		   }
	   }
	   return distance;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
	   
	   int distance = -1;
	   int current = -1;
	   int ancestor = -1;
	   
	   for(int i : v){
		   
		   for(int j : w){
			   
			   current = length(i,j);
			   if(current != -1 && (distance > current || distance == -1)) {
				   distance = current;
				   ancestor = ancestor(i,j);
			   } 
		   }
	   }
	   return ancestor;
   }

   // do unit testing of this class
   public static void main(String[] args){
	   
	    In in = new In("/myGraph.txt");
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	   
   }
}

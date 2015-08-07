import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
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


public class SAP {
	
	private boolean dag = false;
	private boolean rootedDag = true;
	private Iterable<Integer> reversePostOrder;
	private Iterable<Integer> postOrder;
	Digraph G;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G){
	   if(G == null) throw new java.lang.NullPointerException("Graph was null");
	   this.G = G;
	   
	   DirectedCycle dc = new DirectedCycle(G);
	   if (!dc.hasCycle()) dag = true;
	   
	   
	   DepthFirstOrder dfo = new DepthFirstOrder(G);
	   BreadthFirstDirectedPaths bfdp = null;
	   reversePostOrder = dfo.reversePost();
	   postOrder = dfo.post();
	   Integer root = null;
	   
	   for(int i : postOrder){
		  if(root == null) root = i;
		  bfdp = new BreadthFirstDirectedPaths(G,i);
		  if(!bfdp.hasPathTo(root)) {
			  System.out.println("disconnected: " + i);
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
	   if(v >= G.V() || w >= G.V() || w < 0 || v < 0) throw new java.lang.IndexOutOfBoundsException();
	   BreadthFirstDirectedPaths pathsV= new BreadthFirstDirectedPaths(G,v);
	   BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int current = -1;
	   
	   for(int i : reversePostOrder){
		   if(pathsV.hasPathTo(i) && pathsW.hasPathTo(i)){
			   
			   current = pathsV.distTo(i) + pathsW.distTo(i);
			   
			   if(distance > current || distance == -1) {
				   
				   distance = current;
				   
			   }
		   }
		   
	   }
	   return distance;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w){
	   if(v >= G.V() || w >= G.V() || w < 0 || v < 0) throw new java.lang.IndexOutOfBoundsException();
	   BreadthFirstDirectedPaths pathsV= new BreadthFirstDirectedPaths(G,v);
	   BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int ancestor = -1;
	   int current = -1;
	   
	   for(int i : reversePostOrder){
		   if(pathsV.hasPathTo(i) && pathsW.hasPathTo(i)){
			   
			   current = pathsV.distTo(i) + pathsW.distTo(i);
			   
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
	   if(v == null || w == null) throw new java.lang.NullPointerException("Input was null");
	   for(int i : v) if(i >= G.V() || i < 0) throw new java.lang.IndexOutOfBoundsException();
	   for(int i : w) if(i >= G.V() || i < 0) throw new java.lang.IndexOutOfBoundsException();
	   
	   BreadthFirstDirectedPaths pathsV= new BreadthFirstDirectedPaths(G,v);
	   BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int current = -1;
	   
	   for(int i : reversePostOrder){
		   if(pathsV.hasPathTo(i) && pathsW.hasPathTo(i)){
			   
			   current = pathsV.distTo(i) + pathsW.distTo(i);
			   
			   if(distance > current || distance == -1) {
				   
				   distance = current;
				   
			   }
		   }
		   
	   }
	   return distance;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
	   if(v == null || w == null) throw new java.lang.NullPointerException("Input was null");
	   BreadthFirstDirectedPaths pathsV= new BreadthFirstDirectedPaths(G,v);
	   BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(G,w);
	   
	   int distance = -1;
	   int current = -1;
	   int ancestor = -1;
	   
	   for(int i : reversePostOrder){
		   if(pathsV.hasPathTo(i) && pathsW.hasPathTo(i)){
			   
			   current = pathsV.distTo(i) + pathsW.distTo(i);
			   
			   if(distance > current || distance == -1) {
				   
				   distance = current;
				   ancestor = i;
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

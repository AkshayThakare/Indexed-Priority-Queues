
import java.util.Scanner;
/**
 * The MST class implements the Prims algorithm on given graph
 * @author  Akshay Thakare
 * @version 1.0, March 2015 
 */
public class MST {
	static final int Infinity = Integer.MAX_VALUE;

	static int PrimMST(Graph g)
	{
		MST mst = new MST();
		int wmst = 0;
		Graph.Vertex src = g.V[1];
		//changing the weight of 1st vertex to 0
		src.weight = 0;
		/*Adding the vertices to the Priority queue*/
		PriorityQueueIndexed<Graph.Vertex> pq = new PriorityQueueIndexed<Graph.Vertex>(g.V.length);
		for(int i=1;i<g.V.length;i++){
			pq.add(g.V[i]);
		}
		/*iterating till the size of priority queue is greater than 0*/
		while(pq.size>0){
			Graph.Vertex u = pq.deleteMin();
			u.seen = true;
			wmst = wmst + u.weight; //adding the weight of u to wmst 
			//for each vertex in u's adjacency list check the weight of edge with vertex's weight
			for (Graph.Edge e : u.Adj) {
				Graph.Vertex v = e.otherEnd(u); //Vertex v connected to u
				//Check the weight of the vertex with the weight of the edge given by u-v
				if(!(v.seen) && v.weight > e.Weight){
					//Set u as parent of v
					v.parent = u;
					v.weight = e.Weight;
					//Weight of the edge is less, decrease key value of vertex v
					pq.decreaseKey(v);
				}
			}
		}
		return wmst;
	}

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		Graph g = Graph.readGraph(in);
		long startTime = System.currentTimeMillis();
		System.out.println("Weight of MST = "+PrimMST(g));
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed Time: "+elapsedTime);
	}
}

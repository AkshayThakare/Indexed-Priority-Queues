import java.io.*;
import java.util.*;

/**
 * 
 * @author rbk
 * Graph class for Project 2
 */

/**
 * Class to represent a graph
 * 
 *
 */
public class Graph implements Iterable<Graph.Vertex> {
static final int INFINITY = Integer.MAX_VALUE;
    public Vertex[] V; // array of vertices
    public int N; // number of verices in the graph

    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size) {
	N = size;
	V = new Vertex[size + 1];
	// create an array of Vertex objects
	for (int i = 1; i <= size; i++)
	    V[i] = new Vertex(i);
    }
    /**
     * Class that represents an arc in a Graph
     *
     */
    public class Edge {
	public Vertex From; // head vertex
	public Vertex To; // tail vertex
	public int Weight;// weight of the arc

	/**
	 * Constructor for Edge
	 * 
	 * @param u
	 *            : Vertex - The head of the arc
	 * @param v
	 *            : Vertex - The tail of the arc
	 * @param w
	 *            : int - The weight associated with the arc
	 */
	Edge(Vertex u, Vertex v, int w) {
	    From = u;
	    To = v;
	    Weight = w;
	}

	/**
	 * Method to find the other end end of the arc given a vertex reference
	 * 
	 * @param u
	 *            : Vertex
	 * @return
	 */
	public Vertex otherEnd(Vertex u) {
	    // if the vertex u is the head of the arc, then return the tail else return the head
	    if (From == u) {
		return To;
	    } else {
		return From;
	    }
	}

	/**
	 * Method to represent the edge in the form (x,y) where x is the head of
	 * the arc and y is the tail of the arc
	 */
	public String toString() {
	    return "(" + From + "," + To + ")";
	}
    }

    /**
     * Class to represent a vertex of a graph
     * 
     *
     */
    public class Vertex implements Comparable<Vertex>, PQIndex {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int weight; // field for storing int attribute of vertex
	public int indexPQ;  // index of node in priotity queue
	public LinkedList<Edge> Adj; // adjacency list

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
	    name = n;
	    seen = false;
	    parent = null;
	    Adj = new LinkedList<Edge>();
	}

	public void putIndex(int index) {
	    indexPQ = index;
	}

	public int getIndex() {
	    return indexPQ;
	}

	/**
	 * Method used to order vertices, based on algorithm
	 */
	public int compareTo(Vertex v) {
	    return this.weight - v.weight;
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
	    return Integer.toString(name);
	}
    }


    /**
     * Method to add an arc to the graph
     * 
     * @param a
     *            : int - the head of the arc
     * @param b
     *            : int - the tail of the arc
     * @param weight
     *            : int - the weight of the arc
     */
    void addEdge(int a, int b, int weight) {
	Edge e = new Edge(V[a], V[b], weight);
	V[a].Adj.add(e);
	V[b].Adj.add(e);
    }

    /**
     * Method to create an instance of VertexIterator
     */
    public Iterator<Vertex> iterator() {
	return new VertexIterator<Vertex>(V, N);
    }

    /**
     * A Custom Iterator Class for iterating through the vertices in a graph
     * 
     *
     * @param <Vertex>
     */
    @SuppressWarnings("hiding")
	private class VertexIterator<Vertex> implements Iterator<Vertex> {
	private int nodeIndex = 0;
	private Vertex[] iterV;// array of vertices to iterate through
	private int iterN; // size of the array

	/**
	 * Constructor for VertexIterator
	 * 
	 * @param v
	 *            : Array of vertices
	 * @param n
	 *            : int - Size of the graph
	 */
	private VertexIterator(Vertex[] v, int n) {
	    nodeIndex = 0;
	    iterV = v;
	    iterN = n;
	}

	/**
	 * Method to check if there is any vertex left in the iteration
	 * Overrides the default hasNext() method of Iterator Class
	 */
	public boolean hasNext() {
	    return nodeIndex != iterN;
	}

	/**
	 * Method to return the next Vertex object in the iteration
	 * Overrides the default next() method of Iterator Class
	 */
	public Vertex next() {
	    nodeIndex++;
	    return iterV[nodeIndex];
	}

	/**
	 * Throws an error if a vertex is attempted to be removed
	 */
	public void remove() {
	    throw new UnsupportedOperationException();
	}
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	if (args.length > 0) {
	    File inputFile = new File(args[0]);
	    in = new Scanner(inputFile);
	} else {
	    in = new Scanner(System.in);
	}
	Graph g = readGraph(in);
	g.printGraph();
    }

    static Graph readGraph(Scanner in) {
	// read the graph related parameters
	int n = in.nextInt(); // number of vertices in the graph
	int m = in.nextInt(); // number of edges in the graph

	// create a graph instance
	Graph g = new Graph(n);
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    g.addEdge(u, v, w);
	}
	/*setting the index of vertices of the graph*/
	int i=1;
	for (Vertex v : g) {
		v.putIndex(i);
		i++;
	}
	g.initialize();
	in.close();
	return g;
    }


    /**
     * Method to initialize a graph
     *  1) Sets the parent of every vertex as null
     *  2) Sets the seen attribute of every vertex as false 
     *  3) Sets the distance of every vertex as infinite
     * 
     * @param g
     *            : Graph - The reference to the graph
     */
    void initialize() {
	for (Vertex u : this) {
	    u.seen = false;
	    u.parent = null;
	    u.weight = INFINITY;
	}
    }

    /**
     * Method to print the graph
     * 
     * @param g
     *            : Graph - The reference to the graph
     */
    void printGraph() {
	for (Vertex u : this) {
	    System.out.print(u + ": ");
	    for(Edge e: u.Adj) {
		System.out.print(e);
	    }
	    System.out.println();
	}
    }
}
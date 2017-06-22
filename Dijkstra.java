/**
 * Algorithm for the single-source shortest path problem in an undirected graph
 * whose edges have integer weights.
 *  @author Jeffrey Young , CSE13175 , 211015146
 */
public class Dijkstra<V, E> {
	/** Infinity value. */
	protected static final Integer INFINITE = Integer.MAX_VALUE;
	/** Input graph. */
	protected Graph<V, E> graph;
	/** Decoration key for edge weights */
	protected Object WEIGHT;
	/** Decoration key for vertex distances */
	protected Object DIST = new Object();
	/** Decoration key for entries in the priority queue */
	protected Object ENTRY = new Object();
	/** Auxiliary priority queue. */
	protected AdaptablePriorityQueue<Integer, Vertex<V>> Q;

	/**
	 * Executes the method when called, O(n^2) time
	 * 
	 * @param g
	 *            Input graph
	 * @param s
	 *            Source vertex
	 * @param w
	 *            Weight decoration object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Graph<V, E> g, Vertex<V> s, Object w) {
		graph = g;
		WEIGHT = w;
		DefaultComparator dc = new DefaultComparator();
		Q = new HeapAdaptablePriorityQueue<Integer, Vertex<V>>(dc);
		dijkstraVisit(s);
	}

	/**
	 * Get the distance of a vertex from the source vertex. Execute this method.
	 * Returns the length of a shortest path from the source to 'u' after
	 * execution has been called, O(1) time.
	 * 
	 * @param u
	 *            Start vertex for the shortest path tree
	 */
	public int getDist(Vertex<V> u) {
		return (Integer) u.get(DIST);
	}

	/**
	 * The actual execution of Dijkstra's algorithm, O(n^2) time.
	 * 
	 * @param v
	 *            source vertex.
	 */
	@SuppressWarnings("unchecked")
	protected void dijkstraVisit(Vertex<V> v) {
		// store all the vertices in priority queue Q
		for (Vertex<V> u : graph.vertices()) {
			int u_dist;
			if (u == v)
				u_dist = 0;
			else
				u_dist = INFINITE;
			Entry<Integer, Vertex<V>> u_entry = Q.insert(u_dist, u); // autoboxing
			u.put(ENTRY, u_entry);
		}
		while (!Q.isEmpty()) {
			Entry<Integer, Vertex<V>> u_entry = Q.min();
			Vertex<V> u = u_entry.getValue();
			int u_dist = u_entry.getKey();
			Q.remove(u_entry); // remove u from the priority queue
			u.put(DIST, u_dist); // the distance of u is final
			u.remove(ENTRY); // remove the entry decoration of u
			if (u_dist == INFINITE)
				continue; // unreachable vertices are not processed
			// examine all the neighbors of u and update their distances
			for (Edge<E> e : graph.incidentEdges(u)) {
				Vertex<V> z = graph.opposite(u, e);
				Entry<Integer, Vertex<V>> z_entry = (Entry<Integer, Vertex<V>>) z
						.get(ENTRY);
				if (z_entry != null) { // check that z is in Q, i.e., not in the
										// cloud
					int e_weight = (Integer) e.get(WEIGHT);
					int z_dist = z_entry.getKey();
					if (u_dist + e_weight < z_dist) // relaxation of edge e =
													// (u,z)
						Q.replaceKey(z_entry, u_dist + e_weight);
				}
			}
		}
	}
}

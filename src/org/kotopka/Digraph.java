package org.kotopka;

/**
 * {@code Digraph} - Simple digraph implementation.
 */
public class Digraph {

	private final Bag<Integer>[] adj;
	private final int[] indegree;
	private final int vertexCount;
	private int edgeCount;

	@SuppressWarnings("unchecked")
	public Digraph(int vertexCount) {
		if (vertexCount < 1) throw new IllegalArgumentException("Invalid vertex count");

		this.adj = (Bag<Integer>[]) new Bag[vertexCount];
		this.indegree = new int[vertexCount];
		this.vertexCount = vertexCount;

		for (int i = 0; i < adj.length; i++) {
			adj[i] = new Bag<>();
		}
	}

	public int V() {
		return vertexCount;
	}

	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);

		indegree[w]++;
		adj[v].add(w);
		edgeCount++;
	}

	public int E() { return edgeCount; }

	private void validateVertex(int vertex) {
		if (vertex < 0 || vertex > vertexCount) {
			throw new IllegalArgumentException("Invalid vertex: " + vertex);
		}
	}

	public int outdegree(int v) {
		validateVertex(v);

		return adj[v].size();
	}

	public int indegree(int v) {
		validateVertex(v);

		return indegree[v];
	}

	public Iterable<Integer> adj(int v) {
		validateVertex(v);

		return adj[v];
	}

	public Digraph reverse() {
		Digraph reverse = new Digraph(vertexCount);

		for (int v = 0; v < vertexCount; v++) {
			for (int w : adj(v)) {
				reverse.addEdge(w, v);
			}
		}

		return reverse;
	}

}

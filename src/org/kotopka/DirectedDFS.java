package org.kotopka;

/**
 * {@code DirectedDFS} - Tests for vertex reachability from source or multiple sources.
 * WARNING: Only does bare-minimum input validation
 */
public class DirectedDFS {

    private final boolean[] marked;

    public DirectedDFS(Digraph G, int s) {
        if (G == null) throw new IllegalArgumentException("Digraph G cannot be null");
        if (s < 0) throw new IllegalArgumentException("Invalid source " + s);

        this.marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        if (G == null) throw new IllegalArgumentException("Digraph G cannot be null");
        if (sources == null) throw new IllegalArgumentException("Sources cannot be null");

        // should do something like  this but not going to worry about it for this particular use-case
//        for (Integer i : sources) {
//            if (i == null || i < 0 || i > G.V()) {
//                throw new IllegalArgumentException("Invalid source");
//            }
//        }

        this.marked = new boolean[G.V()];

        for (int s : sources) {
            if (!marked[s]) dfs(G, s);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean marked(int v) {
        if (v < 0) throw new IllegalArgumentException("Invalid vertex " + v);

        return marked[v];
    }

}

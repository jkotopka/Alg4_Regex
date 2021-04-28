package org.kotopka;

public class DirectedDFS {

    private final boolean[] marked;

    public DirectedDFS(Digraph G, int s) {
        this.marked = new boolean[G.V()];

    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
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
        return marked[v];
    }

}
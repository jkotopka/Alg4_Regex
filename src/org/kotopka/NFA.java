package org.kotopka;

/**
 * {@code NFA} - Nondeterministic Finite-state Automaton for Regular Expression parsing.
 */
public class NFA {

    private final char[] re; // match transitions
    private final Digraph G; // epsilon transitions
    private final int m;     // number of states

    public NFA(String regexp) {
        this.re = regexp.toCharArray();
        this.m = re.length;
        this.G = new Digraph(m + 1);

        buildEpsilonTransitionDigraph();
    }

    private void buildEpsilonTransitionDigraph() {
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < m; i++) {
            int lp = i;

            // special epsilon-transition rules for () groups and the | or operator
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();

                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }

            // lookahead for a * closure character
            if (i < m - 1 && re[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }

            // these metachars always have an epsilon-transition to the next char
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i + 1);
            }
        }
    }

    public boolean recognizes(String txt) {
        // does the NFA recognize the text?
        Bag<Integer> pc = new Bag<>(); // pc = "program counter"
        DirectedDFS dfs = new DirectedDFS(G, 0);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) pc.add(v);
        }

        for (int i = 0; i < txt.length(); i++) {
            // compute possible NFA states for txt[i + 1]
            Bag<Integer> states = new Bag<>();

            for (int v : pc) {
                if (v < m) {
                    if (re[v] == txt.charAt(i) || re[v] == '.') { // dot . is wildcard char. "wildchar" as it were
                        states.add(v + 1);
                    }
                }
            }

            pc = new Bag<>();
            dfs = new DirectedDFS(G, states);

            for (int v = 0; v < G.V(); v++) {
                if (dfs.marked(v)) pc.add(v);
            }
        }

        for (int v : pc) {
            if (v == m) return true;
        }

        return  false;
    }

    public static void main(String[] args) {
        NFA nfa = new NFA("((A*B|AC)D)");
        System.out.println(nfa.recognizes("AABD"));
        System.out.println(nfa.recognizes("ACBD"));
        System.out.println(nfa.recognizes("ACD"));
        System.out.println(nfa.recognizes("AAABD"));
    }
}

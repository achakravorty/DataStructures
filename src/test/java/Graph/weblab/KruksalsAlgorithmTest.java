package Graph.weblab;

import Graph.weblab.KruksalsAlgorithm.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class KruksalsAlgorithmTest {

    /**
     * Tests if solution works for the given input.
     * Important because there might be different possible MST's for the same graph.
     *
     * @param mst_cost
     *     the real cost of a MST on that graph
     * @param edges
     *     the edges that comprise of the graph whose MST we are creating
     * @param n
     *     the amount of nodes in the graph that the MST should reach
     */
    void assertMST(int mst_cost, List<Edge> edges, int n) {
        // Run solution (& verify that input was not modified)
        List<Edge> original_edges = new ArrayList<>(edges);
        List<Edge> solution = KruksalsAlgorithm.buildMST(n, edges);
        assertEquals(original_edges, edges, "You should not modify the original input!");
        // Test if it even is a spanning tree
        UnionFind uf = new UnionFind(n);
        // Do we have n - 1 edges?
        assertEquals(
                n - 1,
                solution.size(),
                "A spanning tree would have "
                        + (n - 1)
                        + " edges instead of "
                        + solution.size()
                        + "!");
        // Are they all useful? (Do they connect different clusters?)
        for (Edge e : solution) assertTrue(uf.union(e.getFrom(), e.getTo()));
        // Test if it is a minimum spanning tree
        int result_cost = solution.stream().mapToInt(Edge::getCost).sum();
        assertEquals(mst_cost, result_cost);
    }

    List<Edge> buildEdges(int[] info, int m) {
        List<Edge> solution = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int start = i * 3;
            solution.add(new Edge(info[start], info[start + 1], info[start + 2]));
        }
        return solution;
    }

    @Test
    @Timeout(unit = TimeUnit.MILLISECONDS, value = 100)
    public void emptyGraphTest() {
        List<Edge> solution = KruksalsAlgorithm.buildMST(0, new ArrayList<>());
        assertTrue(solution.isEmpty(), "An empty graph should have an empty MST.");
    }

    /**
     * Makes sure they don't just add the smallest cost edge.
     * Makes sure they don't skip necessary edges (most costly edge is necessary)
     *
     * Graph: https://i.imgur.com/BdvaXpV.png
     * MST: https://i.imgur.com/RS9poLa.png
     */
    @Test
    @Timeout(unit = TimeUnit.MILLISECONDS, value = 100)
    public void completeGraphTest() {
        int[] info = new int[] {0, 1, 1, 0, 2, 1, 1, 2, 2, 1, 3, 3, 3, 4, 2, 3, 5, 1, 4, 5, 1};
        List<Edge> input = buildEdges(info, 7);
        assertMST(7, input, 6);
    }

}
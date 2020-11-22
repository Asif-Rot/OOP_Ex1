package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g;
    private HashMap<Integer, node_info> prev;

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * Returning the graph
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return g;
    }

    /**
     * Compute a deep copy of this graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        weighted_graph gn = new WGraph_DS();

        for (node_info n : this.g.getV()) {
            gn.addNode(n.getKey()); // Adding the nodes to the copied grpah
        }

        for (node_info n1 : this.g.getV()) {
            for (node_info n2 : this.g.getV(n1.getKey())) {
                gn.connect(n1.getKey(), n2.getKey(), g.getEdge(n1.getKey(),n2.getKey())); // Connecting the nodes to their neighbors
            }
        }
        return gn;
    }

    /**
     * Implementing Dijkstra algorithm for finding the shortest paths between nodes in a graph.
     * For a given source node in the graph, the algorithm finds the shortest path between that node and every other,
     * while considering the weight of the edge between nodes and take the most "profitable" path to the other node.
     * @param src
     */
    private void Dijkstra(node_info src) {
        Queue<node_info> pq = new PriorityQueue<>();
        HashMap<Integer, Boolean> vis = new HashMap<>();
        prev = new HashMap<>();

        for (node_info n : g.getV()) {
            n.setTag(Double.MAX_VALUE);
            prev.put(n.getKey(), null);
            vis.put(n.getKey(), false);
        }

        g.getNode(src.getKey()).setTag(0);
        pq.add(src);

        while (!pq.isEmpty()) {
            node_info u = pq.poll();
            vis.replace(u.getKey(),true);

            for (node_info n : g.getV(u.getKey())) {
                if (!vis.get(n.getKey())) {
                    double alt = u.getTag() + g.getEdge(u.getKey(), n.getKey());
                    if (alt < n.getTag()) {
                        n.setTag(alt);
                        prev.put(n.getKey(), u);
                        pq.add(n);
                    }
                }
            }
        }
    }

    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 0 || g.nodeSize() == 1)
            return true;
        Dijkstra(g.getV().stream().findFirst().orElse(new WGraph_DS.NodeInfo())); // Goes through the graph from the first node and checks that the graph is connected

        for (node_info n: g.getV()) {
            if (n.getTag() == Double.MAX_VALUE) // If a tag is infinite, it means the Dijkstra algorithm didn't reach it
                return false;
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (shortestPath(src, dest) == null) // if shortestPath returns null, there is no path
            return -1;
        return g.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (g.getNode(src) == null || g.getNode(dest) == null) // Checking if the keys exist
            return null;
        Dijkstra(g.getNode(src)); //Goes through the graph

        Stack<node_info> path = new Stack<>(); // To keep path in stack
        LinkedList<node_info> reverse = new LinkedList<>(); // To reverse the path from end to start

        path.add(g.getNode(dest)); // Adding the destination
        for (node_info i = prev.get(dest); i != null; i = prev.get(i.getKey())) { // Going backwards in Prev
            path.add(i);
        }
        if (path.peek() != g.getNode(src)) // If the path doesn't contain source, that means it's not connected from source to dest
            return null;

        while (!path.isEmpty()) {
            reverse.add(path.pop()); // reversing the path
        }

        return reverse;
    }

    /**
     * Save graph in a file
     * @param file - the file name (may include a relative path).
     * @return
     */

    @Override
    public boolean save(String file) {

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            StringBuilder sb = new StringBuilder();

            for (node_info n1 : g.getV()) {
                for (node_info n2 : g.getV(n1.getKey())) {
                    if (g.hasEdge(n1.getKey(),n2.getKey())) {
                            sb.append(n1.getKey()); // First node
                            sb.append(",");
                            sb.append(n2.getKey()); // Second node
                            sb.append(",");
                            sb.append(g.getEdge(n1.getKey(), n2.getKey())); // getting the edge between them
                    }

                    sb.append("\n");
                    pw.write(sb.toString());
                    sb.setLength(0);
                }
            }
            pw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Loading the saved graph from file
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        String line;
        weighted_graph gload = new WGraph_DS();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(",");
                node_info n1 = new WGraph_DS.NodeInfo(Integer.parseInt(userInfo[0])); // First node
                node_info n2 = new WGraph_DS.NodeInfo(Integer.parseInt(userInfo[1])); // Second node
                // System.out.println("Key: " + userInfo[0] + ", " + "Edge to " + userInfo[1] + " : " + userInfo[2]);
                if(gload.getNode(n1.getKey()) == null)
                    gload.addNode(n1.getKey());
                if (!gload.hasEdge(n1.getKey(), n2.getKey()))
                    gload.connect(n1.getKey(), n2.getKey(), Double.parseDouble(userInfo[2]));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        g = gload;
        return true;
    }
}

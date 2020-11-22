package ex1.src;

import java.util.*;

public class WGraph_DS implements weighted_graph {
    private HashMap<Integer, node_info> nodesMap;
    private HashMap<Integer,HashMap<Integer,Double>> edgeMap;
    private int numberofedges;
    private int modecount;

    public static class NodeInfo implements node_info, Comparable<NodeInfo> {
        private static int counter = 0;
        private int key;
        private String info;
        private double tag;

        public NodeInfo () {
            key = counter++;
            info = null;
            tag = 0;
        }

        public NodeInfo (int key) {
            this.key = key;
            info = null;
            tag = 0;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            tag = t;
        }

        @Override
        public String toString() {
            return "key=" + key + "," + "Tag=" + tag;
        }

        /**
         * For ordering the nodes by Tag
         * @param o
         * @return
         */
        @Override
        public int compareTo(NodeInfo o) {
            if(this.tag - o.getTag() > 0) return 1;
            return -1;
        }
    }

    public WGraph_DS () {
        nodesMap = new HashMap<>();
        numberofedges = 0;
        modecount = 0;
        edgeMap = new HashMap<>();
    }

    /**
     * Getting a node from the graph
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        if (!nodesMap.containsKey(key))
            return null;
        return nodesMap.get(key);
    }

    /**
     * Checking if the keys (node1,node2) are connected
     * @param node1 - key 1
     * @param node2 - key 2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2)
            return false;
        if (edgeMap.containsKey(node1) && edgeMap.containsKey(node2))
            return edgeMap.get(node1).containsKey(node2);
        return false;
    }

    /**
     * Returning the edge between two nodes
     * @param node1 - key 1
     * @param node2 - key 2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if((edgeMap.containsKey(node1) && edgeMap.containsKey(node2))
                && edgeMap.get(node1).containsKey(node2))
            return edgeMap.get(node1).get(node2);
        return -1;
    }

    /**
     * Adding a node to the grap
     * @param key - The node_id
     */
    @Override
    public void addNode(int key) {
        nodesMap.put(key, new NodeInfo(key));
        edgeMap.put(key, new HashMap<>());
        modecount++;
    }

    /**
     * Connecting two nodes
     * @param node1 - key 1
     * @param node2 - key 2
     * @param w - the weight of the edge between them
     */
    @Override
    public void connect(int node1, int node2, double w) {
       if ((!edgeMap.containsKey(node1)) || (!edgeMap.containsKey(node2)))
           return;
       if (node1 == node2)
           return;

       if (edgeMap.get(node1).containsKey(node2)) { // If the nodes are already connected, update the weight
           edgeMap.get(node1).replace(node2, w);
           edgeMap.get(node2).replace(node1, w);
           return;
       }

        edgeMap.get(node1).put(node2,w); // Connecting node1 -> node2
        edgeMap.get(node2).put(node1,w); // Connecting node2 -> node1

        numberofedges++;
        modecount++;
    }

    /**
     * @return all the nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        return nodesMap.values();
    }

    /**
     * @param node_id - key
     * @return all the nodes connected to node_id
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (!nodesMap.containsKey(node_id))
            return null;

        Set<Integer> keys = edgeMap.get(node_id).keySet(); // Get neighbors keys
        ArrayList<node_info> neighbors = new ArrayList<>(keys.size()); // ArrayList for collection
        for (Integer k : keys) { // iterate through the keys
            neighbors.add(nodesMap.get(k));
            }
        return neighbors;
    }

    /**
     * Removing node and the edges to it
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if (getNode(key) == null)
            return null;
        for (node_info n: getV(key)) {
            edgeMap.get(n.getKey()).remove(key);
            numberofedges--;
            modecount++;
        }
        edgeMap.remove(key);
        modecount++;
        return nodesMap.remove(key);

    }

    /**
     * Remove edge between 2 nodes
     * @param node1 - key 1
     * @param node2 - key 2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        edgeMap.get(node1).remove(node2);
        edgeMap.get(node2).remove(node1);

        numberofedges--;
        modecount++;
    }

    /**
     * @return the number of vertices (nodes) in the graph
     */
    @Override
    public int nodeSize() {
        return nodesMap.size();
    }

    /**
     * @return the number of edges
     */
    @Override
    public int edgeSize() {
        return numberofedges;
    }

    /**
     * @return number of changes in the graph
     */
    @Override
    public int getMC() {
        return modecount;
    }

    @Override
    public String toString() {
        return "" + edgeMap;
    }

    /**
     * Overriding equals function for testing
     * @param o - Object that will be casted
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        for (node_info n1: wGraph_ds.getV()) {
            node_info node = wGraph_ds.getNode(n1.getKey());
            if (node == null)
                return false;
            for (node_info n2: wGraph_ds.getV(n1.getKey())) {
                if (wGraph_ds.getEdge(n1.getKey(), n2.getKey()) != getEdge(n1.getKey(), n2.getKey()))
                    return false;
            }
        }
        return wGraph_ds.nodeSize() == nodeSize() && wGraph_ds.edgeSize() == edgeSize();
    }

}

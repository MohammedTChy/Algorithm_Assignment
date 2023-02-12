/**
 * @author Karl Rameld kara 9436
 * @author Mohammed Tahmid Chowdhury moch8386
 */

package in6.src;

import java.util.*;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

    private Set<Connection> zeroConnection = new HashSet<Connection>();

    private Map<Node, Set<Connection>> map = new HashMap<Node, Set<Connection>>(); // list maybe idk

    class Node {
        private T value;
        private boolean visited;

        Node() {
        }

        Node(T t) {
            this.value = t;
            this.visited = false;
        }

        public T getValue() {
            return value;
        }

        public boolean getVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public int hashCode() { 
            int sum = 0;
            int prime = 31;
            String s = this.toString();
            for (int i = 0; i < s.length(); i++) {
                sum += s.charAt(i) * prime * (s.length() - i);
            }
            return sum;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MyUndirectedGraph.Node) {
                if (obj.toString().equals(toString())) { // TODO: can we use String equals?
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    class Connection {
        private Node nodeStart;
        private Node nodeEnd;
        private int weight;

        Connection(Node nodeStart, Node nodeEnd, int weight) {
            this.nodeStart = nodeStart;
            this.nodeEnd = nodeEnd;
            this.weight = weight;
        }

        public Node getNodeStart() {
            return nodeStart;
        }

        public Node getNodeEnd() {
            return nodeEnd;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int hashCode() {
            return nodeStart.hashCode() * 17 + nodeEnd.hashCode() * 31 * weight;
        }

        public boolean equals(Object obj) { // TODO: is it okay to have a equals that doesnt mean the two objects are
                                            // equal?
            if (obj instanceof MyUndirectedGraph.Connection) { // this should avoid complications caused by the type safety right?
                Connection c = (Connection) obj;
                if ((c.getNodeStart().equals(nodeStart) && c.getNodeEnd().equals(nodeEnd))
                        || (c.getNodeStart().equals(nodeEnd) && c.getNodeEnd().equals(nodeStart))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return nodeStart.toString() + " " + nodeEnd.toString() + " " + weight;
        }
    }

    @Override 
    public int getNumberOfNodes() {
        int sum = 0;
        for (Node n : map.keySet()) {
            sum += 1;
        }
        return sum;
    }

    @Override
    public int getNumberOfEdges() {
        int sum = 0;
        Set<Connection> countSet = new HashSet<>();
        for (Set<Connection> c : map.values()) {
            countSet.addAll(c);
        }
        Iterator<Connection> i = countSet.iterator();
        while (i.hasNext()) {
            sum += 1;
        }
        return sum;
    }

    @Override
    public boolean add(T data) {
        Node n = new Node(data);
        if (!map.containsKey(n)) {
            map.put(n, zeroConnection);
            return true;
        }
        return false;
    }

    @Override
    public boolean connect(T nodeStart, T nodeEnd, int cost) {
        if (cost < 1)
            return false;
        Node nodeOne = getNodeWithValueParam(nodeStart);
        Node nodeTwo = getNodeWithValueParam(nodeEnd);
        if (map.containsKey(nodeOne) && map.containsKey(nodeTwo)) {
            establishConnectionsDual(nodeOne, nodeTwo, cost);
            return true;
        }
        return false;
    }

    private void establishConnectionsDual(Node nodeOne, Node nodeTwo, int cost) {
        connector(nodeOne, nodeTwo, cost);
        connector(nodeTwo, nodeOne, cost);
    }

    private void connector(Node nodeStart, Node nodeEnd, int cost) {
        Connection newCon = new Connection(nodeStart, nodeEnd, cost);
        Set<Connection> set = new HashSet<>(map.get(nodeStart));
        Set<Connection> remove = new HashSet<>();
        for (Connection c : set) {
            if (c.equals(newCon))
                remove.add(c);
        }
        for (Connection c : remove) {
            set.remove(c);
        }
        set.add(newCon);
        map.replace(nodeStart, set);
    }

    @Override
    public boolean isConnected(T nodeStart, T nodeEnd) {
        return findConnection(nodeStart, nodeEnd) != null;
    }

    @Override
    public int getCost(T nodeStart, T nodeEnd) {
        Connection c = findConnection(nodeStart, nodeEnd);
        if (c != null)
            return c.getWeight();
        return -1;
    }

    private Connection findConnection(T nodeOne, T nodeEnd) {
        Connection testConnection = new Connection(new Node(nodeOne), new Node(nodeEnd), 0);
        Node n = new Node(nodeOne);
        if (map.containsKey(n)) {
            Set<Connection> set = map.get(n);
            for (Connection c : set) {
                if (c.equals(testConnection))
                    return c;
            }
        }
        return null;
    }

    @Override
    public List<T> depthFirstSearch(T start, T end) {
        Node currentNode = getNodeWithValueParam(start);
        Stack<T> path = new Stack<>();
        do {
            boolean found = false;
            Set<Connection> con = map.get(currentNode);
            currentNode.setVisited(true);
            if (currentNode.getValue().equals(end)) {
                path.push(currentNode.getValue());
                clearVisitited();
                return path;
            }
            for (Connection c : con) {
                if (!c.getNodeEnd().getVisited()) {
                    path.push(currentNode.getValue());
                    currentNode = c.getNodeEnd();
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentNode = new Node(path.pop());
            }
        } while (!path.isEmpty());
        clearVisitited();
        return path;
    }

    @Override
    public List<T> breadthFirstSearch(T start, T end) {
        LinkedList<Node> queue = new LinkedList<>();
        HashMap<Node, Node> previous = new HashMap<>();
        Node currentNode = getNodeWithValueParam(start);
        queue.add(currentNode);
        do {
            currentNode = queue.removeFirst();
            currentNode.setVisited(true);
            if (currentNode.getValue().equals(end)) {
                clearVisitited();
                return getPath(previous, currentNode);
            }
            Set<Connection> con = map.get(currentNode);
            for (Connection c : con) {
                if (!c.getNodeEnd().getVisited()) {
                    previous.put(c.getNodeEnd(), currentNode);
                    queue.add(c.getNodeEnd());
                }
            }
        } while (!queue.isEmpty());
        List<T> empty = new LinkedList<>();
        clearVisitited();
        return empty;
    }

    private List<T> getPath(HashMap<Node, Node> prev, Node start) {
        LinkedList<T> path = new LinkedList<>();
        Node currentNode = start;
        do {
            path.addFirst(currentNode.getValue());
            currentNode = prev.get(currentNode);
        } while (currentNode != null);
        return path;
    }

    /**
     * I Used wikipedias informal desc to construct the followuing method, it does
     * not exacly follow their model.
     * 
     * Quote from https://en.wikipedia.org/wiki/Prim%27s_algorithm :
     * 
     * "The algorithm may informally be described as performing the following steps:
     * Initialize a tree with a single vertex, chosen arbitrarily from the graph.
     * Grow the tree by one edge: of the edges that connect the tree to vertices not
     * yet in the tree, find the minimum-weight edge, and transfer it to the tree.
     * Repeat step 2 (until all vertices are in the tree)."
     * 
     */
    @Override
    public UndirectedGraph<T> minimumSpanningTree() {
        MyUndirectedGraph<T> min = new MyUndirectedGraph<T>();
        Node start = getNode();
        min.add(start.getValue());
        start.setVisited(true);
        while (min.getNumberOfNodes() < getNumberOfNodes()) {
            int changeLater = Integer.MAX_VALUE;
            Node connect = null;
            for (Node n : min.map.keySet()) {
                for (Connection c : map.get(n)) {
                    if (!c.getNodeEnd().getVisited() && c.getWeight() < changeLater) {
                        connect = c.getNodeEnd();
                        changeLater = c.getWeight();
                    }
                }
            }
            min.add(connect.getValue());
            min.connect(start.getValue(), connect.getValue(), changeLater);
            connect.setVisited(true);
            start = connect;
        }
        clearVisitited();
        return min;
    }

    private void clearVisitited() {
        for (Node n : map.keySet()) {
            n.setVisited(false);
        }
    }

    private Node getNode() { // TODO: change this into something slightly better looking later.
        for (Node n : map.keySet()) {
            return n;
        }
        return null;
    }

    private Node getNodeWithValueParam(T t) {
        for (Node n : map.keySet()) {
            if (n.getValue().equals(t))
                return n;
        }
        return null;
    }

}

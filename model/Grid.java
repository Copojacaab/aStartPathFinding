package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Grid {

    private Random rand = new Random();

    private int width;
    private int height;
    private Node[][] nodes;

    private Node startNode;
    private Node endNode;

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        this.nodes = new Node[height][width]; //prima height per row

        // init della griglia
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                nodes[y][x] = new Node(x, y);
            }
        }
    }

    public Node getNode(int x, int y){
        if(x >= 0 && x < width && y >= 0 && y < height){
            return nodes[y][x];
        }
        return null;
    }

    /**Trova i vicini walkable del nodo  */
    public List<Node> getNeighbors(Node node){
        List<Node> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();

        // vicini
        int[] dx = {0,0,1,-1};
        int[] dy = {1,-1,0,0};

        for(int i = 0; i < 4; i++){
            int newX = x + dx[i];
            int newY = y + dy[i];

            Node neighbor = getNode(newX, newY);
            // check per validita e walkable
            if (neighbor != null && neighbor.isWalkable()) {
                neighbors.add(neighbor);
            }
        }
        // se voglio implementare walkable diagonale, aggiungo qui
        return neighbors;
    }

    /**resetta lo stato dell'algoritmo su tutti i nodi */
    public void resetAlgorithmState(){
        for (int y = 0; y < height; y++){
            for(int x = 0; x < width; x++)
                nodes[y][x].resetCosts();
        }
    }

    /*resetta tutto */
    public void resetAllNodes() {
        for(int y=0; y < height; y++){
            for(int x = 0; x < width; x++){
                nodes[y][x].resetFull();
            }
        }
        this.startNode = null;
        this.endNode = null;
    }

    /**generazione di un labirinto casuale 
     * tolto metodo statistico (prob < 0.3)
     * Recursive Backtracer
     */
    public void generateRandomMaze(){
        // cleanup totale
        resetAllNodes();

        // tutti i node wall
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                nodes[y][x].setType(NodeType.WALL);
            }
        }
        // primo nodo (0,0)
        Node dfsStart = getNode(0, 0);
        dfsStart.setType(NodeType.EMPTY);
        Stack <Node> stack = new Stack<>();
        stack.push(dfsStart);
        // loop principale
        while (!stack.isEmpty()) {
            Node current = stack.peek(); //non rimuovo il primo nodo dalla lista
            List<Node> neighbors = getUnvisitedNeighborsDFS(current);

            if(!neighbors.isEmpty()){
                int randomIndex = rand.nextInt(0, neighbors.size());
                // prendo un vicino a caso
                Node next = neighbors.get(randomIndex);
                removeWallBeetween(current, next);
                next.setType(NodeType.EMPTY); // libero il nodo
                stack.push(next); //lo metto nei visititati
            } else {
                stack.pop();
            }
        }
    }

    // setters
    public void setStartNode(Node newStart){
        // check su quello vecchio
        if(startNode != null)
            startNode.setType(NodeType.EMPTY);
        
        newStart.setType(NodeType.START);
        this.startNode = newStart;
    }

    public void setEndNode(Node newEnd){
        if (endNode != null)
            endNode.setType(NodeType.EMPTY);
            
        newEnd.setType(NodeType.END);
        this.endNode = newEnd;
    }

    /**
     * ------------------------------ HELPER ---------------------------
     */

    // metodo per restituire i vicini a distanza 2 per dfs (randomizeMaze)
    private List<Node> getUnvisitedNeighborsDFS(Node currentNode){
        List<Node> neighList = new ArrayList<Node>();
        int xCoord = currentNode.getX();
        int yCoord = currentNode.getY();

        int[] xDiff = {-2,2,0,0};
        int[] yDiff = {0,0,-2,2};

        for (int i = 0; i < 4; i++) {
            int neighXCoord = xCoord + xDiff[i];
            int neighYCoord = yCoord + yDiff[i];
            Node neighNode = getNode(neighXCoord, neighYCoord);

            if (neighNode != null && neighNode.getType() == NodeType.WALL) {
                neighList.add(neighNode);
            }
        }

        return neighList;
    }

    // rimuove il muro tra la cella scelta e il vicino randomico DFS
    private void removeWallBeetween(Node from, Node to){
        int wallX = (from.getX() + to.getX()) / 2;
        int wallY = (from.getY() + to.getY()) / 2;

        getNode(wallX, wallY).setType(NodeType.EMPTY);
    }

    // Basic getters and setters
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public Node getStartNode() { return this.startNode; }
    public Node getEndNode() { return this.endNode; }
    public NodeType genNodeType(int x, int y) {
        Node node = getNode(x, y);

        if(node != null){
            return node.getType();
        }

        return null;
    }
}
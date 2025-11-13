package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    
    private int width;
    private int height;
    private Node[][] nodes;

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
    }
    // Basic getters and setters
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
}
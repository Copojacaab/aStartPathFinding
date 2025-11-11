package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import model.*;
import javax.swing.SwingWorker;

import model.Grid;
// ritorna una List<Node>, non pubblica niente a run time
public class AStarSolver extends SwingWorker<List<Node>, Void>{
    
    Grid grid;
    Node startNode;
    Node endNode;
    
    // Stack per aStar
    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;

    public AStarSolver(Grid grid, Node start, Node end){
        this.grid = grid;
        this.startNode = start;
        this.endNode = end;
    }

    @Override
    protected List<Node> doInBackground() throws Exception {
        // 1. Pqueue per la open e Hash per la closed
        openList = new PriorityQueue<>(
            (node1, node2) -> Double.compare(node1.getFCost(), node2.getFCost()
        ));
        closedList = new HashSet<>();
        // 2. init dello start (costi)
        startNode.setGCost(0);
        startNode.setHCost(calcHeuristic(startNode, endNode));
        startNode.setFCost(startNode.getHCost());
        
        openList.add(startNode); // aggiungo il primo nodo

        // 3. ciclo principale (estraggo finche non trovo end o finisce la open)
        while (!openList.isEmpty()) {
            Node currentNode = openList.poll(); //estraggo il best
            closedList.add(currentNode);
            // check
            if (currentNode == endNode) {
                // backtracing
                reconstructPath(endNode);
            }
            // check dei neighbor
            for (Node neighbor : grid.getNeighbors(currentNode)) {
                // salto se gia' visto
                if (closedList.contains(neighbor)) {
                    continue;
                }
                // ogni mossa costa 1
                double tentativeGCost = currentNode.getGCost() + 1;
                if (tentativeGCost < neighbor.getGCost()) { // se si, trovato percorso migliore
                    neighbor.setParentNode(currentNode); //costi
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calcHeuristic(neighbor, endNode));
                    neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
                    openList.add(neighbor); //buono, aggiungo alla open
                }
            }
        }
        return null; //non esiste percorso
    }

    public void done(){
        
    }
    
    // ---------------- HELPER -------------
    // distanza di manhattan
    private double calcHeuristic(Node from, Node to){
        return (Math.abs(to.getX() - from.getX()) + 
                Math.abs(to.getY() - from.getY()));
    }

    // backtracing del percorso finale
    private List<Node> reconstructPath(Node endNode){
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = current.getParentNode();
        }
        // inverto la lista
        Collections.reverse(path);
        return path;
    }
}

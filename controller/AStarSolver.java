package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import model.*;
import view.GridPanel;
import javax.swing.SwingWorker;

import model.Grid;
// ritorna una List<Node>, non pubblica niente a run time
public class AStarSolver extends SwingWorker<List<Node>, Node>{
    
    Grid grid;
    Node startNode;
    Node endNode;
    // visualizer a runtime
    GridPanel gridPanel;
    Double heuristicWeight;

    // Stack per aStar
    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;

    public AStarSolver(Grid grid, Node start, Node end, GridPanel gridPanel, Double heuristicWeight){
        this.grid = grid;
        this.startNode = start;
        this.endNode = end;
        this.gridPanel = gridPanel;
        this.heuristicWeight =heuristicWeight;
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
        startNode.setHCost(calcHeuristic(startNode, endNode, this.heuristicWeight));
        startNode.setFCost(startNode.getHCost());
        
        openList.add(startNode); // aggiungo il primo nodo

        // Lista per il batchin
        ArrayList<Node> batch = new ArrayList();
        final int BATCH_SIZE = 10;
        // 3. ciclo principale (estraggo finche non trovo end o finisce la open)
        while (!openList.isEmpty()) {
            sleep(5);
            Node currentNode = openList.poll(); //estraggo il best
            closedList.add(currentNode); //chiuso
            //change dello status a closed
            if(currentNode.getType() != NodeType.START && currentNode.getType() != NodeType.END){
                currentNode.setType(NodeType.CLOSED);
                batch.add(currentNode);
                if(batch.size() >= BATCH_SIZE){
                    publish(batch.toArray(new Node[0]));
                    batch = new ArrayList<>();
                }
            }
                // check
            if (currentNode == endNode) {
                if (!batch.isEmpty()) {
                    publish(batch.toArray(new Node[0]));
                }
                // backtracing
                return reconstructPath(endNode);
            }
            // check dei neighbor
            for (Node neighbor : grid.getNeighbors(currentNode)) {
                sleep(2);
                // salto se gia' visto
                if (closedList.contains(neighbor)) {
                    continue;
                }
                // ogni mossa costa 1
                double tentativeGCost = currentNode.getGCost() + 1;
                if (tentativeGCost < neighbor.getGCost()) { // se si, trovato percorso migliore
                    neighbor.setParentNode(currentNode); //costi
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calcHeuristic(neighbor, endNode, this.heuristicWeight));
                    neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
                    if(!openList.contains(neighbor))
                        openList.add(neighbor); //buono, aggiungo alla open
                    // change dello status per visualizer
                    if(neighbor.getType() == NodeType.EMPTY){
                        neighbor.setType(NodeType.OPEN);
                        batch.add(neighbor); 
                    }
                }
            }
        }
        // pubblico l'ultimo batch se non e' vuoto
        if(!batch.isEmpty()){
            publish(batch.toArray(new Node[0]));
        }
        return null; //non esiste percorso
    }

    /**
     * ordina il repaint al gridpanel
     */
    @Override
    protected void process(List<Node> chunks){
        /**
         * for(Node n : chunks){
         *  gridPanel.repaint()
         * }
         */
        // so che il Model e' aggiornato (condiviso)
        this.gridPanel.repaint();
    }
    
    // ---------------- HELPER -------------
    // distanza di manhattan
    private double calcHeuristic(Node from, Node to, Double heuristicWeight){
        return (Math.abs(to.getX() - from.getX()) + 
                Math.abs(to.getY() - from.getY())) * heuristicWeight;
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

    private void sleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}

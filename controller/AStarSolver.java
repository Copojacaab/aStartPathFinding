package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.SwingWorker;

import model.Grid;
import model.Node;
import model.NodeType;
import view.GridPanel;

/**
 * Implementazione dell'algoritmo A* come SwingWorker.
 * Esegue la ricerca in background per non bloccare la UI.
 * Ritorna la lista di nodi del percorso finale.
 */
public class AStarSolver extends SwingWorker<List<Node>, Node>{
    
    Grid grid;
    Node startNode;
    Node endNode;
    GridPanel gridPanel;
    Double heuristicWeight;

    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;
    
    // Contatore per i nodi esaminati (Closed List Size)
    private int exploredCount;

    public AStarSolver(Grid grid, Node start, Node end, GridPanel gridPanel, Double heuristicWeight){
        this.grid = grid;
        this.startNode = start;
        this.endNode = end;
        this.gridPanel = gridPanel;
        this.heuristicWeight =heuristicWeight;
    }

    @Override
    protected List<Node> doInBackground() throws Exception {
        // Init delle strutture dati
        openList = new PriorityQueue<>((node1, node2) -> Double.compare(node1.getFCost(), node2.getFCost()));
        closedList = new HashSet<>();
        
        // Setup del nodo di partenza
        startNode.setGCost(0);
        startNode.setHCost(calcHeuristic(startNode, endNode, this.heuristicWeight));
        startNode.setFCost(startNode.getHCost());
        
        openList.add(startNode); 

        // Buffer per il repaint batch
        ArrayList<Node> batch = new ArrayList<Node>();
        final int BATCH_SIZE = 10;
        
        // Ciclo A* principale
        while (!openList.isEmpty()) {
            sleep(5); // rallenta per la visualizzazione
            Node currentNode = openList.poll(); 
            closedList.add(currentNode); 
            
            // Aggiorna lo stato per il visualizer
            if(currentNode.getType() != NodeType.START && currentNode.getType() != NodeType.END){
                currentNode.setType(NodeType.CLOSED);
                batch.add(currentNode);
                if(batch.size() >= BATCH_SIZE){
                    publish(batch.toArray(new Node[0]));
                    batch = new ArrayList<>();
                }
            }
            
            // Condizione di uscita: destinazione raggiunta
            if (currentNode == endNode) {
                if (!batch.isEmpty()) {
                    publish(batch.toArray(new Node[0]));
                }
                this.exploredCount = closedList.size(); // Salva il risultato
                return reconstructPath(endNode);
            }
            
            // Esamina i vicini
            for (Node neighbor : grid.getNeighbors(currentNode)) {
                sleep(2);
                if (closedList.contains(neighbor)) {
                    continue;
                }
                
                // G-Cost provvisorio (costo di 1 per mossa ortogonale)
                double tentativeGCost = currentNode.getGCost() + 1;
                
                if (tentativeGCost < neighbor.getGCost()) { 
                    // Trovato percorso migliore. Aggiorna i costi.
                    neighbor.setParentNode(currentNode); 
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calcHeuristic(neighbor, endNode, this.heuristicWeight));
                    neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
                    
                    if(!openList.contains(neighbor))
                        openList.add(neighbor); 
                        
                    // Aggiorna lo stato del nodo per la visualizzazione
                    if(neighbor.getType() == NodeType.EMPTY){
                        neighbor.setType(NodeType.OPEN);
                        batch.add(neighbor); 
                    }
                }
            }
        }
        
        // Pubblica l'ultimo batch se necessario
        if(!batch.isEmpty()){
            publish(batch.toArray(new Node[0]));
        }
        
        this.exploredCount = closedList.size(); // Salva il risultato anche in caso di fallimento
        return null; // Percorso non trovato
    }

    /** Ordina il repaint al GridPanel in base ai chunk pubblicati. */
    @Override
    protected void process(List<Node> chunks){
        this.gridPanel.repaint();
    }
    
    // ---------------- HELPER -------------
    
    /** Calcola la distanza di Manhattan pesata. */
    private double calcHeuristic(Node from, Node to, Double heuristicWeight){
        return (Math.abs(to.getX() - from.getX()) + 
                Math.abs(to.getY() - from.getY())) * heuristicWeight;
    }

    /** Ricostruisce il percorso finale risalendo i nodi parent. */
    private List<Node> reconstructPath(Node endNode){
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = current.getParentNode();
        }
        Collections.reverse(path); // Inverte la lista per andare da Start a End
        return path;
    }

    /** Introduce un piccolo ritardo per rendere visibile l'algoritmo. */
    private void sleep(int ms){
        try{
            Thread.sleep(ms);
        } catch (InterruptedException ex){
            // Non bloccare il thread in caso di interruzione
            Thread.currentThread().interrupt();
        }
    }
    
    /** Ritorna il numero di nodi esplorati (dimensione della Closed List). */
    public int getExploredCount() {
        return exploredCount;
    }
}
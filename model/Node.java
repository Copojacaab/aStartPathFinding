package model;

public class Node {
    // identificatori
    private int x;
    private int y;
    private NodeType type;

    // valori per a star
    private double gCost; //costo dallo start
    private double hCost; // costo euristica
    private double fCost; // gcost + hcost
    private Node parentNode;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        this.type = NodeType.EMPTY; // di default cella vuota

        resetCosts();
    }

    /**
     * Resetta costi e stato dell'algoritmo
     * non tocca il tipo (muro, partenza, fine)
     */
    public void resetCosts(){
        this.gCost = Double.POSITIVE_INFINITY;
        this.hCost = Double.POSITIVE_INFINITY;
        this.fCost = Double.POSITIVE_INFINITY;

        this.parentNode = null;
        // se il nodo era open, closed o path
        if(this.type == NodeType.OPEN || this.type == NodeType.CLOSED || this.type == NodeType.PATH){
            this.type = NodeType.EMPTY;
        }
    }

    /**Resetta la cella allo stato di default (totale)
     * Usato per il tasto reset
     */
    public void resetFull(){
        this.type = NodeType.EMPTY;
        resetCosts();
    }

    // ------ Getters e Setters
    public int getX(){ return this.x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return this.y; }
    public void setY(int y) { this.y = y; }

    public NodeType getType() { return this.type; }
    public void setType(NodeType type) { this.type = type; }

    public double getGCost() { return this.gCost; }
    public void setGCost(double gCost) { this.gCost = gCost; }

    public double getHCost() { return this.hCost; }
    public void setHCost(double hCost) { this.hCost = hCost; }

    public double getFCost() { return this.fCost; }
    public void setFCost(double fCost) { this.fCost = fCost; }

    public Node getParentNode() { return this.parentNode; }
    public void setParentNode(Node parent) { this.parentNode = parent; }

    // check per vedere se posso passarci
    public boolean isWalkable(){
        return (type != NodeType.WALL); 
    }
}
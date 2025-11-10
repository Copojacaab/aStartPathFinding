package model;

public enum NodeType {
    EMPTY, //cella vuota (walkable)
    WALL,   // cella muro(block)
    START, // cella di partenza
    END, // cella di arrivo
    OPEN, //cella nella open list
    CLOSED, //cella nella closed list
    PATH //cella nel percorso finale
}

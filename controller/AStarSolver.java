package controller;

import java.util.List;
import model.*;
import javax.swing.SwingWorker;

import model.Grid;

public class AStarSolver extends SwingWorker{
    
    Grid grid;
    Node startNode;
    Node endNode;

    public AStarSolver(Grid grid, Node start, Node end){
        this.grid = grid;
        this.startNode = start;
        this.endNode = end;
    }

    @Override
    protected Object doInBackground() throws Exception {
        return new Object();
    }

    public void done(){
        
    }
    
}

package controller;

/**
 * Per ora implementazione parsiale:
 * - drag del mouse piazzo muri
 * - click del mouse piazzo start e end
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Grid;
import model.Node;
import model.NodeType;
import view.MainFrame;

public class AppController implements MouseListener, MouseMotionListener, ActionListener{
    
    Grid model;
    MainFrame view;

    private Node startNode;
    private Node endNode;
    
    public AppController(Grid model, MainFrame view){
        this.model = model;
        this.view = view;

    }

    /**Aggangia i listenr ai componenti della view */
    public void initController(){
        // aggancio i listener al pannello
        view.getGridPanel().addMouseMotionListener(this);
        view.getGridPanel().addMouseListener(this);

        // CONTROL PANEL
        view.getControlPanel().getResetBtn().addActionListener(this);
        view.getControlPanel().getSolveBtn().addActionListener(this);
    }

    private void handleSolve(){
        // 1. check su start e end
        if (this.startNode == null || this.endNode == null) {
            throw new Error("Start o End Null");
        }
        // 2. cleanup algoritmo
        model.resetAlgorithmState();

        AStarSolver solver = new AStarSolver(this.model, this.startNode, this.endNode);
        // 3. exec in background
        solver.execute();
    }

    private void handleReset(){
        // reset di tutti i nodi
        for(int y = 0; y < model.getHeight(); y++){
            for(int x = 0; x < model.getWidth(); x++){
                model.getNode(x, y).resetFull();
            }
        }
        // reset campi del modello
        this.startNode = null;
        this.endNode = null;

        view.getGridPanel().repaint();
    }

    private void handleGridMouse(MouseEvent e){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. check reset o solve
        if (e.getSource() == view.getControlPanel().getResetBtn()) {
            handleReset();
        }else if(e.getSource() == view.getControlPanel().getSolveBtn()){
            handleSolve();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 1. prendo px dall'event
        int xCoord = e.getX();
        int yCoord = e.getY();
        // 2. calcolo la cella
        int cellSize = view.getGridPanel().getCellSize();
        int cellX = xCoord / cellSize;
        int cellY = yCoord / cellSize;
        // 3. check bordi
        int gridWidth = model.getWidth();
        int gridHeight = model.getHeight();
        if(cellX >= 0 && cellX < gridWidth && cellY >=0 && cellY < gridHeight){
            // 4. aggiorno lo stato del node
            Node node = model.getNode(cellX, cellY);
            if (node != null && node.getType() != NodeType.WALL && node.getType() != NodeType.START && node.getType() != NodeType.END) {
                node.setType(NodeType.WALL);
                // 5. aggiorno la view
                view.getGridPanel().repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 1. trovo la cella
        int xCoord = e.getX();
        int yCoord = e.getY();

        int cellSize = view.getGridPanel().getCellSize();
        int cellX = xCoord / cellSize;
        int cellY = yCoord / cellSize;
        // 2. check bordi
        int gridHeight = model.getHeight();
        int gridWidth = model.getWidth();
        if (cellX >= 0 && cellX < gridWidth && cellY >= 0 && cellY < gridHeight) {
            // check muro
            Node node = model.getNode(cellX, cellY);

            if(node != null && node.getType() != NodeType.WALL){
                // 3. distinguo tasto destro e sinistro
                if(SwingUtilities.isLeftMouseButton(e)){
                    if(this.startNode != null){
                        startNode.setType(NodeType.EMPTY); //prima pulisco il vecchio start
                        node.setType(NodeType.START);
                        this.startNode = node;
                    } else if(this.startNode == null){
                        node.setType(NodeType.START);
                        this.startNode = node;
                    }
                } else if(SwingUtilities.isRightMouseButton(e)){
                    if (this.endNode != null) {
                        endNode.setType(NodeType.EMPTY);
                        node.setType(NodeType.END);
                        this.endNode = node;
                    }else if(this.endNode == null){
                        node.setType(NodeType.END);
                        this.endNode = node;
                    }
                }
            }
        }

        view.getGridPanel().repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}

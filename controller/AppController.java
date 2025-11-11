package controller;

/**
 * Per ora implementazione parsiale:
 * - drag del mouse piazzo muri
 * - click del mouse piazzo start e end
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Grid;
import model.Node;
import model.NodeType;
import view.MainFrame;

public class AppController implements MouseListener, MouseMotionListener, ActionListener{
    
    Grid model;
    MainFrame view;
    
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
        // view.getControlPanel().getSolveButton().addActionListener(this);
        // view.getControlPanel().getResetButton().addActionListener(this);
    }

    private void handleSolve(){

    }

    private void handleReset(){

    }

    private void handleGridMouse(MouseEvent e){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 
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
        // 5. aggiorno la view
        view.getGridPanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
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

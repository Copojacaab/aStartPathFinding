package controller;

/**
 * Per ora implementazione parsiale:
 * - drag del mouse piazzo muri
 * - click del mouse piazzo start e end
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Grid;
import model.Node;
import model.NodeType;
import view.MainFrame;

public class AppController implements MouseListener, MouseMotionListener, ActionListener {

    Grid model;
    MainFrame view;

    private ToolType currentTool;

    private Node startNode;
    private Node endNode;

    public AppController(Grid model, MainFrame view, ToolType currentTool) {
        this.model = model;
        this.view = view;
        this.currentTool = currentTool;
    }

    /** Aggangia i listenr ai componenti della view */
    public void initController() {
        // GRID PANEL
        view.getGridPanel().addMouseMotionListener(this);
        view.getGridPanel().addMouseListener(this);

        // CONTROL PANEL
        view.getControlPanel().getResetBtn().addActionListener(this);
        view.getControlPanel().getSolveBtn().addActionListener(this);
        // tools
        view.getControlPanel().getWallBtn().addActionListener(this);
        view.getControlPanel().getPointsBtn().addActionListener(this);
        view.getControlPanel().getEraseBtn().addActionListener(this);
        // heuristic
        view.getControlPanel().getHeuristicBtn().addActionListener(this);
        // random maze
        view.getControlPanel().getRandMaze().addActionListener(this);
    }

    private void handleSolve(Double heuristicWeight) {
        // 1. check su start e end
        if (this.startNode == null || this.endNode == null) {
            JOptionPane.showMessageDialog(view, "Errore: seleziona un nodo di partenza e uno di arrivo",
                    "Errore di input", JOptionPane.ERROR_MESSAGE);
        }
        // 2. cleanup algoritmo
        model.resetAlgorithmState();

        AStarSolver solver = new AStarSolver(this.model, this.startNode, this.endNode, this.view.getGridPanel(),
                heuristicWeight);

        // 3 ascoltatore al solver
        solver.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if ("state".equals(event.getPropertyName()) &&
                        SwingWorker.StateValue.DONE.equals((event.getNewValue()))) {
                    handleSolverDone(solver);
                }
            }
        });
        // aggiorno la view
        view.getGridPanel().repaint();
        // 4. exec in background
        solver.execute();
    }

    /** recupera il risultato e aggiorna il modello */
    private void handleSolverDone(AStarSolver solver) {
        // 1. prendo il risultato
        List<Node> path;
        try {
            path = solver.get();

            // 2. check
            if (path != null && !path.isEmpty()) {
                // aggiorno modello
                System.out.println("Percorso trovato. Lunghezza: " + path.size());

                for (Node node : path) {
                    // coloro solo path
                    if (node.getType() != NodeType.START && node.getType() != NodeType.END) {
                        node.setType(NodeType.PATH);
                    }
                }
            } else {
                // fallimento
                System.out.println("Percorso non trovato");
                JOptionPane.showMessageDialog(view, "Nessun percorso trovato",
                        "Warning message", JOptionPane.WARNING_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Errore nell'esecuzione di astar",
                    "Errore solver", JOptionPane.ERROR_MESSAGE);
        } finally {
            view.getGridPanel().repaint();
        }
    }

    private void handleReset() {
        model.resetAllNodes(); //reset di tutti i nodi
        this.startNode = null;
        this.endNode = null;
        view.getGridPanel().repaint();
    }

    private void handleHeuristicWeightSet() {
        try {
            Double heuWeight = Double.parseDouble(view.getControlPanel().getHeuristicWeight().getText());
            handleSolve(heuWeight);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                    "Inserisci un numero valido",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // funzione per generare il labirinto casuale
    private void handleRandomMaze(){
        // cleanup totale
        model.resetAllNodes();

        // visito ogni nodo
        for(int y=0; y<model.getHeight(); y++){
            for(int x=0; x<model.getWidth(); x++){
                Double randomNum = Math.random();
                Node node = model.getNode(x, y);
                // se minore di 0.3 wall, altrimenti empty
                if (randomNum < 0.3) {
                    if(node.getType() != NodeType.START && node.getType() != NodeType.END)
                        model.getNode(x,y).setType(NodeType.WALL);
                }
            }
        }
        view.getGridPanel().repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. check reset o solve
        if (e.getSource() == view.getControlPanel().getResetBtn()) { // reset
            handleReset();
        } else if (e.getSource() == view.getControlPanel().getSolveBtn()) { // solve
            handleSolve(1.0);
        } else if (e.getSource() == view.getControlPanel().getWallBtn()) {
            this.currentTool = ToolType.DRAW_WALL;
        } else if (e.getSource() == view.getControlPanel().getPointsBtn()) {
            this.currentTool = ToolType.SET_POINTS;
        } else if (e.getSource() == view.getControlPanel().getEraseBtn()) {
            this.currentTool = ToolType.ERASER;
        } else if (e.getSource() == view.getControlPanel().getHeuristicBtn()) {
            handleHeuristicWeightSet();
        } else if (e.getSource() == view.getControlPanel().getRandMaze()){
            handleRandomMaze();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Node node = getNodeFromMouseEvent(e);

        if (node == null || node.getType() == NodeType.START || node.getType() == NodeType.END) {
            return;
        }
        if (currentTool == ToolType.DRAW_WALL) {
            node.setType(NodeType.WALL);
        } else if (currentTool == ToolType.ERASER) {
            node.setType(NodeType.EMPTY);
        }
            
        view.getGridPanel().repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Node node = getNodeFromMouseEvent(e);

        // Se è null (fuori bordi) o non valido, esci
        if (node == null || node.getType() == NodeType.START || node.getType() == NodeType.END) {
            return;
        }

        if (currentTool == ToolType.SET_POINTS) {
            placeStartOrEnd(e, node);
        } else if (currentTool == ToolType.ERASER) {
            node.setType(NodeType.EMPTY);
        }
        view.getGridPanel().repaint();
    }

    private void placeStartOrEnd(MouseEvent e, Node node) {
        if (SwingUtilities.isLeftMouseButton(e)) {
                // Gestione Start
                if (this.startNode != null) {
                    this.startNode.setType(NodeType.EMPTY); // Pulisci vecchio
                }
                node.setType(NodeType.START); // Setta nuovo
                this.startNode = node;        // Aggiorna riferimento
                
            } else if (SwingUtilities.isRightMouseButton(e)) {
                // Gestione End
                if (this.endNode != null) {
                    this.endNode.setType(NodeType.EMPTY);
                }
                node.setType(NodeType.END);
                this.endNode = node;
            }
    }

    // helper
    private Node getNodeFromMouseEvent(MouseEvent e) {
        int cellSize = view.getGridPanel().getCellSize();
        int cellX = e.getX() / cellSize;
        int cellY = e.getY() / cellSize;

        // Check bordi delegato al Model (più pulito) o fatto qui
        if (cellX >= 0 && cellX < model.getWidth() && cellY >= 0 && cellY < model.getHeight()) {
            return model.getNode(cellX, cellY);
        }
        return null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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

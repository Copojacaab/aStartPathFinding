package controller;

/**
 * Controller principale: gestisce gli eventi dell'utente (mouse, bottoni)
 * e coordina la logica tra la Grid (Model) e la MainFrame (View).
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.Point;
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

    public AppController(Grid model, MainFrame view, ToolType currentTool) {
        this.model = model;
        this.view = view;
        this.currentTool = currentTool;
    }

    /** Aggancia tutti i listener ai componenti della view (Grid e Control Panel). */
    public void initController() {
        // Listener per la Grid (disegno, posizionamento)
        view.getGridPanel().addMouseMotionListener(this);
        view.getGridPanel().addMouseListener(this);
        
        // Listener per i bottoni di Azione
        view.getControlPanel().getResetBtn().addActionListener(this);
        view.getControlPanel().getClearPathBtn().addActionListener(this);
        view.getControlPanel().getSolveBtn().addActionListener(this);
        view.getControlPanel().getRandMaze().addActionListener(this);

        // Listener per i Tool (dalla ToolBarPanel)
        view.getToolBarPanel().getWallButton().addActionListener(this);
        view.getToolBarPanel().getPointsButton().addActionListener(this);
        view.getToolBarPanel().getEraseButton().addActionListener(this);
    }

    /** Avvia la risoluzione A* in background. */
    private void handleSolve(Double heuristicWeight) {
        if (model.getStartNode() == null || model.getEndNode() == null) {
            JOptionPane.showMessageDialog(view, "Seleziona i nodi Start e End.", "Errore di input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        view.getControlPanel().resetResults(); // Pulisci i risultati precedenti
        model.resetAlgorithmState(); // Pulisci lo stato della griglia (OPEN/CLOSED/PATH)

        AStarSolver solver = new AStarSolver(this.model, model.getStartNode(), model.getEndNode(), this.view.getGridPanel(),
                heuristicWeight);

        // Listener per catturare il risultato al termine dell'esecuzione
        solver.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if ("state".equals(event.getPropertyName()) && SwingWorker.StateValue.DONE.equals((event.getNewValue()))) {
                    handleSolverDone(solver);
                }
            }
        });
        
        view.getGridPanel().repaint();
        solver.execute();
    }

    /** Gestisce il risultato finale del Solver. */
    private void handleSolverDone(AStarSolver solver) {
        List<Node> path;
        double finalPathCost = Double.POSITIVE_INFINITY;
        int exploredNodesCount = 0;
        
        try {
            path = solver.get();
            exploredNodesCount = solver.getExploredCount(); // Prende il conteggio dei nodi esaminati

            if (path != null && !path.isEmpty()) {
                // Percorso trovato
                if (model.getEndNode() != null) {
                    finalPathCost = model.getEndNode().getGCost();
                }

                for (Node node : path) {
                    if (node.getType() != NodeType.START && node.getType() != NodeType.END) {
                        node.setType(NodeType.PATH);
                    }
                }
            } else {
                // Percorso non trovato
                JOptionPane.showMessageDialog(view, "Nessun percorso trovato", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Errore nell'esecuzione dell'algoritmo", "Errore Solver", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Aggiorna la UI con le metriche finali (costo e nodi esplorati)
            view.getControlPanel().updateResults(finalPathCost, exploredNodesCount); 
            view.getGridPanel().repaint();
        }
    }

    /** Resetta completamente la griglia e i risultati. */
    private void handleReset() {
        model.resetAllNodes(); 
        view.getGridPanel().repaint();
        view.getControlPanel().resetResults(); 
    }

    /** Pulisce solo lo stato dell'algoritmo (PATH, OPEN, CLOSED). */
    private void handleClearPath(){
        model.resetAlgorithmState();
        for(int y=0; y<model.getHeight(); y++){
            for(int x=0; x<model.getWidth(); x++){
                Node node = model.getNode(x, y);
                if (node.getType() == NodeType.PATH || node.getType() == NodeType.OPEN || node.getType() == NodeType.CLOSED) {
                    node.setType(NodeType.EMPTY);
                }
            }
        }
        view.getGridPanel().repaint();
        view.getControlPanel().resetResults(); 
    }

    /** Gestione generale degli eventi dei bottoni. */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getControlPanel().getResetBtn()) { 
            handleReset();
        } else if(source == view.getControlPanel().getClearPathBtn()){
            handleClearPath();
        } else if (source == view.getControlPanel().getSolveBtn()) { 
            int sliderVal = view.getControlPanel().getHeuristicSlider().getValue();
            handleSolve(sliderVal/10.0);
        } else if (source == view.getControlPanel().getRandMaze()){
            handleClearPath(); // Pulisci prima di generare
            model.generateRandomMaze();
            view.repaint();
        } 
        // Gestione cambio Tool (dalla ToolBar)
        else if (source == view.getToolBarPanel().getWallButton()) {
            this.currentTool = ToolType.DRAW_WALL;
        } else if (source == view.getToolBarPanel().getPointsButton()) {
            this.currentTool = ToolType.SET_POINTS;
        } else if (source == view.getToolBarPanel().getEraseButton()) {
            this.currentTool = ToolType.ERASER;
        }
    }

    /** Gestisce il disegno (Drag del mouse). */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point nodeCoord = view.getGridPanel().getNodeAt(e.getX(), e.getY());
        if (nodeCoord == null) return;
        
        Node node = model.getNode((int) nodeCoord.getX(), (int) nodeCoord.getY());

        if (node == null || node.getType() == NodeType.START || node.getType() == NodeType.END) {
            return;
        }
        
        // Disegna muro o cancella
        if (currentTool == ToolType.DRAW_WALL) {
            node.setType(NodeType.WALL);
        } else if (currentTool == ToolType.ERASER) {
            node.setType(NodeType.EMPTY);
        }
            
        view.getGridPanel().repaint();
    }

    /** Gestisce il click del mouse (Posizionamento Start/End, Cancellazione). */
    @Override
    public void mouseClicked(MouseEvent e) {
        Point nodeCoord = view.getGridPanel().getNodeAt(e.getX(), e.getY());
        if (nodeCoord == null) return;
        
        Node node = model.getNode((int)nodeCoord.getX(),(int)nodeCoord.getY());

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

    /** Logica per posizionare Start (Sinistro) o End (Destro). */
    private void placeStartOrEnd(MouseEvent e, Node node) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            model.setStartNode(node);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            model.setEndNode(node);
        }
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
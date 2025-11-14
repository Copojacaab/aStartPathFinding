package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private GridPanel gridPanel;
    private ControlPanel controlPanel;

    public MainFrame(){
        setTitle("A* PathFinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initLayout();

        setSize(1000, 700);

        this.setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null); //centra la finestra
    }

    private void initLayout(){
        // 1. Imposta il GridBagLayout sul Frame
        setLayout(new GridBagLayout());

        // 2. Crea l'oggetto "regole"
        // Questo oggetto ci serve per dire al layout come comportarsi.
        GridBagConstraints gbc = new GridBagConstraints();

        this.gridPanel = new GridPanel();
        this.controlPanel = new ControlPanel();
        
        // --- Regole per il GridPanel (Colonna 0) ---
        
        // Vogliamo che si espanda in entrambe le direzioni
        gbc.fill = GridBagConstraints.BOTH; 
        
        gbc.gridx = 0; // Colonna 0
        gbc.gridy = 0; // Riga 0
        
        // Questo è l'equivalente del tuo "setResizeWeight(0.9)"
        // Diciamo: "Prendi il 90% di tutto lo spazio extra orizzontale"
        gbc.weightx = 0.9; 
        
        // Diciamo: "Prendi il 100% di tutto lo spazio extra verticale"
        gbc.weighty = 1.0; 
        
        // Aggiungi il pannello con queste regole
        this.add(gridPanel, gbc);

        // --- Regole per il ControlPanel (Colonna 1) ---
        
        gbc.gridx = 1; // Colonna 1
        gbc.gridy = 0; // Riga 0
        
        // Diciamo: "Prendi il 10% di tutto lo spazio extra orizzontale"
        gbc.weightx = 0.1;
        
        // Il weighty rimane 1.0 per occupare tutta l'altezza
        gbc.weighty = 1.0; 
        
        // Aggiungi il pannello con queste nuove regole
        this.add(controlPanel, gbc);

        // NON serve più nessun JSplitPane.
    }


    public GridPanel getGridPanel() { return this.gridPanel; }
    public ControlPanel getControlPanel() { return this.controlPanel; }
}

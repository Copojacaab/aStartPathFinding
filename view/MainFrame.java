package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;


/** finestra principale dell'applicazione, funge da view nel pattern MVC*/
public class MainFrame extends JFrame{
    private GridPanel gridPanel;
    private ControlPanel controlPanel;
    private ToolBarPanel toolBarPanel; // pannello per la barra degli strumenti

    public MainFrame(){
        setTitle("A* PathFinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initLayout();

        setSize(1000, 700);

        this.setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null); //centra la finestra
    }

    /** Inizializza il layout principale utilizzando Gridbag.
     * 2 righe x 2 colonne:
     * - riga 0: toolbarpanel (2 colonne)
     * - riga 1: gridpanel (colonna 0), controlpanel (colonna 1)
     */
    private void initLayout(){
        // 1. imposto il gridbag sul contenitore principale
        setLayout(new GridBagLayout());

        // 2. creo oggetto per regole di posizionamento e dim
        GridBagConstraints gbc = new GridBagConstraints();

        // 3. inizilalizza i componenti principalis
        this.gridPanel = new GridPanel();
        this.controlPanel = new ControlPanel();
        this.toolBarPanel = new ToolBarPanel(); // passo istanza di controlPanel per accedere a proportionalToggle
        
        // --- Regole per la ToolBarPanel (riga 0) ---
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.ipady = 5; 
        this.add(toolBarPanel, gbc);
        
        // -- Regole per GridPanel (riga 1, colonna 0)
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.9;
        gbc.weighty = 1.0;
        gbc.ipady = 0; 
        this.add(gridPanel, gbc);

        // -- Regole per Controlpanel (riga 1, colonna 1)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        this.add(controlPanel, gbc);
    }


    public GridPanel getGridPanel() { return this.gridPanel; }
    public ControlPanel getControlPanel() { return this.controlPanel; }
    public ToolBarPanel getToolBarPanel() { return this.toolBarPanel; }

    /** CLASSE INTERNA: implementa la barra degli strumenti orizzonalte per la tool selection*/

}

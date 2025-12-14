package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;


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
        this.toolBarPanel = new ToolBarPanel(this.controlPanel); // passo istanza di controlPanel per accedere a proportionalToggle
        
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
    public class ToolBarPanel extends JPanel{
        private JToggleButton eraseBtn;
        private JToggleButton pointsBtn;
        private JToggleButton wallBtn;

        /**Costruttore: richiede istanza di control panel per creare i toggle
         * usando la clase interna ProportionalToggleButton
         * @param cp: istanza di ControlPanel
         */
        public ToolBarPanel(ControlPanel cp){
            // configurazione layout: boxlayout orizzontale
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(new Color(59,64,74));
            this.setBorder(new EmptyBorder(5,10,5,10));

            // re-init dei toggle buttons: uso classe definita in ControlPanel
            this.wallBtn = cp.new ProportionalToggleButton("Walls");
            this.pointsBtn = cp.new ProportionalToggleButton("Start/End");
            this.eraseBtn = cp.new ProportionalToggleButton("Erase");

            // raggruppo i toggle in un ButtonGroup per la mutua esclusione
            ButtonGroup toolGroup = new ButtonGroup();
            toolGroup.add(eraseBtn);
            toolGroup.add(pointsBtn);
            toolGroup.add(wallBtn);

            // aggiungo i bottoni al panel con spazio elastico
            this.add(Box.createHorizontalGlue()); //spazio elastico a sx
            this.add(wallBtn);
            this.add(Box.createHorizontalStrut(20));
            this.add(pointsBtn);
            this.add(Box.createHorizontalStrut(20));
            this.add(eraseBtn);
            this.add(Box.createHorizontalGlue()); //spazio elastico a dx

            // default: wall attivo
            wallBtn.setSelected(true);

            // aggiorno stile pulsanti
            Font buttonFont = new Font("Tahoma", Font.BOLD, 16);
            int minHeight = 40;
            int minWidth = 150;
            Dimension toolSize = new Dimension(minWidth, minHeight);

            // applico nuove dim e font
            wallBtn.setFont(buttonFont);
            wallBtn.setPreferredSize(toolSize);
            wallBtn.setMaximumSize(toolSize);
            
            eraseBtn.setFont(buttonFont);
            eraseBtn.setPreferredSize(toolSize);
            eraseBtn.setMaximumSize(toolSize);

            pointsBtn.setFont(buttonFont);
            pointsBtn.setPreferredSize(toolSize);
            pointsBtn.setMaximumSize(toolSize);
        }

        // getters per il controller per agganciare i listeners
        public JToggleButton getWallButton() { return this.wallBtn; }
        public JToggleButton getEraseButton() { return this.eraseBtn; }
        public JToggleButton getPointsButton() { return this.pointsBtn; }
    }
}

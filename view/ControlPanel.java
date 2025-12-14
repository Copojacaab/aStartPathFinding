package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Pannello di controllo laterale con azioni (Reset/Solve),
 * configurazione (Euristica) e risultati (Path Cost/Nodi Esplorati).
 */
public class ControlPanel extends JPanel implements ChangeListener{
    
    // Pulsanti Azione
    private JButton resetBtn;
    private JButton clearPathBtn;
    private JButton solveBtn;
    
    // Elementi Configurazione
    private JSlider heuristicSlider;
    private JButton randMazeBtn;

    // Etichette Euristica
    private JLabel heuristicLabel;
    private JLabel heuristicValueLabel;

    // NUOVO: Etichette Risultati
    private JLabel pathCostLabel; 
    private JLabel costValueLabel; // Usato nome più breve per il valore
    private JLabel nodesExploredLabel; 
    private JLabel exploredValueLabel; // Usato nome più breve per il valore
    
    // Costanti per Font e Colori
    private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 14);
    private static final Font SLIDER_LABEL_FONT = new Font("Tahoma", Font.BOLD, 12);
    private static final Font RESULT_FONT = new Font("Tahoma", Font.PLAIN, 12);
    private static final Color RESULT_COLOR = new Color(230, 230, 230);
    private static final Color VALUE_COLOR = Color.cyan;
    private static final Color FAIL_COLOR = Color.RED;


    public ControlPanel(){
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10));

        JPanel wrapperControl = new JPanel();
        wrapperControl.setLayout(new BoxLayout(wrapperControl, BoxLayout.Y_AXIS));

        // --- Inizializzazione Bottoni ---
        this.resetBtn = new ProportionalButton("Reset: 🔄");
        this.resetBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.resetBtn.setFont(BUTTON_FONT);
        
        this.clearPathBtn = new ProportionalButton("Reset Path ❌");
        this.clearPathBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.clearPathBtn.setFont(BUTTON_FONT);
        
        this.solveBtn = new ProportionalButton("Solve: ▶️");
        this.solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.solveBtn.setFont(BUTTON_FONT);

        this.randMazeBtn = new ProportionalButton("Random Maze: 🎲");
        this.randMazeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.randMazeBtn.setFont(BUTTON_FONT);

        // --- Inizializzazione Etichette Risultati ---
        this.pathCostLabel = new JLabel("Path Cost (G):");
        this.pathCostLabel.setFont(RESULT_FONT);
        this.pathCostLabel.setForeground(RESULT_COLOR);

        this.costValueLabel = new JLabel("-"); 
        this.costValueLabel.setFont(RESULT_FONT);
        this.costValueLabel.setForeground(VALUE_COLOR);

        this.nodesExploredLabel = new JLabel("Nodes Explored:");
        this.nodesExploredLabel.setFont(RESULT_FONT);
        this.nodesExploredLabel.setForeground(RESULT_COLOR);

        this.exploredValueLabel = new JLabel("-"); 
        this.exploredValueLabel.setFont(RESULT_FONT);
        this.exploredValueLabel.setForeground(VALUE_COLOR);
        
        // --- Inizializzazione Etichette Euristica ---
        this.heuristicLabel = new JLabel("Heuristic Weight (W):");
        this.heuristicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicLabel.setFont(SLIDER_LABEL_FONT);
        this.heuristicLabel.setForeground(new Color(230, 230, 230));

        this.heuristicValueLabel = new JLabel("5.0"); 
        this.heuristicValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicValueLabel.setFont(SLIDER_LABEL_FONT);
        this.heuristicValueLabel.setForeground(Color.yellow);

        // --- Inizializzazione Slider ---
        this.heuristicSlider = new JSlider();
        this.heuristicSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicSlider.setFont(BUTTON_FONT); 

        // Configurazione Slider
        heuristicSlider.setMinimum(0);
        heuristicSlider.setMaximum(100);
        heuristicSlider.setMajorTickSpacing(10);
        heuristicSlider.setPaintTicks(true);

        // Etichette personalizzate per i tick
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Dijkstra (W=0)"));
        labelTable.put(100, new JLabel("Greedy (W=10)")); 

        for(JLabel label : labelTable.values()){
            label.setFont(new Font("Tahoma", Font.PLAIN, 10)); 
            label.setForeground(new Color(200, 200, 200));
        }
        heuristicSlider.setLabelTable(labelTable);
        heuristicSlider.setPaintLabels(true);
        heuristicSlider.setValue(50); 

        int sliderHeight = heuristicSlider.getPreferredSize().height;
        heuristicSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, sliderHeight));
        heuristicSlider.setBackground(new Color(59, 64, 74));
        heuristicSlider.setForeground(new Color(230, 230, 230));
        heuristicSlider.setOpaque(true);
        
        // ChangeListener
        heuristicSlider.addChangeListener(this);
        updateHeuristicValueLabel(heuristicSlider.getValue());

        // Aggiungi i componenti al wrapper
        addButtons(wrapperControl);

        this.add(wrapperControl, BorderLayout.NORTH);

        // Stile generale
        this.setBackground(new Color(59, 64, 74));
        wrapperControl.setBackground(new Color(59,64, 74));
        this.setMinimumSize(new Dimension(200,0));
        
        // Assicura che i risultati siano resettati all'avvio
        resetResults();
    }

    // --------------------------------------------------------------------------
    // --- Logica dei Risultati (NUOVO) ---
    // --------------------------------------------------------------------------

    /** Aggiorna il pannello informativo con i risultati finali. */
    public void updateResults(double pathCost, int exploredCount) {
        exploredValueLabel.setText(String.valueOf(exploredCount));
        
        if (pathCost == Double.POSITIVE_INFINITY) {
            costValueLabel.setText("N/A");
            costValueLabel.setForeground(FAIL_COLOR);
        } else {
            costValueLabel.setText(String.format("%.0f", pathCost));
            costValueLabel.setForeground(VALUE_COLOR);
        }
    }

    /** Resetta il pannello informativo allo stato iniziale. */
    public void resetResults() {
        costValueLabel.setText("-");
        exploredValueLabel.setText("-");
        costValueLabel.setForeground(VALUE_COLOR); 
    }

    // --------------------------------------------------------------------------
    // --- Layout & Listeners ---
    // --------------------------------------------------------------------------

    /** Aggiorna l'etichetta dinamica dello slider. */
    @Override
    public void stateChanged(ChangeEvent e){
        if(e.getSource() == heuristicSlider){
            updateHeuristicValueLabel(heuristicSlider.getValue());
        }
    }

    /** Helper per la normalizzazione del valore euristico. */
    private void updateHeuristicValueLabel(int sliderValue){
        double value = sliderValue / 10.0;
        heuristicValueLabel.setText(String.format("%.1f", value));
    }

    /** Organizza i pulsanti e i pannelli nel wrapper verticale. */
    private void addButtons(JPanel wrapperControl){
        int spacing = 10;

        // Sezione 1: Azioni principali
        wrapperControl.add(resetBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(clearPathBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(solveBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing)); 

        // Sezione 1.5: Pannello Risultati (NUOVO)

        wrapperControl.add(Box.createVerticalStrut(spacing * 2)); 

        // Sezione 2: Generazione Labirinto
        wrapperControl.add(randMazeBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing * 2)); 
        
        // Sezione 3: Configurazione Euristica
        JPanel heuristicLabelWrapper = new JPanel();
        heuristicLabelWrapper.setLayout(new BoxLayout(heuristicLabelWrapper, BoxLayout.X_AXIS));
        heuristicLabelWrapper.setBackground(new Color(59, 64, 74));
        heuristicLabelWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        heuristicLabelWrapper.add(heuristicLabel);      
        heuristicLabelWrapper.add(Box.createHorizontalStrut(5));
        heuristicLabelWrapper.add(heuristicValueLabel);  
        heuristicLabelWrapper.add(Box.createHorizontalGlue()); 

        wrapperControl.add(heuristicLabelWrapper); 
        wrapperControl.add(heuristicSlider);
        
        wrapperControl.add(Box.createVerticalStrut(spacing * 2));
        
        JPanel resultsPanel = createResultsPanel();
        wrapperControl.add(resultsPanel);
    }

    /** Helper per costruire il pannello dei risultati. */
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(59, 64, 74)); 
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Costo Totale
        JPanel costWrapper = new JPanel();
        costWrapper.setLayout(new BoxLayout(costWrapper, BoxLayout.X_AXIS));
        costWrapper.setBackground(panel.getBackground());
        costWrapper.add(pathCostLabel);
        costWrapper.add(Box.createHorizontalStrut(5));
        costWrapper.add(costValueLabel);
        costWrapper.add(Box.createHorizontalGlue()); 
        
        // Nodi Esplorati
        JPanel exploredWrapper = new JPanel();
        exploredWrapper.setLayout(new BoxLayout(exploredWrapper, BoxLayout.X_AXIS));
        exploredWrapper.setBackground(panel.getBackground());
        exploredWrapper.add(nodesExploredLabel);
        exploredWrapper.add(Box.createHorizontalStrut(5));
        exploredWrapper.add(exploredValueLabel);
        exploredWrapper.add(Box.createHorizontalGlue()); 
        
        panel.add(costWrapper);
        panel.add(Box.createVerticalStrut(2)); 
        panel.add(exploredWrapper);
        
        return panel;
    }

    // --- Getters per il controller ---
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getClearPathBtn() { return this.clearPathBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
    public JButton getRandMaze() { return this.randMazeBtn; }
    public JSlider getHeuristicSlider() { return heuristicSlider; }

    // ------------------------ CLASSI INTERNE PER STILI (Mantenute, omesse per brevità) ----------------------------
    // Le classi ProportionalButton e ProportionalToggleButton sono invariate.
    public class ProportionalButton extends JButton implements MouseListener{
        private double ratio = 40.0 / 180.0; private int minWidth = 180; private final Dimension MIN_BUTTON_SIZE = new Dimension(minWidth, (int) (minWidth * ratio)); private int maxWidth = 500; private final Dimension MAX_BUTTON_SIZE = new Dimension(maxWidth, (int) (maxWidth * ratio)); private final Color INACTIVE_COLOR = new Color(80, 88, 104); private final Color HOVER_COLOR = new Color(70, 78, 94); private final Color PRESSED_COLOR = new Color(60, 68, 84); public ProportionalButton(String text){ super(text); setBackground(INACTIVE_COLOR); setForeground(new Color(230, 230, 230)); setOpaque(true); setBorderPainted(false); addMouseListener(this); } private Dimension getProportionalSize(){ int width = getParent() != null ? getParent().getWidth() : minWidth; if(width < minWidth){ return MIN_BUTTON_SIZE; }else if (width > maxWidth) { return MAX_BUTTON_SIZE; } else { int height = (int) (width * ratio); return new Dimension(width, height); } } @Override public Dimension getPreferredSize() { int width = getParent() != null ? getParent().getWidth() : minWidth; int height = (int) (width * ratio); return new Dimension(width, height); } @Override public Dimension getMaximumSize() { int width = getParent() != null ? getParent().getWidth() : minWidth; int height = (int) (width * ratio); return new Dimension(width, height); } @Override public void mouseEntered(MouseEvent e) { setBackground(HOVER_COLOR); } @Override public void mouseExited(MouseEvent e) { setBackground(INACTIVE_COLOR); } @Override public void mousePressed(MouseEvent e) { setBackground(PRESSED_COLOR); } @Override public void mouseClicked(MouseEvent e) {} @Override public void mouseReleased(MouseEvent e) { if (contains(e.getPoint())) { setBackground(HOVER_COLOR); } else { setBackground(INACTIVE_COLOR); } } }
    public class ProportionalToggleButton extends JToggleButton implements MouseListener{
        private double ratio = 40.0 / 180.0; private int minWidth = 180; private int minHeight = (int) (minWidth * ratio); private final Dimension MIN_BUTTON_SIZE = new Dimension(minWidth, minHeight); private int maxWidth = 500; private int maxHeight = (int) (maxWidth * ratio); private final Dimension MAX_TOGGLE_SIZE = new Dimension(maxWidth, maxHeight); private final Color INACTIVE_COLOR = new Color(80, 88, 104); private final Color HOVER_COLOR = new Color(70, 78, 94); private final Color ACTIVE_COLOR = Color.yellow; private final Color TEXT_INACTIVE_COLOR = new Color(230, 230, 230); private final Color TEXT_ACTIVE_COLOR = Color.BLACK; public ProportionalToggleButton(String text){ super(text); this.setFont(BUTTON_FONT); setBackground(INACTIVE_COLOR); setForeground(TEXT_INACTIVE_COLOR); setOpaque(true); setBorderPainted(false); this.addMouseListener(this); this.addItemListener(e -> { if(e.getStateChange() == ItemEvent.SELECTED) { setBackground(ACTIVE_COLOR); setForeground(TEXT_ACTIVE_COLOR); } else if (e.getStateChange() == ItemEvent.DESELECTED){ setBackground(INACTIVE_COLOR); setForeground(TEXT_INACTIVE_COLOR); } }); } private Dimension getProportionalSize(){ int width = getParent() != null ? getParent().getWidth() : minWidth; if (width < minWidth){ return MIN_BUTTON_SIZE; } else if (width > maxWidth) { return MAX_TOGGLE_SIZE; } else { int height = (int) (width * ratio); return new Dimension(width, height); } } @Override public Dimension getPreferredSize() { return getProportionalSize(); } @Override public Dimension getMaximumSize() { return getProportionalSize(); } @Override public void mouseEntered(MouseEvent e) { if(!isSelected()){ setBackground(HOVER_COLOR); } } @Override public void mouseExited(MouseEvent e) { if(!isSelected()){ setBackground(INACTIVE_COLOR); } } @Override public void mouseClicked(MouseEvent e) {} @Override public void mousePressed(MouseEvent e) { if (isSelected()) { setBackground(HOVER_COLOR); } } @Override public void mouseReleased(MouseEvent e) { if(!isSelected()){ if(contains(e.getPoint())){ setBackground(HOVER_COLOR); } else{ setBackground(INACTIVE_COLOR); } } } }
}
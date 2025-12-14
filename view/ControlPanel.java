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
 * Pannello di controllo laterale che contiene i pulsanti di azione (Reset, Solve)
 * e la configurazione (Slider Euristica, Random Maze).
 * Implementa ChangeListener per aggiornare l'etichetta dinamica dello slider.
 */
public class ControlPanel extends JPanel implements ChangeListener{
    
    // Pulsanti di Azione
    private JButton resetBtn;
    private JButton clearPathBtn;
    private JButton solveBtn;
    
    // Elementi di Configurazione
    private JSlider heuristicSlider;
    private JButton randMazeBtn;

    // Etichette per l'Euristica
    private JLabel heuristicLabel;
    private JLabel heuristicValueLabel;

    // Font per i pulsanti (compatto)
    private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 14);
    // Font per le etichette dello slider
    private static final Font SLIDER_LABEL_FONT = new Font("Tahoma", Font.BOLD, 12);

    public ControlPanel(){
        // Layout con contenitore
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10)); // Padding intorno al panel

        // Creo wrapper intero (utilizza BoxLayout verticale)
        JPanel wrapperControl = new JPanel();
        wrapperControl.setLayout(new BoxLayout(wrapperControl, BoxLayout.Y_AXIS));

        // --- Inizializzazione dei bottoni di Azione ---
        this.resetBtn = new ProportionalButton("Reset: 🔄");
        this.resetBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.resetBtn.setFont(BUTTON_FONT);
        
        this.clearPathBtn = new ProportionalButton("Reset Path ❌");
        this.clearPathBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.clearPathBtn.setFont(BUTTON_FONT);
        
        this.solveBtn = new ProportionalButton("Solve: ▶️");
        this.solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.solveBtn.setFont(BUTTON_FONT);

        // --- Inizializzazione Randomizzatore Labirinti ---
        this.randMazeBtn = new ProportionalButton("Random Maze: 🎲");
        this.randMazeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.randMazeBtn.setFont(BUTTON_FONT);

        // --- Inizializzazione Slider ed Etichette ---
        
        // Etichetta Fissa
        this.heuristicLabel = new JLabel("Heuristic Weight (W):");
        this.heuristicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicLabel.setFont(SLIDER_LABEL_FONT);
        this.heuristicLabel.setForeground(new Color(230, 230, 230));

        // Etichetta Dinamica
        this.heuristicValueLabel = new JLabel("5.0"); 
        this.heuristicValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicValueLabel.setFont(SLIDER_LABEL_FONT);
        this.heuristicValueLabel.setForeground(Color.yellow);

        // Slider Euristica
        this.heuristicSlider = new JSlider();
        this.heuristicSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicSlider.setFont(BUTTON_FONT); 

        // --- Configurazione Slider ---
        heuristicSlider.setMinimum(0);
        heuristicSlider.setMaximum(100);
        heuristicSlider.setMajorTickSpacing(10);
        heuristicSlider.setPaintTicks(true);

        // Etichette personalizzate per i tick dello slider (CORREZIONE DELLA SOVRAPPOSIZIONE)
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        
        // Etichetta estrema sinistra (W=0)
        labelTable.put(0, new JLabel("Dijkstra (W=0)"));
        
        // Rimosso l'etichetta al tick 10 per evitare collisioni.
        
        // Etichetta estrema destra (W=10)
        labelTable.put(100, new JLabel("Greedy (W=10)")); 

        // Applica stili alle etichette
        for(JLabel label : labelTable.values()){
            label.setFont(new Font("Tahoma", Font.PLAIN, 10)); // Font piccolo per i tick
            label.setForeground(new Color(200, 200, 200));
        }
        heuristicSlider.setLabelTable(labelTable);
        heuristicSlider.setPaintLabels(true);

        heuristicSlider.setValue(50); // Imposta il valore iniziale a W=5.0

        // Imposta l'altezza massima dello slider
        int sliderHeight = heuristicSlider.getPreferredSize().height;
        heuristicSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, sliderHeight));
        heuristicSlider.setBackground(new Color(59, 64, 74));
        heuristicSlider.setForeground(new Color(230, 230, 230));
        heuristicSlider.setOpaque(true);
        
        // Aggiunge il ChangeListener a this (ControlPanel)
        heuristicSlider.addChangeListener(this);
        
        // Inizializza l'etichetta dinamica con il valore di default
        updateHeuristicValueLabel(heuristicSlider.getValue());

        // --- Aggiunge i componenti al wrapper ---
        addButtons(wrapperControl);

        // Aggiunge il wrapper al panel
        this.add(wrapperControl, BorderLayout.NORTH);

        // Style
        this.setBackground(new Color(59, 64, 74));
        wrapperControl.setBackground(new Color(59,64, 74));
        this.setMinimumSize(new Dimension(200,0));
    }

    /**
     * Implementazione di ChangeListener. Aggiorna l'etichetta dinamica W in tempo reale.
     */
    @Override
    public void stateChanged(ChangeEvent e){
        if(e.getSource() == heuristicSlider){
            int sliderValue = heuristicSlider.getValue();
            updateHeuristicValueLabel(sliderValue);
        }
    }

    /**
     * HELPER che aggiorna l'etichetta dinamica W.
     * Normalizza il valore (0-100) a (0.0-10.0).
     * @param sliderValue: valore raw dello slider (0-100)
     */
    private void updateHeuristicValueLabel(int sliderValue){
        // Normalizza il valore dividendo per 10.0
        double value = sliderValue / 10.0;

        // Formatta a una cifra decimale
        heuristicValueLabel.setText(String.format("%.1f", value));
    }

    /**
     * HELPER per l'aggiunta dei pulsanti con spaziatura verticale.
     * @param wrapperControl: JPanel contenitore
     */
    private void addButtons(JPanel wrapperControl){
        int spacing = 10;

        // --- Sezione 1: Azioni principali (Reset, Reset Path, Solve) ---
        wrapperControl.add(resetBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(clearPathBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(solveBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing * 2)); // Spazio separatore

        // --- Sezione 2: Generazione Labirinto ---
        wrapperControl.add(randMazeBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing * 2)); // Spazio separatore
        
        // --- Sezione 3: Configurazione Euristica ---
        
        // Wrapper orizzontale per Etichetta Fissa + Valore Dinamico
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
    }

    // --- Getters per il controller ---
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getClearPathBtn() { return this.clearPathBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
    public JButton getRandMaze() { return this.randMazeBtn; }
    public JSlider getHeuristicSlider() { return heuristicSlider; }

    /** * ------------------------ CLASSI INTERNE PER STILI ----------------------------
     * Mantenute invariate.
     */
    public class ProportionalButton extends JButton implements MouseListener{
        private double ratio = 40.0 / 180.0; 

        private int minWidth = 180;
        private int minHeight = (int) (minWidth * ratio);
        private final Dimension MIN_BUTTON_SIZE = new Dimension(minWidth, minHeight);

        private int maxWidth = 500;
        private int maxHeight = (int) (maxWidth * ratio);
        private final Dimension MAX_BUTTON_SIZE = new Dimension(maxWidth, maxHeight);

        private final Color INACTIVE_COLOR = new Color(80, 88, 104);
        private final Color HOVER_COLOR = new Color(70, 78, 94);
        private final Color PRESSED_COLOR = new Color(60, 68, 84);

        public ProportionalButton(String text){
            super(text);
            
            setBackground(INACTIVE_COLOR); 
            setForeground(new Color(230, 230, 230));
            setOpaque(true);
            setBorderPainted(false);

            addMouseListener(this);
        }

        private Dimension getProportionalSize(){
            int width = getParent() != null ? getParent().getWidth() : minWidth; 

            if(width < minWidth){
                return MIN_BUTTON_SIZE;
            }else if (width > maxWidth) {
                return MAX_BUTTON_SIZE;
            } else {
                int height = (int) (width * ratio);
                return new Dimension(width, height);
            }
        }
        
        @Override
        public Dimension getPreferredSize() {
            int width = getParent() != null ? getParent().getWidth() : minWidth;
            int height = (int) (width * ratio);
            return new Dimension(width, height);
        }

        @Override
        public Dimension getMaximumSize() {
            int width = getParent() != null ? getParent().getWidth() : minWidth;
            int height = (int) (width * ratio);
            return new Dimension(width, height); 
        }
        
        @Override
        public void mouseEntered(MouseEvent e) { setBackground(HOVER_COLOR); }
        @Override
        public void mouseExited(MouseEvent e) { setBackground(INACTIVE_COLOR); }
        @Override
        public void mousePressed(MouseEvent e) { setBackground(PRESSED_COLOR); }
        @Override
        public void mouseClicked(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (contains(e.getPoint())) {
                setBackground(HOVER_COLOR); 
            } else {
                setBackground(INACTIVE_COLOR); 
            }
        }
    }

    public class ProportionalToggleButton extends JToggleButton implements MouseListener{

        private double ratio = 40.0 / 180.0; 

        private int minWidth = 180;
        private int minHeight = (int) (minWidth * ratio);
        private final Dimension MIN_BUTTON_SIZE = new Dimension(minWidth, minHeight);

        private int maxWidth = 500;
        private int maxHeight = (int) (maxWidth * ratio);
        private final Dimension MAX_TOGGLE_SIZE = new Dimension(maxWidth, maxHeight);

        private final Color INACTIVE_COLOR = new Color(80, 88, 104);
        private final Color HOVER_COLOR = new Color(70, 78, 94);
        private final Color ACTIVE_COLOR = Color.yellow;
        private final Color TEXT_INACTIVE_COLOR = new Color(230, 230, 230);
        private final Color TEXT_ACTIVE_COLOR = Color.BLACK; 

        public ProportionalToggleButton(String text){
            super(text);
            
            this.setFont(BUTTON_FONT);

            setBackground(INACTIVE_COLOR);
            setForeground(TEXT_INACTIVE_COLOR);
            setOpaque(true);
            setBorderPainted(false);

            this.addMouseListener(this);
            this.addItemListener(e -> {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    setBackground(ACTIVE_COLOR);
                    setForeground(TEXT_ACTIVE_COLOR); 
                } else if (e.getStateChange() == ItemEvent.DESELECTED){
                    setBackground(INACTIVE_COLOR);
                    setForeground(TEXT_INACTIVE_COLOR); 
                }
            });
        }

        private Dimension getProportionalSize(){
            int width = getParent() != null ? getParent().getWidth() : minWidth;

            if (width < minWidth){
                return MIN_BUTTON_SIZE;
            } else if (width > maxWidth) {
                return MAX_TOGGLE_SIZE;
            } else {
                int height = (int) (width * ratio);
                return new Dimension(width, height);
            }

        }
        
        @Override
        public Dimension getPreferredSize() {
            return getProportionalSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getProportionalSize(); 
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(!isSelected()){
                setBackground(HOVER_COLOR);
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(!isSelected()){
                setBackground(INACTIVE_COLOR);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelected()) {
                setBackground(HOVER_COLOR);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if(!isSelected()){
                if(contains(e.getPoint())){
                    setBackground(HOVER_COLOR);
                } else{ 
                    setBackground(INACTIVE_COLOR);
                }
            }
        }
    }
}
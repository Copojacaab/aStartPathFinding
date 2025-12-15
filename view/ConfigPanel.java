package view;

import view.CustumButton.ProportionalButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**Pannello di configurazione per le impostazioni globali
 * (Dimensioni griglia e velocita di visualizzazione a-star)
 */
public class ConfigPanel extends JPanel implements ChangeListener{

    // -- Componenti UI per dim griglia
    private JSpinner widthSpinner;
    private JSpinner heigthSpinner;
    private JButton applyGridSizeButton; // DA MODIFICARE CON APPLLY UNIFICATO

    // -- Componenti UI per speed Astar
    private JSlider speedSlider;
    private JLabel speedLabel; //etichetta per delay
    private JLabel speedValueLabel; //etichetta per valore numerico

    // -- Costanti stile
    private static final Color BG_COLOR = new Color(59, 64, 74);
    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    private static final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 12);
    private static final Font VALUE_FONT = new Font("Tahoma", Font.PLAIN, 12);
    private static final int INITIAL_WIDTH = 100;
    private static final int INITIAL_HEIGHT = 100;
    private static final int INITIAL_DELAY_MS = 50;
    
    // range per slider velocita
    private static final int MIN_DELAY_MS = 1;
    private static final int MAX_DELAY_MS = 100;

    public ConfigPanel(){
        // configurazione base del pannello
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // aggiungi sezioni
        addGridSizeConfiguration();
        addVerticalSeparator(20);
        addSpeedConfiguration();
    }

    // --- CONFIGURAZIONE GRIGLIA ---

    /** costruisce e aggiunge i componenti per la config della griglia*/
    private void addGridSizeConfiguration(){
        // wrapper per i controlli di input
        JPanel inputWrapper = new JPanel();
        inputWrapper.setLayout(new BoxLayout(inputWrapper, BoxLayout.X_AXIS));
        inputWrapper.setBackground(BG_COLOR);
        inputWrapper.setAlignmentX(Component.BOTTOM_ALIGNMENT);

        // intestazione sezione
        JLabel header = new JLabel("Grid Size (W x H)");
        header.setFont(LABEL_FONT);
        header.setForeground(TEXT_COLOR);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(header);
        addVerticalSeparator(5);

        // 1. spinner larghezza
        widthSpinner = new JSpinner(new SpinnerNumberModel(INITIAL_WIDTH, 10,200, 10));
        setupSpinner(widthSpinner, "W");

        // 2. spinner altezza
        heigthSpinner = new JSpinner(new SpinnerNumberModel(INITIAL_HEIGHT, 10, 200, 10));

        inputWrapper.add(widthSpinner);
        inputWrapper.add(Box.createHorizontalStrut(5));
        inputWrapper.add(new JLabel("x"));
        inputWrapper.add(Box.createHorizontalStrut(5));
        inputWrapper.add(heigthSpinner);
        inputWrapper.add(Box.createHorizontalGlue()); 

        this.add(inputWrapper);
        addVerticalSeparator(10);

        // 3. pulsante apply
        applyGridSizeButton = new ProportionalButton("Apply size");
        applyGridSizeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(applyGridSizeButton);
    } 

    /** applica stili e dimensioni a spinner */
    private void setupSpinner(JSpinner spinner, String tooltip){
        spinner.setPreferredSize(new Dimension(60, 25));
        spinner.setMaximumSize(new Dimension(80, 25));
        spinner.setToolTipText(tooltip);
        spinner.setFont(VALUE_FONT);
        spinner.setBackground(new Color(80, 88, 104));
    }

    // -- CONFIGURAZIONE SPEED A STAR

    /** costruisce e aggiunge componenti per config speed astar */
    private void addSpeedConfiguration() {
        // Slider velocita
        speedSlider = new JSlider(MIN_DELAY_MS, MAX_DELAY_MS, INITIAL_DELAY_MS);
        speedSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        setupSlider(speedSlider);

        // intestazione sezione
        JPanel speedWrapper = new JPanel();
        speedWrapper.setLayout(new BoxLayout(speedWrapper, BoxLayout.X_AXIS));
        speedWrapper.setBackground(BG_COLOR);
        speedWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        speedLabel = new JLabel("Visualizer Delay (ms):");
        speedLabel.setFont(LABEL_FONT);
        speedLabel.setForeground(TEXT_COLOR);

        speedValueLabel = new JLabel(String.valueOf(speedSlider.getValue()));
        speedValueLabel.setFont(VALUE_FONT);
        speedValueLabel.setForeground(Color.cyan);
        
        speedWrapper.add(speedLabel);
        speedWrapper.add(Box.createHorizontalStrut(5));
        speedWrapper.add(speedValueLabel);
        speedWrapper.add(Box.createHorizontalGlue());
        this.add(speedWrapper);
        addVerticalSeparator(5);

   

        // etichette personalizzate
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(MIN_DELAY_MS, new JLabel("Fast (1ms)"));
        labelTable.put(MAX_DELAY_MS, new JLabel("Slow (100ms)"));

        for(JLabel label : labelTable.values()){
            label.setFont(new Font("Tahoma", Font.PLAIN, 10));
            label.setForeground(new Color(200, 200, 200));
        }
        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);

        // listener per aggiornare il valore numerico
        speedSlider.addChangeListener(this);

        this.add(speedSlider);
    }

    /** Applica stili agli slider */
    private void setupSlider(JSlider slider){
        int sliderHeight = slider.getPreferredSize().height;
        slider.setMaximumSize(new Dimension(Integer.MAX_VALUE, sliderHeight));
        slider.setBackground(BG_COLOR);
        slider.setForeground(TEXT_COLOR);
        slider.setOpaque(true);
        slider.setPaintTicks(true);
    }

    // --- LISTENERS E HELPERS GENERICI ---

    /** aggiorna etichetta dinamica slider velocita */
    @Override
    public void stateChanged(ChangeEvent e) {
       if(e.getSource() == speedSlider){
        int value = speedSlider.getValue();
        speedValueLabel.setText(value + "ms");
       }
    }

    /**helper per aggiungere spazio verticale */
    private void addVerticalSeparator(int spacing){
        this.add(Box.createVerticalStrut(spacing));
    }
    
    // --- GETTERS PER CONTROLLER ---
    public JSpinner getWidthSpinner() { return this.widthSpinner; }
    public JSpinner getHeightSpinner() { return this.heigthSpinner; }
    public JButton getApplyGridSizeBtn() { return this.applyGridSizeButton; }
    public JSlider getSpeedSlider() { return this.speedSlider; }

    public int getCurrentDelayMs() { return this.speedSlider.getValue(); }
}

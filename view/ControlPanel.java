package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
public class ControlPanel extends JPanel{
    
    private JButton resetBtn;
    private JButton clearPathBtn;
    private JButton solveBtn;

    private JToggleButton eraseBtn;
    private JToggleButton pointsBtn;
    private JToggleButton wallBtn;

    private JSlider heuristicSlider;

    private JButton randMazeBtn;

    public ControlPanel(){
        Font buttonFont = new Font("Tahoma", Font.BOLD, 20);
        // layout con contenitore
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10)); //padding intorno al panel

        // creo wrapper intero
        JPanel wrapperControl = new JPanel();
        wrapperControl.setLayout(new BoxLayout(wrapperControl, BoxLayout.Y_AXIS));

        // init dei bottoni
        this.resetBtn = new ProportionalButton("Reset: 🔄");
        this.resetBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.resetBtn.setFont(buttonFont);
        this.clearPathBtn = new ProportionalButton("Reset Path ❌");
        this.clearPathBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.clearPathBtn.setFont(buttonFont);
        this.solveBtn = new ProportionalButton("Solve: ▶️");
        this.solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.solveBtn.setFont(buttonFont);

        this.eraseBtn = new ProportionalToggleButton("Erase: ✏️");
        this.eraseBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.eraseBtn.setFont(buttonFont);
        this.pointsBtn = new ProportionalToggleButton("Start/End: 📍");
        this.pointsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.pointsBtn.setFont(buttonFont);
        this.wallBtn = new ProportionalToggleButton("Walls: 🧱");
        this.wallBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.wallBtn.setFont(buttonFont);

        this.heuristicSlider = new JSlider();
        this.heuristicSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.heuristicSlider.setFont(buttonFont);

        this.randMazeBtn = new ProportionalButton("Random Maze: 🎲");
        this.randMazeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.randMazeBtn.setFont(buttonFont);

        // configurazione slider
        heuristicSlider.setMajorTickSpacing(10);
        heuristicSlider.setPaintTicks(true);
        heuristicSlider.setPaintLabels(true);

        int sliderHeight = heuristicSlider.getPreferredSize().height;
        heuristicSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, sliderHeight));
        heuristicSlider.setBackground(new Color(59, 64, 74));
        heuristicSlider.setForeground(new Color(230, 230, 230));
        heuristicSlider.setOpaque(true);
        
        // raggruppo i togglebtn
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(wallBtn);
        toolGroup.add(pointsBtn);
        toolGroup.add(eraseBtn);

        // add dei bottoni
        addButtons(wrapperControl);
        // aggiungo le componenti al wrapper

        // aggiungo il wrapper al panel
        this.add(wrapperControl, BorderLayout.NORTH);

        // colori
        this.setBackground(new Color(59, 64, 74));
        wrapperControl.setBackground(new Color(59,64, 74));

        this.setMinimumSize(new Dimension(200,0));
    }

    // --------------------------- HELPER ------------------
        // Dimension buttonSize = new Dimension(180, 50);
        // Dimension maxButtonSize = new Dimension(360, 100);

    private void addButtons(JPanel wrapperControl){
        int spacing = 10;

        wrapperControl.add(resetBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(clearPathBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(solveBtn);
        
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(wallBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(pointsBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(eraseBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(randMazeBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        wrapperControl.add(heuristicSlider);
    }
    // getter
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getClearPathBtn() { return this.clearPathBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
    public JToggleButton getEraseBtn() { return this.eraseBtn; }
    public JToggleButton getPointsBtn() { return this.pointsBtn; }
    public JToggleButton getWallBtn() { return this.wallBtn; }
    public JButton getRandMaze() { return this.randMazeBtn; }
    public JSlider getHeuristicSlider() { return heuristicSlider; }

    /**bottone personalizzato per proportional scaling */
    private class ProportionalButton extends JButton implements MouseListener{
        private double ratio = 50.0 / 180.0;

        private int minWidth = 180;
        private int minHeight = (int) (minWidth * ratio);
        private final Dimension MIN_BUTTON_SIZE = new Dimension(minWidth, minHeight);

        private int maxWidth = 500;
        private int maxHeight = (int) (maxWidth * ratio);
        private final Dimension MAX_BUTTON_SIZE = new Dimension(maxWidth, maxHeight);

        // colori
        private final Color INACTIVE_COLOR = new Color(80, 88, 104);
        private final Color HOVER_COLOR = new Color(70, 78, 94);
        private final Color PRESSED_COLOR = new Color(60, 68, 84);

        public ProportionalButton(String text){
            super(text);
            
            setBackground(new Color(80, 88, 102));
            setForeground(new Color(230, 230, 230));
            setOpaque(true);
            setBorderPainted(false);

            addMouseListener(this);
        }

        private Dimension getProportionalSize(){
            int width = getParent().getWidth();

            if(width < minWidth){
                return MIN_BUTTON_SIZE;
            }else if (width > maxWidth) {
                return MAX_BUTTON_SIZE;
            } else {
                // calcolo altezza con la proporzione
                int height = (int) (width * ratio);
                return new Dimension(width, height);
            }
            

        }
        
        // sovrascrivo i metodi del layout manager
        @Override
        public Dimension getPreferredSize() {
            return getProportionalSize();
        }

        @Override
        public Dimension getMaximumSize() {
            // Diciamo che la dimensione massima è la stessa della preferita
            return getProportionalSize(); 
        }
                @Override
        public void mouseEntered(MouseEvent e) {
            setBackground(HOVER_COLOR);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(INACTIVE_COLOR);
        }
        @Override
        public void mousePressed(MouseEvent e) {
            setBackground(PRESSED_COLOR);
        }

        // inutili
        @Override
        public void mouseClicked(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (contains(e.getPoint())) {
                setBackground(HOVER_COLOR); // Se sì, torna a hover
            } else {
                setBackground(INACTIVE_COLOR); // Se no, torna a inattivo
            }
        }
    }

    private class ProportionalToggleButton extends JToggleButton implements MouseListener{

        private double ratio = 50.0 / 180.0;

        // dimensioni
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
        private final Color TEXT_ACTIVE_COLOR = Color.BLACK; // Testo scuro per lo sfondo chiaro

        public ProportionalToggleButton(String text){
            super(text);
            
            setBackground(INACTIVE_COLOR);
            setForeground(new Color(230, 230, 230));
            setOpaque(true);
            setBorderPainted(false);

            this.addMouseListener(this);
            // aggiungo il listener a se stesso
            this.addItemListener(e -> {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    setBackground(ACTIVE_COLOR);
                    setForeground(TEXT_ACTIVE_COLOR); // <-- Aggiungi questo
                } else if (e.getStateChange() == ItemEvent.DESELECTED){
                    setBackground(INACTIVE_COLOR);
                    setForeground(TEXT_INACTIVE_COLOR); // <-- Aggiungi questo
                }
            });
        }

        private Dimension getProportionalSize(){
            int width = getParent().getWidth();

            if (width < minWidth){
                return MIN_BUTTON_SIZE;
            } else if (width > maxWidth) {
                return MAX_TOGGLE_SIZE;
            } else {
                // calcolo altezza con la proporzione
                int height = (int) (width * ratio);
                return new Dimension(width, height);
            }

        }
        
        // sovrascrivo i metodi del layout manager
        @Override
        public Dimension getPreferredSize() {
            return getProportionalSize();
        }

        @Override
        public Dimension getMaximumSize() {
            // Diciamo che la dimensione massima è la stessa della preferita
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

        // inutili
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



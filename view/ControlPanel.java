package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

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
        // layout con contenitore
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10,10,10,10)); //padding intorno al panel

        // creo wrapper intero
        JPanel wrapperControl = new JPanel();
        wrapperControl.setLayout(new BoxLayout(wrapperControl, BoxLayout.Y_AXIS));

        // init dei bottoni
        this.resetBtn = new JButton("Reset: 🔄");
        this.clearPathBtn = new JButton("Reset Path ❌");
        this.solveBtn = new JButton("Solve: ▶️");

        this.eraseBtn = new JToggleButton("Erase: ✏️");
        this.pointsBtn = new JToggleButton("Start/End: 📍");
        this.wallBtn = new JToggleButton("Walls: 🧱");

        this.heuristicSlider = new JSlider(10,100,10);

        this.randMazeBtn = new JButton("Random Maze: 🎲");

        // configurazione slider
        heuristicSlider.setMajorTickSpacing(10);
        heuristicSlider.setPaintTicks(true);
        heuristicSlider.setPaintLabels(true);

        // dimensioni dei bottoni
        setButtonPreferredSize();

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
    }

    // --------------------------- HELPER ------------------
// In ControlPanel.java
private void setButtonPreferredSize(){
    Dimension buttonSize = new Dimension(180, 35);
    Dimension maxButtonSize = new Dimension(Integer.MAX_VALUE, 70);

    resetBtn.setPreferredSize(buttonSize);
    resetBtn.setMaximumSize(maxButtonSize);

    clearPathBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    clearPathBtn.setMaximumSize(maxButtonSize);

    solveBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    solveBtn.setMaximumSize(maxButtonSize);

    wallBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    wallBtn.setMaximumSize(maxButtonSize);

    pointsBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    pointsBtn.setMaximumSize(maxButtonSize);

    eraseBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    eraseBtn.setMaximumSize(maxButtonSize);
    
    randMazeBtn.setPreferredSize(buttonSize); // <-- CORRETTO
    randMazeBtn.setMaximumSize(maxButtonSize);

    heuristicSlider.setPreferredSize(new Dimension(180, heuristicSlider.getPreferredSize().height));
    heuristicSlider.setMaximumSize(new Dimension(Integer.MAX_VALUE, heuristicSlider.getPreferredSize().height));
}

    private void addButtons(JPanel wrapperControl){
        int spacing = 10;

        resetBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(resetBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        clearPathBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(clearPathBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(solveBtn);
        
        wrapperControl.add(Box.createVerticalStrut(spacing));
        wallBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(wallBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        pointsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(pointsBtn);
        wrapperControl.add(Box.createVerticalStrut(spacing));
        eraseBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(eraseBtn);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        heuristicSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(heuristicSlider);

        wrapperControl.add(Box.createVerticalStrut(spacing));
        randMazeBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(randMazeBtn);
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
}

package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

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
        this.resetBtn = new JButton("Reset");
        this.clearPathBtn = new JButton("Reset Path");
        this.solveBtn = new JButton("Solve");

        this.eraseBtn = new JToggleButton("Erase");
        this.pointsBtn = new JToggleButton("Start/End");
        this.wallBtn = new JToggleButton("Walls");

        this.heuristicSlider = new JSlider(10,100,10);

        this.randMazeBtn = new JButton("Random Maze");

        // configurazione slider
        heuristicSlider.setMajorTickSpacing(1);
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
        this.setPreferredSize(new java.awt.Dimension(200, 0));
    }

    // --------------------------- HELPER ------------------
    private void setButtonPreferredSize(){
        Dimension buttonSize = new Dimension(180,35);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        resetBtn.setPreferredSize(buttonSize);
        resetBtn.setMaximumSize(buttonSize);

        heuristicSlider.setPreferredSize(new Dimension(180, heuristicSlider.getPreferredSize().height));
        heuristicSlider.setMaximumSize(new Dimension(180, heuristicSlider.getPreferredSize().height));
    }

    private void addButtons(JPanel wrapperControl){
        resetBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(resetBtn);

        clearPathBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(clearPathBtn);
        solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(solveBtn);
        
        wallBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(wallBtn);
        pointsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(pointsBtn);
        eraseBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(eraseBtn);

        heuristicSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapperControl.add(heuristicSlider);
        
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

package view;

import java.awt.BorderLayout;
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

        // raggruppo i togglebtn
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(wallBtn);
        toolGroup.add(pointsBtn);
        toolGroup.add(eraseBtn);

        // aggiungo le componenti al wrapper
        wrapperControl.add(resetBtn);
        wrapperControl.add(clearPathBtn);
        wrapperControl.add(solveBtn);
        
        wrapperControl.add(wallBtn);
        wrapperControl.add(pointsBtn);
        wrapperControl.add(eraseBtn);

        wrapperControl.add(heuristicSlider);
        
        wrapperControl.add(randMazeBtn);

        // aggiungo il wrapper al panel
        this.add(wrapperControl, BorderLayout.NORTH);
        this.setPreferredSize(new java.awt.Dimension(200, 0));
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

package view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
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
        // layout a colonna
        //5,5 per gap tra i componenti

        this.resetBtn = new JButton("Reset");
        this.clearPathBtn = new JButton("Reset Path");
        this.solveBtn = new JButton("Solve");

        this.eraseBtn = new JToggleButton("Erase");
        this.pointsBtn = new JToggleButton("Start/End");
        this.wallBtn = new JToggleButton("Walls");

        this.heuristicSlider = new JSlider(10,100,10);

        this.randMazeBtn = new JButton("Random Maze");

        // da mettere in helper
        heuristicSlider.setMajorTickSpacing(10);
        heuristicSlider.setPaintTicks(true);
        heuristicSlider.setPaintLabels(true);
        // raggruppo in ButtonGroup per sicurezza unicita attivazione
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(wallBtn);
        toolGroup.add(pointsBtn);
        toolGroup.add(eraseBtn);

        this.add(resetBtn);
        this.add(clearPathBtn);
        this.add(solveBtn);
        
        this.add(wallBtn);
        this.add(pointsBtn);
        this.add(eraseBtn);

        this.add(heuristicSlider);
        
        this.add(randMazeBtn);
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

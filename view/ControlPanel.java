package view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
public class ControlPanel extends JPanel{
    
    private JButton resetBtn;
    private JButton solveBtn;

    private JToggleButton eraseBtn;
    private JToggleButton pointsBtn;
    private JToggleButton wallBtn;

    public ControlPanel(){
        this.resetBtn = new JButton("Reset");
        this.solveBtn = new JButton("Solve");

        this.eraseBtn = new JToggleButton("Erase");
        this.pointsBtn = new JToggleButton("Start/End");
        this.wallBtn = new JToggleButton("Walls");

        // raggruppo in ButtonGroup per sicurezza unicita attivazione
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(wallBtn);
        toolGroup.add(pointsBtn);
        toolGroup.add(eraseBtn);

        this.add(resetBtn);
        this.add(solveBtn);
        
        this.add(wallBtn);
        this.add(pointsBtn);
        this.add(eraseBtn);
    }

    // getter
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
    public JToggleButton getEraseBtn() { return this.eraseBtn; }
    public JToggleButton getPointsBtn() { return this.pointsBtn; }
    public JToggleButton getWallBtn() { return this.wallBtn; }
}

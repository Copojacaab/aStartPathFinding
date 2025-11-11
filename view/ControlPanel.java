package view;

import javax.swing.JPanel;
import javax.swing.JButton;
public class ControlPanel extends JPanel{
    
    private JButton resetBtn;
    private JButton solveBtn;

    public ControlPanel(){
        this.resetBtn = new JButton("Reset");
        this.solveBtn = new JButton("Solve");
        this.add(resetBtn);
        this.add(solveBtn);
    }

    // getter
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
}

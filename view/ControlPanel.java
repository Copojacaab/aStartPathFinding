package view;

import javax.swing.JPanel;
import javax.swing.JButton;
public class ControlPanel extends JPanel{
    
    private JButton resetBtn;
    private JButton solveBtn;
    private JButton eraseBtn;

    public ControlPanel(){
        this.resetBtn = new JButton("Reset");
        this.solveBtn = new JButton("Solve");
        this.eraseBtn = new JButton("Erase");
        this.add(resetBtn);
        this.add(solveBtn);
        this.add(eraseBtn);
    }

    // getter
    public JButton getResetBtn() { return this.resetBtn; }
    public JButton getSolveBtn() { return this.solveBtn; }
}

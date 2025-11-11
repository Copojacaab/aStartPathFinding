package view;

import javax.swing.JPanel;
import javax.swing.JButton;
public class ControlPanel extends JPanel{
    
    private JButton resetBtn;

    public ControlPanel(){
        this.resetBtn = new JButton("Reset");
        this.add(resetBtn);
    }

    // getter
    public JButton getResetBtn() { return this.resetBtn; }
}

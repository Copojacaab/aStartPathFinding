package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import view.CustumButton.ProportionalToggleButton;

/**
 * pannello che implementa la barra degli strumenti
 */
public class ToolBarPanel extends JPanel{
    
    private JToggleButton eraseBtn;
    private JToggleButton pointsBtn;
    private JToggleButton wallBtn;


    public ToolBarPanel(){
        // configurazione layout
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(new Color(59, 64, 74));
        this.setBorder(new EmptyBorder(5, 10, 5, 10));

        // re-init dei toggle
        this.wallBtn = new ProportionalToggleButton("Walls");
        this.eraseBtn = new ProportionalToggleButton("Erase");
        this.pointsBtn = new ProportionalToggleButton("Start/End");

        // raggruppo i toggle in un buttongruoup per mutua esclusione
        ButtonGroup toolGroup = new ButtonGroup();
        toolGroup.add(wallBtn);
        toolGroup.add(eraseBtn);
        toolGroup.add(pointsBtn);

        // aggiungo bottoni al panel (elastic space)
        this.add(Box.createHorizontalGlue()); // spazio elastico a sx
        this.add(wallBtn);
        this.add(Box.createHorizontalStrut(10));
        this.add(eraseBtn);
        this.add(Box.createHorizontalStrut(10));
        this.add(pointsBtn);
        this.add(Box.createHorizontalGlue()); //spazio elastico a dx

        // default wall attivo
        wallBtn.setSelected(true);

        // style and dim
        Font buttonFont = new Font("Tahoma", Font.BOLD, 16);
        int minHeight = 40;
        int minWidth = 150;
        Dimension toolSize = new Dimension(minWidth, minHeight);

        wallBtn.setFont(buttonFont);
        wallBtn.setPreferredSize(toolSize);
        wallBtn.setMaximumSize(toolSize);

        eraseBtn.setFont(buttonFont);
        eraseBtn.setPreferredSize(toolSize);
        eraseBtn.setMaximumSize(toolSize);

        pointsBtn.setFont(buttonFont);
        pointsBtn.setPreferredSize(toolSize);
        pointsBtn.setMaximumSize(toolSize);
    }

    // getters per il controller pe agganciare i listener
    public JToggleButton getWallButton() { return this.wallBtn; }
    public JToggleButton getEraseButton() { return this.eraseBtn; }
    public JToggleButton getPointsButton() { return this.pointsBtn; }
}

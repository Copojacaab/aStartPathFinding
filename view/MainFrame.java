package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private GridPanel gridPanel;
    private ControlPanel controlPanel;

    public MainFrame(){
        setTitle("A* PathFinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initLayout();

        setSize(800, 600);
        
        setLocationRelativeTo(null); //centra la finestra
    }

    private void initLayout(){
        setLayout(new BorderLayout());

        this.gridPanel = new GridPanel();
        this.controlPanel = new ControlPanel();
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public GridPanel getGridPanel() { return this.gridPanel; }
    public ControlPanel getControlPanel() { return this.controlPanel; }
}

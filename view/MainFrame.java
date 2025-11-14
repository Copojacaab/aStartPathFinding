package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class MainFrame extends JFrame{
    private GridPanel gridPanel;
    private ControlPanel controlPanel;

    public MainFrame(){
        setTitle("A* PathFinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initLayout();

        setSize(800, 600);

        this.setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null); //centra la finestra
    }

    private void initLayout(){
        setLayout(new BorderLayout());

        this.gridPanel = new GridPanel();
        this.controlPanel = new ControlPanel();
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(gridPanel);
        splitPane.setRightComponent(controlPanel);

        splitPane.setResizeWeight(0.9);
        splitPane.setDividerLocation(0.9);
        
        this.add(splitPane, BorderLayout.CENTER);
    }


    public GridPanel getGridPanel() { return this.gridPanel; }
    public ControlPanel getControlPanel() { return this.controlPanel; }
}

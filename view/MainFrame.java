package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    private GridPanel gridPanel;

    public MainFrame(){
        setTitle("A* PathFinding Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initLayout();

        pack();
        
        setLocationRelativeTo(null); //centra la finestra
    }

    private void initLayout(){
        setLayout(new BorderLayout());

        this.gridPanel = new GridPanel();
        add(gridPanel, BorderLayout.CENTER);
    }

    public GridPanel getGridPanel() { return this.gridPanel; }
}

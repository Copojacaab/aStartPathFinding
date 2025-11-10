import javax.swing.SwingUtilities;

import model.Grid;
import model.NodeType;
import view.MainFrame;

public class Main {
    
    public static void main(String[] args){
        // Avvia interfaccia grafica su event dispatch thread
        SwingUtilities.invokeLater(() -> {

            // crea model
            int largezza = 30;
            int altezza = 20;
            Grid grid = new Grid(largezza, altezza);

            // -- TEST --
            grid.getNode(5,5).setType(NodeType.WALL);
            grid.getNode(5,6).setType(NodeType.WALL);
            grid.getNode(5, 7).setType(NodeType.WALL);
            grid.getNode(10,10).setType(NodeType.START);
            grid.getNode(15,15).setType(NodeType.END);

            // crea view
            MainFrame frame = new MainFrame();

            // collega model e view
            frame.getGridPanel().setGrid(grid);

            frame.setVisible(true);
        });
    }
}

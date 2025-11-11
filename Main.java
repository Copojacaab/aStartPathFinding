import javax.swing.SwingUtilities;

import model.Grid;
import model.NodeType;
import view.MainFrame;
import controller.AppController;

public class Main {
    
    public static void main(String[] args){
        // Avvia interfaccia grafica su event dispatch thread
        SwingUtilities.invokeLater(() -> {

            // crea model
            int largezza = 30;
            int altezza = 20;
            Grid grid = new Grid(largezza, altezza);

            // -- TEST --

            // crea view
            MainFrame frame = new MainFrame();
            // crea il controller
            AppController controller = new AppController(grid, frame);
            controller.initController();

            frame.getGridPanel().setGrid(grid);
            frame.setVisible(true);
        });
    }
}

package view;

import model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GridPanel extends JPanel{
    
    private Grid grid; //riferimento al model

    /** metodo per collegare il model alla view */
    public void setGrid(Grid grid){
        this.grid = grid;
        repaint(); 
    }

    // @Override
    // public Dimension getPreferredSize(){
    //     if (grid == null) {
    //         return new Dimension(800,600);
    //     }

    //     int width = grid.getWidth() * CELL_SIZE;
    //     int height = grid.getHeight() * CELL_SIZE;
    //     return new Dimension(width, height);
    // }

    /**
     * Chiamato da Swing 
     */
    @Override
    protected void paintComponent(Graphics g){
        //cleanup
        super.paintComponent(g);
        if(grid == null)
            return;

        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();

        // disegna ogni cella
        for(int y = 0; y < gridHeight; y++){
            for(int x = 0; x < gridWidth; x++){
                Node node = grid.getNode(x, y);
                Color cellColor = getColorForType(node.getType());

                DynamicDimension dims = getDynamicDimension();
                int cellSize = dims.getCellSize();
                int xOffset = dims.getXOffSet();
                int yOffset = dims.getYOffset();

                // calcolo le coordiante in px
                int drawX = x * cellSize + xOffset;
                int drawY = y * cellSize + yOffset;

                // disegno rettangolo pieno(cella)
                g.setColor(cellColor);
                g.fillRect(drawX, drawY, cellSize, cellSize);
                // disegno bordo nero (griglia)
                g.setColor(Color.black);
                g.drawRect(drawX, drawY, cellSize, cellSize);
            }
        }
    }

    private Color getColorForType(NodeType type){
        switch (type) {
            case EMPTY:
                return Color.white;            case WALL:
                return Color.black;
            case START:
                return Color.green;
            case END:
                return Color.red;
            case OPEN:
                return new Color(175, 215, 230);
            case CLOSED:
                return new Color(145, 240, 145);
            case PATH:
                return Color.yellow;
            default:
                return Color.gray;
        }
    }

    /**
     * Metodo per tradurre px del mouse in nodo della griglia
     * incapsulazione della griglia
     */
    public Node getNodeAt(int mouseX, int mouseY){
        if (grid == null) return null; //check

        // prendo le dimensioni dinamiche
        DynamicDimension dims = getDynamicDimension();
        int cellSize = dims.getCellSize();
        int xOffset = dims.getXOffSet();
        int yOffset = dims.getYOffset();

        // prendo il nodo cliccato 
        int cellX = (mouseX - xOffset) / cellSize;
        int cellY = (mouseY - yOffset) / cellSize;

        return grid.getNode(cellX, cellY);
    }

    // -------------- HELPER ---------------
    private DynamicDimension getDynamicDimension(){
        // prendo le dimensioni del pannello
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        // calcolo dim celle
        int cellWidth = panelWidth / grid.getWidth();
        int cellHeight = panelHeight / grid.getHeight();

        // dimensione piu' piccola per coerenza celle
        int cellSize = Math.min(cellHeight, cellWidth);

        // calcolo offset per centrare
        int xOffset = (panelWidth - (grid.getWidth() * cellSize)) / 2;
        int yOffset = (panelHeight - (grid.getHeight() * cellSize)) / 2;

        return new DynamicDimension(cellSize, xOffset, yOffset);
    }
    // Classe per restituire le dimensioni dinamiche delle celle e della griglia
    final class DynamicDimension{
        private final int cellSize;
        private final int xOffset;
        private final int yOffset;

        public DynamicDimension(int cellSize, int xOffset, int yOffset){
            this.cellSize = cellSize;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        public int getCellSize() { return this.cellSize; }
        public int getXOffSet() { return this.xOffset; }
        public int getYOffset() { return this.yOffset; }
    }
}

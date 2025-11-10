package view;

import model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GridPanel extends JPanel{
    
    private Grid grid; //riferimento al model

    private static final int CELL_SIZE = 25; //25x25 px

    /** metodo per collegare il model alla view */
    public void setGrid(Grid grid){
        this.grid = grid;
        repaint(); 
    }

    @Override
    public Dimension getPreferredSize(){
        if (grid == null) {
            return new Dimension(800,600);
        }

        int width = grid.getWidth() * CELL_SIZE;
        int height = grid.getHeight() * CELL_SIZE;
        return new Dimension(width, height);
    }

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

                // calcolo le coordiante in px
                int drawX = x * CELL_SIZE;
                int drawY = y * CELL_SIZE;

                // disegno rettangolo pieno(cella)
                g.setColor(cellColor);
                g.fillRect(drawX, drawY, CELL_SIZE, CELL_SIZE);
                // disegno bordo nero (griglia)
                g.setColor(Color.black);
                g.drawRect(drawX, drawY, CELL_SIZE, CELL_SIZE);
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

}

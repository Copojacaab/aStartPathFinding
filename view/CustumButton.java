package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 * contiene le classi di componenti custom per i pulsanti
 */
public class CustumButton {
    // font comune
    private static final Font COMMON_FONT = new Font("Tahoma", Font.BOLD, 16);

    // colori di base
    private static final Color INACTIVE_COLOR = new Color(80, 88, 104);
    private static final Color HOVER_COLOR = new Color(70, 78, 94);
    private static final Color PRESSED_COLOR = new Color(60, 68, 84);
    private static final Color ACTIVE_COLOR = Color.yellow;
    private static final Color TEXT_INACTIVE_COLOR = new Color(230, 230, 230);
    private static final Color TEXT_ACTIVE_COLOR = Color.BLACK;

    public static class ProportionalButton extends JButton implements MouseListener {

        private double ratio = 40.0 / 180.0; // Rapporto Altezza/Larghezza (compatto)
        private int minWidth = 180;
        private int maxWidth = 500;

        public ProportionalButton(String text) {
            super(text);
            setFont(COMMON_FONT);
            setBackground(INACTIVE_COLOR);
            setForeground(TEXT_INACTIVE_COLOR);
            setOpaque(true);
            setBorderPainted(false);
            addMouseListener(this);
        }

        /**
         * Calcola la dimensione proporzionale in base alla larghezza del genitore (più
         * robusto).
         */
        private Dimension getProportionalSize() {
            int width = getParent() != null ? getParent().getWidth() : minWidth;
            int height = (int) (width * ratio);

            // Applica limiti min/max
            if (width < minWidth)
                width = minWidth;
            if (width > maxWidth)
                width = maxWidth;

            // Ricalcola altezza finale
            height = (int) (width * ratio);

            return new Dimension(width, height);
        }

        @Override
        public Dimension getPreferredSize() {
            return getProportionalSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getProportionalSize();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setBackground(PRESSED_COLOR);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBackground(HOVER_COLOR);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(INACTIVE_COLOR);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (contains(e.getPoint())) {
                setBackground(HOVER_COLOR);
            } else {
                setBackground(INACTIVE_COLOR);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    public class ProportionalToggleButton extends JToggleButton implements MouseListener {
        private double ratio = 40.0 / 180.0;
        private int minWidth = 180;
        private int maxWidth = 500;

        public ProportionalToggleButton(String text) {
            super(text);
            this.setFont(COMMON_FONT);

            setBackground(INACTIVE_COLOR);
            setForeground(TEXT_INACTIVE_COLOR);
            setOpaque(true);
            setBorderPainted(false);

            this.addMouseListener(this);
            
            this.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setBackground(ACTIVE_COLOR);
                    setForeground(TEXT_ACTIVE_COLOR);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    setBackground(INACTIVE_COLOR);
                    setForeground(TEXT_INACTIVE_COLOR);
                }
            });
        }

        private Dimension getProportionalSize() {
            int width = getParent() != null ? getParent().getWidth() : minWidth;
            
            if(width < minWidth) width = minWidth;
            if(width > maxWidth) width = maxWidth;

            int height = (int) (width * ratio);

            return new Dimension(width, height);
        }

        @Override
        public Dimension getPreferredSize() {
            return getProportionalSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getProportionalSize();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!isSelected()) {
                setBackground(HOVER_COLOR);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!isSelected()) {
                setBackground(INACTIVE_COLOR);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelected()) {
                setBackground(HOVER_COLOR);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!isSelected()) {
                if (contains(e.getPoint())) {
                    setBackground(HOVER_COLOR);
                } else {
                    setBackground(INACTIVE_COLOR);
                }
            }
        }
    }

}

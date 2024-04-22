package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.io.File;
import java.awt.datatransfer.*;
import model.Card;

public class CardPanel extends JPanel {
    private Card card;
    private JLabel cardLabel;
    private Image backgroundImage;

    public CardPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        cardLabel = new JLabel();
        this.add(cardLabel);

        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, new DragGestureListener() {
            public void dragGestureRecognized(DragGestureEvent event) {
                if (card != null) {
                    event.startDrag(null, new TransferableCard(card));
                }
            }
        });
    }

    public void setCard(Card card) {
        this.card = card;
        int x = card.getValue();
        cardLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            if (card.getIsEnemy()) {
                ImageIcon imageIcon = new ImageIcon("Projekt/src/pictures/Enemy-" + String.valueOf(x) + ".png");
                backgroundImage = imageIcon.getImage();
            } else {
                ImageIcon imageIcon = new ImageIcon("Projekt/src/pictures/My-" + String.valueOf(x) + ".png");
                backgroundImage = imageIcon.getImage();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        // Scale the image to fit the panel

        repaint();
    }

    public Card getCard() {
        try {
            return this.card;
        } catch (Exception e) {
            return null;
        }
    }

    private class TransferableCard implements Transferable {
        private Card card;

        public TransferableCard(Card card) {
            this.card = card;
        }

        public Object getTransferData(DataFlavor flavor) {
            return card;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { new DataFlavor(Card.class, "Card") };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(getTransferDataFlavors()[0]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 1, this.getWidth(), this.getHeight(), this);
    }
}

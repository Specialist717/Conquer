package view;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
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
        ImageIcon imageIcon = new ImageIcon("src\\pictures\\karta.png");
        backgroundImage = imageIcon.getImage();
    }

    public void setCard(Card card) {
        this.card = card;
        Font newFont = new Font("Arial", Font.BOLD, 25);
        cardLabel.setFont(newFont);
        // cardLabel.setVerticalAlignment(JLabel.CENTER);
        cardLabel.setHorizontalAlignment(JLabel.CENTER);

        if (card != null) {
            cardLabel.setText(card.toString());
            if (card.getIsEnemy()) {
                this.setBackground(Color.RED);
            } else {
                this.setBackground(Color.GREEN);
            }
        } else {
            cardLabel.setText("");
            this.setBackground(null); 
        }
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
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
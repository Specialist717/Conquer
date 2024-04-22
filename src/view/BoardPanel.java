package view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import model.Card;

public class BoardPanel extends JPanel {
    private static final int SIZE = 6;
    private final CardPanel[][] grid;
    private boolean isDropEnabled = false;
    private Card lastPlacedCard;
    private int lastPlacedX;
    private int lastPlacedY;
    private Image backgroundImage;

    public BoardPanel() {
        this.setLayout(new GridLayout(SIZE, SIZE));
        this.grid = new CardPanel[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    grid[i][j] = new CardPanel();
                    grid[i][j].setOpaque(false);
                    this.add(grid[i][j]);
                }
            }
            
            new DropTarget(this, new DropTargetAdapter() {
                public void drop(DropTargetDropEvent event) {
                    if (isDropEnabled) {
                        try {
                            Card card = (Card) event.getTransferable().getTransferData(new DataFlavor(Card.class, "Card"));
                            int x = event.getLocation().x / (getWidth() / SIZE);
                            int y = event.getLocation().y / (getHeight() / SIZE);
                            try {
                                placeCard(x, y, card);
                                event.dropComplete(true);
                                isDropEnabled = false;
                            } catch (Exception e) {
                                System.out.println("Cannot place card here. Please try again.");
                                event.rejectDrop();
                            }
                        } catch (Exception e) {
                            event.rejectDrop();
                        }
                    } else {
                        event.rejectDrop();
                    }
                }
            });
        } 

        public void placeCard(int x, int y, Card card) throws Exception {
        CardPanel boardField = grid[y][x];
        Card cardhere = boardField.getCard();
        if (boardField.getCard() == null || cardhere.getIsEnemy() == true && cardhere.getValue() < card.getValue()) {
            grid[y][x].setCard(card);
        } else {
            throw new Exception("Cant place card here");
        }

        lastPlacedCard = card;
        lastPlacedX = x;
        lastPlacedY = y;
    }

    public void updateBoard(int x, int y, Card card) {
        grid[y][x].setCard(card);
    }

    public void setDropEnabled(boolean enabled) {
        this.isDropEnabled = enabled;
    }

    public String getLastPlacedCardInfo() {
        if (lastPlacedCard == null) {
            return "No card has been placed yet.";
        } else {
            return lastPlacedX + " " + lastPlacedY + " " + lastPlacedCard.getValue();
        }
    }

    public boolean isDropEnabled() {
        return this.isDropEnabled;
    }  

    public boolean checkWinCondition() {
        if (grid[0][0].getCard() != null && !grid[0][0].getCard().getIsEnemy() && grid[1][1].getCard() != null && !grid[1][1].getCard().getIsEnemy() && grid[2][2].getCard() != null && !grid[2][2].getCard().getIsEnemy() && grid[3][3].getCard() != null && !grid[3][3].getCard().getIsEnemy() && grid[4][4].getCard() != null && !grid[4][4].getCard().getIsEnemy() ||
            grid[1][1].getCard() != null && !grid[1][1].getCard().getIsEnemy() && grid[2][2].getCard() != null && !grid[2][2].getCard().getIsEnemy() && grid[3][3].getCard() != null && !grid[3][3].getCard().getIsEnemy() && grid[4][4].getCard() != null && !grid[4][4].getCard().getIsEnemy() && grid[5][5].getCard() != null && !grid[5][5].getCard().getIsEnemy() ||
            grid[0][1].getCard() != null && !grid[0][1].getCard().getIsEnemy() && grid[1][2].getCard() != null && !grid[1][2].getCard().getIsEnemy() && grid[2][3].getCard() != null && !grid[2][3].getCard().getIsEnemy() && grid[3][4].getCard() != null && !grid[3][4].getCard().getIsEnemy() && grid[4][5].getCard() != null && !grid[4][5].getCard().getIsEnemy() ||
            grid[1][0].getCard() != null && !grid[1][0].getCard().getIsEnemy() && grid[2][1].getCard() != null && !grid[2][1].getCard().getIsEnemy() && grid[3][2].getCard() != null && !grid[3][2].getCard().getIsEnemy() && grid[4][3].getCard() != null && !grid[4][3].getCard().getIsEnemy() && grid[5][4].getCard() != null && !grid[5][4].getCard().getIsEnemy() ||
            grid[0][5].getCard() != null && !grid[0][5].getCard().getIsEnemy() && grid[1][4].getCard() != null && !grid[1][4].getCard().getIsEnemy() && grid[2][3].getCard() != null && !grid[2][3].getCard().getIsEnemy() && grid[3][2].getCard() != null && !grid[3][2].getCard().getIsEnemy() && grid[4][1].getCard() != null && !grid[4][1].getCard().getIsEnemy() ||
            grid[1][4].getCard() != null && !grid[1][4].getCard().getIsEnemy() && grid[2][3].getCard() != null && !grid[2][3].getCard().getIsEnemy() && grid[3][2].getCard() != null && !grid[3][2].getCard().getIsEnemy() && grid[4][1].getCard() != null && !grid[4][1].getCard().getIsEnemy() && grid[5][0].getCard() != null && !grid[5][0].getCard().getIsEnemy() ||
            grid[0][4].getCard() != null && !grid[0][4].getCard().getIsEnemy() && grid[1][3].getCard() != null && !grid[1][3].getCard().getIsEnemy() && grid[2][2].getCard() != null && !grid[2][2].getCard().getIsEnemy() && grid[3][1].getCard() != null && !grid[3][1].getCard().getIsEnemy() && grid[4][0].getCard() != null && !grid[4][0].getCard().getIsEnemy() ||
            grid[1][5].getCard() != null && !grid[1][5].getCard().getIsEnemy() && grid[2][4].getCard() != null && !grid[2][4].getCard().getIsEnemy() && grid[3][3].getCard() != null && !grid[3][3].getCard().getIsEnemy() && grid[4][2].getCard() != null && !grid[4][2].getCard().getIsEnemy() && grid[5][1].getCard() != null && !grid[5][1].getCard().getIsEnemy()
            ) {
            return true;
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].getCard() != null && !grid[i][j].getCard().getIsEnemy()) {
                    if (j <= SIZE - 5 
                    && grid[i][j+1].getCard() != null && !grid[i][j+1].getCard().getIsEnemy() 
                    && grid[i][j+2].getCard() != null && !grid[i][j+2].getCard().getIsEnemy() 
                    && grid[i][j+3].getCard() != null && !grid[i][j+3].getCard().getIsEnemy() 
                    && grid[i][j+4].getCard() != null && !grid[i][j+4].getCard().getIsEnemy()) {
                    return true;
                }
                    if (i <= SIZE - 5 
                    && grid[i+1][j].getCard() != null && !grid[i+1][j].getCard().getIsEnemy() 
                    && grid[i+2][j].getCard() != null && !grid[i+2][j].getCard().getIsEnemy() 
                    && grid[i+3][j].getCard() != null && !grid[i+3][j].getCard().getIsEnemy() 
                    && grid[i+4][j].getCard() != null && !grid[i+4][j].getCard().getIsEnemy()) {
                    return true;
                }
                }
            }
        }
        return false;
    }

    public void clear() {
        this.removeAll(); 
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new CardPanel();
                this.add(grid[i][j]);
            }
        }
        this.revalidate(); 
        this.repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
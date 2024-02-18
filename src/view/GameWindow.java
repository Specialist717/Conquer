package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import model.Card;
import model.Deck;

public class GameWindow extends JFrame {
    private BoardPanel boardPanel;
    private Deck deck = new Deck();
    private Card card1 = null;
    private Card card2 = null;
    private Card card3 = null;
    private CardPanel cardPanel1 = new CardPanel();
    private CardPanel cardPanel2 = new CardPanel();
    private CardPanel cardPanel3 = new CardPanel();
    private boolean isGameFinished = false;
    private JLabel turnLabel;
    private JPanel cards = null;

    public GameWindow(boolean isServer) {
        if (isServer) {
            this.setTitle("Conquer - Server");
        } else {
            this.setTitle("Conquer - Client");
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 680);
        if (isServer) {
            this.setLocation(20, 0);
        } else {
            this.setLocation(670, 0);
        }

        try {
            BufferedImage originalImage = ImageIO.read(new File("src\\pictures\\Icon.png"));
            Image scaledImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            this.setIconImage(scaledImage);
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        this.setLayout(new BorderLayout());
        JPanel boardLayout = new JPanel(new FlowLayout());
        this.cards = new JPanel(new FlowLayout());
        JPanel empty = new JPanel(new FlowLayout());
        this.add(boardLayout, BorderLayout.CENTER);
        this.add(cards, BorderLayout.SOUTH);

        turnLabel = new JLabel();
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(turnLabel, BorderLayout.NORTH);


        boardPanel = new BoardPanel();
        boardPanel.setPreferredSize(new Dimension(500, 500));
        boardLayout.add(boardPanel, BorderLayout.CENTER);
        boardLayout.add(empty);

        this.card1 = this.deck.drawCard();
        cardPanel1.setCard(card1);
        cardPanel1.setPreferredSize(new Dimension(100, 100));
        cardPanel1.setBackground(Color.GREEN); 
        cards.add(cardPanel1, BorderLayout.WEST);
        
        this.card2 = this.deck.drawCard();
        cardPanel2.setCard(card2);
        cardPanel2.setPreferredSize(new Dimension(100, 100)); 
        cardPanel2.setBackground(Color.GREEN); 
        cards.add(cardPanel2, BorderLayout.CENTER);

        this.card3 = this.deck.drawCard();
        cardPanel3.setCard(card3);
        cardPanel3.setPreferredSize(new Dimension(100, 100));
        cardPanel3.setBackground(Color.GREEN); 
        cards.add(cardPanel3, BorderLayout.EAST);

        this.setVisible(true);
    }

    public void drawCard() {     
        String[] lastPlacedCard = getLastPlacedCardInfo().split(" ");
        int value = Integer.parseInt(lastPlacedCard[2]);
        if (value == card1.getValue()) {
            try{
                this.card1 = this.deck.drawCard();
                this.cardPanel1.setCard(card1);
            }catch (IllegalStateException e){
                this.cards.remove(cardPanel1);
            }
        } else if (value == card2.getValue()) {
            try{
                this.card2 = this.deck.drawCard();
                this.cardPanel2.setCard(card2);
            }catch (IllegalStateException e){
                this.cards.remove(cardPanel2);
            }
        } else if (value == card3.getValue()) {
            try{
                this.card3 = this.deck.drawCard();
                this.cardPanel3.setCard(card3);
            }catch (IllegalStateException e){
                this.cards.remove(cardPanel3);
            }
        }

        this.revalidate();
        this.repaint();
    }

    public void setDropEnabled(boolean enabled) {
        boardPanel.setDropEnabled(enabled);
    }

    public String getLastPlacedCardInfo() {
        return boardPanel.getLastPlacedCardInfo();
    }

    public boolean isDropEnabled() {
        return boardPanel.isDropEnabled();
    }

    public void updateBoard(String update) {
        String[] parts = update.split(" ");

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int value = Integer.parseInt(parts[2]);
        boardPanel.updateBoard(x, y, new Card(value, true));
    }

    public boolean checkWinCondition(){
        return boardPanel.checkWinCondition();
    }

    public boolean checkIfDraw() {
        if (deck.isEmpty() && this.cards.getComponentCount() == 1) {
            return true;
        }
        return false;
    }

    public void restartGame() {
        if(this.cards.getComponentCount() == 0){
            this.cards.add(cardPanel1, BorderLayout.WEST);
            this.cards.add(cardPanel2, BorderLayout.CENTER);
            this.cards.add(cardPanel3, BorderLayout.EAST);
        } else if(this.cards.getComponentCount() == 1 && isCardPanelAbsent(cardPanel1) && isCardPanelAbsent(cardPanel2)){ 
            this.cards.add(cardPanel1, BorderLayout.WEST);
            this.cards.add(cardPanel2, BorderLayout.CENTER);
        } else if(this.cards.getComponentCount() == 1 && isCardPanelAbsent(cardPanel1) && isCardPanelAbsent(cardPanel3)){
            this.cards.add(cardPanel1, BorderLayout.WEST);
            this.cards.add(cardPanel3, BorderLayout.EAST);
        } else if(this.cards.getComponentCount() == 1 && isCardPanelAbsent(cardPanel2) && isCardPanelAbsent(cardPanel3)){
            this.cards.add(cardPanel2, BorderLayout.CENTER);
            this.cards.add(cardPanel3, BorderLayout.EAST);
        } else if(this.cards.getComponentCount() == 2 && isCardPanelAbsent(cardPanel1)){
            this.cards.add(cardPanel1, BorderLayout.WEST);
        } else if(this.cards.getComponentCount() == 2 && isCardPanelAbsent(cardPanel2)){
            this.cards.add(cardPanel2, BorderLayout.CENTER);
        } else if(this.cards.getComponentCount() == 2 && isCardPanelAbsent(cardPanel3)){
            this.cards.add(cardPanel3, BorderLayout.EAST);
        }

        boardPanel.clear();
        deck.reset();
        this.card1 = this.deck.drawCard();
        this.cardPanel1.setCard(card1);
        this.card2 = this.deck.drawCard();
        this.cardPanel2.setCard(card2);
        this.card3 = this.deck.drawCard();
        this.cardPanel3.setCard(card3);
    }

    public boolean isCardPanelAbsent(CardPanel cardPanel) {
        for (Component component : this.cards.getComponents()) {
            if (component == cardPanel) {
                return false;
            }
        }
        return true;
    }
    
    public void displayMessage(String message) {
        Object[] options = {"Restart", "Cancel"};
        int n = JOptionPane.showOptionDialog(this, message, null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    
        if (n == JOptionPane.YES_OPTION) {
            restartGame();
        } else if (n == JOptionPane.NO_OPTION) {
            boardPanel.setDropEnabled(false);
            turnLabel.setText("END OF GAME");
            isGameFinished = true;
        }
    }

    public void displayIntro() {
        String message = "Conquer the board by strategically placing cards from your hand of 18, ranging from power 1-18. \nCards can be played on empty spaces or over lower-powered enemy cards. \nSecure victory with a path of 5 cards in a row horizontally, vertically, or diagonally.";
        JOptionPane.showMessageDialog(this, message);
    }

    public void updateTurnLabel(boolean isYourTurn) {
        if (isYourTurn) {
            turnLabel.setText("Your turn");
        } else {
            turnLabel.setText("Enemy turn");
        }
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }
}
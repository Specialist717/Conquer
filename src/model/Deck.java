package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Deck {
    private List<Card> cards;
    private Random random;

    public Deck() {
        this.cards = new ArrayList<>();
        this.random = new Random(); 
        IntConsumer addCard = i -> this.cards.add(new Card(i, false));
        IntStream.rangeClosed(1, 18).forEach(addCard);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No cards left in the deck");
        }
        return cards.remove(random.nextInt(cards.size()));
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void reset() {
        cards.clear(); 
        IntConsumer addCard = i -> cards.add(new Card(i, false));
        IntStream.rangeClosed(1, 18).forEach(addCard); 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString());
            sb.append(", ");
        }
        return sb.toString();
    }
}

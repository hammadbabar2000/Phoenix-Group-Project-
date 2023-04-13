/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;


import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.WILD) {
                for (Card.Value value : Card.Value.values()) {
                    if (value != Card.Value.WILD && value != Card.Value.WILD_DRAW_FOUR) {
                        cards.add(new Card(color, value));
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    cards.add(new Card(Card.Color.WILD, Card.Value.WILD));
                    cards.add(new Card(Card.Color.WILD, Card.Value.WILD_DRAW_FOUR));
                }
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

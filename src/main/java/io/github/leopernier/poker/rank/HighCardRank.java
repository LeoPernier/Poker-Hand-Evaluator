package io.github.leopernier.poker.rank;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a High Card rank: no other combination found,
 * just compare cards in descending order.
 * Priority = 1 (lowest possible).
 */
public class HighCardRank extends AbstractHandRank {
    private final List<Card> cards;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a HighCardRank by sorting the cards in descending order.
     *
     * @param   Hand object containing exactly 5 cards.
     */
    public HighCardRank(Hand hand) {
        this.cards = hand.getHand().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the priority of HighCard (1).
     *
     * @return  1... Useless javadoc honestly
     */
    @Override
    public int getPriority() {
        return 1;
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two HighCardRank objects by comparing each card from highest to lowest.
     *
     * @param other another HandRank, must be instance of HighCardRank
     * @return negative if this < other, zero if equal, positive if this > other
     * @throws IllegalArgumentException if other is not a HighCardRank
     */
    @Override
    public int compareSameRank(HandRank other) {
        if (!(other instanceof HighCardRank))
            throw new IllegalArgumentException("Error: Cannot compare HighCardRank to " + other.getClass());
        HighCardRank o = (HighCardRank)other;
        for (int i = 0; i < cards.size(); i++) {
            int cmp = Integer.compare(cards.get(i).getRank().getRank(), o.cards.get(i).getRank().getRank());
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    /**
     * Returns a string representation like "HighCard K,Q,10,5,2".
     *
     * @return  Descriptive string of this HighCardRank.
     */
    @Override
    public String toString() {
        return "HighCard " + cards.stream().map(c -> "" + c.getRank().getSymbol()).collect(Collectors.joining(","));
    }
}


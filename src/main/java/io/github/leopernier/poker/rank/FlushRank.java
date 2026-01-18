package io.github.leopernier.poker.rank;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Flush rank: all cards have the same suit.
 * Priority = 5 (highest among handled ranks here).
 * Stores cards in descending order for tie-breaking.
 */
public class FlushRank extends AbstractHandRank {
    private final List<Card> cards;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a FlushRank by sorting cards in descending order.
     *
     * @param Hand object containing exactly 5 cards of the same suit.
     */
    public FlushRank(Hand hand) {
        this.cards = hand.getHand().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the priority of Flush (5).
     *
     * @return integer 5
     */
    @Override
    public int getPriority() {
        return 5;
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two FlushRank objects by comparing each card from highest to lowest.
     *
     * @param   Other another HandRank, must be instance of FlushRank.
     * @return  negative if this < other, zero if equal, positive if this > other.
     * @throws  IllegalArgumentException if other is not FlushRank.
     */
    @Override
    public int compareSameRank(HandRank other) {
        if (!(other instanceof FlushRank))
            throw new IllegalArgumentException("Error: Cannot compare FlushRank to " + other.getClass());
        FlushRank o = (FlushRank)other;
        for (int i = 0; i < cards.size(); i++) {
            int cmp = Integer.compare(cards.get(i).getRank().getRank(), o.cards.get(i).getRank().getRank());
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    /**
     * Returns a string representation like "Flush A,K,9,7,3".
     *
     * @return descriptive string of this FlushRank
     */
    @Override
    public String toString() {
        return "Flush " + cards.stream().map(c -> "" + c.getRank().getSymbol()).collect(Collectors.joining(","));
    }
}


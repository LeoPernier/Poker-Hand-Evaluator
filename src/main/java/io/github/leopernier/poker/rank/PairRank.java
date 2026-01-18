package io.github.leopernier.poker.rank;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a One Pair rank: exactly two cards of same value.
 * Priority = 2.
 * Stores pair value and kickers (remaining cards) in descending order.
 */
public class PairRank extends AbstractHandRank {
    private final int pairValue;
    private final List<Integer> kickersDescription;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a PairRank by finding the pair value and sorting remaining kickers.
     *
     * @param   Hand Hand object that must contain exactly one pair of cards.
     * @throws  IllegalArgumentException if hand is not exactly a one-pair hand
     * @throws  IllegalStateException if number of kickers is not exactly 3.
     */
    public PairRank(Hand hand) {
        Map<Integer, Long> freq = hand.getHand().stream().collect(
            Collectors.groupingBy(c -> c.getRank().getRank(), Collectors.counting()));
        int foundPair = -1;
        for (Map.Entry<Integer, Long> e : freq.entrySet())
            if (e.getValue() == 2) {
                if (foundPair != -1)
                    throw new IllegalArgumentException("Error: More than one pair found in what should be a 'One Pair' hand");
                foundPair = e.getKey();
            }
        if (foundPair < 0)
            throw new IllegalArgumentException("Error: Hand is not a Pair");
        this.pairValue = foundPair;
        this.kickersDescription = hand.getHand().stream().map(c -> c.getRank().getRank()).filter(r -> r != pairValue).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        if (kickersDescription.size() != 3)
            throw new IllegalStateException("Error: A Pair must have exactly 3 kickers");
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the integer value of the paired rank (e.g., 11 for Jacks).
     *
     * @return  Integer pair value!
     */
    public int getPairValue() {
        return pairValue;
    }

    /**
     * Returns the priority of Pair (2).
     *
     * @return integer 2
     */
    @Override
    public int getPriority() {
        return 2;
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two PairRank objects by pair value first, then by kickers one by one.
     *
     * @param other another HandRank, must be instance of PairRank
     * @return negative if this < other, zero if equal, positive if this > other
     * @throws IllegalArgumentException if other is not a PairRank
     */
    @Override
    public int compareSameRank(HandRank other) {
        if (!(other instanceof PairRank))
            throw new IllegalArgumentException("Error: Cannot compare PairRank to " + other.getClass());
        PairRank o = (PairRank)other;
        int cmp = Integer.compare(this.pairValue, o.pairValue);
        if (cmp != 0) return cmp;
        for (int i = 0; i < kickersDescription.size(); i++) {
            cmp = Integer.compare(this.kickersDescription.get(i), o.kickersDescription.get(i));
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    /**
     * Returns a string representation like "Pair(10) kickers[13,9,2]".
     *
     * @return  Descriptive string of this PairRank.
     */
    @Override
    public String toString() {
        return "Pair(" + pairValue + ") kickers" + kickersDescription;
    }
}


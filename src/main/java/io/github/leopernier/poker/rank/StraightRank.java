package io.github.leopernier.poker.rank;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Straight rank: five distinct cards in sequence.
 * Priority = 4.
 * Stores the highest card value of the straight.
 */
public class StraightRank extends AbstractHandRank {
    private final int highCardValue;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a StraightRank by verifying that card ranks form a consecutive sequence.
     *
     * @param   Hand Hand object that must contain exactly 5 distinct ranks in a sequence.
     * @throws IllegalArgumentException if hand is not a valid straight (missing cards or duplicates).
     */
    public StraightRank(Hand hand) {
        List<Integer> ranks = hand.getHand().stream().map(c -> c.getRank().getRank()).distinct().sorted().collect(Collectors.toList());
        if (ranks.size() != 5)
            throw new IllegalArgumentException("Error: A straight must have 5 distinct ranks");
        for (int i = 1; i < ranks.size(); i++)
            if (ranks.get(i) != ranks.get(i - 1) + 1)
                throw new IllegalArgumentException("Error: Not a Straight (Missing '" + (ranks.get(i - 1) + 1) + "' between '" + ranks.get(i - 1) + "' and '" + ranks.get(i) + "')");
        this.highCardValue = ranks.get(ranks.size() - 1);
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the integer value of the high card in the straight.
     *
     * @return integer high card value
     */
    public int getHighCardValue() {
        return highCardValue;
    }

    /**
     * Returns the priority of straight (4)
     *
     * @return 4
     */
    @Override
    public int getPriority() {
        return 4;
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two StraightRank objects by their highest card value.
     *
     * @param   Other another HandRank, must be instance of StraightRank.
     * @return  Negative if this < other, zero if equal, positive if this > other,
     * @throws  IllegalArgumentException if other is not StraightRank.
     */
    @Override
    public int compareSameRank(HandRank other) {
        if (!(other instanceof StraightRank))
            throw new IllegalArgumentException("Error: Cannot compare StraightRank to " + other.getClass());

        StraightRank o = (StraightRank)other;
        return Integer.compare(this.highCardValue, o.highCardValue);
    }

    /**
     * Returns a string representation like "Straight(9)".
     *
     * @return descriptive string of this StraightRank
     */
    @Override
    public String toString() {
        return "Straight(" + highCardValue + ")";
    }
}


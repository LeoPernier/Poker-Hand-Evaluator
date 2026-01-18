package io.github.leopernier.poker.rank;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a Three of a Kind rank: exactly three cards of same value.
 * Priority = 3.
 * Stores trio value and two kickers in descending order.
 */
public class ThreeOfKindRank extends AbstractHandRank {
    private final int trioValue;
    private final List<Integer> kickersDescription;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a ThreeOfKindRank by finding the three-of-a-kind value and sorting remaining kickers.
     *
     * @param   Hand Hand object that must contain exactly three cards of the same rank.
     * @throws  IllegalArgumentException if hand is not exactly a Three of a Kind.
     * @throws  IllegalStateException if number of kickers is not exactly 2.
     */
    public ThreeOfKindRank(Hand hand) {
        Map<Integer, Long> freq = hand.getHand().stream().collect(
            Collectors.groupingBy(c -> c.getRank().getRank(), Collectors.counting()));
        int foundTrio = -1;
        for (Map.Entry<Integer, Long> e : freq.entrySet())
            if (e.getValue() == 3) {
                foundTrio = e.getKey();
                break;
            }
        if (foundTrio < 0)
            throw new IllegalArgumentException("Error: Hand is not a Three of a kind");
        this.trioValue = foundTrio;
        this.kickersDescription = hand.getHand().stream().map(c -> c.getRank().getRank()).filter(r -> r != trioValue).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        if (kickersDescription.size() != 2)
            throw new IllegalStateException("Error: A Three of a kind must have exactly 2 kickers");
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the integer value of the three-of-a-kind rank (e.g., 7 for three sevens
     *
     * @return trioValue.
     */
    public int getTrioValue() {
        return trioValue;
    }

    /**
     * Returns the priority of ThreeOfKind (3).
     *
     * @return 3.
     */
    @Override
    public int getPriority() {
        return 3;
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two ThreeOfKindRank objects by trio value first, then by each kicker in order.
     *
     * @param   other another HandRank, must be instance of ThreeOfKindRank.
     * @return  negative if this < other, zero if equal, positive if this > other..
     * @throws  IllegalArgumentException if other is not ThreeOfKindRank.
     */
    @Override
    public int compareSameRank(HandRank other) {
        if (!(other instanceof ThreeOfKindRank))
            throw new IllegalArgumentException("Error: Cannot compare ThreeOfKindRank to " + other.getClass());
        ThreeOfKindRank o = (ThreeOfKindRank)other;
        int cmp = Integer.compare(this.trioValue, o.trioValue);
        if (cmp != 0) return cmp;
        for (int i = 0; i < kickersDescription.size(); i++) {
            cmp = Integer.compare(this.kickersDescription.get(i), o.kickersDescription.get(i));
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    /**
     * Returns a string representation like "ThreeOfKind(4) kickers[10,2]"
     *
     * @return  descriptive string of this ThreeOfKindRank.
     */
    @Override
    public String toString() {
        return "ThreeOfKind(" + trioValue + ") kickers" + kickersDescription;
    }
}


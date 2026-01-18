package io.github.leopernier.poker.rank;

/**
 * HandRank interface represents the ranking of a poker hand.
 * Each implementation must define a priority and how to compare two ranks of the same type.
 */
public interface HandRank extends Comparable<HandRank> {
    /**
     * Returns the priority of this rank.
     * Higher value means stronger combination (e.g., Flush > Pair > HighCard).
     *
     * @return  integer priority of the rank
     */
    int getPriority();

    /**
     * Compares this HandRank to another HandRank of the same type (same combination).
     * Must return negative if this < other, zero if equal, positive if this > other.
     *
     * @param   other another HandRank of the same class
     * @return  negative, zero, or positive depending on comparison
     * @throws  IllegalArgumentException if other is not of the same HandRank subtype
     */
    int compareSameRank(HandRank other);
}


package io.github.leopernier.poker.rank;

/**
 * AbstractHandRank provides a skeletal implementation of HandRank,
 * handling the compareTo logic based on getPriority and compareSameRank.
 */
public abstract class AbstractHandRank implements HandRank {
    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Compares two HandRank objects by priority first, then by compareSameRank if priorities are equal.
     *
     * @param   other another HandRank to compare to
     * @return  Negative, zero, or positive depending on comparison
     */
    @Override
    public int compareTo(HandRank other) {
        int diff = Integer.compare(this.getPriority(), other.getPriority());
        if (diff != 0) return diff;
        return this.compareSameRank(other);
    }

    /**
     * Determines equality based on comparison result being zero.
     *
     * @param   o another object
     * @return  true if o is a HandRank and compareTo returns zero
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HandRank)) return false;
        return this.compareTo((HandRank)o) == 0;
    }

    /**
     * Hash code is simply the priority (not ideal for collisions,
     * but acceptable since compareSameRank further distinguishes equal priorities).
     *
     * @return  Integer hash code
     */
    @Override
    public int hashCode() {
        return getPriority();
    }
}


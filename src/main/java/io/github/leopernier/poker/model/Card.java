package io.github.leopernier.poker.model;

import io.github.leopernier.poker.enums.Rank;
import io.github.leopernier.poker.enums.Suit;

import java.util.Objects;

/**
 * Represents a playing card with a Rank (value) and a Suit (color).
 * Implements Comparable so cards can be sorted by rank value.
 */
public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a Card given a Rank and a Suit.
     *
     * @param   Rank non-null Rank enum.
     * @param   Suit non-null Suit enum.
     * @throws  NullPointerException if rank or suit is null.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank);
        this.suit = Objects.requireNonNull(suit);
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the Rank of this card.
     *
     * @return Rank enum
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the Suit of this card.
     *
     * @return Suit enum
     */
    public Suit getSuit() {
        return suit;
    }

    //----------------
    // Public methods
    //----------------

    /**
     * Parses a string like "TH" or "7C" into a Card instance.
     * First char is rank symbol, second char is suit symbol.
     *
     * @param   s two-character string representation of a card,
     * @return  New Card object.
     * @throws IllegalArgumentException if format invalid or symbol not recognized
     */
    public static Card fromString(String s) {
        if (s == null || s.length() != 2)
            throw new IllegalArgumentException("Error: Invalid card format: '" + s + "'!");
        char rankChar = s.charAt(0);
        char suitChar = s.charAt(1);
        Rank rank = Rank.fromChar(rankChar);
        Suit suit = Suit.fromChar(suitChar);
        return new Card(rank, suit);
    }

    //--------------------------
    // Public overriden methods
    //--------------------------

    /**
     * Returns a string representation, e.g., "TH" or "7C".
     *
     * @return two-character string
     */
    @Override
    public String toString() {
        return "" + rank.getSymbol() + suit.getSymbol();
    }

    /**
     * Checks equality by comparing rank and suit.
     *
     * @param o another object
     * @return true if o is a Card with same rank and suit.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    /**
     * Hash code based on rank and suit.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    /**
     * Compares two cards by their rank's integer value.
     *
     * @param o another Card/
     * @return Negative if this < o, zero if equal, positive if this > o.
     */
    @Override
    public int compareTo(Card o) {
        return Integer.compare(this.rank.getRank(), o.rank.getRank());
    }
}


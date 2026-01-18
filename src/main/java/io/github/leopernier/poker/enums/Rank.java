package io.github.leopernier.poker.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * Enum representing card ranks from Ace (1) to King (13).
 * Provides a lookup from character symbol to enum constant.
 */
public enum Rank {
    ACE('1', 1),
    TWO('2', 2),
    THREE('3', 3),
    FOUR('4', 4),
    FIVE('5', 5),
    SIX('6', 6),
    SEVEN('7', 7),
    EIGHT('8', 8),
    NINE('9', 9),
    TEN('T', 10),
    JACK('J', 11),
    QUEEN('Q', 12),
    KING('K', 13);

    private final char symbol;
    private final int rank;

    private static final Map<Character, Rank> lookup = new HashMap<>();

    static {
        for (Rank r : Rank.values())
            lookup.put(r.symbol, r);
    }

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a Rank enum constant with a symbol and integer value.
     *
     * @param symbol character representing the rank,
     * @param rank integer value (1..13).
     */
    Rank(char symbol, int rank) {
        this.symbol = symbol;
        this.rank   = rank;
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the character symbol of this rank.
     *
     * @return char symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns the integer value of this rank (1..13).
     *
     * @return integer rank
     */
    public int getRank() {
        return rank;
    }

    //----------------
    // Public methods
    //----------------

    /**
     * Looks up a Rank by its character symbol.
     *
     * @param   c character like 'A', '2', 'T', 'J', etc.
     * @return  Corresponding Rank enum constant.
     * @throws  IllegalArgumentException if symbol not found.
     */
    public static Rank fromChar(char c) {
        Rank r = lookup.get(c);
        if (r == null)
            throw new IllegalArgumentException("Error: Invalid rank character '" + c + "'!");
        return r;
    }
}


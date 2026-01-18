package io.github.leopernier.poker.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing card suits: Clubs, Diamonds, Hearts, Spades.
 * Provides a lookup from character symbol to enum constant.
 */
public enum Suit {
    CLUBS('C'),
    DIAMONDS('D'),
    HEARTS('H'),
    SPADES('S');

    private final char symbol;
    private static final Map<Character, Suit> lookup = new HashMap<>();

    static {
        for (Suit s : Suit.values())
            lookup.put(s.symbol, s);
    }

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a Suit enum constant with a symbol.
     *
     * @param Symbol character representing the suit.
     */
    Suit(char symbol) {
        this.symbol = symbol;
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the character symbol of this suit.
     *
     * @return char symbol
     */
    public char getSymbol() {
        return symbol;
    }

    //----------------
    // Public methods
    //----------------

    /**
     * Looks up a Suit by its character symbol.
     *
     * @param   c character like 'C', 'D', 'H', or 'S'.
     * @return  Corresponding Suit enum constant.
     * @throws  IllegalArgumentException if symbol not found.
     */
    public static Suit fromChar(char c) {
        Suit s = lookup.get(c);
        if (s == null)
            throw new IllegalArgumentException("Error: Invalid suit character '" + c + "'!");
        return s;
    }
}


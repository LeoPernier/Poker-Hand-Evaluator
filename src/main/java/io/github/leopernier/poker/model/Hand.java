package io.github.leopernier.poker.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a poker hand of exactly 5 distinct cards.
 * The internal list is sorted and immutable.
 */
public class Hand {
    private final List<Card> cards;

    //-------------
    // Constructor
    //-------------

    /**
     * Constructs a Hand from a list of Card objects.
     *
     * @param   Cards list of exactly 5 distinct Card objects.
     * @throws  IllegalArgumentException if cards is null, size != 5, or duplicates found.
     */
    public Hand(List<Card> cards) {
        if (cards == null)
            throw new IllegalArgumentException("Error: Hand cannot be null!");
        else if (cards.size() != 5)
            throw new IllegalArgumentException("Error: Hand must contain exactly 5 cards but there are " + cards.size() + " cards!");
        else if (cards.stream().distinct().count() != 5)
            throw new IllegalArgumentException("Error: Hand contains duplicate cards!");
        this.cards = Collections.unmodifiableList(cards.stream().sorted().collect(Collectors.toList()));
    }

    //---------
    // Getters
    //---------

    /**
     * Returns an unmodifiable list of cards in this hand, sorted by rank ascending.
     *
     * @return List of Card objects
     */
    public List<Card> getHand() {
        return cards;
    }
}


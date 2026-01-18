package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.FlushRank;
import io.github.leopernier.poker.rank.HandRank;

import java.util.List;
import java.util.Optional;

/**
 * FlushEvaluator checks if all cards in the hand share the same suit.
 * If so, returns a FlushRank.
 * Otherwise returns Optional.empty().
 */
public class FlushEvaluator implements HandEvaluator {
    /**
     * Evaluates whether the given hand is a flush
     *
     * @param   Hand Hand object containing 5 cards.
     * @return Optional of FlushRank if flush, else Optional.empty().
     */
    @Override
    public Optional<HandRank> evaluate(Hand hand) {
        List<Card> cards = hand.getHand();
        if (cards.stream().allMatch(c -> c.getSuit() == cards.get(0).getSuit()))
            return Optional.of(new FlushRank(hand));
        return Optional.empty();
    }
}


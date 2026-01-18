package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.HighCardRank;
import io.github.leopernier.poker.rank.HandRank;

import java.util.Optional;

/**
 * HighCardEvaluator always matches (last fallback).
 * Returns a HighCardRank for any hand (even if better combination exists,
 * but in practice evaluators are tried in priority order).
 */
public class HighCardEvaluator implements HandEvaluator {
    /**
     * Evaluates the given hand always as a high card.
     *
     * @param   Hand Hand object containing 5 cards.
     * @return  Optional of HighCardRank.
     */
    @Override
    public Optional<HandRank> evaluate(Hand hand) {
        return Optional.of(new HighCardRank(hand));
    }
}


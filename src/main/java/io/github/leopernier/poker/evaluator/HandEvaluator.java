package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.HandRank;

import java.util.Optional;

/**
 * HandEvaluator interface defines a method to evaluate a Hand and return a HandRank if the hand matches that evaluator's criteria.
 */
public interface HandEvaluator {
    /**
     * Evaluates the given hand. If the hand matches this evaluator's combination, returns an Optional containing the HandRank.
     * Otherwise returns Optional.empty().
     *
     * @param   Hand Hand object to evaluate.
     * @return  Optional containing HandRank if combination matches, or empty if not.
     */
    Optional<HandRank> evaluate(Hand hand);
}


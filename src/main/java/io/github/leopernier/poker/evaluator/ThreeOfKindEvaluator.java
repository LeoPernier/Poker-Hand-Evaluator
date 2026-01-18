package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.ThreeOfKindRank;
import io.github.leopernier.poker.rank.HandRank;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ThreeOfKindEvaluator checks if the hand contains exactly three cards of the same rank.
 * If so, returns a ThreeOfKindRank. Otherwise returns Optional.empty().
 */
public class ThreeOfKindEvaluator implements HandEvaluator {
    /**
     * Evaluates whether the given hand is a three of a kind.
     *
     * @param   Hand Hand object containing 5 cards.
     * @return  Optional of ThreeOfKindRank if condition met, else Optional.empty().
     */
    @Override
    public Optional<HandRank> evaluate(Hand hand) {
        Map<Integer, Long> freq = hand.getHand().stream().collect(Collectors.groupingBy(c -> c.getRank().getRank(), Collectors.counting()));
        long trioCount = freq.values().stream().filter(count -> count == 3).count();
        if (trioCount == 1)
            return Optional.of(new ThreeOfKindRank(hand));
        return Optional.empty();
    }
}


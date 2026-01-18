package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.PairRank;
import io.github.leopernier.poker.rank.HandRank;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PairEvaluator checks if the hand contains exactly one pair of cards.
 * If so, returns a PairRank. Otherwise returns Optional.empty().
 */
public class PairEvaluator implements HandEvaluator {
    /**
     * Evaluates whether the given hand is a pair.
     *
     * @param   Hand Hand object containing 5 cards.
     * @return  Optional of PairRank if one pair found, else Optional.empty().
     */
    @Override
    public Optional<HandRank> evaluate(Hand hand) {
        Map<Integer, Long> freq = hand.getHand().stream().collect(Collectors.groupingBy(c -> c.getRank().getRank(), Collectors.counting()));
        long pairCount = freq.values().stream().filter(count -> count == 2).count();
        if (pairCount == 1)
            return Optional.of(new PairRank(hand));
        return Optional.empty();
    }
}


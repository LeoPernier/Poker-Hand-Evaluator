package io.github.leopernier.poker.evaluator;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.StraightRank;
import io.github.leopernier.poker.rank.HandRank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * StraightEvaluator checks if the hand forms a sequence of 5 distinct ranks.
 * If so, returns a StraightRank. Otherwise returns Optional.empty().
 */
public class StraightEvaluator implements HandEvaluator {
    /**
     * Evaluates whether the given hand is a straight.
     *
     * @param   hand Hand object containing 5 cards.
     * @return Optional of StraightRank if straight, else Optional.empty().
     */
    @Override
    public Optional<HandRank> evaluate(Hand hand) {
        List<Integer> ranks = hand.getHand().stream().map(c -> c.getRank().getRank()).distinct().sorted().collect(Collectors.toList());
        if (ranks.size() != 5)
            return Optional.empty();
        boolean isSequence = true;
        for (int i = 1; i < ranks.size(); i++)
            if (ranks.get(i) != ranks.get(i - 1) + 1)
                return Optional.empty();
        return Optional.of(new StraightRank(hand));
    }
}


package io.github.leopernier.poker;

import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.HandRank;
import io.github.leopernier.poker.evaluator.HandEvaluator;
import io.github.leopernier.poker.evaluator.FlushEvaluator;
import io.github.leopernier.poker.evaluator.StraightEvaluator;
import io.github.leopernier.poker.evaluator.ThreeOfKindEvaluator;
import io.github.leopernier.poker.evaluator.PairEvaluator;
import io.github.leopernier.poker.evaluator.HighCardEvaluator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Arbiter class is responsible for ranking poker hands and determining the winner(s).
 * It uses a list of HandEvaluator implementations to detect hand combinations.
 */
public class Arbiter {
    private final List<HandEvaluator> evaluators;
    private Map<Hand, HandRank> ranking = Map.of();

    //-------------
    // Constructor
    //-------------

    /**
     * Creates a new Arbiter with a fixed list of evaluators in order of priority:
     * Flush, Straight, ThreeOfKind, Pair, HighCard.
     */
    public Arbiter() {
        this.evaluators = List.of(
            new FlushEvaluator(),
            new StraightEvaluator(),
            new ThreeOfKindEvaluator(),
            new PairEvaluator(),
            new HighCardEvaluator()
        );
    }

    //---------
    // Getters
    //---------

    /**
     * Returns the current ranking map of each Hand to its HandRank.
     * This map is populated after determineWinners is called.
     *
     * @return Map from Hand to HandRank
     */
    public Map<Hand, HandRank> getRanking() {
        return ranking;
    }

    //----------------
    // Public methods
    //----------------

    /**
     * Determines the winning hand(s) among a list of Hand objects.
     * It ranks all hands, finds the best rank, and returns all hands matching that rank.
     *
     * @param hands non-empty list of Hand objects representing each player
     * @return list of Hand objects that share the highest rank (could be multiple in case of a tie)
     * @throws IllegalArgumentException if hands is null or empty
     */
    public List<Hand> determineWinners(List<Hand> hands) {
        if (hands == null || hands.isEmpty())
            throw new IllegalArgumentException("Error: Hand list cannot be empty");
        this.ranking = rankHands(hands);
        HandRank bestRank = ranking.values().stream().max(HandRank::compareTo).orElseThrow();
        return ranking.entrySet().stream().filter(entry -> entry.getValue().compareTo(bestRank) == 0).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    //-----------------
    // Private methods
    //-----------------

    /**
     * Ranks each hand by finding the first evaluator that matches and assigning its HandRank.
     * Evaluators are tried in the order defined in the constructor.
     *
     * @param hands list of Hand objects to rank
     * @return Map from each Hand to its detected HandRank
     */
    private Map<Hand, HandRank> rankHands(List<Hand> hands) {
        Map<Hand, HandRank> result = new HashMap<>();
        for (Hand hand : hands) {
            for (HandEvaluator evaluator : evaluators) {            
                Optional<HandRank> optionalRank = evaluator.evaluate(hand);
                if (optionalRank.isPresent()) {
                    result.put(hand, optionalRank.get());
                    break;
                }
            }
        }
        return result;
    }
}


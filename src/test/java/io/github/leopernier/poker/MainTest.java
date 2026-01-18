package io.github.leopernier.poker;

import org.junit.Test;
import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test suite for the Poker Arbiter. 
 * 
 * This class contains various tests that verify the correct behavior of the Arbiter class
 * when comparing different hands such as flush, pair, straight, etc. Each of these test methods simulates
 * a scenario where 2 or more players (n players) hold specific hands, and it asserts that the Arbiter correctly
 * identifies the winner(s) (1 or many) or if it's a tie.
 * 
 * Note: We keep the code lighthearted—after all, losing at poker stings enough without boring comments.
 */
public class MainTest {

    /**
     * Parses a string representation of five cards (e.g., "2D 5D QD KD 7D") into a Hand object.
     *
     * @param s string with exactly five 2-character card codes separated by spaces
     * @return Hand object constructed from the given cards
     * @throws IllegalArgumentException if any card string is invalid
     */
    private Hand parseHand(String s) {
        String[] tokens = s.trim().toUpperCase().split("\\s+");
        List<Card> cards = List.of(
            Card.fromString(tokens[0]),
            Card.fromString(tokens[1]),
            Card.fromString(tokens[2]),
            Card.fromString(tokens[3]),
            Card.fromString(tokens[4])
        );
        return new Hand(cards);
    }

    /**
     * Test that a flush beats a high card.
     * Player 1: flush (all diamonds) vs. Player 2: just a high card.
     * Expected: only Player 1 wins.
     */
    @Test
    public void p1FlushVsP2HighCard() {
        Hand h1 = parseHand("2D 5D QD KD 7D"); // flush (with diamonds)
        Hand h2 = parseHand("1S 4C KH TD 3S"); // High card
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (flush) should win against P2 (high card)", winners.contains(h1));
    }

    /**
     * Test that a flush beats a pair.
     * Player 1: flush vs. Player 2: pair of Aces.
     * Expected: only Player 1 wins.
     */
    @Test
    public void p1FlushVsP2Pair() {
        Hand h1 = parseHand("2D 5D QD KD 7D"); // Flush (diamonds)
        Hand h2 = parseHand("1S 4C 1H TD 3S"); // Pair of Aces
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (flush) should win against P2 (pair)", winners.contains(h1));
    }

    /**
     * Test that two flushes with identical ranks result in a tie.
     * Player 1: flush in diamonds, Player 2: flush in clubs, same card values.
     * Expected: both hands tie.
     */
    @Test
    public void tieFlushEqualRanks() {
        Hand h1 = parseHand("2D 5D QD KD 7D"); // Flush (diamonds)
        Hand h2 = parseHand("2C 5C QC KC 7C"); // flush (clubs), same vals
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("Both flushes with the same ranks should tie", 2, winners.size());
        assertTrue("Both hands should be in the winners list", winners.contains(h1) && winners.contains(h2));
    }

    /**
     * Test that a flush beats a high card when Player 2 holds the flush.
     * Player 1: high card vs. Player 2: flush
     * Expected: only Player 2 wins.
     */
    @Test
    public void p2FlushVsP1HighCard() {
        Hand h1 = parseHand("1S 4C KH TD 3S"); // high card
        Hand h2 = parseHand("2D 5D QD KD 7D"); // flush (diamonds)
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (flush) should win against P1 (high card)", winners.contains(h2));
    }

    /**
     * Test that two equal pairs with identical kickers result in a tie.
     * Both hands: pair of Aces with kickers {7, 5, 4}.
     * Expected: tie between both players.
     */
    @Test
    public void tiePairEqualPairValueAndKickers() {
        Hand h1 = parseHand("1S 4C 1H 7D 5S"); // Pair of Aces, kickers {7, 5, 4}
        Hand h2 = parseHand("1C 5H 1D 7H 4D"); // Pair of Aces, same kickers
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("Same pair + same kickers: TIE", 2, winners.size());
        assertTrue("Both hands should be in the winners list", winners.contains(h1) && winners.contains(h2));
    }

    /**
     * Test that a higher pair beats a lower pair.
     * Player 1: pair of 5s vs. Player 2: pair of 8s.
     * Expected: only Player 2 wins.
     */
    @Test
    public void p2PairHigherThanP1Pair() {
        Hand h1 = parseHand("5C 5D 2H 9S JC"); // Pair of 5s
        Hand h2 = parseHand("8H 8S 3C 4D TD"); // pair of 8s
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (pair of 8s) should beat P1 (pair of 5s)", winners.contains(h2));
    }

    /**
     * Test that two high-card hands with exactly the same values result in a tie.
     * Both hands: {K, Q, 10, 7, 2} in different suits.
     * Expected: tie between both players.
     */
    @Test
    public void tieHighCardExactSameValues() {
        Hand h1 = parseHand("KD QC TS 7H 2D"); // High card {K, Q, 10, 7, 2}
        Hand h2 = parseHand("KH QH TC 7D 2S"); // High card {K, Q, 10, 7, 2}
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("Same card values: tie", 2, winners.size());
        assertTrue("Both hands should be in the winners list", winners.contains(h1) && winners.contains(h2));
    }

    /**
     * Test a scenario with three players holding different categories.
     * Player 1: flush, Player 2: three of a kind (4s), Player 3: pair of Jacks
     * Expected: only Player 1 wins (flush > three of a kind > pair).
     */
    @Test
    public void multiPlayerScenario() {
        Hand p1 = parseHand("2D 5D QD KD 7D"); // Flush, highest card King
        Hand p2 = parseHand("4C 4H 4S 9D 2C"); // Three of a kind (4s)
        Hand p3 = parseHand("JD JS 3C 8H 2H"); // Pair of Jacks
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(p1, p2, p3));
        assertEquals("In this case, P1 (flush) should win", 1, winners.size());
        assertTrue("P1 should be in the winners list", winners.contains(p1));
    }

    /**
     * Test that a straight beats a high card.
     * Player 1: straight (1-2-3-4-5), Player 2: high card King.
     * Expected: only Player 1 wins.
     */
    @Test
    public void p1StraightVsP2HighCard() {
        Hand h1 = parseHand("1C 2D 3H 4S 5C"); // Straight 1-2-3-4-5
        Hand h2 = parseHand("7D 9H QH KH 2S"); // High card King
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (straight) should win against P2 (high card)", winners.contains(h1));
    }

    /**
     * Test that a straight beats a pair.
     * Player 1: straight (1-2-3-4-5), Player 2: pair of 9s.
     * Expected: only Player 1 wins.
     */
    @Test
    public void p1StraightVsP2Pair() {
        Hand h1 = parseHand("1C 2D 3H 4S 5C"); // Straight 1-2-3-4-5
        Hand h2 = parseHand("9D 9H 3C 7S 2C"); // Pair of 9s
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (straight) should beat P2 (pair)", winners.contains(h1));
    }

    /**
     * Test that a flush beats a straight.
     * Player 1: straight (1-2-3-4-5), Player 2: flush.
     * Expected: only Player 2 wins.
     */
    @Test
    public void p1StraightVsP2Flush() {
        Hand h1 = parseHand("1C 2D 3H 4S 5C"); // Straight 1-2-3-4-5
        Hand h2 = parseHand("2D 5D QD KD 7D"); // Flush (of diamonds)
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (flush) should win against P1 (straight)", winners.contains(h2));
    }

    /**
     * Test that between two straights, the one with the higher top card wins.
     * Player 1: straight up to 5, Player 2: straight up to 6.
     * Expected: only Player 2 wins.
     */
    @Test
    public void p1StraightVsP2Straight() {
        Hand h1 = parseHand("1C 2D 3H 4S 5C"); // Ttraight up to 5
        Hand h2 = parseHand("2C 3D 4H 5S 6C"); // Straight up to 6
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (straight to 6) should beat P1 (straight to 5)", winners.contains(h2));
    }

    /**
     * Test that two identical straights result in a tie.
     * Both hands: straight 1-2-3-4-5.
     * Expected: tie between both players.
     */
    @Test
    public void tieStraightExact() {
        Hand h1 = parseHand("1C 2D 3H 4S 5C"); // Straight 1-2-3-4-5
        Hand h2 = parseHand("1D 2H 3C 4D 5H"); // Straight 1-2-3-4-5
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("Same straight: TIE", 2, winners.size());
        assertTrue("Both hands should be in the winners list", winners.contains(h1) && winners.contains(h2));
    }

    /**
     * Test that a three of a kind beats a high card.
     * Player 1: three of a kind (4s), Player 2: high card.
     * Expected: only Player 1 wins
     */
    @Test
    public void p1ThreeOfKindVsP2HighCard() {
        Hand h1 = parseHand("4C 4D 4H 9S 2C"); // three of a kind (4s)
        Hand h2 = parseHand("KD QC TS 7H 2D"); // High card
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (three of a kind) should beat P2 (high card)", winners.contains(h1));
    }

    /**
     * Test that a three of a kind beats a pair.
     * Player 1: three of a kind (4s), Player 2: pair of Jacks.
     * Expected: only Player 1 wins.
     */
    @Test
    public void p1ThreeOfKindVsP2Pair() {
        Hand h1 = parseHand("4C 4D 4H 9S 2C"); // Three of a kind (4s)
        Hand h2 = parseHand("JH JS 3C 8H 2H"); // Pair of Jacks
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P1 (three of a kind) should beat P2 (pair)", winners.contains(h1));
    }

    /**
     * Test that a flush beats a three of a kind.
     * Player 1: three of a kind (4s), Player 2: flush.
     * Expected: only Player 2 wins
     */
    @Test
    public void p1ThreeOfKindVsP2Flush() {
        Hand h1 = parseHand("4C 4D 4H 9S 2C"); // Three of a kind (4s)
        Hand h2 = parseHand("2D 5D QD KD 7D"); // Flush (with diamonds)
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (flush) should beat P1 (three of a kind)", winners.contains(h2));
    }

    /**
     * Test that between two three of a kinds, the higher trip value wins.
     * Player 1: three of a kind (4s), Player 2: three of a kind (5s)
     * expected: only Player 2 wins.
     */
    @Test
    public void p1ThreeOfKindVsP2ThreeOfKind() {
        Hand h1 = parseHand("4C 4D 4H 6S 7C"); // Three of a kind (4s)
        Hand h2 = parseHand("5C 5D 5H 2S 3C"); // Three of a kind (5s)
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("There must be exactly one winner", 1, winners.size());
        assertTrue("P2 (three of a kind of 5s) should beat P1 (three of a kind of 4s)", winners.contains(h2));
    }

    /**
     * Test that two identical three of a kinds with same kickers result in a tie.
     * Both hands: three of a kind (4s) with kickers {7, 6}.
     * Expected: tie between both players.
     */
    @Test
    public void tieThreeOfKindExact() {
        Hand h1 = parseHand("4C 4D 4H 7S 6C"); // Three of a kind (4s), kickers {7, 6}
        Hand h2 = parseHand("4S 4H 4D 6S 7D"); // Three of a kind (4s), same kickers
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(h1, h2));
        assertEquals("Same three of a kind + same kickers: TIE", 2, winners.size());
        assertTrue("Both hands should be in the winners list", winners.contains(h1) && winners.contains(h2));
    }

    /**
     * Test a multi-way tie scenario with different categories and identical pairs.
     * Player 1: pair of 6s with kickers {K,9,8}, Player 2: same pair and kickers, Player 3: high card.
     * Expected: Player 1 and Player 2 tie; Player 3 is eliminated.
     */
    @Test
    public void multiWayTieDifferentCategories() {
        Hand p1 = parseHand("6C 6D KH 9S 8C"); // Pair of 6s, kickers {K, 9, 8}
        Hand p2 = parseHand("6H 6S KD 9H 8D"); // Pair of 6s, kickers {K, 9, 8}
        Hand p3 = parseHand("KD QC TS 7H 2D"); // high card
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(p1, p2, p3));
        assertEquals("P1 and P2 should tie, P3 is out", 2, winners.size());
        assertTrue("P1 and P2 should be in the winners list", winners.contains(p1) && winners.contains(p2));
        assertFalse("P3 should not be in the winners list", winners.contains(p3));
    }

    /**
     * Test a full game scenario with five players holding each category.
     * P1: high card, P2: pair of 9s, P3: straight, P4: three of a kind, P5: flush.
     * Expected: only Player 5 wins (flush beats all).
     */
    @Test
    public void fullGameScenarioFivePlayers() {
        Hand p1 = parseHand("KD QC TS 7H 2D");       // High card
        Hand p2 = parseHand("9H 9D 4C 7S 2C");       // Pair of 9s
        Hand p3 = parseHand("1C 2D 3H 4S 5C");       // Straight
        Hand p4 = parseHand("4C 4D 4H 6S 7C");       // Three of a kind (4s)
        Hand p5 = parseHand("2D 5D QD KD 7D");       // Flush
        Arbiter arb = new Arbiter();
        List<Hand> winners = arb.determineWinners(List.of(p1, p2, p3, p4, p5));
        assertEquals("Only P5 (flush) should win", 1, winners.size());
        assertTrue("P5 should be in the winners list", winners.contains(p5));
    }

    // ---------- Error tests (exceptions) ----------

    /**
     * Test that an invalid rank character throws IllegalArgumentException.
     *
     * @throws IllegalArgumentException expected because 'X' is not a valid rank
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidCardFormatThrows_Xrank() {
        Card.fromString("XD");
    }

    /**
     * Test that a card string of length 3 (e.g., "10H") throws IllegalArgumentException.
     *
     * @throws IllegalArgumentException expected because format should be exactly 2 characters
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidCardFormatThrows_TooLong() {
        Card.fromString("10H");
    }

    /**
     * Test that constructing a hand with fewer than 5 cards throws IllegalArgumentException.
     *
     * @throws IllegalArgumentException expected because a hand must have exactly 5 cards
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidHandSizeThrows_TooFew() {
        List<Card> tooFew = List.of(
            Card.fromString("2D"),
            Card.fromString("4C"),
            Card.fromString("5D"),
            Card.fromString("QH")
        );
        new Hand(tooFew);
    }

    /**
     * Test that constructing a hand with more than 5 cards throws IllegalArgumentException.
     *
     * @throws IllegalArgumentException expected because a hand must have exactly 5 cards
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidHandSizeThrows_TooMany() {
        List<Card> tooMany = List.of(
            Card.fromString("3C"),
            Card.fromString("7D"),
            Card.fromString("QH"),
            Card.fromString("KD"),
            Card.fromString("5D"),
            Card.fromString("3H")
        );
        new Hand(tooMany);
    }

    /**
     * Test that duplicate cards in a hand throw IllegalArgumentException.
     *
     * @throws IllegalArgumentException expected because no duplicates allowed
     */
    @Test(expected = IllegalArgumentException.class)
    public void duplicateCardsInHandThrows() {
        List<Card> duplicates = List.of(
            Card.fromString("2D"),
            Card.fromString("2D"), // Duplicate
            Card.fromString("QC"),
            Card.fromString("KD"),
            Card.fromString("7H")
        );
        new Hand(duplicates);
    }
}


package io.github.leopernier.poker;

import io.github.leopernier.poker.model.Card;
import io.github.leopernier.poker.model.Hand;
import io.github.leopernier.poker.rank.HandRank;
import io.github.leopernier.poker.rank.FlushRank;
import io.github.leopernier.poker.rank.StraightRank;
import io.github.leopernier.poker.rank.ThreeOfKindRank;
import io.github.leopernier.poker.rank.PairRank;
import io.github.leopernier.poker.rank.HighCardRank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class handles input/output and orchestrates hand parsing, arbitration, and result display.
 * Supports both command-line arguments mode and interactive stdin mode.
 */
public class Main {
    /**
     * Entry point for the poker arbiter application.
     * If arguments are provided, each argument is treated as a hand.
     * Otherwise, reads number of players and then each hand from stdin.
     *
     * @param args array of hand strings, each containing 5 cards separated by spaces
     */
    public static void main(String[] args) {
        List<Hand> hands = new ArrayList<>();

        // Read the hands
        if (args.length > 0) {
            if (args.length == 1) {
                System.err.println("Error: There must be at least 2 players!");
                return;
            }
            for (String arg : args) {
                Hand h = parseHandFromString(arg);
                hands.add(h);
            }
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Number of players: ");
            String line = sc.nextLine().trim();
            int n;
            try {
                n = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid nummber of players (" + line + ")!");
                return;
            }
            if (n < 2) {
                System.err.println("Error: There must be at least 2 players!");
                return;
            }
            for (int i = 1; i <= n; i++) {
                System.out.print("p" + i + "? ");
                String handString = sc.nextLine().trim();
                Hand h = parseHandFromString(handString);
                hands.add(h);
            }
            sc.close();
        }

        // Run the arbiter
        Arbiter arbiter = new Arbiter();
        List<Hand> winners = arbiter.determineWinners(hands);
        Map<Hand, HandRank> ranking = arbiter.getRanking();

        // Get a description of each hand
        Map<Hand, String> handsDesc = ranking.entrySet().stream().collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, e -> rankDescription(e.getKey(), e.getValue())));

        // Print the result
        if (winners.size() == 1) {
            Hand winHand = winners.get(0);
            int winIndex = hands.indexOf(winHand) + 1;
            System.out.println("Result: P" + winIndex);

            StringBuilder sb = new StringBuilder();
            sb.append("Description: ").append(handsDesc.get(winHand)).append(" for P").append(winIndex).append(" beats ");
            boolean firstLoser = true;

            for (Hand h : hands) {
                if (!h.equals(winHand)) {
                    int index = hands.indexOf(h) + 1;
                    if (!firstLoser)
                        sb.append(", ");
                    sb.append(handsDesc.get(h)).append(" for P").append(index);
                    firstLoser = false;
                }
            }
            System.out.println(sb.toString());
        } else {
            System.out.print("result: TIE among ");
            for (int i = 0; i < winners.size(); i++) {
                int index = hands.indexOf(winners.get(i)) + 1;
                System.out.print("P" + index);
                if (i < winners.size() - 1)
                    System.out.print(" and ");
            }
            System.out.println();
        }
    }

    //-----------------
    // Private methods
    //-----------------

    /**
     * Parses a hand string of exactly 5 cards separated by whitespace into a Hand object.
     * Each card token must be two characters long (rank + suit).
     * If format is invalid, prints error and exits.
     *
     * @param   s string containing 5 cards like "TH 7C AD 3S 2D"
     * @return  Hand object constructed from parsed cards
     * @throws  IllegalArgumentException if input is null or blank
     */
    private static Hand parseHandFromString(String s) {
        if (s == null || s.isBlank()) {
            System.err.println("Error: Empty or invalid hand!");
            System.exit(1);
        }
        String[] tokens = s.trim().toUpperCase().split("\\s+");
        if (tokens.length != 5) {
            System.err.println("Error: A hand must contain exactly 5 cards separated by spaces (" + s + ")!");
            System.exit(1);
        }
        List<Card> cards = new ArrayList<>();
        try {
            for (String t : tokens)
                cards.add(Card.fromString(t));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Error parsing cards: \"" + e.getMessage() + "\"");
            System.exit(1);
        }
        return new Hand(cards);
    }

    /**
     * Builds a human-readable description for a given Hand and its HandRank.
     * For example: "pair of jacks" or "flush of hearts".
     *
     * @param   h Hand object.
     * @param   hr HandRank object detected for that hand.
     * @return  Description string.
     */
    private static String rankDescription(Hand h, HandRank hr) {
        if (hr instanceof FlushRank) {
            char suitSymbol = h.getHand().get(0).getSuit().getSymbol();
            String word = suitCharToWord(suitSymbol);
            return "suit of " + word;
        } else if (hr instanceof StraightRank) {
            int high = ((StraightRank) hr).getHighCardValue();
            String word = rankToWord(high);
            return "straight to " + word;
        } else if (hr instanceof ThreeOfKindRank) {
            int trio = ((ThreeOfKindRank) hr).getTrioValue();
            String word = rankToWord(trio);
            return "three of a kind of " + word;
        } else if (hr instanceof PairRank) {
            int pair = ((PairRank) hr).getPairValue();
            String word = rankToWord(pair);
            return "pair of " + word;
        } else if (hr instanceof HighCardRank) {
            int rank = h.getHand().get(4).getRank().getRank();
            String word = rankToWord(rank);
            return "high card of " + word;
        }
        return hr.toString();
    }

    /**
     * Converts an integer rank value to its English word equivalent for face cards or numeric string.
     *
     * @param r integer rank (1 for Ace, 11 for Jack, 12 for Queen, 13 for King, else numeric).
     * @return  English word or numeric string.
     */
    private static String rankToWord(int r) {
        switch(r) {
            case 1:  return "ace";
            case 11: return "jack";
            case 12: return "queen";
            case 13: return "king";
            default: return String.valueOf(r);
        }
    }

    /**
     * Converts a suit character symbol to its English word.
     *
     * @param   c character 'C', 'D', 'H', or 'S'.
     * @return  "clubs", "diamonds", "hearts", or "spades".
     */
    private static String suitCharToWord(char c) {
        switch(c) {
            case 'C':   return "clubs";
            case 'D':   return "diamonds";
            case 'H':   return "hearts";
            case 'S':   return "spades";
            default:    return "unknown";
        }
    }
}


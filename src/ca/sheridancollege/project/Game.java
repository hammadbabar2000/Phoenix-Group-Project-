/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private ArrayList<ArrayList<Card>> playerHands;
    private Card currentCard;
    private int currentPlayer;
    private int direction;
    private int numPlayers;

    public Game(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4.");
        }
        this.numPlayers = numPlayers;
deck = new Deck();
playerHands = new ArrayList<>();
for (int i = 0; i < numPlayers; i++) {
ArrayList<Card> hand = new ArrayList<>();
for (int j = 0; j < 7; j++) {
hand.add(deck.drawCard());
}
playerHands.add(hand);
}
currentCard = deck.drawCard();
currentPlayer = 0;
direction = 1;
}
    private void displayCurrentStatus() {
    System.out.println("\nCurrent Card: " + currentCard);
    System.out.println("Player " + (currentPlayer + 1) + "'s Turn");
    System.out.println("Your Hand: \n" + playerHands.get(currentPlayer));
}

private void changeDirection() {
    direction = -direction;
}

private void nextPlayer() {
    currentPlayer += direction;
    if (currentPlayer >= numPlayers) {
        currentPlayer = 0;
    } else if (currentPlayer < 0) {
        currentPlayer = numPlayers - 1;
    }
}

private int calculateScore(ArrayList<Card> hand) {
    int score = 0;
    for (Card card : hand) {
        switch (card.getValue()) {
            case ZERO:
                score += 0;
                break;
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
            case NINE:
                score += card.getValue().ordinal();
                break;
            case SKIP:
            case REVERSE:
            case DRAW_TWO:
                score += 20;
                break;
            case WILD:
            case WILD_DRAW_FOUR:
                score += 50;
                break;
        }
    }
    return score;
}

private boolean isCardPlayable(Card selectedCard) {
    return isCardColorMatch(selectedCard) || isCardValueMatch(selectedCard) || isCardWild(selectedCard);
}

private boolean isCardColorMatch(Card selectedCard) {
    return selectedCard.getColor() == currentCard.getColor();
}

private boolean isCardValueMatch(Card selectedCard) {
    return selectedCard.getValue() == currentCard.getValue();
}

private boolean isCardWild(Card selectedCard) {
    return selectedCard.getColor() == Card.Color.WILD;
}

private boolean isDeckEmpty() {
    return deck.isEmpty();
}

private boolean hasPlayerWon(int playerIndex) {
    return playerHands.get(playerIndex).isEmpty();
}

private boolean isValidPlayerChoice(int choice) {
    return choice >= -1 && choice < playerHands.get(currentPlayer).size();
}

public void play() {
    Scanner scanner = new Scanner(System.in);
    boolean gameOver = false;

    while (!gameOver) {
        displayCurrentStatus();
        System.out.print("Enter the index of the card you want to play or -1 to draw: ");
        int choice = -2;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter an integer.");
            scanner.nextLine(); // Clear the input buffer
            continue;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            scanner.nextLine(); // Clear the input buffer
            continue;
        }

        ArrayList<Card> currentPlayerHand = playerHands.get(currentPlayer);

        if (choice == -1) {
            if (isDeckEmpty()) {
                System.out.println("No cards left in the deck. Game over!");
                                break;
            } else {
                Card drawnCard = deck.drawCard();
                currentPlayerHand.add(drawnCard);
                System.out.println("You drew: " + drawnCard);
            }
        } else if (isValidPlayerChoice(choice)) {
            Card selectedCard = currentPlayerHand.get(choice);
            if (isCardPlayable(selectedCard)) {
                currentCard = selectedCard;
                currentPlayerHand.remove(choice);

                switch (currentCard.getValue()) {
                    case SKIP:
                        nextPlayer();
                        break;
                    case REVERSE:
                        changeDirection();
                        break;
                    case DRAW_TWO:
                        nextPlayer();
                        for (int i = 0; i < 2; i++) {
                            if (!isDeckEmpty()) {
                                playerHands.get(currentPlayer).add(deck.drawCard());
                            }
                        }
                        break;
                    case WILD:
                        System.out.print("Select a new color (RED, GREEN, BLUE, YELLOW): ");
                        scanner.nextLine(); // Clear the input buffer
                        String newColor = scanner.nextLine().toUpperCase();
                        try {
                            currentCard.setColor(Card.Color.valueOf(newColor));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid color! Color remains unchanged.");
                        }
                        break;
                    case WILD_DRAW_FOUR:
                        System.out.print("Select a new color (RED, GREEN, BLUE, YELLOW): ");
                        scanner.nextLine(); // Clear the input buffer
                        String newColorFour = scanner.nextLine().toUpperCase();
                        try {
                            currentCard.setColor(Card.Color.valueOf(newColorFour));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid color! Color remains unchanged.");
                        }
                        nextPlayer();
                        for (int i = 0; i < 4; i++) {
                            if (!isDeckEmpty()) {
                                playerHands.get(currentPlayer).add(deck.drawCard());
                            }
                        }
                        break;
                    default:
                        break;
                }

                if (hasPlayerWon(currentPlayer)) {
                    System.out.println("Player " + (currentPlayer + 1) + " wins!");
                    System.out.println("Final scores:");
                    for (int i = 0; i < numPlayers; i++) {
                        System.out.println("Player " + (i + 1) + ": " + calculateScore(playerHands.get(i)));
                    }
                    gameOver = true;
                } else {
                    nextPlayer();
                }
            } else {
                System.out.println("Invalid move! The selected card cannot be played.");
            }
        } else {
            System.out.println("Invalid choice! Please choose a valid card index or -1 to draw.");
        }
    }
}
}



                    

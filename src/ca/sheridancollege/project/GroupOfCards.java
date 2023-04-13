/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;



import java.util.InputMismatchException;
import java.util.Scanner;

public class GroupOfCards {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players (2-4): ");
        int numPlayers = 0;
        try {
            numPlayers = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter an integer.");
            scanner.nextLine(); // Clear the input buffer
            System.exit(1);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            scanner.nextLine(); // Clear the input buffer
            System.exit(1);
        }

        while (numPlayers < 2 || numPlayers > 4) {
            System.out.println("Invalid number of players! Please enter a number between 2 and 4.");
            System.out.print("Enter the number of players (2-4): ");
            try {
                numPlayers = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                scanner.nextLine(); // Clear the input buffer
                continue;
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the input buffer
                continue;
            }
        }
        Game game = new Game(numPlayers);
        game.play();
    }
}

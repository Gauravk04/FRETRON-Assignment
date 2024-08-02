import java.util.*;

public class Que3 {

    public static void main(String[] args) {
        int[][] board = handleInput();

        Scanner s = new Scanner(System.in);
        System.out.print("Enter the coordinates for your “special” castle (format: row,col): ");
        String[] castleCoords = s.nextLine().split(",");
        int i1 = Integer.parseInt(castleCoords[0]) - 1; // Convert to 0-based index
        int i2 = Integer.parseInt(castleCoords[1]) - 1; // Convert to 0-based index

        // Mark the position of the castle
        board[i2][i1] = 2;

        // Track unique paths
        int count = 0;
        int soldierKillCount = 0;
        int[][] vis = new int[10][10]; // Visited array

        // Start finding paths
        count = findMyHomeCastleSoldiers(board, i2, i1, count, soldierKillCount, vis);
        System.out.println("Total unique paths: " + count);
    }

    public static int findMyHomeCastleSoldiers(int[][] board, int i2, int i1, int count, int soldierKillCount, int[][] vis) {
        // Check for boundaries
        if (i2 < 0 || i2 >= 10 || i1 < 0 || i1 >= 10) {
            return count; // Out of bounds
        }

        // If we are back at the starting position and all soldiers are killed
        if (i2 == 0 && i1 == 1 && soldierKillCount > 0) { // Assuming (1,2) is the home position
            return count + 1;
        }

        // Attempt to kill soldiers and move left
        // Check left direction
        for (int j = i1 - 1; j >= 0; j--) {
            if (board[i2][j] == 1) { // Soldier present
                // Kill the soldier
                board[i2][j] = 0; // Mark as empty
                vis[i2][j] = 1; // Mark as visited
                // Move left and recursively search
                count = findMyHomeCastleSoldiers(board, i2, j, count, soldierKillCount + 1, vis);
                // Backtrack
                board[i2][j] = 1; // Restore soldier
                vis[i2][j] = 0; // Unmark as visited
                break; // Stop after the first kill
            }
        }

        // Try jumping over soldiers in the left direction
        for (int j = i1 - 1; j >= 0; j--) {
            if (board[i2][j] == 0) { // Empty cell, jump possible
                count = findMyHomeCastleSoldiers(board, i2, j, count, soldierKillCount, vis);
            }
        }

        return count; // Return the count of unique paths
    }

    public static int[][] handleInput() {
        int[][] board = new int[10][10];
        Scanner s = new Scanner(System.in);

        // Initialize the board
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = 0; // All cells are initially empty
            }
        }

        // Input soldiers
        for (int i = 1; i <= 11; i++) {
            System.out.print("Enter coordinates for soldier " + i + " (format: row,col): ");
            String[] soldierCoords = s.nextLine().split(",");
            int i1 = Integer.parseInt(soldierCoords[0]) - 1; // Convert to 0-based index
            int i2 = Integer.parseInt(soldierCoords[1]) - 1; // Convert to 0-based index

            // Validate coordinates
            if (i2 >= 0 && i2 < 10 && i1 >= 0 && i1 < 10) {
                board[i2][i1] = 1; // Place soldier on the board
            } else {
                System.out.println("Invalid coordinates! Try again.");
                i--; // Repeat the input for this soldier
            }
        }
        return board;
    }
}

import java.util.*;

public class Que3 {

    private static final int BOARD_SIZE = 10;

    public static void main(String[] args) {
        int[][] board = handleInput();

        Scanner s = new Scanner(System.in);
        System.out.print("Enter the coordinates for your “special” castle (format: row,col): ");
        String[] castleCoords = s.nextLine().split(",");
        int i1 = Integer.parseInt(castleCoords[0]) - 1; // Convert to 0-based index
        int i2 = Integer.parseInt(castleCoords[1]) - 1; // Convert to 0-based index

        // Mark the position of the castle
        board[i2][i1] = 2;

        // Find unique paths
        Set<List<Move>> uniquePaths = findMyHomeCastleSoldiers(board, i2, i1);
        System.out.println("Total unique paths: " + uniquePaths.size());

        // Display paths
        int pathIndex = 1;
        for (List<Move> path : uniquePaths) {
            System.out.println("Path " + pathIndex + ":");
            for (Move move : path) {
                System.out.println("Kill " + move.coordinate + ". Turn " + move.direction);
            }
            pathIndex++;
            System.out.println();
        }
    }

    public static Set<List<Move>> findMyHomeCastleSoldiers(int[][] board, int i2, int i1) {
        Set<Coordinate> soldiers = new HashSet<>();
        // Collect all soldier coordinates
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 1) {
                    soldiers.add(new Coordinate(col, row));
                }
            }
        }

        // Start finding paths from the castle
        Coordinate castle = new Coordinate(i1, i2);
        return findPaths(castle, soldiers);
    }

    private static Set<List<Move>> findPaths(Coordinate castle, Set<Coordinate> soldiers) {
        Set<List<Move>> pathHistory = new HashSet<>();
        move(castle, soldiers, 'R', new ArrayList<>(), pathHistory, castle);
        move(castle, soldiers, 'D', new ArrayList<>(), pathHistory, castle);
        move(castle, soldiers, 'L', new ArrayList<>(), pathHistory, castle);
        move(castle, soldiers, 'U', new ArrayList<>(), pathHistory, castle);
        return pathHistory;
    }

    private static void move(Coordinate position, Set<Coordinate> soldiers, char direction,
                             List<Move> path, Set<List<Move>> pathHistory, Coordinate castle) {
        int[][] deltas = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        char[] directions = {'R', 'D', 'L', 'U'};
        Map<Character, Integer> directionMap = Map.of('R', 0, 'D', 1, 'L', 2, 'U', 3);

        int x = position.x, y = position.y;
        int dx = deltas[directionMap.get(direction)][0];
        int dy = deltas[directionMap.get(direction)][1];

        while (0 <= x + dx && x + dx < BOARD_SIZE && 0 <= y + dy && y + dy < BOARD_SIZE) {
            x += dx;
            y += dy;
            Coordinate nextPosition = new Coordinate(x, y);

            if (soldiers.contains(nextPosition)) {
                Set<Coordinate> newSoldiers = new HashSet<>(soldiers);
                newSoldiers.remove(nextPosition);
                List<Move> newPath = new ArrayList<>(path);
                newPath.add(new Move(nextPosition, direction));
                pathHistory.add(newPath);

                // Continue moving in the same direction to check for further soldiers
                move(new Coordinate(x, y), newSoldiers, direction, newPath, pathHistory, castle);
            }

            // Check if we reached the castle
            if (nextPosition.equals(castle)) {
                return;
            }
        }
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

class Coordinate {
    int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Move {
    Coordinate coordinate;
    char direction;

    Move(Coordinate coordinate, char direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }
}

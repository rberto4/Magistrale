import java.util.*;

public class HillClimbing8Queens {

    static final int N = 8;
    static Random random = new Random();

    public static void main(String[] args) {
        int[] solution = hillClimbing();
        printBoard(solution);
        System.out.println("Conflitti: " + countConflicts(solution));
    }

    // Algoritmo di miglioramento iterativo
    static int[] hillClimbing() {
        int[] current = randomBoard();

        while (true) {
            int currentConflicts = countConflicts(current);
            int[] next = null;
            int bestConflicts = currentConflicts;

            // Esplora il vicinato
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (col == current[row]) continue; // stessa posizione, salta

                    int[] neighbor = current.clone();
                    neighbor[row] = col;
                    int conflicts = countConflicts(neighbor);

                    if (conflicts < bestConflicts) {
                        bestConflicts = conflicts;
                        next = neighbor;
                    }
                }
            }

            // Se nessun vicino è migliore, termina
            if (next == null) break;
            current = next;
        }

        return current;
    }

    // Crea una scacchiera casuale
    static int[] randomBoard() {
        int[] board = new int[N];
        for (int i = 0; i < N; i++) {
            board[i] = random.nextInt(N);
        }
        return board;
    }

    // Conta i conflitti tra regine
    static int countConflicts(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (board[i] == board[j]) conflicts++; // stessa colonna
                if (Math.abs(board[i] - board[j]) == Math.abs(i - j)) conflicts++; // stessa diagonale
            }
        }
        return conflicts;
    }

    // Stampa la scacchiera
    static void printBoard(int[] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i] == j ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}

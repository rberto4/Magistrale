public class HillClimbingFunction {

    static double f(double x) {
        return x * Math.sin(x);
    }

    static double hillClimbing(double startX, double step, int maxIterations) {
        double currentX = startX;
        double currentVal = f(currentX);

        for (int i = 0; i < maxIterations; i++) {
            double nextX1 = currentX + step;
            double nextX2 = currentX - step;

            double val1 = f(nextX1);
            double val2 = f(nextX2);

            if (val1 > currentVal) {
                currentX = nextX1;
                currentVal = val1;
            } else if (val2 > currentVal) {
                currentX = nextX2;
                currentVal = val2;
            } else {
                // Nessun vicino migliore: massimo locale raggiunto
                break;
            }
        }

        return currentX;
    }

    public static void main(String[] args) {
        double start = 2.0;
        double step = 0.01;
        int maxIter = 100;

        double result = hillClimbing(start, step, maxIter);
        System.out.printf("Massimo locale trovato in x = %.4f con valore f(x) = %.4f\n", result, f(result));
    }
}

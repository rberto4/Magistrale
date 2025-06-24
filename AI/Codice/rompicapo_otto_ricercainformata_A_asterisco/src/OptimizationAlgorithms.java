import java.util.*;

public class OptimizationAlgorithms {

    public static class Result {
        public final double bestX;
        public final List<Double> pathX; // lista x degli stati visitati
        public final List<Double> pathF; // lista f(x) per ogni stato

        public Result(double bestX, List<Double> pathX, List<Double> pathF) {
            this.bestX = bestX;
            this.pathX = pathX;
            this.pathF = pathF;
        }
    }

    public static double f(double x) {
        return x * Math.sin(x);
    }

    public static Result hillClimbing(double start, double step, int maxIter) {
        double current = start;
        List<Double> pathX = new ArrayList<>();
        List<Double> pathF = new ArrayList<>();

        pathX.add(current);
        pathF.add(f(current));

        for (int i = 0; i < maxIter; i++) {
            double nextUp = current + step;
            double nextDown = current - step;
            double fCurr = f(current);
            double fUp = f(nextUp);
            double fDown = f(nextDown);

            // Trova il vicino con valore massimo
            double bestNeighbor = current;
            double bestValue = fCurr;

            if (fUp > bestValue) {
                bestNeighbor = nextUp;
                bestValue = fUp;
            }
            if (fDown > bestValue) {
                bestNeighbor = nextDown;
                bestValue = fDown;
            }

            if (bestNeighbor == current) {
                // massimo locale raggiunto
                break;
            } else {
                current = bestNeighbor;
                pathX.add(current);
                pathF.add(bestValue);
            }
        }
        return new Result(current, pathX, pathF);
    }

    public static Result simulatedAnnealing(double start, double temp, double coolingRate, int maxIter) {
        Random rand = new Random();
        double current = start;
        double best = current;

        List<Double> pathX = new ArrayList<>();
        List<Double> pathF = new ArrayList<>();

        pathX.add(current);
        pathF.add(f(current));

        double temperature = temp;

        for (int i = 0; i < maxIter; i++) {
            double next = current + (rand.nextDouble() * 2 - 1); // passo casuale tra -1 e 1
            double currentValue = f(current);
            double nextValue = f(next);

            if (nextValue > currentValue) {
                current = next;
            } else {
                double acceptanceProb = Math.exp((nextValue - currentValue) / temperature);
                if (acceptanceProb > rand.nextDouble()) {
                    current = next;
                }
            }

            if (f(current) > f(best)) {
                best = current;
            }

            temperature *= coolingRate;

            pathX.add(current);
            pathF.add(f(current));

            if (temperature < 1e-3) break;  // stop se troppo freddo
        }
        return new Result(best, pathX, pathF);
    }
}

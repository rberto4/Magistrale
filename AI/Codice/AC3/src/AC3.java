import java.util.*;

public class AC3 {
    private Map<String, Set<Integer>> domains;
    private Map<String, List<Arc>> constraints;

    // Classe per rappresentare un arco tra due variabili
    private static class Arc {
        String from;
        String to;
        
        public Arc(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Arc arc = (Arc) o;
            return from.equals(arc.from) && to.equals(arc.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

    public AC3() {
        this.domains = new HashMap<>();
        this.constraints = new HashMap<>();
    }

    // Aggiunge una variabile con il suo dominio
    public void addVariable(String variable, Set<Integer> domain) {
        domains.put(variable, new HashSet<>(domain));
        constraints.putIfAbsent(variable, new ArrayList<>());
    }

    // Aggiunge un vincolo binario tra due variabili
    public void addConstraint(String var1, String var2) {
        constraints.get(var1).add(new Arc(var1, var2));
        constraints.get(var2).add(new Arc(var2, var1));
    }

    // Implementazione dell'algoritmo AC3
    public boolean runAC3() {
        Queue<Arc> queue = new LinkedList<>();
        
        // Inizializza la coda con tutti gli archi
        for (List<Arc> arcs : constraints.values()) {
            queue.addAll(arcs);
        }

        while (!queue.isEmpty()) {
            Arc arc = queue.poll();
            if (revise(arc)) {
                if (domains.get(arc.from).isEmpty()) {
                    return false; // Nessuna soluzione possibile
                }

                // Aggiungi alla coda tutti gli archi che puntano a arc.from
                for (Arc neighborArc : getNeighbors(arc.from)) {
                    if (!neighborArc.to.equals(arc.to)) {
                        queue.add(neighborArc);
                    }
                }
            }
        }
        return true;
    }

    // Restituisce tutti gli archi che puntano a una variabile
    private List<Arc> getNeighbors(String variable) {
        return constraints.get(variable);
    }

    // Implementa la revisione del dominio per un arco
    private boolean revise(Arc arc) {
        boolean revised = false;
        Set<Integer> fromDomain = domains.get(arc.from);
        Set<Integer> toDomain = domains.get(arc.to);

        Set<Integer> valuesToRemove = new HashSet<>();
        
        // Per ogni valore nel dominio della variabile di origine
        for (int x : fromDomain) {
            boolean hasSupport = false;
            
            // Cerca un valore nel dominio della variabile di destinazione che soddisfi il vincolo
            for (int y : toDomain) {
                if (satisfiesConstraint(x, y)) {
                    hasSupport = true;
                    break;
                }
            }
            
            if (!hasSupport) {
                valuesToRemove.add(x);
                revised = true;
            }
        }
        
        fromDomain.removeAll(valuesToRemove);
        return revised;
    }

    // Verifica se due valori soddisfano il vincolo
    private boolean satisfiesConstraint(int x, int y) {
        // Qui implementiamo un semplice vincolo di disuguaglianza
        // In un'implementazione reale, questo metodo dovrebbe essere personalizzato
        // in base ai vincoli specifici del problema
        return x != y;
    }

    // Metodo per ottenere i domini correnti
    public Map<String, Set<Integer>> getDomains() {
        return new HashMap<>(domains);
    }

    // Main di esempio
    public static void main(String[] args) {
        AC3 ac3 = new AC3();

        // Esempio: problema di colorazione del grafo con 3 colori (1, 2, 3)
        Set<Integer> colors = new HashSet<>(Arrays.asList(1, 2, 3));

        // Aggiungi variabili (nodi del grafo)
        ac3.addVariable("X1", colors);
        ac3.addVariable("X2", colors);
        ac3.addVariable("X3", colors);

        // Aggiungi vincoli (archi del grafo)
        ac3.addConstraint("A", "B");
        ac3.addConstraint("B", "C");
        ac3.addConstraint("C", "A");

        // Esegui l'algoritmo AC3
        boolean result = ac3.runAC3();
        System.out.println("Risultato AC3: " + result);
        System.out.println("Domini finali: " + ac3.getDomains());
    }
}
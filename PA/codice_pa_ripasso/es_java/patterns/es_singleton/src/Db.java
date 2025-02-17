import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Db {
    private static volatile Db instance;
    private List<String> posti;
    private HashMap<Integer, String> data;

    // costruttore privato
    private Db() {
        // Usa ArrayList per rendere la lista mutabile
        posti = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        data = new HashMap<>();
    }

    // accesso pubblico a istanza singola di Db (thread-safe)
    public static Db getInstance() {
        if (instance == null) {
            synchronized (Db.class) {
                if (instance == null) {
                    instance = new Db();
                }
            }
        }
        return instance;
    }

    public void stampaPosti() {
        for (String posto : posti) {
            System.out.print(posto + " ");
        }
        System.out.println();
    }

    private Boolean checkPostiDisponibili() {
        for (String p : posti) {
            if (!p.equals("x")) {
                return true;
            }
        }
        return false;
    }

    private Boolean checkUtenteGiaRegistrato(String nome, String cognome) {
        return data.containsValue(nome + " " + cognome);
    }

    public void prenotaPosto(String nome, String cognome) {
        if (checkUtenteGiaRegistrato(nome, cognome)) {
            System.out.println("Spettatore già registrato");
            return;
        }

        if (!checkPostiDisponibili()) {
            System.out.println("Posti esauriti");
            return;
        }

        int posto = 0;
        for (String p : posti) {
            if (!p.equals("x")) {
                posto = Integer.parseInt(p);
                data.put(posto, nome + " " + cognome);
                posti.set(posto - 1, "x");
                break;
            }
        }
        System.out.println("Prenotazione effettuata");
        System.out.println("Posto: " + posto);
    }

    public void prenotaPosto(String nome, String cognome, int posto) {
        if (checkUtenteGiaRegistrato(nome, cognome)) {
            System.out.println("Spettatore già registrato");
            return;
        }

        if (!checkPostiDisponibili()) {
            System.out.println("Posti esauriti");
            return;
        }

        if (posti.get(posto - 1).equals("x")) {
            System.out.println("Posto già prenotato");
            return;
        }

        data.put(posto, nome + " " + cognome);
        posti.set(posto - 1, "x");
        System.out.println("Prenotazione effettuata");
        System.out.println("Posto: " + posto);
    }
}

package es_gpt;

public class Main {
    public static void main(String[] args) {
        Persona p1 = new Persona("Luca", 25);
        Persona p2 = new Persona("Anna", 30);
        Studente s1 = new Studente("Mario", 22, "123456");

        p1.stampaInfo();
        p2.stampaInfo();
        s1.stampaInfo();

        System.out.println(Calcolatrice.somma(5, 3));
        System.out.println(Calcolatrice.somma(5.5, 3.5));
        System.out.println(Calcolatrice.somma(5, 3, 2));

        Cerchio c1 = new Cerchio(5);
        System.out.println(c1.calcolaArea());
        Rettangolo r1 = new Rettangolo(5.1, 3.7);
        System.out.println(r1.calcolaArea());

        ContoBancario cb1 = new ContoBancario("Mario Rossi", 1000);
        try {
            cb1.preleva(500);
            System.out.println("Saldo -> "+ cb1.getSaldo());
            cb1.preleva(600);
            System.out.println("Saldo -> "+ cb1.getSaldo());
        } catch (SaldoInsufficienteException e) {
            System.out.println(e.getMessage());
        }
    }
}

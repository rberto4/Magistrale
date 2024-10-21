package exe5_hash;

public class test {

	public static void main(String[] args) {
	      try {
	            // Creazione dell'oggetto FinancialHistory con ammontare iniziale di 5000
	            FinancialHistory history = new FinancialHistory(5000);
	            System.out.println("---- stampo il valore del saldo iniziale, prima di qualsiasi operazione ----");
	            System.out.println("Saldo attuale: " + history.cashOnHand());

	           // Aggiunta di entrate e uscite
	            history.receiveFrom(1750, "Stipendio");
	            history.spendFor(850, "Affitto");
	            history.spendFor(150, "Spesa alimentare");
	            history.spendFor(50, "Benzina auto");
	            history.receiveFrom(20, "Buono regalo");

	            // Test dei metodi
	            System.out.println("Saldo attuale: " + history.cashOnHand());
	            System.out.println("Entrate da 'Stipendio': " + history.receivedFrom("Stipendio"));
	            System.out.println("Uscite per 'Affitto': " + history.spentFor("Affitto"));
	            
	            System.out.println("Saldo attuale: " + history.cashOnHand());

	            // Stampa delle entrate e delle uscite
	            System.out.println(history.printlncomes());
	            System.out.println(history.printExpenditures());
	            
	            
	            history.receiveFrom(200, "Bonifico bonus");
	            
	            System.out.println("Saldo attuale: " + history.cashOnHand());
	            history.spendFor(20, "Cena in pizzeria");

	            System.out.println("Saldo attuale: " + history.cashOnHand());
	            
	            System.out.println("Approccio ciclo while");
	            System.out.println(history.printlncomes());
	            System.out.println(history.printExpenditures());
	            	            
	            System.out.println("Approccio foreach");
	            System.out.println(history.printlncomes_foreach());
	            System.out.println(history.printExpenditures_foreach());
	            
	            

	        } catch (NegAmountException | NegCashException e) {
	            System.out.println(e.getMessage());
	        }
	}

}

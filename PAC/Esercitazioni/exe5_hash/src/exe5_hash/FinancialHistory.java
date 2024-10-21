package exe5_hash;
import java.util.*;


public class FinancialHistory implements FinancialHistoryIF {
	
	 private float cashOnHand;
	 private Hashtable<String, Float> incomes;
	 private Hashtable<String, Float> expenditures;
	 
	 
	    // Costruttore
    public FinancialHistory(float amount) throws NegAmountException {
        if (amount < 0) {
            throw new NegAmountException("L'ammontare iniziale non può essere negativo.");
        }
        this.cashOnHand = amount;
        this.incomes = new Hashtable<>();
        this.expenditures = new Hashtable<>();
    }
    
	    
	@Override
	public float cashOnHand() {
		
		return cashOnHand;
	}

	@Override
	public float receivedFrom(String s) {
		 if (incomes.containsKey(s)) {
	            return incomes.get(s);
	        }
		 
		 // creare eccezione ad hoc
	     return 0;
	}

	@Override
	public float spentFor(String s) {
		 if (expenditures.containsKey(s)) {
	            return expenditures.get(s);
	        }
		 
		 // creare eccezione ad hoc
	     return 0;
	}

	@Override
	public void receiveFrom(float amount, String s)  throws NegAmountException {
		if (amount < 0) {
            throw new NegAmountException("L'entrata non può essere negativa.");
        }
        this.cashOnHand += amount;
        this.incomes.put(s, amount);
	}

	@Override
	public void spendFor(float amount, String s) throws NegAmountException, NegCashException{
		if (amount < 0) {
            throw new NegAmountException("L'entrata non può essere negativa.");
        }
		
		 if (this.cashOnHand - amount < 0) {
	            throw new NegCashException("Saldo insufficiente per effettuare questa spesa.");
	        }
		 
        this.cashOnHand -= amount;
        this.expenditures.put(s, amount);
	}

	@Override
	public String printlncomes() {
		
		
		
		// approccio con forEach 
		
		
		StringBuilder s = new StringBuilder();
		s.append("Incomes : \n");
		
		incomes.forEach((k, v) -> {
			s.append(k+" : "+v.toString()+"\n");
		});
		
		return s.toString();
		
	}

	@Override
	public String printExpenditures() {
		
		
		// approccio con forEach 
		
		StringBuilder s = new StringBuilder();
		s.append("Expenditures : \n");
		
		expenditures.forEach((k, v) -> {
			s.append(k+" : "+v.toString()+"\n");
		});
		
		return s.toString();
	}


	@Override
	public String printlncomes_foreach() {
		// approccio con List<> di chiavi e ciclo while
		
				StringBuilder s = new StringBuilder();
				Enumeration <String> lista_keys = incomes.keys();
				
				while (lista_keys.hasMoreElements()) {
		            String key = lista_keys.nextElement();
		            s.append(key+" : "+incomes.get(key)+"\n");
		            
				}
				return s.toString();
				
	}


	@Override
	public String printExpenditures_foreach() {
		
		// approccio con Enumaration<String> di chiavi e ciclo while
		StringBuilder s = new StringBuilder();
		Enumeration <String> lista_keys = expenditures.keys();
		
		while (lista_keys.hasMoreElements()) {
            String key = lista_keys.nextElement();
            s.append(key+" : "+expenditures.get(key)+"\n");
            
		}
		return s.toString();
	}

}


//Definizione delle eccezioni 

class NegAmountException extends Exception {
 public NegAmountException(String message) {
     super(message);
 }
}

class NegCashException extends Exception {
 public NegCashException(String message) {
     super(message);
 }
}


package exe5_hash;



public interface FinancialHistoryIF {
	float cashOnHand();
	float receivedFrom(String s);
	float spentFor(String s);
	void receiveFrom(float amount, String s) throws NegAmountException;
	void spendFor(float amount, String s) throws NegAmountException, NegCashException;
	String printlncomes();
	String printExpenditures();
	
	// implementazioni differenti 
	
	String printlncomes_foreach();
	String printExpenditures_foreach();
}

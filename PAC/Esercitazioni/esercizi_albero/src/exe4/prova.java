package exe4;

public class prova {

	public static void main(String[] args) {
		AlberoRossoNero albero = new AlberoRossoNero();
		albero.inserisci("Cane");
		albero.inserisci("Cane");
		albero.inserisci("Cane", "Rex");
		albero.inserisci("Cane", "Duffy");
		//albero.inserisci("Cane");

		albero.inserisci("Giraffa");
		albero.inserisci("Zebra");
		albero.inserisci("Anaconda");
		
		
		
		System.out.println("Contiene il valore rex ? : " + albero.contineneValore("Rex"));

		
		System.out.println(albero.stampaInOrdineAlfabetico());
		
		albero.elimina("Zebra");
		
		System.out.println("Provo ad eliminare zebra");

		System.out.println(albero.stampaInOrdineAlfabetico());
		
		
		/*
		System.out.println("Contains A:" + albero.contains("A"));

		System.out.println("Contains Z:" + albero.contains("Z"));

		System.out.println("Contains i:" + albero.contains("i"));

		System.out.println("Delete A:" + (albero.delete("A") == true ? "success" : "fail"));
		System.out.println("Delete i:" + (albero.delete("i") == true ? "success" : "fail"));
		System.out.println("Delete Balena:" + (albero.delete("Balena") == true ? "success" : "fail"));

		System.out.println(albero.printOrdedString());
		
		*/
	}

}

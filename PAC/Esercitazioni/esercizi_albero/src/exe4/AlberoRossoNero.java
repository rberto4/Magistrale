package exe4;
import java.util.Set;
import java.util.TreeMap;


public class AlberoRossoNero implements StringCollection {
	
	
	private TreeMap<String, String> AlberoTreeMap;

	public AlberoRossoNero() {
		AlberoTreeMap = new TreeMap<>();
	}	
	
	
	@Override
	public void inserisci(String stringa, String value) {
		AlberoTreeMap.put(stringa,value);
		
	}

	@Override
	public void inserisci(String stringa) {
		AlberoTreeMap.put(stringa,"");
		
	}
	
	@Override
	public void elimina(String stringa) {
		AlberoTreeMap.remove(stringa);
	}

	@Override
	public boolean continene(String stringa) {
		// TODO Auto-generated method stub
		return AlberoTreeMap.containsKey(stringa);
	}
	
	@Override
	public String stampaInOrdineAlfabetico() {
		Set <String> keys = AlberoTreeMap.keySet(); // vista ordinata
		return keys.toString();
	}


	@Override
	public boolean contineneValore(String value) {
		return AlberoTreeMap.containsValue(value);
	}
	
}




























































/*
public class AlberoRossoNero implements StringCollection {

	private TreeMap<String, String> albero;

	public AlberoRossoNero() {
		albero = new TreeMap<>();
	}

	@Override
	public void insert(String stringToAdd) {
		albero.put(stringToAdd, "");
	}

	@Override
	public boolean delete(String stringToDelete) {
		return (albero.remove(stringToDelete) != null ? true : false);
	}

	@Override
	public boolean contains(String stringToSearch) {
		return albero.containsKey(stringToSearch);
	}

	@Override
	public String printOrdedString() {
		Set<String> keys = albero.keySet();
		return keys.toString();
	}

}
*/
/**
 * @author Federico Imberti
 */

package lezione2.albero;
import java.util.List; //Per l'output delle visite
import java.util.LinkedList; //Per l'output delle visite

import lezione2.coda.*; //Per la visita BFS (come esempio di struttura dati propria)
import java.util.Stack; //Per la visita DFS (come esempio di struttura dati predefinita di JFC)

public class AlberoBinarioImpl implements AlberoBinario{
	protected NodoBinario radice;

	//Metodo costruttore di default
	public AlberoBinarioImpl(){
		radice = null;
	}
	
	//Metodi costruttori: due varianti (uno con l'info ed uno direttamente con il nodo)
	public AlberoBinarioImpl(Object e){
		radice = new NodoBinario(e);
	}

	public AlberoBinarioImpl(NodoBinario rad){
		radice = rad;
	}
	
	//Metodi accessori:
	public NodoBinario radice(){
		return radice;	
	}
	
	
	public int numNodi(){
	 return numNodi(radice);	
	}
		
	/**
	 * Restituisce il numero di nodi dell'albero radicato in un nodo
	 * <code>r</code>. Tale informazione viene determinata conteggiando
	 * ricorsivamente il numero di nodi dei sottoalberi
	 * radicati nei figli di <code>r</code>.
	 * 
	 * @return il numero di nodi presenti nell'albero radicato in <code>r</code>.
	 */
	private int numNodi(NodoBinario r) {
		return r == null ? 0 : numNodi(r.sinistro) + numNodi(r.destro) + 1;
	}
	
	public int grado(NodoBinario v) {
		int grado = 0;
		if (v == null) return -1;
		if (((NodoBinario) v).sinistro != null) grado++;
		if (((NodoBinario) v).destro != null) grado++;
		return grado;
	}
	
	public Object info(NodoBinario v){
		return v.elem;
	}
	
	//Restituisce true se v e' un figlio sinistro	
	public boolean figlioSinistro(NodoBinario v){
		if(v.equals(v.padre.sinistro)) return true;
		else return false;		
	}
	
	//Restituisce true se v e' un figlio sinistro	
	public boolean figlioDestro(NodoBinario v){
		if(v.equals(v.padre.destro)) return true;
		else return false;		
	}
	
    public NodoBinario padre(NodoBinario v){
	   return v.padre;
    }
	
	public NodoBinario sin(NodoBinario v){
		return v.sinistro;
	}
		
	public NodoBinario des(NodoBinario v){
		return v.destro;
	}
	
	public void cambiaInfo(NodoBinario v, Object info){
		v.elem =info; 
	}
	
	public void scambiaInfo(NodoBinario v1, NodoBinario v2){
		Object app = v1.elem;
		v1.elem = v2.elem;
		v2.elem = app;
	}
			
	//aggiunge "albero" come sottoalbero sinistro di "padre"
	public void innestaSin(NodoBinario padre, AlberoBinario albero){
		NodoBinario figlio =  albero.radice();
		if(figlio!=null) figlio.padre = padre;
		padre.sinistro = figlio;
	}

	//aggiunge "albero" come sottoalbero destro di "padre"
	public void innestaDes(NodoBinario padre, AlberoBinario albero){
		NodoBinario figlio =  albero.radice();
		if(figlio!=null) figlio.padre = padre;
		padre.destro = figlio;
	}


	//elimina il sottoalbero sinistro di "padre" e lo restituisce
	public AlberoBinario potaSinistro(NodoBinario padre){
		NodoBinario figlio = padre.sinistro;
		figlio.padre=null;
		AlberoBinario newalbero = new AlberoBinarioImpl(figlio);
		padre.sinistro = null;
		return newalbero;
	}	

	//elimina il sottoalbero destro di "padre" e lo restituisce
	public AlberoBinarioImpl potaDestro(NodoBinario padre){
		NodoBinario figlio = padre.destro;
		figlio.padre=null;
		AlberoBinarioImpl newalbero = new AlberoBinarioImpl(figlio);
		padre.destro = null;
		return newalbero;
	}	
		
	//Stacca e restituisce il sottoalbero radicato in un certo
	// NodoBinario "rimosso" dell'albero
	public AlberoBinario pota(NodoBinario rimosso){
		if(rimosso == null) return new AlberoBinarioImpl(); //l'albero vuoto 
		if(rimosso.padre==null){ //"rimosso" � la radice dell'albero
			radice = null;
			return new AlberoBinarioImpl(rimosso); //restituiamo l'intero albero
		}
		NodoBinario pad = rimosso.padre;
		if(figlioSinistro(rimosso)){ //se "rimosso" � figlio sinistro 
			AlberoBinario newalbero = potaSinistro(pad);
			return newalbero;						
		}
		else{ ////"rimosso" � figlio destro 
			AlberoBinario newalbero = potaDestro(pad);
			return newalbero;									
		}
	}	
	
	
	//Restituisce la lista degli elementi in una visita DFS (iterativa)
	public List visitaDFS(){
		List output = new LinkedList();//lista di elementi in output
		Stack<NodoBinario> pila = new Stack<NodoBinario>(); //Classe generica Stack<T>
		if(radice!=null) pila.push(radice);
		while(!pila.isEmpty()){
			NodoBinario u = pila.pop();//Richiederebbe il casting se usassimo la classe Stack non generica
			output.add(u.elem);//mettiamo in output corrente
			//inseriamo in pila il figlio destro e poi quello sinistro
			if(u.destro!=null) pila.push(u.destro);
			if(u.sinistro!=null) pila.push(u.sinistro);
		}
		return output;
	}
	
	//Restituisce la lista degli elementi in una visita BFS
	public List visitaBFS(){
		List output = new LinkedList();
		Coda coda = new CodaCollegata();
		if(radice!=null) coda.enqueue(radice);
		while(!coda.isEmpty()){
			NodoBinario u = (NodoBinario) coda.dequeue();
			output.add(u.elem); //Il metodo add di LinkedList aggiunge il nuovo elemnto alla fine
			//Inseriamo in coda il figlio sinistro e poi quello destro
			if(u.sinistro!=null) coda.enqueue(u.sinistro);
			if(u.destro!=null) coda.enqueue(u.destro);			
		}
		return output;
	}

	@Override
	public int level(NodoBinario nodo) {
		if(nodo == null) return 0;
		if(nodo.padre == null) return 0;
		return level(nodo.padre) + 1;
	}

	@Override
	public int altezza() {
		return altezza(radice);
	}
	private int altezza(NodoBinario root, int altezza) {
		
		if(root == null) return altezza;
		
		int altSx = altezza;
		int altDx = altezza;
		if(root.destro != null) 
			altSx = altezza(root.destro, ++altSx);
		
		if(root.sinistro != null)
			altDx = altezza(root.sinistro, ++altDx);
		
		return Integer.max(altDx, altSx);
		
	}
	private int altezza(NodoBinario root) {
		if(root == null) return 0;
		if(root.destro == null && root.sinistro == null) return 0;
		return Integer.max(altezza(root.sinistro), altezza(root.destro)) + 1;
	}

	@Override
	public int numFoglie() {
		return numFoglie(radice);
	}
	private int numFoglie(NodoBinario root) {
		if(root == null) return 0;
		
		if(root.destro == null && root.sinistro == null) return 1;
		
		return numFoglie(root.sinistro) + numFoglie(root.destro);
	}

	@Override
	public int numNodiInterni() {
		return numNodiInterni(radice);
	}
	private int numNodiInterni(NodoBinario root) {
		if(root == null) return 0;
		
		if(root.destro != null || root.sinistro != null) {
			return 1 + numNodiInterni(root.destro) + numNodiInterni(root.sinistro);
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object anotherTree) {
		
		if(anotherTree == null || this == null) return false;
		if(!(anotherTree instanceof AlberoBinario)) return false;
		if(this == anotherTree) return true;
		
		AlberoBinario altro = (AlberoBinario)anotherTree;
		return equals(this.radice(), altro.radice());
	}
	private boolean equals(NodoBinario n1, NodoBinario n2) {
		
		//2 alberi vuoti sono uguali
		if(n1 == null && n2 == null) return true;
		
		if(n1 != null && n2 != null && n1.equals(n2) ) {
			return equals(n1.sinistro, n2.sinistro) && equals(n1.destro, n2.destro);
		}
		
		return false;
	}
	
	@Override
	public void eliminaFoglieUguali() {
		eliminaFoglieUguali(this.radice);
	}
	private NodoBinario eliminaFoglieUguali(NodoBinario root) {
		if (root == null) return null;
		
		if(this.grado(root) == 0) return root;
		
		NodoBinario fogliaSx = eliminaFoglieUguali(root.sinistro);
		NodoBinario fogliaDx = eliminaFoglieUguali(root.destro);
		if(fogliaSx != null && fogliaDx != null && fogliaDx.equals(fogliaSx))
			pota(fogliaDx);
		
		return null;
	}

	@Override
	public boolean search(Object elem) {
		return search(elem, radice);
	}
	private boolean search(Object elem, NodoBinario n) {		
		if(n == null) return false;
		
		if(n.equals(elem)) return true;
		
		return search(elem, n.sinistro) || search(elem, n.destro);
	}

	@Override
	public List<NodoBinario> nodiCardine() {
		return nodiCardine(this.radice, new LinkedList<NodoBinario>());
	}
	private List<NodoBinario> nodiCardine(NodoBinario n, List<NodoBinario> cardine) {
		if(n == null) return cardine;
		if(this.level(n) == this.altezza(n, 0)) cardine.add(n);
		
		if(n.destro != null) nodiCardine(n.destro, cardine);
		if(n.sinistro != null) nodiCardine(n.sinistro, cardine);
		
		return cardine;
	}
	
}
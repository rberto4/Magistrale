package albero_binario;
import java.util.List; //Per l'output delle visite
import java.util.Queue;
import java.util.LinkedList; //Per l'output delle visite

import code.*; //Per la visita BFS (come esempio di struttura dati propria)
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
	
	// restituisce il numero di figli di un nodo
	public int grado(NodoBinario v) {
		int grado = 0;
		if (v == null) return -1;
		if (((NodoBinario) v).sinistro != null) grado++;
		if (((NodoBinario) v).destro != null) grado++;
		return grado;
	}
	
	// restituisce il livello dell'albero
	public int level() {
		if( radice ==null) return -1;
		return level(radice);
	};
	
	//restituisce il livello di un nodo = distanza dalla radice
	public int level(NodoBinario u) {
		if (u.padre==null)
			return 0;
		return level (u.padre) +1;
	};			
	
	// restituisce l'altezza dell'albero
	public int altezza() {
		if( radice == null) 
			{return 0;}
		else {
			return altezza(radice);
		}
	};
	
	//restituisce l'altezza di un nodo = distanza max dalle foglie
	private int altezza(NodoBinario u) {
		Integer  hsx, hdx;
		hsx= 0;
		hdx=0;
		if (u.sinistro==null && u.destro==null) { //sono in una foglia
			return 0;}
		else if(u.sinistro!=null) {
			hsx= altezza(u.sinistro);}
		else if (u.destro!=null) {
			hdx = altezza(u.destro);}
		if (hsx>hdx) {
			return 1+hsx;}
		else {
			return 1+hdx;}
		
	};	
	
	//restituisce il numero di nodi interni dell'albero
	public int numNodiInterni() {
		if (radice==null) return 0;
		return numNodiInterni(radice);
		
	};
	
	//restituisce il numero di nodi interni di un nodo
	private int numNodiInterni(NodoBinario u) {
		Integer nsx, ndx;
		nsx = 0;
		ndx = 0;
		if (u.sinistro!=null)
			nsx = numNodiInterni(u.sinistro);
		if (u.destro!=null)
			ndx = numNodiInterni(u.destro);
		return 1+nsx+ndx; //aggiungo nel conto anche u
	}
	
	//restituisce il numero di foglie dell'albero
	public int numFoglie() {
		if (radice==null) return 0;
		return numFoglie(radice);
		
	};
	
	//restituisce il numero di foglie di un nodo
	private int numFoglie(NodoBinario u) {
		Integer nsx, ndx;
		nsx = 0;
		ndx = 0;
		if (u.sinistro ==null && u.destro==null)
			{return 1;}
		if (u.sinistro!=null)
			{nsx = numFoglie(u.sinistro);}
		if (u.destro!=null)
			{ndx = numFoglie(u.destro);}
		return nsx+ndx;
	}
	
	//restituisce true se l'albero è identico ad un altro
	public boolean equals(NodoBinario v) {
		boolean sx,dx;
		sx = false;
		dx = false;
		if ((radice==null && v!=null) ||
		    (radice!=null && v== null))
			{return false;} //uno dei due alberi è vuoto
		if (radice==null && v==null)
			{return true;}  //entrambi gli alberi sono vuoti
		if (( info(radice) != info(v) )||
			(radice.sinistro != null) != (v.sinistro != null) || 
			(radice.destro != null)   != (v.destro != null)
			)	
			{return false;} //sono diversi
		else if ( info(radice) == info(v) &&
				(radice.sinistro== null && v.sinistro ==null) ||
				(radice.destro == null && v.destro== null)
				)
			{return true;} //sono due foglie uguali
		else if (radice.sinistro!=null)
			{sx = equalsNodo (radice.sinistro, v.sinistro);}
		else if (radice.destro !=null) 
			{dx= equalsNodo (radice.destro, v.destro);}
		return sx && dx;
	}
	
	private boolean equalsNodo (NodoBinario u, NodoBinario v){
		boolean sx,dx;
		sx = false;
		dx = false;
		if ((u==null && v!=null) ||
		    (u!=null && v== null))
			{return false;} //uno dei due alberi è vuoto
		if (u==null && v==null)
			{return true;}  //entrambi gli alberi sono vuoti
		if (info(u) != info(v) ||
			(u.sinistro != null) != (v.sinistro != null) || 
			(u.destro != null)   != (v.destro != null)
			)	
			{return false;} //sono diversi
		else if (( info(u) == info(v)) &&
				(u.sinistro== null && v.sinistro ==null) ||
				(u.destro == null && v.destro== null)
				)
			{return true;} //sono due foglie uguali
		else if (u.sinistro!=null)
			{sx = equalsNodo (u.sinistro, v.sinistro);}
		else if (u.destro !=null) 
			{dx= equalsNodo (u.destro, v.destro);}
		return sx && dx;
	}
			
	public void eliminaFoglieUguali() {
		Object sx,dx;
		Integer nfoglie;
		nfoglie= 0;
		sx=null;
		dx=null;
		if (radice==null) return;
		if (radice.sinistro != null) {
			if (radice.sinistro.sinistro!= null ||
				radice.sinistro.destro!= null) // radice.sinistro non è una foglia
					{eliminaFoglieUguali(radice.sinistro);}
			else // è una foglia
				{sx = info(radice.sinistro);
				nfoglie ++;}
		}
		if (radice.destro != null) {
			if (radice.destro.sinistro!= null ||
				radice.destro.destro!= null) // radice.destro non è una foglia
					{eliminaFoglieUguali(radice.destro);}
			else // è una foglia
				{dx = info(radice.destro);
				nfoglie ++;}
		}				
		if (nfoglie ==2 && sx==dx)
			{radice.sinistro = null;
			 radice.destro = null;
		}
		return;
	}
	
	private void eliminaFoglieUguali (NodoBinario u) {
		Object sx,dx;
		Integer nfoglie;
		nfoglie= 0;
		sx=null;
		dx=null;
		if (u==null) return;
		if (u.sinistro != null) {
			if (u.sinistro.sinistro!= null ||
				u.sinistro.destro!= null) // u.sinistro non è una foglia
					{eliminaFoglieUguali(u.sinistro);}
			else // è una foglia
				{sx = info(u.sinistro);
				nfoglie ++;}
		}
		if (u.destro != null) {
			if (u.destro.sinistro!= null ||
				u.destro.destro!= null) // u.destro non è una foglia
					{eliminaFoglieUguali(u.destro);}
			else // è una foglia
				{dx = info(u.destro);
				nfoglie ++;}
		}				
		if (nfoglie ==2 && sx==dx)
			{u.sinistro = null;
			 u.destro = null;
		}
		return;
	}
		
	public boolean search(Object o) {
		boolean sx, dx;
		sx= false;
		dx=false;
		if (radice ==null) return false;
		if (info(radice) ==o) return true;
		if (radice.sinistro != null) sx=search(o, radice.sinistro);
		if (radice.destro != null) dx=search(o, radice.destro);
		return sx || dx;
	}

	private boolean search(Object o, NodoBinario u) {
		boolean sx, dx;
		sx= false;
		dx=false;
		if (u ==null) return false;
		if (info(u) ==o) return true;
		if (u.sinistro != null) sx=search(o, u.sinistro);
		if (u.destro != null) dx=search(o, u.destro);
		return sx || dx;
	}
	
	LinkedList<Object> listaNodiCardine; //variabile globale
	
	public List nodiCardine() { //rende la lista dei nodi con livello = altezza
    	listaNodiCardine = new LinkedList<Object> ();  
		if (radice == null) return listaNodiCardine;
		if (radice.sinistro==null && radice.destro==null) {
			listaNodiCardine.add(radice); //ho solo la radice h = p = 0
			return listaNodiCardine;
		}
		if (radice.sinistro!=null) nodiCardine(radice.sinistro);
		if (radice.destro!=null) nodiCardine (radice.destro);
		return listaNodiCardine;		
	};
	
	private void nodiCardine(NodoBinario u) {
		if (level(u) == altezza(u)){
			listaNodiCardine.add(u);
		}
		if (u.sinistro!=null) nodiCardine(u.sinistro);
		if (u.destro!=null) nodiCardine (u.destro);
		return;
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

	
}


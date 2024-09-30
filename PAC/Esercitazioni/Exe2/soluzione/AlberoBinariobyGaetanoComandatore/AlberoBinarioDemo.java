package albero_binario;
import java.util.LinkedList;
import java.util.Queue;


public class AlberoBinarioDemo{
	public static void main(String argc[]){
		System.out.println("alb1=nodo1=1");
		AlberoBinario alb1 = new AlberoBinarioImpl(1);
		NodoBinario nodo1 = alb1.radice();

		System.out.println("alb2=nodo2=2");
		AlberoBinario alb2 = new AlberoBinarioImpl(2);
		NodoBinario nodo2 = alb2.radice();

		System.out.println("alb3=nodo3=3");
		AlberoBinario alb3 = new AlberoBinarioImpl(3);
		NodoBinario nodo3 = alb3.radice();

		System.out.println("alb4=nodo4=4");
		AlberoBinario alb4 = new AlberoBinarioImpl(4);
		NodoBinario nodo4 = alb4.radice();

		System.out.println("alb5=nodo5=5");
		AlberoBinario alb5 = new AlberoBinarioImpl(5);
		NodoBinario nodo5 = alb5.radice();

		System.out.println("alb6=nodo6=6");
		AlberoBinario alb6 = new AlberoBinarioImpl(6);
		NodoBinario nodo6 = alb6.radice();


		System.out.println("alb1.innestaDes(nodo1,alb3)"); alb1.innestaDes(nodo1,alb3);
		System.out.println("alb1.innestaSin(nodo1,alb2)"); alb1.innestaSin(nodo1,alb2);
		System.out.println("alb1.innestaDes(nodo3,alb4)"); alb1.innestaDes(nodo3,alb4);
		System.out.println("alb1.innestaSin(nodo2,alb5)"); alb1.innestaSin(nodo2,alb5);
		System.out.println("alb1.innestaDes(nodo2,alb6)"); alb1.innestaDes(nodo2,alb6);
		
			
		System.out.println("alb1.visitaDFS()"); 
		LinkedList visita = (LinkedList) alb1.visitaDFS();
		System.out.println(visita.toString());

		System.out.println("alb1.visitaBFS()"); 
		visita = (LinkedList) alb1.visitaBFS();
        System.out.println(visita.toString());

        /*
		System.out.println("alb8=alb1.potaSinistro(nodo1)"); 
		AlberoBinario alb8 = alb1.potaSinistro(nodo1);
		System.out.println("alb1.visitaDFS()"); 
		visita = (LinkedList) alb1.visitaDFS();
		System.out.println(visita.toString());
		System.out.println("alb8.visitaDFS()"); 
		visita = (LinkedList) alb8.visitaDFS();
		System.out.println(visita.toString());
		*/
        
		Integer h;
		h = alb1.altezza();
		System.out.println("altezza = " + h);
		h= alb1.numNodiInterni();
		System.out.println("num nodi interni = " + h);
		h= alb1.numFoglie();		
		System.out.println("num foglie = " + h);
		boolean test;
		test = alb1.equals(alb6);
		System.out.println("sono uguale a me = " + test);
		alb1.eliminaFoglieUguali();
		System.out.println("num foglie = " + alb1.numFoglie());
		System.out.println("cerco 3 = " + alb1.search(3));
		System.out.println("cerco 10 = " + alb1.search(10));
		
    	Queue<NodoBinario> myList = new LinkedList<NodoBinario> (); 
		myList =(Queue<NodoBinario>)alb1.nodiCardine(); //devo fare il cast
		while (!myList.isEmpty()){
			NodoBinario n;
			n=myList.poll();
			System.out.println("Nodo Cardinale: " + n.elem.toString());
			
		}
	}	
}
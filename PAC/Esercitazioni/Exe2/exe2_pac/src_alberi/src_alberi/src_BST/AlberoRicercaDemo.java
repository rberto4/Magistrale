import java.util.LinkedList;


public class AlberoRicercaDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AlberoBR diz = new AlberoBR();
		
		System.out.println("insert(12,6)");	diz.insert(12,6);
			

		System.out.println("insert(8,4)");	diz.insert(8,4);
		
		System.out.println("insert(6,3)");	diz.insert(6,3);
		
		System.out.println("insert(4,2)");	diz.insert(4,2);
		
		System.out.println("insert(2,1)");	diz.insert(2,1);
		
		System.out.println("insert(10,5)");	diz.insert(10,5);
		
		System.out.println("insert(14,7)");	diz.insert(14,7);
		
		LinkedList visita = (LinkedList) diz.inorder();
		for (Object r : visita){  
		System.out.print(((Record) r ).toString());
		}
		System.out.println();
		
		System.out.println("search(5)="+diz.search(5));
		System.out.println("search(3)="+diz.search(3));
		System.out.println("search(6)="+diz.search(6));
		System.out.println("search(8)="+diz.search(8));
		System.out.println();


		System.out.println("delete(6)"); diz.delete(6);
		visita = (LinkedList) diz.inorder();
		for (Object r : visita){  
		System.out.print(((Record) r ).toString());	}			
		System.out.println();
		
        System.out.println("delete(3)"); diz.delete(3);
        visita = (LinkedList) diz.inorder();
		for (Object r : visita){  
		System.out.print(((Record) r ).toString());	}			
		System.out.println();
						
		
        System.out.println("delete(1)"); diz.delete(1);
        visita = (LinkedList) diz.inorder();
		for (Object r : visita){  
		System.out.print(((Record) r ).toString());	}			
		System.out.println();				
		
        System.out.println("delete(8)"); diz.delete(8);
        visita = (LinkedList) diz.inorder();
		for (Object r : visita){  
		System.out.print(((Record) r ).toString());	}			
		System.out.println();			
		
	}

}

package exe_jgraph;
import exe_jgraph.GrafoFb;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GrafoFb fb = GrafoFb();
		
		 fb.grafo.addVertex("a");
		 fb.grafo.addVertex("b");
	        fb.grafo.addVertex("c");
	      
	        fb.grafo.addEdge("a", "b");
	        fb.grafo.addEdge("b", "d");
	        fb.grafo.addEdge("d", "c");
	       

	}

}

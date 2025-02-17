import java.util.ArrayList;
import java.util.List;

public class Coda <T extends Number> {
   private List<T> array;
   public Coda() {
       this.array = new ArrayList<T>();
   }
   public void insert(T t) {
       array.add(t);
   }
   public void stampa(){
       System.out.print("Coda : ");
       for (T t: array) {
           System.out.print(t+" ");
       }
   }
 }

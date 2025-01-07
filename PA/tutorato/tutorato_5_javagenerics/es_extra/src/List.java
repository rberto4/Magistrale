public class List {
    private Object[] array;
    private int len;

    public List (Object[] a){
        this.array = a;
        this.len = a.length;
    }

    public List concatena(List l){
        Object[] a = new Object[len+l.len];
   
        for (int i = 0; i<len; i++){
            a[i] = array[i];
        }
        for (int i = 0; i<l.len; i++){
            a[i + l.len] = l.array[i];
        }
        
        return new List(a);
    }
    
    public void print(List l){
        System.out.println("Array : ");
        for (int i = 0; i<l.len; i++){
            System.out.print(l.array[i] + " ");
        }
    }

    public void cancella (List l){
        l.array = null;
    }
}

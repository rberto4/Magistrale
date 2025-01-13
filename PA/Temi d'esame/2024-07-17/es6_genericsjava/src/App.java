public class App {
    public static void main(String[] args) throws Exception {
    
        Integer[] a1 = {1,4,2,6,7,6,1};
        Integer[] a2 ={10,11,12,12};

        MySet s1 = new MySet<>(a1);
        MySet s2 = new MySet<>(a2);

        s1.printSet();
        s2.printSet();

        s1.unionSet(s1,s2);
        s1.printSet();
}
}

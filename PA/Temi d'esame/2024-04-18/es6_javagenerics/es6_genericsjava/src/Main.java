public class Main {
    public static void main(String[] args) {

        // OrdereredSet generics, accetta tutti gli objects

        Integer [] a1 = {1,7,3,4,5,5,1};
        OrderedSet os1 = new OrderedSet(a1);
        os1.printSet();

        System.out.println(" \n--------------------------------------- ");

        CharSequence [] a2 = {"a","q","b","b","z","a","f"};
        OrderedSet os2 = new OrderedSet(a2);
        os2.printSet();

        System.out.println(" \n--------------------------------------- ");
        os1.orderObjects();
        os1.printSet();

        System.out.println();
        os2.orderObjects();
        os2.printSet();
    }
}
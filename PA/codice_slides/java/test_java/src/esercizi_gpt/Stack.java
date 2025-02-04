package esercizi_gpt;
import java.util.ArrayList;
/**
 * 🔴 Esercizio 3 - Stack generico con Linked List
*  Implementa una classe generica Stack<T> basata su una lista concatenata. Implementa i metodi push(), pop(), peek(), isEmpty().
🔹 Obiettivo: Applicare generics a una struttura dati dinamica.
 */
public class Stack<T> {
    private ArrayList<T> elements;

    public Stack(){
        elements = new ArrayList<>();
    }
    public void push(T item){
        elements.add(item);
    }
    public void pop(){
        if(elements.isEmpty()){
            throw new IllegalStateException("Stack vuoto!");
        }
        elements.remove(elements.size() - 1);
    }
    public T peek(){
        if(elements.isEmpty()){
            throw new IllegalStateException("Stack vuoto!");
        }
        return elements.get(elements.size() - 1);
    }
    public void getStuck (){
        for (T elem : elements) {
            System.out.println(elem);
        }
    }
    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack: " + stack); // Output: [10, 20, 30]
        stack.getStuck(); // Output: 30
        stack.pop();
        System.out.println("Stack dopo pop(): " + stack); // Output: [10, 20]
        System.out.println("isEmpty(): " + stack.isEmpty()); // Output: false
    }
}

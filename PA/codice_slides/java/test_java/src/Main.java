public class Main
{
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<>();
		stack.push(10);
		stack.push(20);
		stack.push(30);

		System.out.println("Stack: " + stack); // Output: Stack: [10, 20, 30]
		System.out.println("Peek: " + stack.peek()); // Output: Peek: 30
		System.out.println("Pop: " + stack.pop()); // Output: Pop: 30
		System.out.println("Stack dopo pop: " + stack); // Output: Stack: [10, 20]
		
		Stack<String> stack_string = new Stack<>();
		stack_string.push("Ciao");
		stack_string.push("come");
		stack_string.push("va?");
		System.out.println(stack_string.toString());


	}
}

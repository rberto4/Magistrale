package Es3;

public interface PilaInterface {

	// returrns true is the stack is empty
	<T> boolean isEmpty();

	// Add elem ad last element
	<T> void push(T elem);

	// Pop and return the last element
	<T> T pop();

	// Return the last element
	<T> T top();

}

package ordinamenti_array;

public class algoritmi implements interface_algoritmi {

	@Override
	public int[] mergesort(int[]array) {
		
		  if (array.length <= 1) {
	            return array;
	        }

	        // Dividere l'array in due metà
	        int mid = array.length / 2;
	        int[] leftHalf = mergeSort(java.util.Arrays.copyOfRange(array, 0, mid));
	        int[] rightHalf = mergeSort(java.util.Arrays.copyOfRange(array, mid, array.length));

	}	
	
}

package white.pages.coding.challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ArrayHopper {
	
	/**
	 * This function counts the number of hops from each array index and returns value as an integer along with caching those values 
	 * for subsequent calls.
	 * @param inputArray -> non-negative array of integers read from file.
	 * @param arrayIndex -> the current index of the recursive call
	 * @param cache      -> an array the same size as input array that caches return values from recursive calls.
	 * @returns an int value corresponding to the minimum number of hops required to traverse the array from given index.
	 */
	public int arrayHopper(Integer[] inputArray, int arrayIndex, Integer[] cache){
		//Setting minimum number of Hops from each index as Integer.MAX_VALUE-2
		int minHops = Integer.MAX_VALUE-2;
		
		//Edge Case1. Reached end of array or beyond.
		if(arrayIndex >= inputArray.length){
			return 0;
		}
		
		//Edge Case2. Array Value is 0, no path from this index possible.
		if(inputArray[arrayIndex] == 0){
			return -1;
		}
		
		//If that index has already been queried before, directly get value from cache.
		int hops;
		if(cache[arrayIndex]>-2){
			hops = cache[arrayIndex];
		}
		

		int maxRecursiveCallsAtIndex = inputArray[arrayIndex];
		int iter= arrayIndex+1;
		
		//A boolean flag to check whether an index has been visited or not. 
		//(To handle case when function at a particular index returns -1.)
		boolean found = false;
		while(iter <= maxRecursiveCallsAtIndex+arrayIndex){
			hops = arrayHopper(inputArray, iter, cache);
			if(hops>-1 && hops < minHops+1){
				found = true;
				minHops = hops+1;
			}
			iter++;
		}
		
		//Put in cache
		if(arrayIndex<inputArray.length && found){
			cache[arrayIndex] = minHops;	
		}
		
		//put in cache if function returns -1.
		if (!found) {
			cache[arrayIndex] = -1;
			return -1;
		}

		return minHops;
	}
	
	/**
	 * This function takes in a file path in String format and returns an array initialized with values within the file.
	 * @param filePath
	 * @returns Integer array.
	 * @throws IOException
	 */
	public Integer[] initArrayFromFile(String filePath) throws IOException{
		try{
			FileReader filereader = new FileReader(new File(filePath));
			BufferedReader bufferedReader = new BufferedReader(filereader);
			List<Integer> inputList = new ArrayList<>();
			String inputRead = null;
			while((inputRead = bufferedReader.readLine())!=null){
				inputList.add(Integer.parseInt(inputRead));
			}
			bufferedReader.close();
			return  inputList.toArray(new Integer[inputList.size()]);
		}
		catch(IOException ex){
			throw new FileNotFoundException(String.format("No file found at specified path: ", filePath));
		}
	}
	
	/**
	 * Helper Function to initialize cache with all values -2 and size equal to the input array. 
	 * @param input
	 * @returns an Integer array
	 */
	public Integer[] fillCache(Integer[] input){
		Integer[] cache = new Integer[input.length];
		for(int index =0; index<input.length; index++){
			cache[index] = -2;
		}		return cache;
	}
	public void printArray(Integer[] array){
		int length = array.length;
		for(int index = 0 ; index<length; index++){
			System.out.println(array[index]);
		}
	}
	
	public static void main(String args[]) throws IOException{	
		ArrayHopper hopper = new ArrayHopper();
		Integer[] inputArray = hopper.initArrayFromFile(args[0]);
		Integer[] cache = hopper.fillCache(inputArray);
		hopper.arrayHopper(inputArray, 0, cache);
		hopper.printArray(cache);
	}
}

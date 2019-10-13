package exercise3;

import assignment2AADS.assignment2.A2Measure;

public class MyMeasure implements A2Measure {

  @Override
  public boolean isSameCollection(int[] array1, int[] array2) {
    if (array1.length != array2.length) {
      return false;
    }

    array1 = mergeSort(array1);
    array2 = mergeSort(array2);

    for (int i = 0; i < array1.length; i ++) {
      if(array1[i] != array2[i]) {
        return false;
      }
    }
    return true;
  }
  
  @Override
  public int minDifferences(int[] array1, int[] array2) {
    if (array1.length != array2.length) {
      throw new IllegalArgumentException("Arrays are not of equals length");
    }
 
    array1 = mergeSort(array1);
    array2 = mergeSort(array2);
    
    int difference = 0;
    for (int i = 0; i < array1.length; i++) {
      difference += Math.pow(array1[i] - array2[i], 2);
    }
    return difference;
  }

  @Override
  public int[] getPercentileRange(int[] arr, int lower, int upper) {
    if (lower > upper || lower < 0 || lower > 100 || upper < 0 || upper > 100) {
      throw new IllegalArgumentException("lower must be >= upper and both must be a value between 0-100");
    }

    arr = mergeSort(arr);
    int highestValue = arr[arr.length - 1];
    int lowestValue = arr[0];
    double ratio = 100 / (highestValue - lowestValue);

    double lowerBound = Math.floor(lower / ratio);
    double upperBound = Math.ceil(upper / ratio);

    int[] filteredArray = new int[100];
    int sz = 0;
    // TODO better search (filter) for lower and upper bounds
    // System.out.println("params (" + lower + ", " + upper + ") - filtering array to include ints > " + lowerBound + " and <= " + upperBound);
    for(int i = 0; i < arr.length; i++) {
      if (arr[i] > lowerBound && arr[i] <= upperBound) {
        filteredArray[sz] = arr[i];
        sz ++;
      }
    }
    int[] trimmedFilteredArray = new int[sz];
    for(int i = 0; i < sz; i ++) {
      trimmedFilteredArray[i] = filteredArray[i];
    }

    return trimmedFilteredArray;
  }

  private int[] mergeSort (int[] in) {
    //Splitting the array in two parts. first will be equal or 1 unit smaller than second
    int[] sorted = new int[in.length];
    int[] first = new int[in.length / 2];
    int[] second = new int[in.length - first.length];

    for(int i = 0; i < first.length; i ++)
      first[i] = in[i];
    for(int i = 0; i < second.length; i ++)
      second[i] = in[first.length + i];

    // Calls itself recursively if the array is at least 2 units long, making sure the array is split down to the smallest fragment before starting to sort
    if(first.length > 1)
      first = mergeSort(first);
    if(second.length > 1)
      second = mergeSort(second);

    // Sorting
    int f = 0, s = 0;
    while(f < first.length || s < second.length) {
      if(f == first.length) 				// If first array is depleted, add all elements from second
        sorted[f + s] = second[s ++];
      else if(s == second.length) 		// Vice versa
        sorted[f + s] = first[f ++];
      else if(first[f] < second[s]) 		// If element from first is smaller than element from second, add element from first and increase first compare index
        sorted[f + s] = first[f ++];
      else 								// Vice versa
        sorted[f + s] = second[s ++];
    }
    return sorted;
  } 
  
}
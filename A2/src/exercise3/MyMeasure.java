package exercise3;

import assignment2AADS.assignment2.A2Measure;
import tools.SimpleIntList;

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
      throw new IllegalArgumentException("The arrays are not of equal length");
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
    System.out.println(highestValue);
    int lowestValue = arr[0];
    double ratio = 100 / (highestValue - lowestValue);

    double lowerBound = Math.floor(lower / ratio);
    double upperBound = Math.ceil(upper / ratio);
    
    SimpleIntList filteredList = new SimpleIntList();
    for(int i = 0; i < arr.length; i++) {
      if (arr[i] > lowerBound && arr[i] <= upperBound) {
        filteredList.add(arr[i]);
      }
    }

    return filteredList.toArray();
  }

  private int[] mergeSort (int[] in) {
    // Splitting the array in two parts. first will be equal or 1 unit smaller than second
    int[] sortedArr = new int[in.length];
    int[] firstHalf = new int[in.length / 2];
    int[] secondHalf = new int[in.length - firstHalf.length];

    for(int i = 0; i < firstHalf.length; i ++)
      firstHalf[i] = in[i];
    for(int i = 0; i < secondHalf.length; i ++)
      secondHalf[i] = in[firstHalf.length + i];

    // Calls itself recursively if the array is at least 2 units long, making sure the array is split down to the smallest fragment before starting to sort
    if(firstHalf.length > 1)
      firstHalf = mergeSort(firstHalf);
    if(secondHalf.length > 1)
      secondHalf = mergeSort(secondHalf);

    // Sorting
    int firstPos = 0, secondPos = 0;
    while(firstPos < firstHalf.length || secondPos < secondHalf.length) {
      if(firstPos == firstHalf.length)           // If first array is depleted, add all elements from second
        sortedArr[firstPos + secondPos] = secondHalf[secondPos ++];
      else if(secondPos == secondHalf.length) 		// Vice versa
        sortedArr[firstPos + secondPos] = firstHalf[firstPos ++];
      else if(firstHalf[firstPos] < secondHalf[secondPos]) 	// If element from first is smaller than element from second, add element from first and increase first compare index
        sortedArr[firstPos + secondPos] = firstHalf[firstPos ++];
      else 								            // Vice versa
        sortedArr[firstPos + secondPos] = secondHalf[secondPos ++];
    }
    return sortedArr;
  }
  
}
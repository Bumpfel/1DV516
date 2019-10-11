package assignment2AADS.assignment2;

public class FindPrime {

  public static void main(String[] args) {

    long timestamp = System.currentTimeMillis();
    // int prime = 1; //, i = 0;
    // while(i < 1000) {
    // for(int i = 0; i < 10; i++) {
    //   prime = findNextPrime(prime + 1);
    //   System.out.println(prime);
    // }
    printPrimeSieve(20);
    long timeTaken = System.currentTimeMillis() - timestamp;
    // System.out.println("----time taken: " + timeTaken + "ms");

  }

  private static void printPrimeSieve(int n) {
    boolean[] prime = new boolean[n + 1];
    for(int i = 0; i < n; i ++) { //make list
      prime[i] = true;
    }

    for(int p = 2; p * p <= n; p ++) {
      if(prime[p]) {
        for(int i = p * 2; i <= n; i += p) {
          prime[i] = false;
        }
      }
    }

    for (int i = 2; i <= n; i++) {
      if (prime[i])
        System.out.println(i);
    }
  }

  private static int findNextPrime(int n) {
    if (isPrime(n)) {
      return n;
    }
    return findNextPrime(n + 1);
  }
 
  private static boolean isPrime(int n) {
    for(int i = 2; i < n; i++) {
      if(n % i == 0) {
        return false;
      }
    }
    return true;
  }


}
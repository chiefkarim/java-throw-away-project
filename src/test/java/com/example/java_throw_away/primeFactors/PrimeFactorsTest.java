package com.example.java_throw_away.primeFactors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PrimeFactorsTest {
  private List<Integer> list(Integer... ints) {
    return Arrays.asList(ints);
  }

  private void assertPrimeFactors(List<Integer> primeFactors, int number) {
    assertEquals(primeFactors, of(number));
  }

  @Test
  public void canFactorIntoPrimes() throws Exception {
    assertPrimeFactors(list(), 1);
    assertPrimeFactors(list(2), 2);
    assertPrimeFactors(list(3), 3);
    assertPrimeFactors(list(2, 2), 4);
    assertPrimeFactors(list(5), 5);
    assertPrimeFactors(list(2, 3), 6);
    assertPrimeFactors(list(7), 7);
    assertPrimeFactors(list(2, 2, 2), 8);
    assertPrimeFactors(list(3, 3), 9);
    assertPrimeFactors(list(2 , 2 ,  3 , 5 , 7 , 7 ,11 , 13), 2 *  2 * 3 * 5 * 7 * 7 * 11 * 13);
  }

  private List<Integer> of(int n) {
    ArrayList<Integer> factors = new ArrayList<Integer>();
    for (int deviser = 2; n > 1; deviser++)
      for (; n % deviser == 0; n /= deviser)
        factors.add(deviser);
    return factors;
  }
}

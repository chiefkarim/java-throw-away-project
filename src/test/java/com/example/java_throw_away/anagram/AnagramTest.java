package com.example.java_throw_away.anagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AnagramTest {
  private String[] wordList = { "dotting", "document","car", "house", "menu", "dogmatic", "anotation", "ilustrative","gin","nig" };

  @Test
  public void shouldReturnEmptyString_WhenGivenNull() throws Exception {
    assertEquals("", anagram(null));
  }

  @Test
  public void shouldReturnEmptyString_WhenGivenEmptyString() throws Exception {
    assertEquals("", anagram(""));
  }

  @Test
  public void shouldReturTheGivenWord_WhenNoMatchFound() throws Exception {
    assertEquals("plane", anagram("plane"));
  }

  @Test
  public void sizeOfTheReturnedTowWrodsCombinedShouldBeEqualToTheProvidedWord_WhenGivenOneWord()
      throws Exception {
    String[] testAnagram = anagram("documenting").split(" ");
    assertEquals(11, testAnagram[0].length() + testAnagram[1].length());
  }

  @Test
  public void shouldReturnTwoWordAnagram_WhenGivenOneWord() {
    String testAnagram = anagram("documenting");
    assertTrue(testAnagram.equals("document gin") || testAnagram.equals("document nig"));
  }

  private String anagram(String word) {
    if (word == null)
      return "";
    if (word.length() >= 1) {
      return getTwoWordsAnagram(word, wordList);
    }
    return "";
  }

  private String getTwoWordsAnagram(String word, String[] wordList) {
    int counter = 0;

    while (counter < wordList.length - 1) {
      for (int i = 0; i < wordList.length - 1; i++) {
        if (isSumOfTwoWordsGreaterThanOriginalWord(wordList[counter], wordList[i], word)) {
          String anagram = wordList[counter] + wordList[i];
          boolean correctAnagram = true;
          for (int j = 0; j < word.length()-1; j++) {
            if (!anagram.contains("" + word.charAt(j))) {
              correctAnagram = false;
              break;
            }
          }
          if (correctAnagram)
            return wordList[counter] + " " + wordList[i];
        }
      }
      counter++;
    }
    return word;
  }

  private boolean isSumOfTwoWordsGreaterThanOriginalWord(String firstWord, String secondWord, String originalWord) {
    return firstWord.length() + secondWord.length() == originalWord.length();
  }

}

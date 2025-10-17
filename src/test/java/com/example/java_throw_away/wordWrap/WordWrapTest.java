package com.example.java_throw_away.wordWrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordWrapTest {

  @Test
  public void returnEmptyStringWhenPassed_Null() throws Exception {
    assertEquals("", wrap(null, 10));
    assertEquals("", wrap("", 10));
    assertEquals("hello\n", wrap("hello ", 6));
    assertEquals("hello\n ", wrap("hello  ", 6));
    assertEquals("hello\n kari", wrap("hello  kari", 6));
    assertEquals("hello\n kari\nwhat", wrap("hello  kari what", 6));
    assertEquals("hello\n kari\nwhat \nis ", wrap("hello  kari what  is ", 6));
    assertEquals("hello karim \nwhat is your\nname?", wrap("hello karim  what is your name?", 13));
    assertEquals("hello karim\n what is your\nname?", wrap("hello karim  what is your name?", 12));
    assertEquals("hello\nkarim\n what\nis your\nname?", wrap("hello karim  what is your name?", 5));
  }

  private String wrap(String text, int width) {
    if (text == null)
      return "";
    List<String> chars = getChars(text);
    for (int index = width; chars.size() >= index; index += width) {
      while (chars.size() >= index && !chars.get(index - 1).equals(" "))
        index++;
      if (chars.size() >= index)
        chars.set(index - 1, "\n");
    }
    return getStringFromChars(chars);
  }

  private String getStringFromChars(List<String> chars) {
    String wrappedString = "";
    for (String ch : chars)
      wrappedString += ch;
    return wrappedString;
  }

  private ArrayList<String> getChars(String text) {
    return new ArrayList<String>(Arrays.asList(text.split("")));
  }

}

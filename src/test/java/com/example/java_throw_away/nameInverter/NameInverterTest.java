package com.example.java_throw_away.nameInverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NameInverterTest {
  private void assertInvertedNameEquals(String name, String invertedName) {
    assertEquals(invertedName, invertName(name));
  }

  @Test
  public void givenNull_returnEmptyString() throws Exception {
    assertInvertedNameEquals(null, "");
  }

  @Test
  public void givenEmptyString_returnEmptyString() throws Exception {
    assertInvertedNameEquals("", "");
  }

  @Test
  public void givenSimpleName_returnSimpleName() throws Exception {
    assertInvertedNameEquals("karim", "karim");
  }

  @Test
  public void givenFirstLast_returnLastFirst() throws Exception {
    assertInvertedNameEquals("First Last", "Last, First");
  }

  @Test
  public void givenSimpleNameWthSpaces_returnSimpleNameWithoutSpaces() throws Exception {
    assertInvertedNameEquals(" First ", "First");
  }

  @Test
  public void givenFirstLastWithExtraSpaces_returnFirstLastWithoutExtraSpaces() throws Exception {
    assertInvertedNameEquals(" First   Last", "Last, First");
  }

  private String invertName(String name) {
    if (isNameNullOrEmpty(name))
      return "";
    else {
      String names[] = name.trim().split("\\s+");
      if (names.length == 1)
        return names[0];
      else {
        return String.format("%s, %s", names[1], names[0]);
      }
    }
  }

  private boolean isNameNullOrEmpty(String name) {
    return name == null || name.length() <= 0;
  }

}

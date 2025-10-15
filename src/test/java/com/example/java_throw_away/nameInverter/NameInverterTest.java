package com.example.java_throw_away.nameInverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NameInverterTest {
  private NameInverter nameInverter;

  @BeforeEach
  public void setup() {
    nameInverter = new NameInverter();
  }

  private void assertInvertedNameEquals(String name, String invertedName) {
    assertEquals(invertedName, nameInverter.invertName(name));
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

  @Test
  public void ignoreHonorific() throws Exception {
    assertInvertedNameEquals("Mr. First Last", "Last, First");
    assertInvertedNameEquals("Mrs. First Last", "Last, First");
  }

  @Test
  public void postNominals_stayAtEnd() throws Exception {
    assertInvertedNameEquals("First Last Sr.", "Last, First Sr.");
    assertInvertedNameEquals("First Last BS. Phd.", "Last, First BS. Phd.");
  }

  @Test
  public void integration() throws Exception {
    assertInvertedNameEquals("   Karim Menna  III esq.    ", "Menna, Karim III esq.");
  }

}

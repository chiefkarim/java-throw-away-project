package com.example.java_throw_away.nameInverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  private String invertName(String name) {
    if (isNameNullOrEmpty(name))
      return "";
    else {
      List<String> names = splitNames(name);
      if (names.size() > 1 && isHonorific(names.get(0)))
        names.remove(0);
      if (isPostNominal(names)) {
        List<String> postNominals;
        postNominals = names.subList(2, names.size());
        String postNominal = "";
        for (String pn : postNominals)
          postNominal += pn + " ";
        return String.format("%s, %s %s", names.get(1), names.get(0), postNominal).trim();
      }
      if (names.size() == 1)
        return names.get(0);
      else {
        return String.format("%s, %s", names.get(1), names.get(0));
      }
    }
  }

  private boolean isPostNominal(List<String> names) {
    return names.size() > 2;
  }

  private boolean isHonorific(String word) {
    return word.matches("Mr\\.|Mrs\\.");
  }

  private List<String> splitNames(String name) {
    List<String> names = new ArrayList<String>(Arrays.asList(name.trim().split("\\s+")));
    return names;
  }

  private boolean isNameNullOrEmpty(String name) {
    return name == null || name.length() <= 0;
  }

}

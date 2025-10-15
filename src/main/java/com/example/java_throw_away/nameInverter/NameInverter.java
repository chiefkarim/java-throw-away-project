package com.example.java_throw_away.nameInverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameInverter {
  String invertName(String name) {
    if (isNameNullOrEmpty(name))
      return "";
    else
      return formatName(removeHonorific(splitNames(name)));
  }

  private String formatName(List<String> names) {
    if (names.size() == 1)
      return names.get(0);
    else {
      return formatMultiElementName(names);
    }
  }

  private String formatMultiElementName(List<String> names) {
    String postNominals = getPostNominals(names);
    String lastName = names.get(1);
    String firstName = names.get(0);
    return String.format("%s, %s %s", lastName, firstName, postNominals).trim();
  }

  private String getPostNominals(List<String> names) {
    String postNominalString = "";
    if (names.size() > 2) {
      List<String> postNominals = names.subList(2, names.size());
      for (String pn : postNominals)
        postNominalString += pn + " ";
    }
    return postNominalString;
  }

  private List<String> removeHonorific(List<String> names) {
    if (names.size() > 1 && isHonorific(names.get(0)))
      names.remove(0);
    return names;
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

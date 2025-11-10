package com.example.java_throw_away.argsParser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ArgsParserTest {

  @Test
  public void shouldThrowError_WhenPassedInSchemaWithoutTypes() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> parseArgs(Map.of(1, "karim")));
  }
//TODO:: switch to an object for the schema for extendability and testability
  private void parseArgs(Map<String,Map<String,Object>> Schema) {


  }

}

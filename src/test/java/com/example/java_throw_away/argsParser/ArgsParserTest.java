package com.example.java_throw_away.argsParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class ArgsParserTest {

  @Test
  public void schemaFlag_ShouldHaveTypeAndNumberPropertiesWithCorrectTypes() throws Exception {
    SchemaFlag exampleFlag = new SchemaFlag(String.class, 0);
    assertTrue(exampleFlag.number instanceof Integer);
    assertTrue(exampleFlag.type == String.class);
  }

  @Test
  public void schemaFlag_ShouldHaveZeroAsDefaultNumberValueAndStringAsDefaultType() throws Exception {
    SchemaFlag exampleFlag = new SchemaFlag(null, null);
    assertTrue(exampleFlag.number == 0);
    assertTrue(exampleFlag.type == String.class);
  }

  @Test
  public void schema_ShouldInitiateSchemaCorrectly() throws Exception {
    Schema exampleSechema = new Schema(Map.of("-c", new SchemaFlag(null, null), "-a", new SchemaFlag(String.class, 1),
        "-b", new SchemaFlag(Integer.class, 2)));
    assertEquals(String.class, exampleSechema.schema.get("-c").type);
    assertEquals(0, exampleSechema.schema.get("-c").number);
    assertEquals(String.class, exampleSechema.schema.get("-a").type);
    assertEquals(1, exampleSechema.schema.get("-a").number);
    assertEquals(Integer.class, exampleSechema.schema.get("-b").type);
    assertEquals(2, exampleSechema.schema.get("-b").number);
  }

  @Test
  public void schema_shouldThrowErrorIfSchemaKeyDoesntStartWithMinus() throws Exception {
    Map<String, SchemaFlag> InvalidSchema = Map.of("ch", new SchemaFlag(String.class, 2));
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Schema(InvalidSchema);
    });
    assertEquals("Flag name must start with minus sign.", exception.getMessage());
  }

  @Test
  public void schema_shouldThrowErrorIfSchemaKeyIsNotOneChar() throws Exception {
    Map<String, SchemaFlag> InvalidSchema = Map.of("-char", new SchemaFlag(String.class, 2));
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Schema(InvalidSchema);
    });
    assertEquals("Flag name must be one char long.", exception.getMessage());
  }

  class Schema {
    private Map<String, SchemaFlag> schema;

    Schema(Map<String, SchemaFlag> schema) {
      for (String key : schema.keySet()) {
        if (!key.startsWith("-")) {
          throw new IllegalArgumentException("Flag name must start with minus sign.");
        }
        if (key == null || key.length() != 2) {
          throw new IllegalArgumentException("Flag name must be one char long.");
        }

      }
      this.schema = schema;
    }
  }

  class SchemaFlag {
    private Class<?> type = String.class;
    private Integer number = 0;

    SchemaFlag(Class<?> type, Integer number) {
      if (type != null)
        this.type = type;
      if (number != null)
        this.number = number;
    }

  }

}

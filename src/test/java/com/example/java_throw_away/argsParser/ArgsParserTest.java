package com.example.java_throw_away.argsParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.format.number.money.CurrencyUnitFormatter;

import net.bytebuddy.asm.MemberSubstitution.Current;

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

  @Test
  public void schema_shouldThrowErrorIfSchemaKeyIsNotFromTheAlphabit() throws Exception {
    Map<String, SchemaFlag> InvalidSchema = Map.of("-1", new SchemaFlag(String.class, 2));
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Schema(InvalidSchema);
    });
    assertEquals("Flag name must be from the alphabit.", exception.getMessage());
  }

  @Test
  public void schemaParserShouldThrowError_WhenArgumentsDontMatchSchema() throws Exception {
    Schema schema = new Schema(Map.of("-l", new SchemaFlag(null, 2), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(String.class, 2)));
    List<String> invalidFlagArgs = List.of("-l", "2", "-l", "-d", "-a");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema, invalidFlagArgs);
    });
    assertEquals("Invalid flag -a.", exception.getMessage());
  }

  @Test
  public void schemaParserShouldNotThrowFlagError_WhenNegativeIntigerIsPassedAsArgumentValue() throws Exception {
    Schema schema = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(String.class, 2)));
    List<String> invalidFlagArgs = List.of("-l", "-2", "-d");
    assertDoesNotThrow(() -> {
      parseArgs(schema, invalidFlagArgs);
    }, "Shouldn't throw flag error when negative number is passed as argument value");
  }

  @Test
  public void schemaParserShouldThrowError_WhenFlagHasMoreThanOneValue() throws Exception {
    Schema schema = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(String.class, 2)));
    List<String> invalidFlagArgs = List.of("-l", "-2", "2", "-d");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema, invalidFlagArgs);
    });
    assertEquals("Each flag should Have One Value Associated with it.",
        exception.getMessage());
  }

  @Test
  public void schemaParserShouldThrowError_WhenArgumentFlagNumberIsGreaterThanSchemaFlagNumber() throws Exception {
    Schema schema = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(String.class, 2)));
    List<String> invalidFlagArgs = List.of("-l", "-2", "-d", "-l");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema, invalidFlagArgs);
    });
    assertEquals("Flag Number exceeded supported amount 1 for flag -l.",
        exception.getMessage());

    Schema schema2 = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 2), "-d",
        new SchemaFlag(String.class, 2)));
    List<String> invalidFlagArgs2 = List.of("-l", "-2", "-p", "-p", "-p", "-p");
    Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema2, invalidFlagArgs2);
    });
    assertEquals("Flag Number exceeded supported amount 2 for flag -p.",
        exception2.getMessage());
  }

  @Test
  public void schemaParserShouldThrowError_WhenArgumentFlagValueDoesntMatchSchemaFlagType() throws Exception {
    Schema schema = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(Integer.class, 2)));
    List<String> invalidFlagArgs = List.of("-l", "-d", "hello");
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema, invalidFlagArgs);
    });
    assertEquals("Flag -d value hello doesn't match flag type Integer.",
        exception.getMessage());

    Schema schema2 = new Schema(Map.of("-l", new SchemaFlag(null, 1), "-p", new SchemaFlag(Integer.class, 1), "-d",
        new SchemaFlag(Boolean.class, 2)));
    List<String> invalidFlagArgs2 = List.of("-l", "-d", "1");
    Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
      parseArgs(schema2, invalidFlagArgs2);
    });
    assertEquals("Flag -d value 1 doesn't match flag type Boolean.",
        exception2.getMessage());
  }

  private void parseArgs(Schema schema, List<String> arguments) {
    Map<String, Integer> flagsCounter = new HashMap<>();
    for (int i = 0; i < arguments.size(); i++) {
      String currentArg = arguments.get(i);
      if (isFlag(currentArg)) {
        if (!schema.schema.containsKey(currentArg))
          throw new IllegalArgumentException("Invalid flag " + currentArg + ".");
        if (flagsCounter.containsKey(currentArg)) {
          Integer allowedNumber = schema.schema.get(currentArg).number;
          if (allowedNumber < flagsCounter.get(currentArg) + 1)
            throw new IllegalArgumentException(
                "Flag Number exceeded supported amount " + allowedNumber + " for flag " + currentArg + ".");
          else {
            flagsCounter.put(currentArg, flagsCounter.get(currentArg) + 1);
          }
        } else {
          flagsCounter.put(currentArg, 1);
        }

      } else {
        if (i - 1 >= 0) {
          String prevArg = arguments.get(i - 1);
          if (!isFlag(prevArg))
            throw new IllegalArgumentException("Each flag should Have One Value Associated with it.");
          Class<?> currentFlagType = schema.schema.get(prevArg).type;
          try {
            if (currentFlagType == Integer.class)
              Integer.parseInt(currentArg);
            else if (currentFlagType == Boolean.class) {
              if (!currentArg.toLowerCase().equals("true") || currentArg.toLowerCase().equals("false"))
                throw new IllegalArgumentException();
            }
          } catch (Exception e) {
            throw new IllegalArgumentException(
                "Flag " + prevArg + " value " + currentArg + " doesn't match flag type "
                    + currentFlagType.getSimpleName() + ".");
          }
        }
      }

    }
  }

  private boolean isFlag(String currentArg) {
    return currentArg.startsWith("-") && Character.isAlphabetic(currentArg.charAt(1));
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
        if (!Character.isAlphabetic(key.charAt(1)))
          throw new IllegalArgumentException("Flag name must be from the alphabit.");

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

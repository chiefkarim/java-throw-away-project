package com.example.java_throw_away;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest{
  
  @Test
  void shouldHaveEmail(){
    User user = new User("contact@karimmenna.com","123");

    assertNotNull(user.getEmail());
  }

  @Test 
  void shouldReturnCorrectEmail(){
    User user = new User("contact@karimmenna.com","123");

    assertEquals("contact@karimmenna.com",user.getEmail());
  }

  @Test
  void shouldHavePassword(){
    User user = new User("contact@karimmenna.com","123");
    assertNotNull(user.getPassword());
  }

  // @Test 
  // void shouldHaveCorrectPassword(){
  //   User user = new User("contact@karimmenna.com","123");
  //   assertEquals("123",user.getPassword());
  // }
 
  @Test
  void passwordShouldNotBeStoredInPlainText(){
    User user = new User("contact@karimmenna.com","123");
    assertNotEquals("123",user.getPassword());
  }
  
  @Test 
  void hashPasswordShouldProduceConsistentHash(){
    User user = new User("contact@karimmenna.com","123");
    
    String hash1 = user.hashPassword("123");
    String hash2 = user.hashPassword("123");

  assertEquals(hash1,hash2);
  }

  @Test 
  void hashPasswordShouldBeDifferentForDifferentPasswords(){
    User user = new User("contact@karimmenna.com","123");

    String hash1 = user.hashPassword("123");
    String hash2 = user.hashPassword("1234");

    assertNotEquals(hash1,hash2);
  }

}

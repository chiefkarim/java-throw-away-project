package com.example.java_throw_away;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloControllerTest{

  @Test
  void HelloShouldReturnGreetingMessage(){
    HelloController controller = new HelloController();
    String result = controller.hello();
    assertEquals("hello world",result);
  }
}

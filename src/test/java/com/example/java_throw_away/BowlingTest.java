package com.example.java_throw_away;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingTest {
  private Game g;

  public void rollMany(int n, int pins) {
    for (int i = 0; i < n; i++) {
      g.roll(pins);
    }
  }

  public void rollSpare() {
    g.roll(5);
    g.roll(5);
  }

  @BeforeEach
  public void setUp() throws Exception {
    g = new Game();
  }

  @Test
  public void gutterGame() throws Exception {
    rollMany(20, 0);
    assertEquals(0, g.score());
  }

  @Test
  public void rollManyOnes() throws Exception {
    rollMany(20, 1);
    assertEquals(20, g.score());
  }

  @Test
  public void allOnes() throws Exception {
    rollMany(20, 1);
    assertEquals(20, g.score());
  }

  @Test
  public void oneSpare() throws Exception {
    rollSpare();
    g.roll(3);
    rollMany(17, 0);
    assertEquals(16, g.score());
  }

  @Test
  public void oneStrike() throws Exception {
    rollStrike();
    g.roll(5);
    g.roll(3);
    rollMany(17, 0);
    assertEquals(26, g.score());
  }

  private void rollStrike() {
	g.roll(10);
  }

}

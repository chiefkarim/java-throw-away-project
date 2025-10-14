package com.example.java_throw_away;

public class Game {
  private int rolls[] = new int[21];
  private int currRoll = 0;

  public void roll(int pins) {
    rolls[currRoll++] = pins;
  }

  public int score() {
    int score = 0;
    int firstInFrame = 0;
    for (int frame = 0; frame < 10; frame++) {
      if (isSpare(firstInFrame)) {
        score += 10 + nextBallForSpare(firstInFrame);
        firstInFrame += 2;
      } else if (isStrike(firstInFrame)) {
        score += 10 + nextTwoBallsForStrike(firstInFrame);
        firstInFrame += 1;
      } else {
        score += twoBallsInFrame(firstInFrame);
        firstInFrame += 2;
      }
    }
    return score;
  }

  private int twoBallsInFrame(int firstInFrame) {
    return rolls[firstInFrame] + rolls[firstInFrame + 1];
  }

  private int nextBallForSpare(int firstInFrame) {
    return rolls[firstInFrame + 2];
  }

  private int nextTwoBallsForStrike(int firstInFrame) {
    return rolls[firstInFrame + 1] + rolls[firstInFrame + 2];
  }

  private boolean isStrike(int firstInFrame) {
    return rolls[firstInFrame] == 10;
  }

  private boolean isSpare(int firstInFrame) {
    return rolls[firstInFrame] + rolls[firstInFrame + 1] == 10;
  }

}

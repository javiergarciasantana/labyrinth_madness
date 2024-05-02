package labyrinth_madness;

public class Square {
  private int state; // 1 wall, 0 free, -1 backtracked, >=2 solution
  // private int step; // number of steps to reach this square
  private int x, y; // coordinates of the square

  public Square(int state, int x, int y) {
    this.state = state;
    this.x = x;
    this.y = y;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  /*
   * public int getStep() {
   * return step;
   * }
   *
   * public void setStep(int step) {
   * this.step = step;
   * }
   */

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

}

// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Square class is declared

package labyrinth_madness.src;

/**
 * Represents a square in the labyrinth.
 */
public class Square {
  private int state; // 1 wall, 0 free, -1 backtracked, >=2 solution
  private int x, y; // coordinates of the square

  /**
   * Constructs a square with the given state and coordinates.
   *
   * @param state The state of the square.
   * @param x     The x-coordinate of the square.
   * @param y     The y-coordinate of the square.
   */
  public Square(int state, int x, int y) {
    this.state = state;
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the state of the square.
   *
   * @return The state of the square.
   */
  public int getState() {
    return state;
  }

  /**
   * Sets the state of the square.
   *
   * @param state The state of the square.
   */
  public void setState(int state) {
    this.state = state;
  }

  /**
   * Gets the x-coordinate of the square.
   *
   * @return The x-coordinate of the square.
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the x-coordinate of the square.
   *
   * @param x The x-coordinate of the square.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets the y-coordinate of the square.
   *
   * @return The y-coordinate of the square.
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the y-coordinate of the square.
   *
   * @param y The y-coordinate of the square.
   */
  public void setY(int y) {
    this.y = y;
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
}

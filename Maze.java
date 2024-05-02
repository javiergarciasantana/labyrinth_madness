package labyrinth_madness;

/**
 * Represents a maze for solving labyrinth problems.
 */
public class Maze {

  private int width_, height_;
  private Square[][] maze_;

  /**
   * Parametric constructor for Maze.
   *
   * @param matrix The maze matrix.
   * @param h      The length of the maze.
   * @param w      The width of the maze.
   * @param x      The initial x-coordinate.
   * @param y      The initial y-coordinate.
   */
  public Maze(int w, int h) {
    width_ = w;
    height_ = h;
    maze_ = new Square[width_][height_];

    // Generate a matrix of random 1s and 0s
    for (int i = 0; i < width_; i++) { // Iterating over rows
      for (int j = 0; j < height_; j++) { // Iterating over columns
        maze_[i][j] = new Square(genBin(), i, j);
      }
    }
  }

  public Square getSquare(int x_pos, int y_pos) {
    return maze_[x_pos][y_pos];
  }

  public void setElem(int x_pos, int y_pos, int val) {
    maze_[x_pos][y_pos].setState(val);
  }

  public int getHeight() {
    return height_;
  }

  public int getWidth() {
    return width_;
  }

  /**
   * Checks if the position is at the maze boundary.
   *
   * @param x_pos The x-coordinate.
   * @param y_pos The y-coordinate.
   * @return True if at boundary, false otherwise.
   */
  public boolean Finished(int x_pos, int y_pos) {
    return (x_pos == 0 && y_pos >= 0 && y_pos < height_) ||
        (y_pos == 0 && x_pos >= 0 && x_pos < width_) ||
        (x_pos == width_ - 1 && y_pos >= 0 && y_pos < height_) ||
        (y_pos == height_ - 1 && x_pos >= 0 && x_pos < width_);
  }

  private int genBin() {
    float r = (float) Math.random();
    if (r > 0.8) {
      return 1;
    } else {
      return 0;
    }
  }
}

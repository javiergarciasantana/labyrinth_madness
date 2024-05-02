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
  }

  public Maze(int w, int h, boolean isDiagonal) {
    width_ = w;
    height_ = h;
    maze_ = new Square[width_][height_];

    int[][] matrix = {
      {1, 1, 1, 1, 1, 1, 1},
      {1, 0, 0, 0, 1, 0, 1},
      {1, 0, 1, 0, 0, 0, 1},
      {1, 0, 0, 0, 0, 1, 1},
      {1, 1, 1, 1, 0, 1, 1},
      {0, 0, 0, 0, 0, 0, 0},
      {1, 1, 1, 1, 1, 0, 1}
    };

    // Copy the values from matrix to maze_
    for (int i = 0; i < width_; i++) {
      for (int j = 0; j < height_; j++) {
        maze_[i][j] = new Square(matrix[i][j], i, j);
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

  public int[][] getMatrix() {
    int[][] matrix = new int[width_][height_];
    for (int i = 0; i < width_; i++) {
      for (int j = 0; j < height_; j++) {
        matrix[i][j] = maze_[i][j].getState();
      }
    }
    return matrix;
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

}

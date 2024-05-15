// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Maze class is declared

package labyrinth_madness.src;

/**
 * Represents a maze for solving labyrinth problems.
 */
public class Maze {

  private int width_, height_;
  private Square[][] maze_;

  /**
   * Parametric constructor for Maze.
   *
   * @param w The width of the maze.
   * @param h The height of the maze.
   */
  public Maze(int w, int h) {
    width_ = w;
    height_ = h;
    maze_ = new Square[width_][height_];

    int[][] matrix = {
<<<<<<< HEAD:Maze.java
        { 1, 1, 1, 1, 1, 1, 1 },
        { 1, 0, 0, 0, 1, 0, 1 },
        { 1, 0, 1, 0, 0, 0, 1 },
        { 1, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0, 0, 0 },
        { 1, 1, 1, 1, 1, 0, 1 }
=======
      {1, 0, 1, 1, 1, 1, 1},
      {1, 0, 1, 0, 0, 0, 1},
      {1, 0, 1, 0, 1, 0, 1},
      {1, 0, 1, 0, 0, 0, 1},
      {1, 0, 0, 0, 0, 1, 1},
      {0, 0, 1, 1, 0, 0, 1},
      {1, 0, 1, 1, 1, 1, 1}
>>>>>>> bed4544aeed728659ea218a19bbe33432822e579:src/Maze.java
    };
  
  

    // Copy the values from matrix to maze_
    for (int i = 0; i < width_; i++) {
      for (int j = 0; j < height_; j++) {
        maze_[i][j] = new Square(matrix[i][j], i, j);
      }
    }

  }

  /**
   * Gets the square at the specified position.
   *
   * @param x_pos The x-coordinate.
   * @param y_pos The y-coordinate.
   * @return The square at the specified position.
   */
  public Square getSquare(int x, int y) {
    if (x < 0 || x >= width_ || y < 0 || y >= height_) {
      return null;
    }
    return maze_[x][y];
  }

  /**
   * Sets the value of the square at the specified position.
   *
   * @param x_pos The x-coordinate.
   * @param y_pos The y-coordinate.
   * @param val   The value to set.
   */
  public void setElem(int x_pos, int y_pos, int val) {
    maze_[x_pos][y_pos].setState(val);
  }

  /**
   * Gets the height of the maze.
   *
   * @return The height of the maze.
   */
  public int getHeight() {
    return height_;
  }

  /**
   * Gets the width of the maze.
   *
   * @return The width of the maze.
   */
  public int getWidth() {
    return width_;
  }

  /**
   * Gets the matrix representation of the maze.
   *
   * @return The matrix representation of the maze.
   */
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

  public boolean isEdge(Square s) {
    return (s.getX() == 0 || s.getX() == width_ - 1 || s.getY() == 0 || s.getY() == height_ - 1);
  }

  public void printMatrix() {
    for (int i = 0; i < width_; i++) {
      for (int j = 0; j < height_; j++) {
        System.out.print(maze_[i][j].getState() + " ");
      }
      System.out.println();
    }
    System.out.println("-----------------");
  }
}
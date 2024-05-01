// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Maze class is declared
//

package labyrinth_madness; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a maze for solving labyrinth problems.
 */
public class Maze {

  private int width_, length_, size;
  private List<List<Integer>> maze_ = new ArrayList<>();

  /**
   * Default constructor for Maze.
   */
  public Maze() {
    maze_.clear();
  }

  /**
   * Parametric constructor for Maze.
   *
   * @param matrix    The maze matrix.
   * @param l         The length of the maze.
   * @param w         The width of the maze.
   * @param x         The initial x-coordinate.
   * @param y         The initial y-coordinate.
   */
  public Maze(int l, int w) {
    length_ = l;
    width_ = w;
    size = length_ * width_;
  }

  public int getElem(int x_pos, int y_pos) {
    return maze_.get(y_pos).get(x_pos);
  }

  public void setElem(int x_pos, int y_pos, int val) {
    maze_.get(y_pos).set(x_pos, val);
  }

  public int getLength() {
    return length_;
  }

  public int getWidth() {
    return width_;
  }

  /**
   * Checks if the position is at the maze boundary.
   *
   * @param x_pos   The x-coordinate.
   * @param y_pos   The y-coordinate.
   * @return        True if at boundary, false otherwise.
   */
  public boolean Finished(int x_pos, int y_pos) {
    return (x_pos == 0 && y_pos >= 0 && y_pos < length_) ||
        (y_pos == 0 && x_pos >= 0 && x_pos < width_) ||
        (x_pos == width_ - 1 && y_pos >= 0 && y_pos < length_) ||
        (y_pos == length_ - 1 && x_pos >= 0 && x_pos < width_);
  }

}

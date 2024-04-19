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

  protected int initial_x_, initial_y_;
  protected int width_, length_, size;
  protected List<List<Integer>> maze_ = new ArrayList<>();
  protected List<Map.Entry<Integer, Integer>> nodes = new ArrayList<>();
  protected List<Integer> rules_ = new ArrayList<>();
  protected int xMove[] = {-1, 0, 1, 0};
  protected int yMove[] = {0, -1, 0, 1};

  /**
   * Default constructor for Maze.
   */
  public Maze() {
    nodes.clear();
    rules_.clear();
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
  public Maze(List<List<Integer>> matrix, int l, int w, int x, int y) {
    initial_x_ = x - 1;
    initial_y_ = y - 1;
    length_ = l;
    width_ = w;
    maze_ = matrix;
    size = length_ * width_;
    nodes.add(Map.entry(initial_x_ - 1, initial_y_ - 1));
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

  /**
   * Adds a node to the maze.
   *
   * @param x   The x-coordinate of the node.
   * @param y   The y-coordinate of the node.
   */
  public void addToNodes(int x, int y) {
    nodes.add(Map.entry(x, y));
  }

  /**
   * Adds a rule to the maze.
   *
   * @param r   The rule to add.
   */
  public void addToRules_(int r) {
    rules_.add(r);
  }
}

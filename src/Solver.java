// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Solver class is declared
//

package labyrinth_madness.src;

import java.util.ArrayList;
import java.util.List;

public class Solver {

  // Fields
  protected Maze maze_;
  // Not needed, cause the head of the list is the current position
  // protected int initial_x_, initial_y_;
  protected List<Square> nodes_ = new ArrayList<>();
  protected List<Integer> rules_ = new ArrayList<>();
  protected int moves[][] = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
  // protected Tree tree;

  // Constructors

  /**
   * Parameterized constructor for Solver class.
   * Initializes maze with given parameters and sets initial configuration.
   *
   * @param m The maze matrix.
   * @param x The initial x-coordinate.
   * @param y The initial y-coordinate.
   */
  protected Solver(Maze m, int x, int y, int step) {
    maze_ = m;
    nodes_.add(maze_.getSquare(x, y));
    getCurrent().setState(step_);

  }

  // Methods

  /**
   * Moves to the specified position.
   *
   * @param x_pos The x-coordinate of the position.
   * @param y_pos The y-coordinate of the position.
   * @return True if move is valid, false otherwise.
   */
  protected boolean move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0
        && x_pos < maze_.getWidth() && y_pos < maze_.getHeight()
        && maze_.getSquare(x_pos, y_pos).getState() == 0);
  }

  /**
   * Prints the maze table.
   *
   */
  protected void printTable() {

    for (int i = 0; i < maze_.getHeight(); ++i) {
      for (int j = 0; j < maze_.getWidth(); ++j) {
        if (maze_.getSquare(j, i).getState() / 10 < 1) {
          System.out.print(" ");
        }
        if (maze_.getSquare(j, i).getState() >= 0) {
          System.out.print(" " + maze_.getSquare(j, i).getState());
        } else {
          System.out.print(maze_.getSquare(j, i).getState());
        }
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  /**
   * Adds a rule to the maze.
   *
   * @param r The rule to add.
   */
  protected void addToRules(int r) {
    rules_.add(r);
  }

  // Returns the last node, i.e., the current position
  protected Square getCurrent() {
    return nodes_.get(nodes_.size() - 1);
  }

  /**
   * Returns the list of nodes.
   *
   * @return The list of nodes.
   */
  public List<Square> getNodes() {
    return nodes_;
  }

}

// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the DfsSolver class is declared
//

package labyrinth_madness;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a solver for solving mazes using Depth-First Search algorithm.
 */
public class DfsSolver {

  // Fields
  private int step_;
  private Maze maze_;
  private int initial_x_, initial_y_;
  private List<Square> nodes_ = new ArrayList<>();
  private List<Integer> rules_ = new ArrayList<>();
  private int xMove[] = { -1, 0, 1, 0 };
  private int yMove[] = { 0, -1, 0, 1 };

  /**
   * Parametric constructor for DfsSolver.
   *
   * @param m      The maze.
   * @param x      The initial x-coordinate.
   * @param y      The initial y-coordinate.
   */

  public DfsSolver(Maze m, int x, int y) {
    maze_ = m;
    step_ = 2;
    initial_x_ = x - 1;
    initial_y_ = y - 1;
    Square s = maze_.getSquare(initial_x_, initial_y_);
    s.setState(step_);
    nodes_.add(s);
  }

  /**
   * Writes maze information.
   */
  public void Write() {
    printData();
    if (solve(initial_x_, initial_y_, step_ + 1) == 1) {
      // printTable(true);
    }
  }

  /**
   * Prints maze data.
   */
  private void printData() {
    System.out.println("PART 1. Data\n" + "  1.1. Labyrinth.\n");
  }

  /**
   * Moves to the next position in the maze.
   *
   * @param x_pos The current x-coordinate.
   * @param y_pos The current y-coordinate.
   * @return True if move is possible, false otherwise.
   */
  private boolean move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0
        && x_pos < maze_.getWidth() && y_pos < maze_.getHeight()
        && maze_.getSquare(x_pos, y_pos).getState() < 1
        && maze_.getSquare(x_pos, y_pos).getState() > -1);
  }

  /**
   * Solves the maze using Depth-First Search algorithm.
   *
   * @param x_pos The current x-coordinate.
   * @param y_pos The current y-coordinate.
   * @param step  The current step number.
   * @return 1 if the solution is found, 0 otherwise.
   */
  private int solve(int x_pos, int y_pos, int step) {
    // printTable(false);
    if (maze_.Finished(x_pos, y_pos)) {
      return 1;
    }
    int x_next, y_next;
    for (int k = 0; k < 4; ++k) {
      x_next = x_pos + xMove[k];
      y_next = y_pos + yMove[k];

      if (move(x_next, y_next)) {
        // maze_.setElem(x_next, y_next, step);
        Square s = maze_.getSquare(x_next, y_next);
        s.setState(step);
        step_ = step;
        rules_.add(k + 1);
        // nodes_.add(Map.entry(x_next, y_next));
        nodes_.add(s);

        try {
          // Sleep for 1 second (1000 milliseconds)
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // Handle interruption if needed
          e.printStackTrace();
        }
        if (solve(x_next, y_next, step + 1) == 1) {
          return 1;
        } else {
          // maze_.setElem(x_next, y_next, -1);
          // Square s = maze_.getSquare(x_next, y_next);
          s.setState(-1);
          rules_.remove(rules_.size() - 1);
          nodes_.add(s);
          try {
            // Sleep for 1 second (1000 milliseconds)
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
          }
          // printTable(false);
        }
      }
    }
    return 0;
  }

  /**
   * Adds a rule to the maze.
   *
   * @param r The rule to add.
   */
  public void addToRules(int r) {
    rules_.add(r);
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

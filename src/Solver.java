// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Solver class is declared
//

package labyrinth_madness.src;

import java.util.Deque;
import java.util.LinkedList;

public abstract class Solver {

  // Fields
  protected Maze maze_;
  // Not needed, cause the head of the list is the current position
  // protected int initial_x_, initial_y_;
  protected Deque<Square> nodes_ = new LinkedList<>();
  // protected Deque<Integer> rules_ = new LinkedList<>();
  protected int moves[][] = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
  protected Square solution;

  // Constructors

  /**
   * Parameterized constructor for Solver class.
   * Initializes maze with given parameters and sets initial configuration.
   *
   * @param m The maze matrix.
   * @param x The initial x-coordinate.
   * @param y The initial y-coordinate.
   */
  protected Solver(Maze m, Square start) {
    maze_ = m;
    start.setState(2);
    nodes_.add(start);
  }

  // Abstract methods
  public abstract boolean Step();

  protected abstract Square getCurrentSquare();

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
   * Returns the list of nodes.
   *
   * @return The list of nodes.
   */
  public Deque<Square> getNodes() {
    return nodes_;
  }

  // Gets the new square after applying the rule
  // Null if the move is not possible
  protected Square moveSquare(Square s, int rule[]) {
    return maze_.getSquare(s.getX() + rule[0], s.getY() + rule[1]);
  }

  protected void setSolution(Square s) {
    solution = s;
  }

  protected Square getSolution() {
    return solution;
  }

}

// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the BfsSolver class is declared
//

package labyrinth_madness;

import java.io.*;
import java.util.*;

public class BfsSolver extends Solver {

  // Fields
  private int wave_;
  private Queue<Map.Entry<Integer, Integer>> cola_ = new LinkedList<>();

  // Constructors

  /**
   * Parameterized constructor for BfsSolver class.
   * Initializes maze with given parameters and sets initial configuration.
   *
   * @param m    The maze matrix.
   * @param x         The initial x-coordinate.
   * @param y         The initial y-coordinate.
   */
  public BfsSolver(Maze m, int x, int y) {
    super(m, x, y, 2);
    wave_ = 2;
    cola_.add(new AbstractMap.SimpleEntry<>(initial_x_, initial_y_));
  }

  // Methods

  /**
   * Writes maze data.
   */
  public void Write() {
    if (Solve(wave_) == 0) {
      printTable();
    }
  }

  /**
   * Solves the maze.
   *
   * @param wave  The current wave number.
   * @return      1 if solution is found, 0 otherwise.
   */
  private int Solve(int wave) {
    --wave_;
    while (!cola_.isEmpty()) {
      Map.Entry<Integer, Integer> k = cola_.poll();
      int x_pos = k.getKey();
      int y_pos = k.getValue();
      if (wave_ < maze_.getSquare(x_pos, y_pos).getState()) {
        ++wave_;
        try {
          Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruption if needed
        }
        printTable();
      }
      for (int j = 0; j < 4; ++j) {
        if (move(x_pos + xMove[j], y_pos + yMove[j])) {

          cola_.add(new AbstractMap.SimpleEntry<>(x_pos + xMove[j], y_pos + yMove[j]));

          maze_.setElem(x_pos + xMove[j], y_pos + yMove[j], maze_.getSquare(x_pos, y_pos).getState() + 1);
          
          if (maze_.Finished(x_pos + xMove[j], y_pos + yMove[j])) {

            try {
              Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption if needed
            }
            printTable();
            return 1;
          }
        } 
      }
    }
    return 0;
  }

  /**
   * Adds a node to the maze.
   *
   * @param x   The x-coordinate of the node.
   * @param y   The y-coordinate of the node.
   */
  public void addToNodes(int x, int y) {
    nodes_.add(maze_.getSquare(x, y));
  }
}
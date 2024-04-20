// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the DfsSolver class is declared
//

package labyrinth_madness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a solver for solving mazes using Depth-First Search algorithm.
 */
public class DfsSolver extends Maze {
  private int step_;
  private int trials_;

  /**
   * Default constructor for DfsSolver.
   */
  public DfsSolver() {
    super();
    trials_ = 0;
    step_ = 2;
  }

  /**
   * Parametric constructor for DfsSolver.
   *
   * @param matrix    The maze matrix.
   * @param l         The length of the maze.
   * @param w         The width of the maze.
   * @param x         The initial x-coordinate.
   * @param y         The initial y-coordinate.
   */
  public DfsSolver(List<List<Integer>> matrix, int l, int w, int x, int y) {
    super(matrix, l, w, x, y);
    trials_ = 0;
    step_ = 2;
    maze_.get(initial_y_).set(initial_x_, step_);
    nodes_.add(Map.entry(initial_x_, initial_y_));
  }

  /**
   * Reads maze information from a text file.
   *
   * @param textFile   The path to the text file.
   * @param initial_x  The initial x-coordinate.
   * @param initial_y  The initial y-coordinate.
   * @throws IOException if an I/O error occurs.
   */
  public void Read(String textFile, int initial_x, int initial_y) throws IOException {
    List<List<Integer>> matrix = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(textFile));
    String line;
    int length = 0;
    while ((line = reader.readLine()) != null) {
      List<Integer> row = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) != ' ') {
            row.add(line.charAt(i) - '0');
          }
        }
      matrix.add(row);
      length++;
    }
    reader.close();

    int width = matrix.get(0).size();
    matrix.remove(matrix.size() - 1);

    length_ = length - 1;
    width_ = width;
    initial_x_ = initial_x - 1;
    initial_y_ = initial_y - 1;
    maze_ = matrix;
    maze_.get(initial_y_).set(initial_x_, step_);
  }

  /**
   * Writes maze information.
   */
  public void Write() {
    printData();
    if (solve(initial_x_, initial_y_, step_ + 1) == 1) {
      printTable(true);
    }
  }

  /**
   * Prints maze data.
   */
  private void printData() {
    System.out.println("PART 1. Data\n" + "  1.1. Labyrinth.\n");
  }

  /**
   * Prints maze table.
   *
   * @param header  True to print header, false otherwise.
   */
  private void printTable(boolean header) {
    if (header) {
      System.out.println("\nPART 2. Results \n" + "  2.1. Path is found.");
      System.out.println("  2.2. Path graphically:");
    } else {
      System.out.println("ITERATION: " + step_);
    }
    for (int i = length_ - 1; i >= 0; --i) {
      for (int j = 0; j < width_; ++j) {
        if (maze_.get(i).get(j) / 10 < 1) {
          System.out.print(" ");
        }
        if (maze_.get(i).get(j) >= 0) {
          System.out.print(" " + maze_.get(i).get(j));
        } else {
          System.out.print(maze_.get(i).get(j));
        }
      }
      System.out.println();
    }
    System.out.println();
  } 

  /**
   * Moves to the next position in the maze.
   *
   * @param x_pos  The current x-coordinate.
   * @param y_pos  The current y-coordinate.
   * @return       True if move is possible, false otherwise.
   */
  private boolean move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0
            && x_pos < width_ && y_pos < length_
            && maze_.get(y_pos).get(x_pos) < 1
            && maze_.get(y_pos).get(x_pos) > -1);
  }

  /**
   * Solves the maze using Depth-First Search algorithm.
   *
   * @param x_pos  The current x-coordinate.
   * @param y_pos  The current y-coordinate.
   * @param step   The current step number.
   * @return       1 if the solution is found, 0 otherwise.
   */
  private int solve(int x_pos, int y_pos, int step) {
    printTable(false);
    if (Finished(x_pos, y_pos)) {
      return 1;
    }
    int x_next, y_next;
    for (int k = 0; k < 4; ++k) {
      x_next = x_pos + xMove[k];
      y_next = y_pos + yMove[k];
      ++trials_;

      if (move(x_next, y_next)) {
        maze_.get(y_next).set(x_next, step);
        step_ = step;
        rules_.add(k + 1);
        nodes_.add(Map.entry(x_next, y_next));
        
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
          maze_.get(y_next).set(x_next, -1);
          rules_.remove(rules_.size() - 1);
          nodes_.remove(nodes_.size() - 1);
          try {
            // Sleep for 1 second (1000 milliseconds)
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            // Handle interruption if needed
            e.printStackTrace();
          }
          printTable(false);
        }
      }
    }
    return 0;
  }
}

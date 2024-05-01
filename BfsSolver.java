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

public class BfsSolver extends Maze {

  // Fields
  private int wave_;
  private int step_;
  private int n_nodes_;
  private int final_x_;
  private int final_y_;
  private Queue<Map.Entry<Integer, Integer>> cola_ = new LinkedList<>();
  private List<Map.Entry<Integer, Integer>> d_ = new ArrayList<>();
  private List<List<Integer>> pred_;
  private Maze maze_;
  private int initial_x_, initial_y_;
  protected List<Map.Entry<Integer, Integer>> nodes_ = new ArrayList<>();
  private List<Integer> rules_ = new ArrayList<>();
  private int xMove[] = {-1, 0, 1, 0};
  private int yMove[] = {0, -1, 0, 1};

  // Constructors

  /**
   * Default constructor for BfsSolver class.
   * Initializes wave_, step_, and n_nodes_ to default values.
   */
  public BfsSolver() {
    wave_ = 2;
    step_ = 1;
    n_nodes_ = 1;
  }

  /**
   * Parameterized constructor for BfsSolver class.
   * Initializes maze with given parameters and sets initial configuration.
   *
   * @param matrix    The maze matrix.
   * @param l         The length of the maze.
   * @param w         The width of the maze.
   * @param x         The initial x-coordinate.
   * @param y         The initial y-coordinate.
   */
  public BfsSolver(int l, int w, int x, int y) {
    maze_ = new Maze(l, w);
    initial_x_ = x - 1;
    initial_y_ = y - 1;
    wave_ = 2;
    step_ = 1;
    n_nodes_ = 1;
    maze_.setElem(initial_x_, initial_y_, wave_);
    cola_.add(new AbstractMap.SimpleEntry<>(initial_x_, initial_y_));
  }

  // Methods

  /**
   * Moves to the specified position.
   *
   * @param x_pos  The x-coordinate of the position.
   * @param y_pos  The y-coordinate of the position.
   * @return       True if move is valid, false otherwise.
   */
  private boolean Move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0 
         && x_pos < maze_.getWidth()  && y_pos < maze_.getLength() 
         && maze_.getElem(x_pos, y_pos) < 1 
         && maze_.getElem(x_pos, y_pos) > -1);
  }


  /**
   * Writes maze data.
   */
  public void Write() {
    printData();
    if (Solve(wave_) == 0) {
      TrailOfCrums();
      printTable(true);
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
      if (wave_ < maze_.getElem(k.getKey(), k.getValue())) {
        ++wave_;
        try {
          Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruption if needed
        }
        printTable(false);
      }
      ++step_;
      for (int j = 0; j < 4; ++j) {
        if (Move(k.getKey() + xMove[j], k.getValue() + yMove[j])) {
          ++n_nodes_;
          cola_.add(new AbstractMap.SimpleEntry<>(k.getKey() + xMove[j], k.getValue() + yMove[j]));
          pred_.get(k.getValue() + yMove[j]).set(k.getKey() + xMove[j], j);
          maze_.setElem(k.getKey() + xMove[j], k.getValue() + yMove[j], maze_.getElem(k.getKey() + 1, k.getValue()));
          if (Finished(k.getKey() + xMove[j], k.getValue() + yMove[j])) {
            final_x_ = k.getKey() + xMove[j];
            final_y_ = k.getValue() + yMove[j];
            try {
              Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption if needed
            }
            printTable(true);
            return 1;
          }
        } 
      }
    }
    return 0;
  }

  /**
   * Traces back the solution path.
   */
  private void TrailOfCrums() {
    int x_pos = final_x_;
    int y_pos = final_y_;
    while (pred_.get(y_pos).get(x_pos) != -1) {
      addToNodes(x_pos, y_pos);
      addToRules(pred_.get(y_pos).get(x_pos));
      switch (pred_.get(y_pos).get(x_pos)) {
        case 0:
          x_pos = x_pos - xMove[0];
          y_pos = y_pos - yMove[0];
          break;
        case 1:
          x_pos = x_pos - xMove[1];
          y_pos = y_pos - yMove[1];
          break;
        case 2:
          x_pos = x_pos - xMove[2];
          y_pos = y_pos - yMove[2];
          break;
        case 3:
          x_pos = x_pos - xMove[3];
          y_pos = y_pos - yMove[3];
          break;
        default: 
          break;
      }
    }
  }

  /**
   * Prints maze data.
   */
  private void printData() {
    System.out.println("PART 1. Data\n" + "  1.1. Labyrinth.\n");
    printTable(false);
    System.out.println("\n    1.2 Initial position: X=" + (initial_x_ + 1) + ", Y=" 
                      + (initial_y_ + 1) + ", L=" + wave_);
  }

  /**
   * Prints the maze table.
   *
   * @param header  Indicates whether to print header or not.
   */
  private void printTable(boolean header) {
    if (header) {
      System.out.println("\nPART 2. Results \n" + "  3.1. Path is found." + "\n  3.2. Path graphically:\n");
    } else {
      System.out.println("ITERATION: " + wave_);
    }
    for (int i = maze_.getLength() - 1; i >= 0; --i) {
      for (int j = 0; j < maze_.getWidth(); ++j) {
        if (maze_.getElem(j, i) / 10 < 1) {
          System.out.print(" ");
        }
        if (maze_.getElem(j, i) >= 0) {
          System.out.print(" " + maze_.getElem(j, i));
        } else {
          System.out.print(maze_.getElem(j, i));
        }
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

    /**
   * Adds a node to the maze.
   *
   * @param x   The x-coordinate of the node.
   * @param y   The y-coordinate of the node.
   */
  public void addToNodes(int x, int y) {
    nodes_.add(Map.entry(x, y));
  }

  /**
   * Adds a rule to the maze.
   *
   * @param r   The rule to add.
   */
  public void addToRules(int r) {
    rules_.add(r);
  }
}

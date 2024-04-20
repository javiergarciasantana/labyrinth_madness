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
  public BfsSolver(List<List<Integer>> matrix, int l, int w, int x, int y) {
    super(matrix, l, w, x, y);
    wave_ = 2;
    step_ = 1;
    n_nodes_ = 1;
    maze_.get(initial_y_).set(initial_x_, wave_);
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
  public boolean Move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0 
         && x_pos < width_  && y_pos < length_ 
         && maze_.get(y_pos).get(x_pos) < 1 
         && maze_.get(y_pos).get(x_pos) > -1);
  }

  /**
   * Reads maze data from the specified file.
   *
   * @param input       The input file name.
   * @param initial_x   The initial x-coordinate.
   * @param initial_y   The initial y-coordinate.
   * @throws IOException  If an I/O error occurs.
   */
  public void Read(String input, int initial_x, int initial_y) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(input));
    List<List<Integer>> matrix = new ArrayList<>();
    int length = 0;
    String line;
    while ((line = reader.readLine()) != null) {
      List<Integer> aux = new ArrayList<>();
      for (int i = 0; i < line.length(); ++i) {
        if (line.charAt(i) != ' ') {
          aux.add(line.charAt(i) - '0');
        } 
      } 
      matrix.add(aux);
      ++length;
    }
    reader.close();
    
    int width = matrix.get(0).size();
    matrix.remove(matrix.size() - 1);

    length_ = length - 1;
    width_ = width;
    initial_x_ = initial_x - 1;
    initial_y_ = initial_y - 1;
    maze_ = matrix;
    pred_ = matrix;
    pred_.get(initial_y_).set(initial_x_, -1);
    maze_.get(initial_y_).set(initial_x_, wave_);
  
    cola_.add(new AbstractMap.SimpleEntry<>(initial_x_, initial_y_));
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
  public int Solve(int wave) {
    --wave_;
    while (!cola_.isEmpty()) {
      Map.Entry<Integer, Integer> k = cola_.poll();
      if (wave_ < maze_.get(k.getValue()).get(k.getKey())) {
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
          maze_.get(k.getValue() + yMove[j]).set(k.getKey() + xMove[j], maze_.get(k.getValue()).get(k.getKey()) + 1);
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
  public void TrailOfCrums() {
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
  public void printData() {
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
  public void printTable(boolean header) {
    if (header) {
      System.out.println("\nPART 2. Results \n" + "  3.1. Path is found." + "\n  3.2. Path graphically:\n");
    } else {
      System.out.println("ITERATION: " + wave_);
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
    System.out.println();
  }
}

package labyrinth_madness;

import java.io.*;
import java.util.*;

public class BfsSolver extends Maze {

  private int wave_;
  private int step_;
  private int n_nodes_;
  private int final_x_;
  private int final_y_;
  private Queue<Map.Entry<Integer, Integer>> cola_ = new LinkedList<>();
  private List<Map.Entry<Integer, Integer>> d_ = new ArrayList<>();
  private List<List<Integer>> pred_;

  public BfsSolver() {
    wave_ = 2;
    step_ = 1;
    n_nodes_ = 1;
  }

  public BfsSolver(List<List<Integer>> matrix, int l, int w, int x, int y) {
    super(matrix, l, w, x, y);
    wave_ = 2;
    step_ = 1;
    n_nodes_ = 1;

    maze_.get(initial_y_).set(initial_x_, wave_);
    cola_.add(new AbstractMap.SimpleEntry<>(initial_x_, initial_y_));
  }

  public boolean Move(int x_pos, int y_pos) {
    return (x_pos >= 0 && y_pos >= 0 
         && x_pos < width_  && y_pos < length_ 
         && maze_.get(y_pos).get(x_pos) < 1 
         && maze_.get(y_pos).get(x_pos) > -1);
  }

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
    maze_.get(initial_y_).set(initial_x_, wave_);
    pred_.get(initial_y_).set(initial_x_, -1);
  
    cola_.add(new AbstractMap.SimpleEntry<>(initial_x_, initial_y_));
  }

  public void Write() {
    printData();
    if (Solve(wave_) == 0) {
      TrailOfCrums();
      printTable(true);
    }
  }

  public int Solve(int wave) {
    printTrace();
    --wave_;
    while (!cola_.isEmpty()) {
      Map.Entry<Integer, Integer> k = cola_.poll();
      if (wave_ < maze_.get(k.getValue()).get(k.getKey())) {
        ++wave_;
        System.out.println("\nWAVE " + (wave_ - 1) + ", label L=\"" + (wave_ + 1) + "\"");
      }
      System.out.println("\n Close CLOSE=" + step_ + ", X=" + (k.getKey() + 1) + ", Y=" + (k.getValue() + 1) + ".");
      ++step_;
      for (int j = 0; j < 4; ++j) {
        System.out.print("   R" + (j + 1) + ". X=" + (k.getKey() + xMove[j] + 1) + ", Y=" + (k.getValue() + yMove[j] + 1) + ". ");
        if (Move(k.getKey() + xMove[j], k.getValue() + yMove[j])) {
          ++n_nodes_;
          System.out.print("Free. NEWN=" + n_nodes_ + ". ");
          cola_.add(new AbstractMap.SimpleEntry<>(k.getKey() + xMove[j], k.getValue() + yMove[j]));
          maze_.get(k.getValue() + yMove[j]).set(k.getKey() + xMove[j], maze_.get(k.getValue()).get(k.getKey()) + 1);
          pred_.get(k.getValue() + yMove[j]).set(k.getKey() + xMove[j], j);
          if (Finished(k.getKey() + xMove[j], k.getValue() + yMove[j])) {
            final_x_ = k.getKey() + xMove[j];
            final_y_ = k.getValue() + yMove[j];
            System.out.println("Terminal.");
            return 1;
          }
        } 
        System.out.println();
      }
    }
    return 0;
  }

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

  public void printData() {
    System.out.println("PART 1. Data\n" + "  1.1. Labyrinth.\n");
  
    printTable(false);
    System.out.println("\n    1.2 Initial position: X=" + (initial_x_ + 1) + ", Y=" 
                + (initial_y_ + 1) + ", L=" + wave_);
  }

  public void printTrace() {
    System.out.println("\nPART 2. Trace");
    System.out.println("\nWAVE 0, label L=\"" + 2 + "\"");
    System.out.println(". Initial Position X=" + (initial_x_ + 1) + ", Y=" + (initial_y_ + 1) 
                + ", NEWN=" + n_nodes_);
  }

  public void printTable(boolean header) {
    int x_axis = length_;
    System.out.println("\nPART 3. Results \n" + "  3.1. Path is found." + "\n  3.2. Path graphically:\n");
    for (int i = length_ - 1; i >= 0; --i) {
      if (x_axis < 10) {
        System.out.print(" ");
      }
      System.out.print("   " + x_axis + " |"); 
      --x_axis;
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
    
    System.out.print("      ------------------");
    for (int i = 5; i < width_; ++i) {
      System.out.print("---");
    }
    System.out.println("> X, U");
    System.out.print("         ");
    for (int i = 1; i <= width_; ++i) {
      if (i >= 9) {
        System.out.print(i + " ");
      } else {
        System.out.print(i + "  ");
      }
    }
    System.out.println();
    System.out.println("\n  3.3. Rules:");
    for (int i = rules_.size() - 1; i >= 0; --i) {
      System.out.print(" R" + (rules_.get(i) + 1) + ",");
    }
    System.out.println("\n\n  3.4. Nodes:");
    System.out.print(" [X=" + (initial_x_ + 1) + ", Y=" + (initial_y_ + 1) + "],");
    for (int i = nodes_.size() - 1; i >= 0; --i) {
      System.out.print(" [X=" + (nodes_.get(i).getKey() + 1) + ", Y=" + (nodes_.get(i).getValue() + 1) + "],");
    }
    System.out.println();
  }
}

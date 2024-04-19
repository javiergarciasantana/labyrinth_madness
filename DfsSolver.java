package labyrinth_madness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class DfsSolver extends Maze {
  private int step_;
  private int trials_;

  public DfsSolver() {
    super();
    trials_ = 0;
    step_ = 2;
  }

  public DfsSolver(List<List<Integer>> matrix, int l, int w, int x, int y, int ver) {
    super(matrix, l, w, x, y);
    trials_ = 0;
    step_ = 2;
    maze_.get(initial_y_).set(initial_x_, step_);
    nodes.add(Map.entry(initial_x_, initial_y_));
  }

  public void read(String textFile, int initial_x, int initial_y) throws IOException {
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
    for (List<Integer> row : maze_) {
      for (int cell : row) {
          System.out.print(cell + " ");
      }
      System.out.println();
    }
  }
  public void write() {
    printData();
    if (solve(initial_x_, initial_y_, step_ + 1) == 1) {
      printTable(true);
    }
}

public void printData() {
    System.out.println("PART 1. Data\n" + "  1.1. Labyrinth.\n");
    printTable(false);
    System.out.println("\n    1.2 Initial position: X=" + (initial_x_ + 1) + ", Y=" +
            (initial_y_ + 1) + ", L=" + step_);
}

public void printTrace(int x_pos, int y_pos, int step, int move) {
    System.out.print("\n     " + trials_ + ") ");
    for (int i = 3; i < step; ++i) {
        System.out.print("-");
    }
    System.out.println("R" + (move + 1) + ". U=" + (x_pos + 1) + ", V=" + (y_pos + 1) + ". L=" + step + ".");
}

public void printTable(boolean header) {
    int x_axis = length_;
    if (header) {
        System.out.println("\nPART 3. Results \n" + "  3.1. Path is found.");
        System.out.println("  3.2. Path graphically:");
    }
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

    System.out.print("     ------------------");
    for (int i = 5; i < width_; ++i) {
        System.out.print("---");
    }
    System.out.println("> X, U");
    System.out.print("        ");
    for (int i = 1; i <= width_; ++i) {
        if (i >= 9) {
            System.out.print(i + " ");
        } else {
            System.out.print(i + "  ");
        }
    }
    System.out.println();
    if (header) {
        System.out.print("\n  3.3. Rules: ");
        for (Integer rule : rules_) {
            System.out.print("R" + rule + ", ");
        }
        System.out.println("\n\n  3.4. Nodes: ");
        for (Map.Entry<Integer, Integer> entry : nodes) {
            System.out.print("[X=" + (entry.getKey() + 1) + ",Y=" + (entry.getValue() + 1) + "], ");
        }
        System.out.println();
    }
}

public boolean move(int x_pos, int y_pos) {
    if (maze_.get(y_pos).get(x_pos) > 1) {
        System.out.println(" Thread.");
    } else if (maze_.get(y_pos).get(x_pos) == 1) {
        System.out.println(" Wall.");
    }
    return (x_pos >= 0 && y_pos >= 0
            && x_pos < width_ && y_pos < length_
            && maze_.get(y_pos).get(x_pos) < 1
            && maze_.get(y_pos).get(x_pos) > -1);
}

public int solve(int x_pos, int y_pos, int step) {
    if (Finished(x_pos, y_pos)) {
        return 1;
    }
    int x_next, y_next;
    for (int k = 0; k < 4; ++k) {
        x_next = x_pos + xMove[k];
        y_next = y_pos + yMove[k];
        ++trials_;
        printTrace(x_next, y_next, step, k);
        if (move(x_next, y_next)) {
            maze_.get(y_next).set(x_next, step);
            step_ = step;
            rules_.add(k + 1);
            nodes.add(Map.entry(x_next, y_next));
            System.out.println(" Free. L:=L+1=" + step + ". LAB[" + (x_next + 1) +
                    "," + (y_next + 1) + "]:=" + step + ".");
            if (solve(x_next, y_next, step + 1) == 1) {
                return 1;
            } else {
                System.out.print("         ");
                for (int i = 3; i < step; ++i) {
                    System.out.print("-");
                }
                System.out.println("Backtrack from X=" + (x_next + 1) + ", Y=" + (y_next + 1) +
                        ", L=" + step + ". LAB[" + (x_next + 1) + "," + (y_next + 1) + "]:=-1. L:=L-1=" +
                        (step - 1) + ".");
                
                maze_.get(y_next).set(x_next, -1);
                rules_.remove(rules_.size() - 1);
                nodes.remove(nodes.size() - 1);
            }
        }
    }
    return 0;
}
}

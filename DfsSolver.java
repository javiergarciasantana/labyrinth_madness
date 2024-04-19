package labyrinth_madness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class DfsSolver extends Maze {
  private int ver_;
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
    ver_ = ver;
    maze_.get(initial_y_).set(initial_x_, step_);
    nodes.add(Map.entry(initial_x_, initial_y_));
  }

  public void read(String textFile, int ver) throws IOException {
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
    
    System.out.println(line);
    int initial_x = Character.getNumericValue(line.charAt(0));
    int initial_y;
    if (line.charAt(1) != ' ') {
        initial_x = initial_x * 10 + Character.getNumericValue(line.charAt(1));
        initial_y = Integer.parseInt(line.substring(3));
    } else {
        initial_y = Integer.parseInt(line.substring(2));
    }

    int width = matrix.get(0).size();
    matrix.remove(matrix.size() - 1);

    ver_ = ver;
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
}

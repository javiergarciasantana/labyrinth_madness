package labyrinth_madness; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Maze {

  protected int initial_x, initial_y;
  protected int width, length, size;
  protected List<List<Integer>> maze = new ArrayList<>();
  protected List<Map.Entry<Integer, Integer>> nodes = new ArrayList<>();
  protected List<Integer> rules = new ArrayList<>();

  public Maze() {
    nodes.clear();
    rules.clear();
  }

  public Maze(List<List<Integer>> matrix, int l, int w, int x, int y) {
    initial_x = x - 1;
    initial_y = y - 1;
    length = l;
    width = w;
    maze = matrix;
    size = length * width;
    nodes.add(Map.entry(initial_x - 1, initial_y - 1));
  }

  public boolean Finished(int x_pos, int y_pos) {
    return (x_pos == 0 && y_pos >= 0 && y_pos < length) ||
        (y_pos == 0 && x_pos >= 0 && x_pos < width) ||
        (x_pos == width - 1 && y_pos >= 0 && y_pos < length) ||
        (y_pos == length - 1 && x_pos >= 0 && x_pos < width);
  }

  public void addToNodes(int x, int y) {
    nodes.add(Map.entry(x, y));
  }

  public void addToRules(int r) {
    rules.add(r);
  }
}

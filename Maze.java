package labyrinth_madness; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Maze {

  protected int initial_x_, initial_y_;
  protected int width_, length_, size;
  protected List<List<Integer>> maze_ = new ArrayList<>();
  protected List<Map.Entry<Integer, Integer>> nodes = new ArrayList<>();
  protected List<Integer> rules_ = new ArrayList<>();
  protected int xMove[] = {-1, 0, 1, 0};
  protected int yMove[] = {0, -1, 0, 1};

  public Maze() {
    nodes.clear();
    rules_.clear();
  }

  public Maze(List<List<Integer>> matrix, int l, int w, int x, int y) {
    initial_x_ = x - 1;
    initial_y_ = y - 1;
    length_ = l;
    width_ = w;
    maze_ = matrix;
    size = length_ * width_;
    nodes.add(Map.entry(initial_x_ - 1, initial_y_ - 1));
  }

  public boolean Finished(int x_pos, int y_pos) {
    return (x_pos == 0 && y_pos >= 0 && y_pos < length_) ||
        (y_pos == 0 && x_pos >= 0 && x_pos < width_) ||
        (x_pos == width_ - 1 && y_pos >= 0 && y_pos < length_) ||
        (y_pos == length_ - 1 && x_pos >= 0 && x_pos < width_);
  }

  public void addToNodes(int x, int y) {
    nodes.add(Map.entry(x, y));
  }

  public void addToRules_(int r) {
    rules_.add(r);
  }
}

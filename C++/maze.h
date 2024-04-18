#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#ifndef MAZE_H
#define MAZE_H

class Maze {
 public:
  Maze() {
    nodes_.clear();
    rules_.clear();
  }

  Maze(const std::vector<std::vector<int> >& matrix, int l, int w, int x, int y) {
    initial_x_ = x - 1;
    initial_y_ = y - 1;
    length_ = l;
    width_ =  w;
    maze_ = matrix;
    size_ = length_ * width_;
    nodes_.push_back(std::make_pair(initial_x_ - 1, initial_y_ - 1));
  }

  bool Finished(int x_pos, int y_pos) {
    return((x_pos == 0 && y_pos >=0 && y_pos < length_) ||
           (y_pos == 0 && x_pos >=0 && x_pos < width_) || 
           (x_pos == width_ - 1 && y_pos >=0 && y_pos < length_) ||
           (y_pos == length_ -1 && x_pos >=0 && x_pos < width_));
  }

  void addToNodes(const int x, const int y) {
    nodes_.push_back(std::make_pair(x, y));
  }

  void addToRules(const int r) {
    rules_.push_back(r);
  }

 protected:
  int initial_x_, initial_y_;
  int width_, length_, size_;
  int xMove[4] = {-1, 0, 1, 0};
  int yMove[4] = {0, -1, 0, 1};
  std::vector<std::vector<int> > maze_;
  std::vector<int> rules_;
  std::vector<std::pair<int, int> > nodes_;
  std::ofstream output_file{"out-short.txt"};
  std::ofstream output_file_2{"out-long.txt"};
};

#endif
#include <iostream>
#include <fstream>
#include <vector>
#include <string>

#include "maze.h"

#ifndef DFS_H
#define DFS_H

class DfsSolver : protected Maze {
 public:
  DfsSolver() : Maze(), trials_(0), step_(2) {}
  DfsSolver(const std::vector<std::vector<int> >& matrix, int l, int w, int x, int y, 
      int ver) : Maze(matrix, l, w, x, y), trials_(0), step_(2) {

    ver_ = ver;
    maze_[initial_y_][initial_x_] = step_;
    nodes_.push_back(std::make_pair(initial_x_, initial_y_));

  }

  void Read(std::ifstream& input, int ver, std::string text_file);
  void Write();
  void printData(std::ostream& out) const;
  void printTrace(std::ostream& out, int x_pos, int y_pos, int step, int move) const;
  void printTable(std::ostream& out, bool header=true) const;
  bool Move(int x_pos, int y_pos);
  int Solve(int x_pos, int y_pos, int step);

 private:
  int ver_;
  int step_;
  int trials_;
};

#endif
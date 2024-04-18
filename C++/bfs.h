#include "maze.h"

#ifndef BFS_H
#define BFS_H

class BfsSolver : protected Maze {

 public:
  BfsSolver() : wave_(2), step_(1), n_nodes_(1) {}
  BfsSolver(const std::vector<std::vector<int> >& matrix, int l, int w, int x, int y) 
            : Maze(matrix,l,w,x,y), wave_(2), step_(1), n_nodes_(1) {

    maze_[initial_y_][initial_x_] = wave_;
    cola_.push(std::make_pair(initial_x_, initial_y_));//iniciamos el recorrido desde el nodo i+1
  }

  bool Move(int x_pos, int y_pos);
  void Read(std::ifstream& input, int ver, std::string text_file);
  void Write();
  void printData(std::ostream& out);
  void printTrace(std::ostream& out);
  void printTable(std::ostream& out, bool header=true);
  int Solve(int wave);
  void TrailOfCrums();

 private:
  int wave_;
  int step_;
  int n_nodes_;
  int ver_;
  int final_x_, final_y_;
  std::queue<std::pair<int, int> > cola_;
  std::vector<std::pair<int, int> > d_;
  std::vector<std::vector<int> > pred_;
};

#endif
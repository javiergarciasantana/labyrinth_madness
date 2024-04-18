#include <iostream>
#include <string>

#include "dfs.h"
#include "bfs.h"

int main(int argc, char* argv[]) {
  DfsSolver maze_bfs;
  std::ifstream input;
  maze_bfs.Read(input, std::stoi(argv[2]), argv[1]);
  maze_bfs.Write();
  return 0;
};
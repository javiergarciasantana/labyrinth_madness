#include "dfs.h"

void DfsSolver::Read(std::ifstream& input, int ver, std::string text_file) {
  std::vector<int> aux;
  std::vector<std::vector<int> > matrix;
  int length, counter = 0;
  input.open(text_file, std::ios_base::in);
  if (input.is_open()) {
    std::string line;
    while (getline(input, line)) {
      for (int i = 0; i < line.size(); ++i) {
        if (line[i] != ' ') {
          aux.push_back(line[i] - '0');
        } 
      } 
      matrix.push_back(aux);
      aux.clear();
      ++length;
    }
    std::string first_num, second_num;
    first_num += line[0];
    if (line[1] != ' ') {
      first_num += line[1];
      for (int i = 3; i < line.size(); ++i) {
        second_num += line[i];
      }
    } else {
      for (int i = 2; i < line.size(); ++i) {
        second_num += line[i];
      }
    }
    int initial_x = std::stoi(first_num);
    int initial_y = std::stoi(second_num);

    int width = matrix[0].size();
    matrix.erase(matrix.end() - 1);

    ver_ = ver;
    length_ = length - 1;
    width_ = width;
    initial_x_ = initial_x - 1;
    initial_y_ = initial_y - 1;
    maze_.clear();
    maze_ = matrix;
    maze_[initial_y_][initial_x_] = step_;

  } else {
    std::cerr << "An error was produced opening the file" << std::endl;
    exit(EXIT_FAILURE);
  }
}

void DfsSolver::Write() {

  printData(std::cout);
  printData(output_file);
  printData(output_file_2);
  if (Solve(initial_x_, initial_y_, step_ + 1)) {
    printTable(std::cout);
    printTable(output_file);
    printTable(output_file_2);
  }
}

void DfsSolver::printData(std::ostream& out) const {
  out << "PART 1. Data\n" << "  1.1. Labyrinth." << std::endl << std::endl;
  
  printTable(out, false);
  out << "\n    1.2 Initial position: X=" << initial_x_ + 1 << ", Y=" 
  << initial_y_ + 1 << ", L=" << step_ << std::endl;
}

void DfsSolver::printTrace(std::ostream& out, int x_pos, int y_pos, int step, int move) const {
  out << "\n     " << trials_ << ") ";
  for (int i = 3; i < step; ++i) {
    out << "-";
  }
  out << "R" << move + 1 << ". U=" << x_pos + 1 << ", V=" << y_pos + 1 << ". L=" << step << ".";
}

void DfsSolver::printTable(std::ostream& out, bool header) const {
  int x_axis = length_;
  if (header) {
    out << "\nPART 3. Results \n" << "  3.1. Path is found." << std::endl;
    out << "  3.2. Path graphically:" << std::endl;
  }
  for (int i = length_ - 1; i >= 0; --i) {
    if (x_axis < 10) {
      out << " ";
    }
    out << "   " << x_axis << " |"; 
    --x_axis;
    for (int j = 0; j < width_; ++j) {
      if (maze_[i][j] / 10 < 1) {
        out << " ";
      }
      if (maze_[i][j] >= 0) {
        out << " " << maze_[i][j];
      } else {
        out << maze_[i][j];
      }

    }
    out << std::endl;
  }
    
  out << "     ------------------";
  for (int i = 5; i < width_; ++i) {
    out << "---";
  }
  out << "> X, U" << std::endl;
  out << "        ";
  for (int i = 1; i <= width_; ++i) {
    if (i >= 9) {
      out << i << " ";
    } else {
      out << i << "  ";
    }
  }
  out << std::endl;
  if (header) {
    out << "\n  3.3. Rules: ";
    for (int i = 0; i < rules_.size(); ++i) {
      out << "R" << rules_[i] << ", ";
    }
    out << std::endl;
    out << "\n  3.4. Nodes: ";
    for (int i = 0; i < nodes_.size(); ++i) {
      out << "[X=" << nodes_[i].first + 1 << ",Y=" << nodes_[i].second + 1 << "], ";
    }
    out << std::endl;
  }
}

bool DfsSolver::Move(int x_pos, int y_pos) {
  if (maze_[y_pos][x_pos] > 1) {
    output_file_2 << " Thread.\n";
  } else if (maze_[y_pos][x_pos] == 1) {
    output_file_2 << " Wall.\n";
  }
  return (x_pos >= 0 && y_pos >=0 
         && x_pos < width_  && y_pos < length_ 
         && maze_[y_pos][x_pos] < 1 
         && maze_[y_pos][x_pos] > -1);
}

int DfsSolver::Solve(int x_pos, int y_pos, int step) {

    if (Finished(x_pos, y_pos)) {
      return 1;
    }
    int x_next, y_next;

    for (int k = 0; k < 4; ++k) {
      x_next = x_pos + xMove[k];
      y_next = y_pos + yMove[k];
      ++trials_;

      printTrace(output_file_2, x_next, y_next, step, k);
      if (Move(x_next, y_next)) {
        maze_[y_next][x_next] = step;
        step_ = step;
        rules_.push_back(k + 1);
        nodes_.push_back(std::make_pair(x_next, y_next));
        // std::cout << "\n x: " << x_next + 1 << "  y: " << y_next + 1 << std::endl;
        // printTable(std::cout, false);
        output_file_2 << " Free. L:=L+1=" << step << ". LAB[" << x_next + 1
        << "," << y_next + 1 << "]:=" << step << ".\n";

        if (Solve(x_next, y_next, step + 1) == 1) {
          return 1;
        } else {
          output_file_2 << "         ";
          for (int i = 3; i < step; ++i) {
            output_file_2 << "-";
          }
          output_file_2 << "Backtrack from X=" << x_next + 1 << ", Y=" << y_next + 1 <<
          ", L=" << step << ". LAB[" << x_next + 1 << "," << y_next + 1 << "]:=-1. L:=L-1="
          << step - 1 << ".\n";
          if (ver_ == 1) {
            maze_[y_next][x_next] = -1;
          } else if (ver_ == 2) {
            maze_[y_next][x_next] = 0;
          }
          rules_.erase(rules_.end() - 1);
          nodes_.erase(nodes_.end() - 1);
        }
      } 
    }
    return 0;
  }
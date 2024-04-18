#include "bfs.h"

void BfsSolver::Read(std::ifstream& input, int ver, std::string text_file) {
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
    pred_.clear();
    maze_ = matrix;
    pred_ = matrix;
    maze_[initial_y_][initial_x_] = wave_;
    pred_[initial_y_][initial_x_] = -1;
  
    cola_.push(std::make_pair(initial_x_, initial_y_));//iniciamos el recorrido desde el nodo i+1

  } else {
    std::cerr << "An error was produced opening the file" << std::endl;
    exit(EXIT_FAILURE);
  }
}

void BfsSolver::Write() {

  printData(std::cout);
  printData(output_file);
  printData(output_file_2);
  if (Solve(wave_)) {
    TrailOfCrums();
    printTable(std::cout);
    printTable(output_file);
    printTable(output_file_2);
  }
}

int BfsSolver::Solve(int wave) {

  printTrace(output_file_2);
  --wave_;
  while (!cola_.empty()) {//al menos entra una vez al visitar el nodo i+1 y contin�a hasta que la cola_ se vac�e
      std::pair<int, int> k = cola_.front(); //cogemos el nodo k+1 de la cola_
      cola_.pop(); //lo sacamos de la cola_
      //Hacemos el recorrido sobre L desde el nodo k+1
      if (wave_ < maze_[k.second][k.first]) {
        ++wave_;
        output_file_2 << "\nWAVE " << wave_ - 1 << ", label L=" << '"' << wave_ + 1 << '"';
      }
      output_file_2 << "\n Close CLOSE=" << step_ << ", X=" << k.first + 1 << ", Y=" << k.second + 1 << ".\n";
      ++step_;
      for (unsigned j = 0; j < 4; ++j) {
        //Recorremos todos los nodos u adyacentes al nodo k+1
        //Si el nodo u no esta visitado
        output_file_2 << "   R" << j + 1 << ". X=" << k.first + xMove[j] + 1 << ", Y=" << k.second + yMove[j] + 1 << ". ";
        if (Move(k.first + xMove[j], k.second + yMove[j])) {
        // Lo visitamos
          ++n_nodes_;
          output_file_2 << "Free. NEWN=" << n_nodes_ << ". ";
        // Lo metemos en la cola_
          cola_.push(std::make_pair(k.first + xMove[j], k.second + yMove[j]));
        // Le calculamos su etiqueta distancia
          maze_[k.second + yMove[j]][k.first + xMove[j]] = maze_[k.second][k.first] + 1;
          pred_[k.second + yMove[j]][k.first + xMove[j]] = j;
        // Hemos terminado, nos encontramos en una casilla libre en el borde del maze
          if (Finished(k.first + xMove[j], k.second + yMove[j])) {
            final_x_ = k.first + xMove[j];
            final_y_ = k.second + yMove[j];
            output_file_2 << "Terminal.\n";
            return 1;
          }
        } 
        output_file_2 << std::endl;
      }
    }
  return 0;
}

void BfsSolver::TrailOfCrums() {
  int x_pos = final_x_;
  int y_pos = final_y_;
  while(pred_[y_pos][x_pos] != -1) {
    addToNodes(x_pos, y_pos);
    addToRules(pred_[y_pos][x_pos]);

    switch(pred_[y_pos][x_pos]) {
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

bool BfsSolver::Move(int x_pos, int y_pos) {
  if (maze_[y_pos][x_pos] == 1) {
    output_file_2 << "Wall.";
  } else if (maze_[y_pos][x_pos] > 1) {
    output_file_2 << "CLOSED OR OPEN.";
  }
  return (x_pos >= 0 && y_pos >=0 
         && x_pos < width_  && y_pos < length_ 
         && maze_[y_pos][x_pos] < 1 
         && maze_[y_pos][x_pos] > -1);
}

void BfsSolver::printData(std::ostream& out) {
  out << "PART 1. Data\n" << "  1.1. Labyrinth." << std::endl << std::endl;
  
  printTable(out, false);
  out << "\n    1.2 Initial position: X=" << initial_x_ + 1 << ", Y=" 
  << initial_y_ + 1 << ", L=" << wave_ << std::endl;
}

void BfsSolver::printTrace(std::ostream& out) {
  out << "\nPART 2. Trace" << std::endl;
  out << "\nWAVE " << 0 << ", label L=" << '"' << 2 << '"';
  out << ". Initial Position X=" << initial_x_ + 1 << ", Y=" << initial_y_ + 1 
      << ", NEWN=" << n_nodes_ << std::endl;
  
}

void BfsSolver::printTable(std::ostream& out, bool header) {
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
    
  out << "      ------------------";
  for (int i = 5; i < width_; ++i) {
    out << "---";
  }
  out << "> X, U" << std::endl;
  out << "         ";
  for (int i = 1; i <= width_; ++i) {
    if (i >= 9) {
      out << i << " ";
    } else {
      out << i << "  ";
    }
  }
  out << std::endl;
  if (header) {
    out << "\n  3.3. Rules:";

    for (int i = rules_.size() - 1; i >= 0; --i) {
      out << " R" << rules_[i] + 1 << ",";
    }
    out << std::endl;

    out << "\n  3.4. Nodes:";

    out << " [X=" << initial_x_ + 1 << ", Y=" << initial_y_ + 1 << "],";
    for (int i = nodes_.size() - 1; i >= 0; --i) {
      out << " [X=" << nodes_[i].first + 1 << ", Y=" << nodes_[i].second + 1 << "],";
    }
    out << std::endl;
  }
}
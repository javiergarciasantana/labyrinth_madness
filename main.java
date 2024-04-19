// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file
//

package labyrinth_madness;

import java.io.IOException;;

public class main {
  public static void main(String[] args) { 

    try {
      String filePath = "labyrinth_madness/inputs/input.txt";//Filepath
      BfsSolver solver = new BfsSolver();//Call the DFS default constructor(Just initialized 2 variables)

      solver.Read(filePath, 5, 4);//We call the read method to get the info from the
                                                      //textfile(need to change to just give matrix of integers)
                                                      //and we also give it the initial position of the explorer
      
      solver.Write();//Simply calls the solve() method and prints the result

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

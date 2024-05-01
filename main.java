// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file
//

package labyrinth_madness;

import java.io.IOException;
import java.util.Scanner;;

public class main {
  public static void main(String[] args) { 
    System.out.println("Welciome to LABYRINTH_MADNESS");
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Please select an option");
      System.out.println("[0]Exit");
      System.out.println("[1]Solve using DFS");
      System.out.println("[2]Solve using BFS");
      System.out.println("[h] Help");

      String input = scanner.nextLine();

      switch (input) {
          case "0":
              System.out.println("Goodbye!");
              return;
          case "1":
                DfsSolver solver_dfs = new DfsSolver();//Call the DFS default constructor(Just initialized 2 variables)
                solver_dfs.Write();//Simply calls the solve() method and prints the result

              break;
          case "2":
                BfsSolver solver_bfs = new BfsSolver();//Call the DFS default constructor(Just initialized 2 variables      
                solver_bfs.Write();//Simply calls the solve() method and prints the result
              break;
          case "h":
              System.out.println("No help at the moment");
              break;
          default:
              System.out.println("Please enter a valid option");
              break;
      }
    }
  }
}

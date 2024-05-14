// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file
//

package labyrinth_madness.src;

import java.util.Scanner;

import processing.core.PApplet;

public class Main {
  public static void main(String[] args) {

    Maze m = new Maze(7, 7);

    System.out.println("Welciome to LABYRINTH_MADNESS");
    Scanner scanner = new Scanner(System.in);

    System.out.println("Please select an option");
    System.out.println("[0]Exit");
    System.out.println("[1]Solve using DFS");
    System.out.println("[2]Solve using BFS");
    System.out.println("[h] Help");

    String input = scanner.nextLine();
    
    DfsSolver solver_dfs = null;
    BfsSolver solver_bfs = null;

    switch (input) {
      case "0":
        System.out.println("Goodbye!");
        return;
      case "1":
        solver_dfs = new DfsSolver(m, 5, 4);
        break;
      case "2":
        solver_bfs = new BfsSolver(m, 5, 4);
        break;
      case "h":
        System.out.println("No help at the moment");
        break;
      default:
        System.out.println("Please enter a valid option");
        break;
    }

    if (solver_dfs != null) {
      solver_dfs.Write();
    }

    if (solver_bfs != null) {
      solver_bfs.Write();
    }

    // printMatrix(m.getMatrix());
    String[] processingArgs = { "MySketch" };
    MySketch mySketch = new MySketch(m, solver_dfs);
    PApplet.runSketch(processingArgs, mySketch);
    

  }

  public static void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }
}

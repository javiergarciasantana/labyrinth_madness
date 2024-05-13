// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file
//

package labyrinth_madness;

import processing.core.PApplet;

public class main {
  public static void main(String[] args) {

    System.out.println("Welcome to LABYRINTH_MADNESS");

    // As soon as the program is started, an empty, default-sized maze is created
    Maze m = new Maze(6, 6);
    // And a solver is created
    DfsSolver solver = new DfsSolver(m, 4, 3);
    // NOTE: the size of the maze is hardcoded for now to 800x800

    // The sketch is started
    String[] processingArgs = { "MySketch" };
    MySketch mySketch = new MySketch(m, solver);
    PApplet.runSketch(processingArgs, mySketch);
    // NOTE: the solving is handled by the sketch itself,
    // repeatedly calling the solver.step() method
    // TODOO: allow the user to interact with the maze
    // by left-clicking on the squares to toggle their state
    // and right-clicking to set the starting point and solve the maze
    // We will temporarily use a default maze to debug
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

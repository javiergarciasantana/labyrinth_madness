// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file
//

package labyrinth_madness.src;

import processing.core.PApplet;

public class Main {
  public static void main(String[] args) {

    // NOTE: the size of the maze is hardcoded for now to 800x800
    String[] processingArgs = { "MySketch" };
    MySketch mySketch = new MySketch();
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

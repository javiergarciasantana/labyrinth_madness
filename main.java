package labyrinth_madness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import expression_simplifier.ExprSimplifier;

public class main {
  public static void main(String[] args) { 

    try {
      String filePath = "labyrinth_madness/input.txt";
      DfsSolver solver = new DfsSolver();
      solver.read(filePath, 2);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

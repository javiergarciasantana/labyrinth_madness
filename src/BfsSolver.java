// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the BfsSolver class is declared
//

package labyrinth_madness.src;

import java.util.*;

public class BfsSolver extends Solver {
  // Fields
  private int wave_;
  private Queue<TreeNode> queue_ = new LinkedList<>();
  private TreeNode root;

  // Constructors
  public BfsSolver(Maze m, int x, int y) {
    super(m, x, y, 2);
    wave_ = 1;
    root = new TreeNode(m.getSquare(x - 1, y - 1), null);
    queue_.add(root);
  }

  // Methods
  public void Write() {
    int result = 0;
    while (result == 0) {
      result = Solve();
    }
    root.printTree(root,""); // Print the tree after solving
  }

  private int Solve() {
    while (!queue_.isEmpty()) {
      TreeNode node = queue_.poll();
      Square square = node.getSquare();
      int x_pos = square.getX();
      int y_pos = square.getY();
      //System.out.println("Current position: " + x_pos + ", " + y_pos + " element: " + square.getState()) ;
      if (wave_ < square.getState()) {
        ++wave_;
        try {
          Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
          e.printStackTrace(); // Handle interruption if needed
        }
        printTable();
      }
      for (int j = 0; j < 4; ++j) {
        if (move(x_pos + xMove[j], y_pos + yMove[j])) {

          Square nextSquare = maze_.getSquare(x_pos + xMove[j], y_pos + yMove[j]);
          nextSquare.setState(square.getState() + 1);
          TreeNode childNode = new TreeNode(nextSquare, node);
          node.addChild(childNode);
          queue_.add(childNode);


          if (maze_.Finished(x_pos + xMove[j], y_pos + yMove[j])) {
            try {
              Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
            } catch (InterruptedException e) {
              e.printStackTrace(); // Handle interruption if needed
            }
            printTable();
            return 1;
          }
        }
      }
    }
    return 0;
  }
}

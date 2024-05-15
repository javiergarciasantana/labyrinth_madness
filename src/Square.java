// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the Square class is declared

package labyrinth_madness.src;

import java.util.List;

/**
 * Represents a square in the labyrinth.
 */
public class Square {
  private int state; // 1 wall, 0 free, -1 backtracked, >=2 solution
  private int x, y; // coordinates of the square
  private Square parent;
  private boolean visited;
  private boolean isSolution;

  /**
   * Constructs a square with the given state and coordinates.
   *
   * @param state The state of the square.
   * @param x     The x-coordinate of the square.
   * @param y     The y-coordinate of the square.
   */
  public Square(int state, int x, int y) {
    this.state = state;
    this.x = x;
    this.y = y;
    this.parent = null;
  }

  /**
   * Gets the state of the square.
   *
   * @return The state of the square.
   */
  public int getState() {
    return state;
  }

  /**
   * Sets the state of the square.
   *
   * @param state The state of the square.
   */
  public void setState(int state) {
    this.state = state;
  }

  /**
   * Switches the state of the square and returns the new state.
   */
  public int switchState() {
    if (state == 0) {
      state = 1;
    } else {
      state = 0;
    }
    return state;
  }

  /**
   * Gets the x-coordinate of the square.
   *
   * @return The x-coordinate of the square.
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the x-coordinate of the square.
   *
   * @param x The x-coordinate of the square.
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Gets the y-coordinate of the square.
   *
   * @return The y-coordinate of the square.
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the y-coordinate of the square.
   *
   * @param y The y-coordinate of the square.
   */
  public void setY(int y) {
    this.y = y;
  }

  public Square getParent() {
    return parent;
  }

  public void setParent(Square parent) {
    this.parent = parent;
  }

  // Method to print the tree
  public void printTree(TreeNode node, String indent) {
    if (node == null)
      return;
    System.out.println(indent + "└─ " + node.getSquare().getState()); // Print square data with indentation
    List<TreeNode> children = node.getChildren();
    for (int i = 0; i < children.size(); i++) {
      TreeNode child = children.get(i);
      // Adjust indentation based on the position of the child
      String childIndent = indent + (i == children.size() - 1 ? "    " : "│   ");
      printTree(child, childIndent); // Recursively print children with updated indentation
    }
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public void setIsSolution(boolean isSolution) {
    this.isSolution = isSolution;
  }

  public void rebuildPathToOrigin() {
    isSolution = true;
    if (parent != null) {
      parent.setIsSolution(true);
      parent.rebuildPathToOrigin();
    }
  }

  public boolean isWall() {
    return state == 1;
  }

  public boolean isFree() {
    return state == 0;
  }

  public boolean isBacktracked() {
    return state == -1;
  }

  public boolean isInList() {
    return state >= 2;
  }

  public boolean isSolution() {
    return isSolution;
  }

  public boolean isVisited() {
    return visited;
  }
}

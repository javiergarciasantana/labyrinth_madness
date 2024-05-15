// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the DfsSolver class is declared
//

package labyrinth_madness.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a solver for solving mazes using Depth-First Search algorithm.
 */
public class DfsSolver extends Solver {

  // Fields
  private int step_;
  private Maze maze_;

  // Instead of specifying the initial x and y coordinates
  // And updating them locally in solve()
  // private int initial_x_, initial_y_;
  // We store the actual step here
  // private Square getCurrent();
  // !!! We actually don't need this variable, because we can get the current
  // position from the last element of the nodes_ list

  private List<Square> nodes_ = new ArrayList<>();
  private List<Integer> rules_ = new ArrayList<>();
  private int move[][] = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
  private boolean isBacktracking = false;

  /**
   * Parametric constructor for DfsSolver.
   *
   * @param m The maze.
   * @param x The initial x-coordinate.
   * @param y The initial y-coordinate.
   */
  // We are working with the real coordinates (from 0)
  // and not the professor's ones (from 1)
  public DfsSolver(Maze m, int x, int y) {
    super(m, x, y, 2);
    step_ = 2;
    // Add the initial position to the list of nodes
    // TODOO: check if the initial position is valid
    // or if we need to default to the first free square
    nodes_.add(maze_.getSquare(x, y));
    getCurrent().setState(step_);
  }

  // Here we do the same as in the solve() method,
  // but the rules are applied one by one
  public void step() {
    // If the current position is the exit, return
    if (maze_.isEdge(getCurrent())) {
      maze_.printMatrix(); // Debugging
      return;
    }
    int start;
    // Otherwise, keep looking for the exit
    if (!isBacktracking) {
      // If we are not backtracking, we apply the rules from the first one
      start = 0;
    } else {
      // But if we are backtracking, it means that we have already tried some rules
      // Specifically, the last rule in the list is the inverse of the one that led us
      // to the dead end
      // So we start from the successive to its inverse
      start = (getInverseRule(rules_.get(rules_.size() - 1)) + 1) % 4;
      // Then we remove it, cause we are not going to use it anymore and
      // it is not part of the solution
      rules_.remove(rules_.size() - 1);
    }
    for (int k = start; k < 4; ++k) {
      // Calculate the next square
      Square nextSquare = moveSquare(getCurrent(), move[k]);
      // If the move is allowed...
      if (nextSquare != null && nextSquare.isFree()) {
        // Move to the next position
        forwardStep(nextSquare, k);
        maze_.printMatrix(); // Debugging
        return;
      }
    }
    // If we are here, it means that we have tried all the possible moves
    // And none of them is valid
    // So we need to backtrack
    backtrackStep();
    maze_.printMatrix(); // Debugging
    return;
  }

  /**
   * Returns the list of nodes.
   *
   * @return The list of nodes.
   */
  public List<Square> getNodes() {
    return nodes_;
  }

  // Returns the last node, i.e., the current position
  private Square getCurrent() {
    return nodes_.get(nodes_.size() - 1);
  }

  private int getInverseRule(int rule) {
    return (rule + 2) % 4;
  }

  // Gets the new square after applying the rule
  // Null if the move is not possible
  private Square moveSquare(Square s, int rule[]) {
    return maze_.getSquare(s.getX() + rule[0], s.getY() + rule[1]);
  }

  private void backtrackStep() {
    // Set the state of the dead end to -1
    getCurrent().setState(-1);
    // Go back to the last position where we had a choice (the previous node)
    // Note that we just remove the last node,
    // So that the new head (that we use as current position)
    // will be the previous node
    nodes_.remove(nodes_.size() - 1);
    // And set the backtracking flag to true
    isBacktracking = true;
    // NOTE: we don't remove the last rule
    // Because we need it to know which rule led us to the dead end
    // And reverse it
    // We will remove it in the step() method
  }

  private void forwardStep(Square nextSquare, int ruleIndex) {
    // Move to the next position
    // Which means add the node to the list of nodes
    nodes_.add(nextSquare);
    // Increment the step number
    step_++;
    // Update the state of the square
    getCurrent().setState(step_);
    // Add the rule to the list of rules
    rules_.add(ruleIndex);
    // Update the backtracking flag
    isBacktracking = false;
  }

}

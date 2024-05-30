// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the DfsSolver class is declared

package labyrinth_madness.src;

/**
 * Represents a solver for solving mazes using Depth-First Search algorithm.
 */
public class DfsSolver extends Solver {

  /**
   * Constructs a new DfsSolver object with the specified maze, starting position,
   * and search depth.
   *
   * @param m The maze to be solved.
   * @param x The x-coordinate of the starting position.
   * @param y The y-coordinate of the starting position.
   */
  public DfsSolver(Maze m, Square s) {
    super(m, s);
  }

  /**
   * Performs a single step of the DFS algorithm to solve the maze.
   *
   * @return true if a solution is found, false otherwise.
   */
  public boolean Step() {

    if (solution != null) {
      solution.setVisited(true);
      return true;
    }

    Square currentSquare = popCurrent();

    if (currentSquare == null) {
      return false;
    }

    boolean isDeadEnd = true;
    currentSquare.setVisited(true);
    for (int k = 0; k < 4; ++k) {
      Square nextSquare = moveSquare(currentSquare, moves[k]);
      if (nextSquare != null && nextSquare.isFree()) {
        isDeadEnd = false;
        nextSquare.setState(currentSquare.getState() + 1);
        pushNext(nextSquare);
        nextSquare.setParent(currentSquare);
        if (maze_.isEdge(nextSquare)) {
          solution = nextSquare;
          return true;
        }
      }
    }

    if (isDeadEnd) {
      backtrackUntilNextNode(currentSquare);
    }

    return false;
  }

  /**
   * Backtracks from the current square until the next node in the stack is
   * reached. Sets the state of the squares to -1.
   *
   * @param current The current square.
   */
  public void backtrackUntilNextNode(Square current) {
    Square deadend = current;
    while (deadend != null && deadend.getState() != getCurrentSquare().getState() - 1) {
      deadend.setState(-1);
      System.out.println("Backtracking: " + deadend.getX() + " " + deadend.getY());
      deadend = deadend.getParent();
    }
    return;
  }

  /**
   * Returns the current square without removing it from the stack.
   *
   * @return The current square, or null if the stack is empty.
   */
  public Square getCurrentSquare() {
    return nodes_.peekLast();
  }

  /**
   * Pushes the specified square onto the stack.
   *
   * @param next The square to be pushed.
   */
  private void pushNext(Square next) {
    nodes_.offerLast(next);
  }

  /**
   * Pops the top square from the stack.
   *
   * @return The popped square, or null if the stack is empty.
   */
  private Square popCurrent() {
    return nodes_.pollLast();
  }

}

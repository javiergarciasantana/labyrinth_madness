// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the BfsSolver class is declared

package labyrinth_madness.src;

/**
 * This class represents a Breadth-First Search (BFS) solver for a maze.
 * It extends the Solver class and implements the solving algorithm using BFS.
 */
public class BfsSolver extends Solver {

  /**
   * Constructs a new BfsSolver object with the specified maze and starting
   * position.
   *
   * @param m The maze to be solved.
   * @param x The x-coordinate of the starting position.
   * @param y The y-coordinate of the starting position.
   */
  public BfsSolver(Maze m, int x, int y) {
    super(m, x, y, 2);
  }

  /**
   * Performs a single step of the BFS solving algorithm.
   *
   * @return true if the solution is found, false otherwise.
   */
  public boolean Step() {
    if (solution != null) {
      return true;
    }
    Square currentSquare = dequeueCurrent();
    if (currentSquare == null) {
      return false;
    }
    currentSquare.setVisited(true);
    for (int k = 0; k < 4; ++k) {
      Square nextSquare = moveSquare(currentSquare, moves[k]);
      if (nextSquare != null && nextSquare.isFree()) {
        nextSquare.setState(currentSquare.getState() + 1);
        enqueueNext(nextSquare);
        nextSquare.setParent(currentSquare);
        if (maze_.isEdge(nextSquare)) {
          solution = nextSquare;
          solution.setVisited(true);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the current square without removing it from the queue.
   *
   * @return The current square, or null if the queue is empty.
   */
  public Square getCurrentSquare() {
    return nodes_.peekLast();
  }

  /**
   * Appends the next square to be processed at the head of the deque.
   *
   * @param next, the next square to be processed.
   *
   */
  private void enqueueNext(Square next) {
    nodes_.offerFirst(next);
  }

  /**
   * Removes the current square from the tail of the deque.
   *
   * @return The current square, null if empty.
   */
  private Square dequeueCurrent() {
    return nodes_.pollLast();
  }
}

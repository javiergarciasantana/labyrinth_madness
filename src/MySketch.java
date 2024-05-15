// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the MySketch class is declared

package labyrinth_madness.src;

import processing.core.PApplet;

/**
 * Represents the sketch for the labyrinth visualization.
 */
public class MySketch extends PApplet {
  private Maze m;
  private Solver solver;
  private int stepX;
  private int stepY;
  // HARDCODED
  private int updatingFrames = 20;
  // private boolean isBeingSolved = false;

  // NOTE: the size of the maze is hardcoded for now to 800x800
  private int width = 800;
  private int height = 800;

  private int[] solverPosition = { 0, 0 };
  private boolean mazeSolved = false;

  /**
   * Constructs a MySketch object with the given maze and solver.
   *
   * @param m      The maze.
   * @param solver The solver for the maze.
   */
  MySketch(Maze m, Solver solver) {
    this.m = m;
    this.solver = solver;
    stepX = width / m.getWidth();
    stepY = height / m.getHeight();
    System.out.println("Width: " + width + " Height: " + height);
    System.out.println("Maze Width: " + m.getWidth() + " Maze Height: " + m.getHeight());
    System.out.println("StepX: " + stepX + " StepY: " + stepY);
  }

  public void settings() {
    size(800, 800);
  }

  public void draw() {
    background(150);
    drawGrid();

    if (frameCount % updatingFrames == 0) {
      if (!mazeSolved) {
        mazeSolved = solver.Step();
      } else {
        Square solution = solver.getSolution();
        solution.rebuildPathToOrigin();
      }
    }

    // Render the maze
    renderMaze();

    renderSolver();

  }

  public void renderMaze() {
    for (int i = 0; i < m.getWidth(); i++) {
      for (int j = 0; j < m.getHeight(); j++) {
        renderSquare(m.getSquare(i, j));
      }
    }
  }

  public void renderSquare(Square s) {
    pushMatrix();
    int pos[] = calculateSquarePos(s);
    translate(pos[0], pos[1]);
    if (s.isWall()) {
      fill(0);
    } else if (s.isFree()) {
      fill(255);
    } else {
      if (s.isSolution()) {
        fill(0, 255, 0);
      } else if (s.isVisited()) {
        fill(0, 0, 255);
      } else if (s.isInList()) {
        fill(0, 255, 255);
      }
      if (s.isBacktracked()) {
        fill(255, 0, 0);
      }

    }

    rect(0, 0, stepX, stepY);
    popMatrix();
  }

  private void drawGrid() {
    pushMatrix();
    strokeWeight(2);
    stroke(255, 0, 0);

    pushMatrix();
    for (int i = 0; i < m.getWidth(); i++) {
      translate(stepX, 0);
      line(0, 0, 0, height);
    }
    popMatrix();
    pushMatrix();
    for (int i = 0; i < m.getHeight(); i++) {
      translate(0, stepY);
      line(0, 0, width, 0);
    }
    popMatrix();

    popMatrix();
  }

  public int[] calculateSquarePos(Square s) {
    int[] pos = new int[2];
    pos[0] = s.getX() * stepX;
    pos[1] = s.getY() * stepY;
    return pos;
  }

  public void renderSolver() {
    Square currentSquare;
    if (solver.getNodes().size() > 0 && solver.solution == null) {
      currentSquare = solver.getCurrentSquare();
      solverPosition[0] = currentSquare.getX();
      solverPosition[1] = currentSquare.getY();
    }
    if (solver.solution != null) {
      solverPosition[0] = solver.getSolution().getX();
      solverPosition[1] = solver.getSolution().getY();
    }
    pushMatrix();
    translate(solverPosition[0] * stepX + stepX / 2, solverPosition[1] * stepY + stepY / 2);
    circle(0, 0, 20);
    popMatrix();
  }

}

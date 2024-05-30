// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the MySketch class is declared

package labyrinth_madness.src;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

/**
 * Represents the sketch for the labyrinth visualization.
 */
public class MySketch extends PApplet {

  private Maze m;
  private Solver solver;
  private int stepX;
  private int stepY;
  private int updatingFrames = 40;

  private int width = 605;
  private int height = 700;

  private int[] solverPosition = { 0, 0 };
  private boolean mazeSolved = false;
  private boolean wallsPlaced = false;
  private boolean solverStarted = false;
  private boolean solverPlaced = false;
  PImage solverSprite;

  /**
   * Constructs a MySketch object with the given maze and solver.
   *
   * @param m      The maze.
   * @param solver The solver for the maze.
   */
  MySketch() {
    this.m = new Maze(7, 7);
    stepX = (width - 5) / m.getWidth();
    stepY = (height - 100) / m.getHeight();
    System.out.println("Width: " + width + " Height: " + height);
    System.out.println("Maze Width: " + m.getWidth() + " Maze Height: " + m.getHeight());
    System.out.println("StepX: " + stepX + " StepY: " + stepY);
  }

  /**
   * Sets the size of the sketch window.
   */
  public void settings() {
    size(width, height);
  }

  /**
   * Sets up the sketch.
   */
  public void setup() {
    PFont font;
    font = createFont("Arial", 50, true);
    textFont(font);
    frameRate(60);
  }

  /**
   * Draws the sketch.
   */
  public void draw() {
    translate(5, 5);
    background(0);
    fill(255);
    rect(0, height - 100, width - 10, 90);

    if (solverStarted) {
      if (frameCount % updatingFrames == 0) {
        if (!mazeSolved) {
          mazeSolved = solver.Step();
        } else {
          Square solution = solver.getSolution();
          solution.rebuildPathToOrigin();
        }
      }
      renderMaze();
      renderSolver();
    } else {
      renderMaze();
      renderChoiceButton();
      if (solverPlaced) {
        renderSolver();
      }
    }
    // Render the maze

  }

  /**
   * Gets the square at the current mouse position.
   *
   * @return The square at the mouse position.
   */
  private Square getSquareAtMousePosition() {
    int x = mouseX / stepX;
    int y = mouseY / stepY;
    return m.getSquare(x, y);
  }

  /**
   * Renders the maze.
   */
  public void renderMaze() {
    for (int i = 0; i < m.getWidth(); i++) {
      for (int j = 0; j < m.getHeight(); j++) {
        renderSquare(m.getSquare(i, j));
      }
    }
  }

  /**
   * Renders the choice button.
   */
  public void renderChoiceButton() {
    pushMatrix();
    fill(0);
    textSize(20);
    if (!solverPlaced) {
      text("Press LEFT button of your mouse to create or destroy a wall", 4, height - 70);
      text("Press RIGHT button of your mouse to set the starting point", 4, height - 50);
    } else {
      text("Press 'b' to start BFS", 4, height - 70);
      text("Press 'd' to start DFS", 4, height - 50);
      text("Press 'r' to choose another starting point", 4, height - 30);

    }
    popMatrix();
  }

  /**
   * Renders a square.
   *
   * @param s The square to render.
   */
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
        fill(32, 193, 0);
      } else if (s.isVisited()) {
        fill(8, 14, 169);
      } else if (s.isInList()) {
        fill(166, 169, 255);
      }
      if (s.isBacktracked()) {
        fill(255, 0, 0);
      }

    }

    rect(0, 0, stepX, stepY);
    popMatrix();
  }

  /**
   * Calculates the position of a square.
   *
   * @param s The square.
   * @return The position of the square.
   */
  public int[] calculateSquarePos(Square s) {
    int[] pos = new int[2];
    pos[0] = s.getX() * stepX;
    pos[1] = s.getY() * stepY;
    return pos;
  }

  /**
   * Renders the solver.
   */
  public void renderSolver() {
    Square currentSquare;
    if (!solverStarted) {
    } else if (solver.getNodes().size() > 0 && solver.solution == null) {
      currentSquare = solver.getCurrentSquare();
      solverPosition[0] = currentSquare.getX();
      solverPosition[1] = currentSquare.getY();
    } else if (solver.solution != null) {
      solverPosition[0] = solver.getSolution().getX();
      solverPosition[1] = solver.getSolution().getY();
    }

    pushMatrix();

    fill(0);
    translate(solverPosition[0] * stepX, solverPosition[1] * stepY);
    circle(stepX / 2, stepY / 2, 20);
    popMatrix();
  }

  /**
   * Handles the mouse press event.
   */
  public void mousePressed() {
    if (!wallsPlaced) {
      Square s = getSquareAtMousePosition();
      if (mouseButton == LEFT) {
        s.invertState();
      } else if (mouseButton == RIGHT) {
        if (!solverStarted && !s.isWall()) {
          solverPosition[0] = s.getX();
          solverPosition[1] = s.getY();
          wallsPlaced = true;
          solverPlaced = true;
        }
      }

    }
  }

  /**
   * Handles the key press event.
   */
  public void keyPressed() {
    if (key == 'r') {
      m.resetSolution();
      wallsPlaced = false;
      mazeSolved = false;
      solverStarted = false;
      solverPlaced = false;
    } else if (key == 'd' && wallsPlaced && !solverStarted) {
      Square s = m.getSquare(solverPosition[0], solverPosition[1]);
      this.solver = new DfsSolver(m, s);
      solverStarted = true;
    } else if (key == 'b' && wallsPlaced && !solverStarted) {
      Square s = m.getSquare(solverPosition[0], solverPosition[1]);
      this.solver = new BfsSolver(m, s);
      solverStarted = true;
    }
  }
}

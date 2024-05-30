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
  // HARDCODED
  private int updatingFrames = 20;
  // private boolean isBeingSolved = false;

  private int width = 605;
  private int height = 700;

  private int[] solverPosition = { 0, 0 };
  private boolean mazeSolved = false;
  private boolean wallsPlaced = false;
  private boolean solverStarted = false;

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

  public void settings() {
    size(width, height);
    solverSprite = loadImage(getClass().getResource("solver.png").getPath());
  }

  public void setup() {
    PFont font;
    font = createFont("Arial", 50, true);
    textFont(font);
    frameRate(60);
  }

  public void draw() {
    translate(5, 5);
    background(0);
    if (wallsPlaced) {
      if (frameCount % updatingFrames == 0) {
        if (!mazeSolved) {
          if (solverStarted) {
            mazeSolved = solver.Step();
          }
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

    }
    // Render the maze

  }

  private Square getSquareAtMousePosition() {
    int x = mouseX / stepX;
    int y = mouseY / stepY;
    return m.getSquare(x, y);
  }

  public void renderMaze() {
    for (int i = 0; i < m.getWidth(); i++) {
      for (int j = 0; j < m.getHeight(); j++) {
        renderSquare(m.getSquare(i, j));
      }
    }
  }

  public void renderChoiceButton() {
    pushMatrix();
    fill(255);
    rect(0, height - 100, width, 100);
    fill(0);
    textSize(20);
    text("Press LEFT button of your mouse to create or destroy a wall", 4, height - 70);
    text("Press RIGHT button of your mouse to set the starting point", 4, height - 50);
    text("Press 'r' to choose another starting point", 4, height - 30);
    popMatrix();
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

  public int[] calculateSquarePos(Square s) {
    int[] pos = new int[2];
    pos[0] = s.getX() * stepX;
    pos[1] = s.getY() * stepY;
    return pos;
  }

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

    fill(255);
    translate(solverPosition[0] * stepX, solverPosition[1] * stepY);
    // circle(0, 0, 20);
    image(solverSprite, 0, 0, stepX, stepY);
    popMatrix();
  }

  public void mousePressed() {
    if (!wallsPlaced) {
      Square s = getSquareAtMousePosition();
      if (mouseButton == LEFT) {
        s.invertState();
      } else if (mouseButton == RIGHT) {
        if (!solverStarted) {
          solverPosition[0] = s.getX();
          solverPosition[1] = s.getY();
        }
        wallsPlaced = true;
      }

    }
  }

  public void keyPressed() {
    if (key == 'r') {
      m.reset();
      wallsPlaced = false;
      mazeSolved = false;
      solverStarted = false;
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

// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the MySketch class is declared

package labyrinth_madness;

import processing.core.PApplet;
import java.util.List;

/**
 * Represents the sketch for the labyrinth visualization.
 */
public class MySketch extends PApplet {
  private Maze m;
  private DfsSolver solver;
  private int stepX;
  private int stepY;
  // HARDCODED
  private int updatingFrames = 50;
  // private boolean isBeingSolved = false;

  // NOTE: the size of the maze is hardcoded for now to 800x800
  private int width = 800;
  private int height = 800;

  /**
   * Constructs a MySketch object with the given maze and solver.
   *
   * @param m      The maze.
   * @param solver The solver for the maze.
   */
  MySketch(Maze m, DfsSolver solver) {
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
    // Draw the grid
    drawGrid();
    // Step ahead in the solver
    // This step updates the state of the maze every time
    if (frameCount % updatingFrames == 0) {
      solver.step();
    }
    // Render the maze
    renderMaze();
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
    if (s.getState() == 1) {
      fill(0);
    } else if (s.getState() == 0) {
      fill(255);
    } else if (s.getState() == -1) {
      fill(255, 0, 0);
    } else {
      fill(0, 255, 0);
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

  /**
   * Displays the live path on the maze.
   *
   * @param path The path to display.
   */
  public void displayLivePath(List<Square> path) {
    for (int i = 0; i < path.size(); i++) {
      Square s = path.get(i);
      colorSquare(s, 0, 255, 0);
    }
  }

  /**
   * Colors a square with the given RGB values.
   *
   * @param s The square to color.
   * @param r The red component.
   * @param g The green component.
   * @param b The blue component.
   */
  public void colorSquare(Square s, int r, int g, int b) {
    pushMatrix();
    stroke(255, 0, 0);
    translate(s.getX() * stepX, s.getY() * stepY);
    fill(r, g, b);
    rect(0, 0, stepX, stepY);
    popMatrix();
  }

  public int[] calculateSquarePos(Square s) {
    int[] pos = new int[2];
    pos[0] = s.getX() * stepX;
    pos[1] = s.getY() * stepY;
    return pos;
  }

}

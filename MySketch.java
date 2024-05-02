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

  private DfsSolver solver;
  private Maze m;
  private int stepX;
  private int stepY;
  private int stepsOverThousand = 1;

  /**
   * Constructs a MySketch object with the given maze and solver.
   *
   * @param m      The maze.
   * @param solver The solver for the maze.
   */
  MySketch(Maze m, DfsSolver solver) {
    this.m = m;
    this.solver = solver;
    stepX = 800 / this.m.getWidth();
    stepY = 800 / this.m.getHeight();
  }

  public void settings() {
    size(800, 800);
  }

  public void draw() {
    background(150);
    drawGrid();
    renderMaze();
    displayLivePath(solver.getNodes().subList(0, stepsOverThousand));
    if (frameCount % 60 == 0 && stepsOverThousand < solver.getNodes().size()) {
      stepsOverThousand++;

    }
  }

  public void renderMaze() {
    for (int i = 0; i < m.getWidth(); i++) { // Iterating over rows
      for (int j = 0; j < m.getHeight(); j++) { // Iterating over columns
        Square s = m.getSquare(i, j);
        if (s.getState() == 1) {
          colorSquare(s, 0, 0, 0);
        } else {
          colorSquare(s, 255, 255, 255);
        }
      }
    }
  }

  private void drawGrid() {
    pushMatrix();
    strokeWeight(4);
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

}

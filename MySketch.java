package maze;

import processing.core.PApplet;

public class MySketch extends PApplet {

  private Maze m;
  private int stepX;
  private int stepY;

  MySketch(Maze m) {
    this.m = m;
    stepX = 800 / this.m.getWidth();
    stepY = 800 / this.m.getHeight();
  }

  public void settings() {
    size(800, 800);
  }

  public void draw() {
    background(150);
    pushMatrix();
    renderMatrix();
    popMatrix();
  }

  public void renderMatrix() {
    drawGrid();
    renderSquares();
  }

  public void renderSquares() {
    for (int i = 0; i < m.getWidth(); i++) { // Iterating over rows
      for (int j = 0; j < m.getHeight(); j++) { // Iterating over columns
        displaySquare(m.getSquare(i, j));
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

  // Display the square
  public void displaySquare(Square s) {
    pushMatrix();
    stroke(255, 0, 0);
    translate(s.getX() * stepX, s.getY() * stepY);
    // If the square is a wall, fill it with black
    // If the square is free, fill it with white
    // If the square is backtracked, fill it with gray
    // Otherwise it is solution, fill it with green
    if (s.getState() == 1) {
      fill(0);
    } else if (s.getState() == 0) {
      fill(255);
    } else if (s.getState() == -1) {
      fill(125);
    } else {
      fill(0, 255, 0);
    }
    rect(0, 0, stepX, stepY);
    popMatrix();
  }

}

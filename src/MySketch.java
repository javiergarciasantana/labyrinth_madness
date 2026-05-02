// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: File where the MySketch class is declared

package labyrinth_madness.src;

import processing.core.PApplet;
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

  private int width = 608;
  private int height = 700;

  private int[] solverPosition = { 0, 0 };
  private boolean mazeSolved = false;
  private boolean wallsPlaced = false;
  private boolean solverStarted = false;
  private boolean solverPlaced = false;
  
  // New variables for UI and dragging
  private int mazeSize = 7;
  private boolean draggingSlider = false;
  private int dragColor = 1; // 1 to draw a wall, 0 to erase

  /**
   * Constructs a MySketch object with the given maze and solver.
   */
  MySketch() {
    this.m = new Maze(mazeSize, mazeSize);
    stepX = (width - 5) / m.getWidth();
    stepY = (height - 100) / m.getHeight();
    System.out.println("Width: " + width + " Height: " + height);
    System.out.println("Maze Width: " + m.getWidth() + " Maze Height: " + m.getHeight());
    System.out.println("StepX: " + stepX + " StepY: " + stepY);
  }

  public void settings() {
    size(width, height);
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
    fill(255);
    // UI Panel at the bottom
    rect(0, height - 100, width - 10, 90);

    if (solverStarted) {
      if (frameCount % updatingFrames == 0) {
        if (!mazeSolved) {
          mazeSolved = solver.Step();
        } else {
          Square solution = solver.getSolution();
          if (solution != null) {
            solution.rebuildPathToOrigin();
          }
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
    fill(0);
    if (!wallsPlaced) {
      textSize(16);
      text("Left click/drag: toggle walls  |  Right click: set starting point", 4, height - 75);
      text("Leave a free tile on the perimeter for the exit. Press 'S' to confirm.", 4, height - 55);
      
      // Draw the slider
      fill(200);
      rect(10, height - 35, 200, 10);
      fill(100);
      float knobX = map(mazeSize, 5, 40, 10, 210);
      rect(knobX - 5, height - 40, 10, 20);
      fill(0);
      textSize(16);
      text("Maze Size: " + mazeSize, 230, height - 25);
    } else {
      textSize(20);
      text("Press 'b' to start BFS", 4, height - 70);
      text("Press 'd' to start DFS", 4, height - 50);
      text("Press 'r' to restart setup", 4, height - 30);
    }
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
      // The starting point is already set in solverPosition by the mouse
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
    // Use ellipse instead of circle for safe Docker compatibility
    ellipse(stepX / 2.0f, stepY / 2.0f, 20, 20);
    popMatrix();
  }

  public void mousePressed() {
    if (!wallsPlaced) {
      // If the mouse is in the bottom UI area
      if (mouseY >= height - 100) {
        if (mouseX >= 10 && mouseX <= 220 && mouseY >= height - 45 && mouseY <= height - 15) {
          draggingSlider = true;
        }
        return;
      }

      Square s = getSquareAtMousePosition();
      if (s == null) return;

      if (mouseButton == LEFT) {
        // Avoid overwriting the starting point with a wall
        if (solverPlaced && s.getX() == solverPosition[0] && s.getY() == solverPosition[1]) return;
        
        dragColor = s.isWall() ? 0 : 1; // If it's a wall, the brush erases. If free, it paints.
        s.setState(dragColor);
      } else if (mouseButton == RIGHT) {
        if (!s.isWall()) {
          solverPosition[0] = s.getX();
          solverPosition[1] = s.getY();
          solverPlaced = true;
        }
      }
    }
  }

  public void mouseDragged() {
    // Logic to drag the size slider
    if (draggingSlider) {
      float val = map(mouseX, 10, 210, 5, 40);
      int newSize = constrain(Math.round(val), 5, 40);
      if (newSize != mazeSize) {
        mazeSize = newSize;
        m = new Maze(mazeSize, mazeSize);
        stepX = (width - 5) / m.getWidth();
        stepY = (height - 100) / m.getHeight();
        solverPlaced = false;
      }
      return;
    }

    // Logic to paint/erase walls when dragging
    if (!wallsPlaced && mouseButton == LEFT) {
      if (mouseY >= height - 100) return; // Do not paint over the menu
      
      Square s = getSquareAtMousePosition();
      if (s != null) {
        if (solverPlaced && s.getX() == solverPosition[0] && s.getY() == solverPosition[1]) return;
        s.setState(dragColor);
      }
    }
  }

  public void mouseReleased() {
    draggingSlider = false;
  }

  public void keyPressed() {
    // If we are in setup and press 's'
    if (!wallsPlaced && (key == 's' || key == 'S')) {
      if (solverPlaced) {
        wallsPlaced = true; // Ends the setup phase and shows the BFS/DFS menu
      }
    } 
    // Reset the program
    else if (key == 'r' || key == 'R') {
      m.resetSolution();
      wallsPlaced = false;
      mazeSolved = false;
      solverStarted = false;
      solverPlaced = false;
    } 
    // Start BFS or DFS
    else if (key == 'd' && wallsPlaced && !solverStarted) {
      Square s = m.getSquare(solverPosition[0], solverPosition[1]);
      this.solver = new DfsSolver(m, s);
      solverStarted = true;
    } 
    else if (key == 'b' && wallsPlaced && !solverStarted) {
      Square s = m.getSquare(solverPosition[0], solverPosition[1]);
      this.solver = new BfsSolver(m, s);
      solverStarted = true;
    }
  }
}
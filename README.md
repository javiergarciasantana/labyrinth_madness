# HOW TO USE THE DFS & BFS CLASS

## Precautions:
- **File Reading Issue:** There's a known problem with the text file reader that prevents it from reading the last line. As a workaround, add a line with all ones at the end of the file.

## File Format:
- **Matrix Orientation:** Matrices in the text files are presented upside down due to the AI format, but this won't affect the functionality.

## Interacting with the DFS & BFS Class:
To utilize the DFS & BFS class from your main file, follow these steps:

```java
package labyrinth_madness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import expression_simplifier.ExprSimplifier;

public class main {
  public static void main(String[] args) { 

    try {
      String filePath = "labyrinth_madness/input_2.txt";//Filepath
      DfsSolver solver = new DfsSolver();//Call the DFS default constructor, also---->BfsSolver solver = new BfsSolver()
      solver.read(filePath, 5, 4);//We call the read method to get the info from the
                                                  //textfile and we also give it the initial position of the explorer
      solver.write();//Simply calls the solve() method and prints the result

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
```
It is also interesting to know how the read(String,int,int) method works. This creates a matrix and changes the previously initialized maze to the matrix extracted from the textfile.

## IMPORTANT:
There is also a parametric constructor for the DFS & BFS class:
```java
public DfsSolver(List<List<Integer>> matrix, int l, int w, int x, int y) {
    super(matrix, l, w, x, y);
    trials_ = 0;
    step_ = 2;
    maze_.get(initial_y_).set(initial_x_, step_);
    nodes.add(Map.entry(initial_x_, initial_y_));
  }


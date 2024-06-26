// AUTOR: Javier Garcia Santana & Valerio Siniscalco
// DATE: 19/04/2024
// VERSION: 4.0
// COURSE: OOP
// NAME: Labyrinth Madness
// COMMENTS: Main file

package labyrinth_madness.src;

import processing.core.PApplet;

public class Main {
  public static void main(String[] args) {
    String[] processingArgs = { "MySketch" };
    MySketch mySketch = new MySketch();
    PApplet.runSketch(processingArgs, mySketch);
  }
}

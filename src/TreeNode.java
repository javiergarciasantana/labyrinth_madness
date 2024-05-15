package labyrinth_madness.src;

import java.util.ArrayList;
import java.util.List;

// TreeNode class to represent each node in the tree
class TreeNode {
  private Square square;
  private TreeNode parent;
  private List<TreeNode> children;

  public TreeNode(Square square, TreeNode parent) {
    this.square = square;
    this.parent = parent;
    this.children = new ArrayList<>();
  }

  public Square getSquare() {
    return square;
  }

  public TreeNode getParent() {
    return parent;
  }

  public List<TreeNode> getChildren() {
    return children;
  }

  public void addChild(TreeNode child) {
    children.add(child);
  }
  // Method to print the tree
  public void printTree(TreeNode node, String indent) {
    if (node == null) return;
    System.out.println(indent + "└─ " + node.getSquare().getState()); // Print square data with indentation
    List<TreeNode> children = node.getChildren();
    for (int i = 0; i < children.size(); i++) {
      TreeNode child = children.get(i);
      String childIndent = indent + (i == children.size() - 1 ? "    " : "│   "); // Adjust indentation based on the position of the child
      printTree(child, childIndent); // Recursively print children with updated indentation
    }
  }
}
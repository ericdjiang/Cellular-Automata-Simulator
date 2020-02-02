package cellsociety;

import java.util.ArrayList;

public class Model {
  private ArrayList<ArrayList<Cell>> myGrid;

  public Model(ArrayList<ArrayList<Cell>> grid){
    myGrid = grid;
  }

  public ArrayList<ArrayList<Cell>> getGrid(){
    return myGrid;
  }

  public Cell getCell(int x, int y){
    if(x >= myGrid.size() || x < 0){
      return null;}
    if(y >= myGrid.get(x).size() || y < 0){
      return null;}
    return myGrid.get(x).get(y);
  }

  public  ArrayList<Cell> getNeighbors(Cell cell){
    ArrayList<Cell> neighbors = new ArrayList<>();
    int x = cell.getX();
    int y = cell.getY();
    int[] xSteps = {0, 0, 1, -1};
    int[] ySteps = {1, -1, 0, 0};
    for(int i = 0; i < xSteps.length; i++){
      Cell neighbor = getCell(x+xSteps[i], y+ySteps[i]);
      if(neighbor != null)
        neighbors.add(neighbor);
    }
    return neighbors;
  }
}

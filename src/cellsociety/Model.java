package cellsociety;

import cellsociety.cells.Cell;

import java.util.ArrayList;

public class Model {
  private int myHeight;
  private int myWidth;
  private ArrayList<ArrayList<Cell>> myGrid;

  public Model(ArrayList<ArrayList<Cell>> grid){
    myGrid = grid;
  }
  public Model(int height, int width, String configString){
    myHeight = height;
    myWidth = width;
    int stringIdx = 0;
    ArrayList<ArrayList<Cell>> grid = new ArrayList <> ();

    for(int i = 0; i < myHeight; i++){
      grid.add(new ArrayList<Cell>());
      for(int j = 0; j < myWidth; j++){
        int state = Character.getNumericValue(configString.charAt(stringIdx));
        grid.get(i).add(new Cell(state, i, j));
        stringIdx++;
      }
    }
    myGrid =  grid;
  }
  public ArrayList<ArrayList<Cell>> getGrid(){
    return myGrid;
  }

  public int getHeight(){
    return myHeight;
  }

  public int getWidth(){
    return myWidth;
  }
  public Cell getCell(int x, int y){
    if(x >= myGrid.size() || x < 0){
      return null;}
    if(y >= myGrid.get(x).size() || y < 0){
      return null;}
    return myGrid.get(x).get(y);
  }

  public ArrayList<Cell> getNeighbors(int x, int y, int diagonals){
    Cell cell = getCell(x, y);
    ArrayList<Cell> neighbors = new ArrayList<>();

    int[] xSteps = new int[1];
    int[] ySteps = new int[1];
    if(diagonals == 8) {
      xSteps = new int[]{0, 0, 1, -1, 1, 1, -1, -1};
      ySteps = new int[]{1, -1, 0, 0, 1, -1, 1, -1};
    }else if (diagonals == 4){
      xSteps = new int[]{0, 0, 1, -1};
      ySteps = new int[]{1, -1, 0, 0};
    }
    for(int i = 0; i < xSteps.length; i++){
      Cell neighbor = getCell(x+xSteps[i], y+ySteps[i]);
      if(neighbor != null)
        neighbors.add(neighbor);
    }
    return neighbors;
  }


}

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


}

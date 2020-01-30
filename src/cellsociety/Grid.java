package cellsociety;

import java.util.ArrayList;

public class Grid {
  private ArrayList<ArrayList<Cell>> myGrid;

  public Grid(ArrayList<ArrayList<Cell>> grid){
    myGrid = grid;
  }

  public ArrayList<ArrayList<Cell>> getGrid(){
    return myGrid;
  }
}

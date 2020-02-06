package cellsociety;

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


}

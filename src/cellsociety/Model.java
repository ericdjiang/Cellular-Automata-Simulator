package cellsociety;

import cellsociety.cells.Cell;

import java.util.ArrayList;
import java.util.Random;

public class Model {
  public static final String COUNT_STRING = "count";
  public static final String PROB_STRING = "probability";

  private int myHeight;
  private int myWidth;
  private boolean myFinite;
  private ArrayList<ArrayList<Cell>> myGrid;

  /*public Model(ArrayList<ArrayList<Cell>> grid){
    myGrid = grid;
  }*/

  public Model(int height, int width, String configString, boolean finite){
    myHeight = height;
    myWidth = width;
    myFinite = finite;
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

  public Model(int height, int width, ArrayList<Double> list, String type, boolean finite){
    myHeight = height;
    myWidth = width;
    myFinite = finite;
    ArrayList<ArrayList<Cell>> grid = new ArrayList<>();

    for(int i = 0; i < myHeight; i++){
      grid.add(new ArrayList<>());
      for(int j = 0; j < myWidth; j++){
        int state = 0;
        if(type.equals(PROB_STRING)) {
          state = findStateFromProbability(list);
        }else if(type.equals(COUNT_STRING)){
          state = findStateFromCount(list);
        }
        grid.get(i).add(new Cell(state, i, j));
      }
    }
    myGrid = grid;
  }

  private int findStateFromProbability(ArrayList<Double> probs){
    double randDouble = new Random().nextDouble();
    for(int i =  0; i < probs.size(); i++){
      if(randDouble < probs.get(i)){
        return i;
      }
    }
    return 0;
  }

  private int findStateFromCount(ArrayList<Double> counts){
    int randInt = new Random().nextInt(counts.size());
    while(counts.get(randInt) <= 0){
      randInt = new Random().nextInt(counts.size());
    }
    counts.set(randInt, counts.get(randInt) -  1);
    return randInt;
  }

  public int getHeight(){
    return myHeight;
  }

  public int getWidth(){
    return myWidth;
  }
  public Cell getCell(int x, int y){
    if(myFinite) {
      if (x >= myGrid.size() || x < 0) {
        return null;
      }
      if (y >= myGrid.get(x).size() || y < 0) {
        return null;
      }
    }
    else {
      if (x >= myGrid.size()){
        x -= myGrid.size();
      }else if(x < 0){
        x += myGrid.size();
      }
      if (y >= myGrid.get(x).size()){
        y -= myGrid.get(x).size();
      }else if(y < 0){
        y += myGrid.get(x).size();
      }
    }
    return myGrid.get(x).get(y);
  }

  public ArrayList<Cell> getNeighbors(int x, int y, int diagonals){
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

  public int numState(int state){
    int count = 0;
    for(int i = 0; i < myHeight; i++){
      for(int j = 0; j < myWidth; j++){
        if(getCell(i, j).getState() == state){
          count++;
        }
      }
    }
    return count;
  }

}

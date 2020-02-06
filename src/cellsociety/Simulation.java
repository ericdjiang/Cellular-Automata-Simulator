package cellsociety;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
  private Model myModel;
  ArrayList<ArrayList<Cell>> myGrid;

  public Simulation(Model model) {
    this.myModel = model;
    myGrid = myModel.getGrid();
  }
  public void run(){
    findNewStates();
    setNewStates();
  }
  //protected abstract ArrayList<Cell> getNeighbors(Cell cell);
  protected abstract void findNewStates();

  private void setNewStates() {
    for(int i = 0; i < myGrid.size(); i++) {
      for (int j = 0; j < myGrid.get(i).size(); j++) {
        myGrid.get(i).get(j).updateState();
      }
    }
  }
}

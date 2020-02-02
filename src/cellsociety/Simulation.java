package cellsociety;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
  private Model myModel;
  public void run(){
    findNewStates();
    setNewStates();
  }
  protected abstract ArrayList<Cell> getNeighbors(Cell cell);
  protected abstract void findNewStates();
  protected abstract void setNewStates();
}

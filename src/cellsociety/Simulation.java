package cellsociety;

import java.util.List;

public abstract class Simulation {
  public abstract List<Cell> getNeighbors();
  public abstract void findNewStates();
  public abstract void setNewStates();
}

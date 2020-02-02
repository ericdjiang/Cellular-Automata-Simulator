package cellsociety;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
  private Model myGrid;


  public abstract void findNewStates();
  public abstract void setNewStates();
}

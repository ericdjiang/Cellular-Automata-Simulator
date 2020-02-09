package cellsociety;

import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {
  protected Model myModel;

  public Simulation(Model model) {
    this.myModel = model;
  }

  public void run(){
    findNewStates();
    setNewStates();
  }

  public HBox getExtraInputs(){
    return new HBox();
  }

  protected abstract void findNewStates();


  private void setNewStates() {
    for(int i = 0; i < myModel.getHeight(); i++) {
      for (int j = 0; j < myModel.getWidth(); j++) {
        myModel.getCell(i,j).updateState();
      }
    }
  }
}

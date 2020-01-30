package cellsociety;

import java.util.HashMap;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Visualizer {
  private Model myModel;
  private HashMap<String, String> mySimulationParams;


  public Visualizer(Model myModel, HashMap<String, String> mySimulationParams){
    this.myModel = myModel;
    this.mySimulationParams = mySimulationParams;
  }

  public Scene makeScene(){
    BorderPane root = new BorderPane();

    // create scene to hold UI
    Scene scene = new Scene(root, 400, 600);

    return scene;
  }

}

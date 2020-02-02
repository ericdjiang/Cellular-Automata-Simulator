package cellsociety.visualization;

import cellsociety.Model;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javax.imageio.ImageIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class Visualizer {
  // Simulation model and parameters
  private Model myModel;
  private HashMap<String, String> mySimulationParams;
  private int PARAM_ROWS = 20;
  private int PARAM_COLS = 20;

  private int GRID_WIDTH = 400;
  private int GRID_HEIGHT = 400;

  private int STAGE_HEIGHT = GRID_HEIGHT + 200;
  private int STAGE_WIDTH = GRID_WIDTH;
  // Buttons
  private Button playButton;
  private Button stopButton;
  private Button stepButton;

  // Simulation objects
  private Group gridWrapper = new Group();
  private Slider slider;

  // Simulation states
  private boolean simPaused = true;
  private int simSpeed;


  public Visualizer(Model model, HashMap<String, String> simulationParams){
    this.myModel = model;
    this.mySimulationParams = simulationParams;

    this.simSpeed = 1;
  }

  public Scene makeScene(){
    BorderPane root = new BorderPane();

    root.setCenter(gridWrapper);
    displayNewGrid();

//    root.setTop(makeTopPanel());

    root.setBottom(makeInputPanel());

    // create scene to hold UI
    Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    return scene;
  }

  public double getSimSpeed(){
    return slider.getValue();
  }

  // Making input panel to hold buttons and sliders
  private Node makeInputPanel () {
    playButton = makeButton("Start", event -> startSimulation());
    stopButton = makeButton("Stop", event -> stopSimulation());
    stepButton = makeButton("Step", event -> stepSimulation());

    slider = new Slider();
    slider.setMin(100);
    slider.setMax(2000);
    slider.setShowTickLabels(true);

    HBox hbox = new HBox(playButton, stopButton, stepButton, slider);
    return hbox;
  }

  private Button makeButton(String property, EventHandler<ActionEvent> handler){
    Button result = new Button();
    result.setText(property);
    result.setOnAction(handler);
    return result;
  }

  private void startSimulation(){
    simPaused = false;
  }

  private void stopSimulation(){
    simPaused = true;
    System.out.println("stopped");
  }

  public boolean isSimPaused(){
    return simPaused;
  }

  private void stepSimulation(){
    startSimulation();

    runSimulation();
    stopSimulation();
  }
  public void runSimulation(){
    clearOldGrid();
    displayNewGrid();
  }
  private void clearOldGrid() {
    gridWrapper.getChildren().clear();
  }

  private void displayNewGrid() {
    int cellWidth = GRID_WIDTH / PARAM_COLS;
    int cellHeight = GRID_HEIGHT / PARAM_ROWS;

    for (int i = 0; i < PARAM_ROWS; i++){
      int startX = GRID_WIDTH - cellWidth*myModel.getGrid().get(i).size()/2;
      for (int j = 0; j < PARAM_COLS; j++) {
        VisualCell cell = new VisualCell(cellWidth*i+startX, cellHeight*j, cellWidth, cellHeight, myModel.getCell(i, j).getState());
        gridWrapper.getChildren().add(cell);
      }
    }
  }
}

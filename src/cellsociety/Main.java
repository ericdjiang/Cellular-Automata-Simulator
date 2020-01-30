package cellsociety;

import javafx.application.Application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  // Default simulation speed
  private final int FRAMES_PER_SECOND = 60;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

  // Simulation specific params
  private static final String SIMULATION_NAME = "fire";
  public static final String TITLE = SIMULATION_NAME.toUpperCase() + " Simulation";

  // Class objects
  private XMLParser myXMLParser;
  private Model myModel;
  private HashMap<String, String> simulationParams;

  /**
   * Begins the simulation loop via timeline
   *
   * @param stage holds the scene and all objects to be drawn to the screen
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    // Read in parameters and layout from XML
    myXMLParser = new XMLParser(SIMULATION_NAME+"_xml");
    ArrayList<ArrayList<Cell>> grid = myXMLParser.generateGridFromXML();
    simulationParams = myXMLParser.getSimulationParams();

    // Generate Model
    myModel = new Model(grid);

    // Generate View, passing Model and Simulation parameters to the View
    Visualizer display = new Visualizer(myModel, simulationParams);
    stage.setScene(display.makeScene());
    stage.setTitle(TITLE);
    stage.show();

    // Setup timeline which will call step to advance the simulation by one
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
 }

  /**
   * Advances the simulation by one step
   */
  private void step() {
    // call find new state and setnewstate on Simulation object
  }

  public static void main(String[] args) {
    launch(args);
  }
}

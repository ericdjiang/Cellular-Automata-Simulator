package cellsociety;

import cellsociety.simulations.GameOfLifeSim;
import cellsociety.visualization.Visualizer;
import javafx.application.Application;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
  // Default simulation speed
//  private final int FRAMES_PER_SECOND = 60;
  private int millisecondDelay = 1000;

  // Simulation specific params
  private Timeline animation;
  private static final String SIMULATION_NAME = "fire";
  public static final String TITLE = SIMULATION_NAME.toUpperCase() + " Simulation";

  // Class objects
  private XMLParser myXMLParser;
  private Model myModel;
  private HashMap<String, String> simulationParams;
  private Visualizer myVisualizer;
  private Simulation mySimulation;

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
    mySimulation = new GameOfLifeSim(myModel);
    // Generate View, passing Model and Simulation parameters to the View
    myVisualizer = new Visualizer(myModel, simulationParams);
    stage.setScene(myVisualizer.makeScene());
    stage.setTitle(TITLE);
    stage.show();

    // Setup timeline which will call step to advance the simulation by one
    KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
 }

  /**
   * Advances the simulation by one step
   */
  private void step() {
    if(!myVisualizer.isSimPaused()) { // if the simulation is not stopped
      // call find new state and setnewstate on Simulation object
      mySimulation.run();
      //myModel = mySimulation.getModel();
      myVisualizer.stepSimulation();
      // get simulation speed from visualizer
      setSimulationSpeed(myVisualizer.getSimSpeed());
    }
  }

  private void setSimulationSpeed(double simulationSpeed){ //take in simulationspeed as seconds in between each step
    millisecondDelay = (int) simulationSpeed;
    animation.stop();
    animation.getKeyFrames().clear();

    KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }


  public static void main(String[] args) {
    launch(args);
  }
}

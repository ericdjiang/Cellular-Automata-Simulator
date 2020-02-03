package cellsociety;

import cellsociety.simulations.FireSim;
import cellsociety.simulations.GameOfLifeSim;
import cellsociety.simulations.PercolationSim;
import cellsociety.simulations.SegregationSim;
import cellsociety.visualization.Visualizer;
import cellsociety.xml.XMLParser;
import java.io.File;
import javafx.application.Application;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

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
  private Stage myStage;

  // File loading
  // kind of data files to look for
  public static final String DATA_FILE_EXTENSION = "*.xml";
  // NOTE: generally accepted behavior that the chooser remembers where user left it last
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);


  /**
   * Begins the simulation loop via timeline
   *
   * @param stage holds the scene and all objects to be drawn to the screen
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    myStage = stage;

        /**KARTHIK**/
//    // Generate Model
//    myModel = new Model(grid);
//    //mySimulation = new GameOfLifeSim(myModel);
//    //mySimulation = new PercolationSim(myModel);
//    //mySimulation = new SegregationSim(myModel, 0.3);
//    mySimulation = new FireSim(myModel, 0.5);
//    // Generate View, passing Model and Simulation parameters to the View
//    myVisualizer = new Visualizer(myModel, simulationParams, mySimulation);
//    stage.setScene(myVisualizer.makeScene());
//    stage.setTitle(TITLE);
//    stage.show();
    loadConfiguration();

    // Setup timeline which will call step to advance the simulation by one
    KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
 }

 private void loadConfiguration() throws Exception{
   // Read in parameters and layout from XML
   myXMLParser = new XMLParser();
   myXMLParser.initializeDocBuilder(FILE_CHOOSER.showOpenDialog(myStage));
   ArrayList<ArrayList<Cell>> grid = myXMLParser.generateGridFromXML();
   simulationParams = myXMLParser.getSimulationParams();
   System.out.println(simulationParams);

   // Generate Model
   myModel = new Model(grid);

   switch(simulationParams.get("simName")){
     case "Fire":
       mySimulation = new GameOfLifeSim(myModel);
       break;
     case "Game of Life":
       mySimulation = new FireSim(myModel, Double.parseDouble(simulationParams.get("catchProb")));
       break;
     case "percolation":
       mySimulation = new PercolationSim(myModel);
       break;
     case "segregation":
       mySimulation = new SegregationSim(myModel, Double.parseDouble(simulationParams.get("threshold")));
       break;
     default:
       break;
   }


   // Generate View, passing Model and Simulation parameters to the View
   myVisualizer = new Visualizer(myModel, simulationParams, mySimulation);

   myStage.setScene(myVisualizer.makeScene());
   myStage.setTitle(simulationParams.get("simName"));
   myStage.show();
 }
  /**
   * Advances the simulation by one step
   */
  private void step() {
    if(!myVisualizer.isSimPaused()) { // if the simulation is not stopped
      // call find new state and setnewstate on Simulation object
      mySimulation.run();
      //myModel = mySimulation.getModel();
      myVisualizer.runSimulation();
      // get simulation speed from visualizer
      setSimulationSpeed(myVisualizer.getSimSpeed());
    } else if (!myVisualizer.getXMLLoaded()){
      try {
        loadConfiguration();
      } catch(Exception e) {
        System.out.println("could not load configuration file");
      }

      myVisualizer.setXMLLoaded(true);

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

  // set some sensible defaults when the FileChooser is created
  private static FileChooser makeChooser (String extensionAccepted) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    // pick a reasonable place to start searching for files
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
    return result;
  }


  public static void main(String[] args) {
    launch(args);
  }
}

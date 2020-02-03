package cellsociety;

import cellsociety.simulations.GameOfLifeSim;
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
    // Read in parameters and layout from XML
    myXMLParser = new XMLParser();
    ArrayList<ArrayList<Cell>> grid = myXMLParser.generateGridFromXML();
    simulationParams = myXMLParser.getSimulationParams();

    // Generate Model
    myModel = new Model(grid);
    mySimulation = new GameOfLifeSim(myModel);

    // Generate View, passing Model and Simulation parameters to the View
    myVisualizer = new Visualizer(myModel, simulationParams, mySimulation);

    myStage = stage;
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
//    if(!myVisualizer.isSimPaused()) { // if the simulation is not stopped
//      // call find new state and setnewstate on Simulation object
//      mySimulation.run();
//      //myModel = mySimulation.getModel();
//      myVisualizer.runSimulation();
//      // get simulation speed from visualizer
//      setSimulationSpeed(myVisualizer.getSimSpeed());
//    } else if (!myVisualizer.getXMLLoaded()){
//      File dataFile = FILE_CHOOSER.showOpenDialog(myStage);
//      XMLParser xmlParser = new XMLParser(dataFile);
//      simulationParams = xmlParser.getSimulationParams();
//      ArrayList<ArrayList<Cell>> grid = myXMLParser.generateGridFromXML();
//
////        try {
////          Pair<String, Game> p = new Pair<>(dataFile.getName(), new XMLParser("media").getGame(dataFile));
////          // do something "interesting" with the resulting data
////          showMessage(AlertType.INFORMATION, p.getFirst() + "\n" + p.getSecond().toString());
////        }
////        catch (XMLException e) {
////          // handle error of unexpected file format
////          showMessage(AlertType.ERROR, e.getMessage());
////        }
////        dataFile = FILE_CHOOSER.showOpenDialog(primaryStage);
//
//      myVisualizer.setXMLLoaded(true);
//
//    }
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

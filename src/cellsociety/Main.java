package cellsociety;

import cellsociety.simulations.*;
import cellsociety.visualization.Visualizer;
import cellsociety.xml.XMLException;
import cellsociety.xml.XMLParser;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {
  public static final String COUNT_STRING = "count";
  public static final String PROB_STRING = "probability";
  // Default simulation speed
//  private final int FRAMES_PER_SECOND = 60;
  private int millisecondDelay = 1000;

  // Simulation specific params
  private Timeline animation;

  // Class objects
  private XMLParser myXMLParser;
  private HashMap<String, String> simulationParams;
//  private Visualizer myVisualizer;
  private Simulation mySimulation;
  private Stage myStage;

  private List<Visualizer> myVisualizers = new ArrayList<>();

  // File loading
  // kind of data files to look for
  public static final String DATA_FILE_EXTENSION = "*.xml";
  // NOTE: generally accepted behavior that the chooser remembers where user left it last
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  private boolean fileChooserOpen = false;

  private Group root = new Group();
  private Scene scene = new Scene(root, 100, 1500);
  /**
   * Begins the simulation loop via timeline
   *
   * @param stage holds the scene and all objects to be drawn to the screen
   * @throws Exception
   */
  @Override
  public void start(Stage stage) {
    myStage = stage;
    myStage.setScene(scene);
    myStage.show();

    addNewSim(0);
    addNewSim(450);

//    loadConfiguration(new File ("C:\\Users\\edj9\\workspace308\\simulation_team16\\data\\gol_preset.xml"));

    // Setup timeline which will call step to advance the simulation by one
    KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
 }

 private void addNewSim(int yPos){
   fileChooserOpen = true;
   // Read in parameters and layout from XML

   Model myModel = new Model();
   try {
     myXMLParser = new XMLParser();
     myXMLParser.initializeDocBuilder(new File("C:\\Users\\edj9\\workspace308\\simulation_team16\\data\\gol_preset.xml"));


     simulationParams = myXMLParser.getSimulationParams();
     myXMLParser.validateParams();
     fileChooserOpen = false;


     int gridHeight = Integer.valueOf(simulationParams.get("gridHeight"));
     int gridWidth = Integer.valueOf(simulationParams.get("gridWidth"));
     String assignmentType = simulationParams.get("assignmentType");
     int neighbors = Integer.valueOf(simulationParams.get("neighborCount"));
     String edgeType = simulationParams.get("edgeType");
     boolean finite = edgeType.equals("finite");
     if(assignmentType.equals("probability")){
       ArrayList<Double> probs = new ArrayList<>();
       probs.add(Double.valueOf(simulationParams.get("state0")));
       int counter = 1;
       while(true){
         String prob = simulationParams.get("state" + counter);
         if(prob == null){
           break;
         }
         probs.add(Double.valueOf(prob) + probs.get(counter-1));
         counter++;
       }
       myModel = new Model(gridHeight, gridWidth, probs, PROB_STRING, finite, neighbors);
     }


     else if(assignmentType.equals("counts")) {
       int counter = 0;
       ArrayList<Double> counts = new ArrayList<>();
       while (true) {
         String count = simulationParams.get("state" + counter);
         if (count == null) {
           break;
         }
         counts.add(Double.valueOf(count));
         counter++;

         myModel = new Model(gridHeight, gridWidth, counts, COUNT_STRING, finite, neighbors);
       }
     }
     else if(assignmentType.equals("preset")) {
       String configString = simulationParams.get("gridValues");
       myModel = new Model(gridHeight, gridWidth, configString, finite, neighbors);
     }


     // Generate Model
     //myModel = new Model(grid);
     switch(simulationParams.get("simName")){
       case "Game of Life":
         mySimulation = new GameOfLifeSim(myModel);
         break;
       case "Fire":
         double catchProb = Double.parseDouble(simulationParams.get("catchProb"));
         System.out.println("catchProb = " + catchProb);
         mySimulation = new FireSim(myModel, catchProb);
         break;
       case "Percolation":
         mySimulation = new PercolationSim(myModel);
         break;
       case "Segregation":
         mySimulation = new SegregationSim(myModel, Double.parseDouble(simulationParams.get("threshold")));
         break;
       case "PredatorPrey":
         mySimulation = new PredatorPreySim(myModel, Integer.parseInt(simulationParams.get("breedTime")), Integer.parseInt(simulationParams.get("starveTime")));
       default:
         break;
     }

     // Generate View, passing Model and Simulation parameters to the View
     Visualizer myVisualizer = new Visualizer(myModel, simulationParams, mySimulation);
     myVisualizers.add(myVisualizer);
     root.getChildren().add(myVisualizer.makePane(yPos));

   } catch (XMLException e) {
     Alert alert = new Alert(AlertType.ERROR, e.getMessage());
     alert.setOnHidden(evt -> loadConfiguration(FILE_CHOOSER.showOpenDialog(myStage)));
     alert.show();
   }
 }
 private void loadConfiguration(File fileName){
//   fileChooserOpen = true;
//   // Read in parameters and layout from XML
//
//   Model myModel = new Model();
//   try {
//     myXMLParser = new XMLParser();
//     myXMLParser.initializeDocBuilder(fileName);
//
//     simulationParams = myXMLParser.getSimulationParams();
//     myXMLParser.validateParams();
//     fileChooserOpen = false;
//
//
//     int gridHeight = Integer.valueOf(simulationParams.get("gridHeight"));
//     int gridWidth = Integer.valueOf(simulationParams.get("gridWidth"));
//     String assignmentType = simulationParams.get("assignmentType");
//     int neighbors = Integer.valueOf(simulationParams.get("neighborCount"));
//     String edgeType = simulationParams.get("edgeType");
//     boolean finite = edgeType.equals("finite");
//     if(assignmentType.equals("probability")){
//       ArrayList<Double> probs = new ArrayList<>();
//       probs.add(Double.valueOf(simulationParams.get("state0")));
//       int counter = 1;
//       while(true){
//         String prob = simulationParams.get("state" + counter);
//         if(prob == null){
//           break;
//         }
//         probs.add(Double.valueOf(prob) + probs.get(counter-1));
//         counter++;
//       }
//       myModel = new Model(gridHeight, gridWidth, probs, PROB_STRING, finite, neighbors);
//     }
//
//
//     else if(assignmentType.equals("counts")) {
//       int counter = 0;
//       ArrayList<Double> counts = new ArrayList<>();
//       while (true) {
//         String count = simulationParams.get("state" + counter);
//         if (count == null) {
//           break;
//         }
//         counts.add(Double.valueOf(count));
//         counter++;
//
//         myModel = new Model(gridHeight, gridWidth, counts, COUNT_STRING, finite, neighbors);
//       }
//     }
//   else if(assignmentType.equals("preset")) {
//     String configString = simulationParams.get("gridValues");
//     myModel = new Model(gridHeight, gridWidth, configString, finite, neighbors);
//   }
//
//
//     // Generate Model
//     //myModel = new Model(grid);
//     switch(simulationParams.get("simName")){
//       case "Game of Life":
//         mySimulation = new GameOfLifeSim(myModel);
//         break;
//       case "Fire":
//         double catchProb = Double.parseDouble(simulationParams.get("catchProb"));
//         System.out.println("catchProb = " + catchProb);
//         mySimulation = new FireSim(myModel, catchProb);
//         break;
//       case "Percolation":
//         mySimulation = new PercolationSim(myModel);
//         break;
//       case "Segregation":
//         mySimulation = new SegregationSim(myModel, Double.parseDouble(simulationParams.get("threshold")));
//         break;
//       case "PredatorPrey":
//         mySimulation = new PredatorPreySim(myModel, Integer.parseInt(simulationParams.get("breedTime")), Integer.parseInt(simulationParams.get("starveTime")));
//       default:
//         break;
//     }
//
//     // Generate View, passing Model and Simulation parameters to the View
//     myVisualizer = new Visualizer(myModel, simulationParams, mySimulation);
//
////     myStage.setScene(myVisualizer.makePane()); asdf asdfasdfasdf asdfasdfasd
//     myStage.setTitle(simulationParams.get("simName"));
//     myStage.show();
//
//   } catch (XMLException e) {
//     Alert alert = new Alert(AlertType.ERROR, e.getMessage());
//     alert.setOnHidden(evt -> loadConfiguration(FILE_CHOOSER.showOpenDialog(myStage)));
//     alert.show();
//   }

 }
  /**
   * Advances the simulation by one step
   */
  private void step() {
    System.out.println("stepping");
    for(Visualizer myVisualizer: myVisualizers){
    if(!myVisualizer.isSimPaused()) { // if the simulation is not stopped
      // call find new state and setnewstate on Simulation object
      myVisualizer.getMySimulation().run();
      //myModel = mySimulation.getModel();
      myVisualizer.runSimulation();
      // get simulation speed from visualizer
      setSimulationSpeed(myVisualizer.getSimSpeed());
    } else if (!myVisualizer.getXMLLoaded() && !fileChooserOpen){
      loadConfiguration(FILE_CHOOSER.showOpenDialog(myStage));

      myVisualizer.setXMLLoaded(true);

    }
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

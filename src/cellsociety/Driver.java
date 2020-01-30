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

public class Driver extends Application {

  // Simulation specific params
  private static final String SIMULATION_NAME = "fire";

  // stage styling
  private final String TITLE = "Simulation_16";
  private final int STAGE_WIDTH = 700;
  private final int STAGE_MARGIN = 50;
  private final int STAGE_HEIGHT = 400 + STAGE_MARGIN;
  private final int STAGE_PADDING_X = 50;
  private final int STAGE_PADDING_Y = 50;
  private final Paint BACKGROUND = Color.BLACK;

  // timeline speed
  private final int FRAMES_PER_SECOND = 60;
  private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

  // root node to hold scene objects
  private Group root = new Group();

  // Class objects
  private XMLParser myXMLParser;
  private Grid myGrid;
  private HashMap<String, String> simulationParams;

  public Driver(String SIMULATION_NAME){
    // Read in parameters and layout from XML
//    myXMLParser = new XMLParser(SIMULATION_NAME+"_xml");
//    ArrayList<ArrayList<Cell>> grid = myXMLParser.generateGridFromXML();
//    simulationParams = myXMLParser.getSimulationParams();
//
//    // construct Grid object to store data from XML
//    myGrid = new Grid(grid);

  }

  /**
   * Returns a scene object
   *
   * @param width      width of scene
   * @param height     height of scene
   * @param background background color of scene
   * @return scene object
   */
  private Scene setupGame(int width, int height, Paint background) {
    // create a scene that contains all game objects
    Scene scene = new Scene(root, width, height, background);
    // handle keyboard input for level changes/cheat codes
//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    return scene;
  }

  /**
   * Begins the simulation loop via timeline
   *
   * @param stage holds the scene and all objects to be drawn to the screen
   * @throws Exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    Scene myScene = setupGame(STAGE_WIDTH, STAGE_HEIGHT, BACKGROUND);

    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();

    // game loop which repeatedly calls step() method to rerender scene
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();

    Visualizer myVisualizer = new Visualizer(myScene, animation);
  }

  /**
   * Advances the simulation by one step
   */
  private void step() {

  }
}

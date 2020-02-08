package cellsociety.visualization;

import cellsociety.Model;
import cellsociety.Simulation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.paint.Color;


public class Visualizer {
  // Simulation model and parameters
  private Model myModel;
  private HashMap<String, String> mySimulationParams;
  private int PARAM_ROWS = 20;
  private int PARAM_COLS = 20;

  private Simulation mySimulation;

  private int GRID_WIDTH = 400;
  private int GRID_HEIGHT = 400;

  private int STAGE_HEIGHT = GRID_HEIGHT + 200;
  private int STAGE_WIDTH = GRID_WIDTH + 400;
  // Buttons
  private Button playButton;
  private Button stopButton;
  private Button stepButton;
  private Button configButton;

  private int lastX = 0;

  private ArrayList<XYChart.Series> allSeries = new ArrayList<>();
  // Simulation objects
  private Group gridWrapper = new Group();
  private Group graphWrapper = new Group();
  private Slider slider;

  private String[] labelList;
  // Simulation states
  private boolean simPaused = true;
  private boolean xmlLoaded = true;


  public Visualizer(Model model, HashMap<String, String> simulationParams, Simulation simulation){
    this.myModel = model;
    this.mySimulationParams = simulationParams;
    this.PARAM_COLS = Integer.parseInt(simulationParams.get("gridWidth"));
    this.PARAM_ROWS = Integer.parseInt(simulationParams.get("gridHeight"));
    this.mySimulation = simulation;
    this.labelList = simulationParams.get("stateLabels").split(",");
    initializeSeriesList();
  }

  private void initializeSeriesList(){
    for(int i = 0; i < labelList.length; i++){
      allSeries.add(new XYChart.Series<>());
    }
  }
  public Scene makeScene(){
    BorderPane root = new BorderPane();
    root.setLeft(gridWrapper);
    root.setRight(graphWrapper);
    //root.setCenter(gridWrapper);
    displayNewGrid();
    addGraph();
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

    configButton = makeButton("Select File", event -> setXMLLoaded(false));
    HBox hbox = new HBox(playButton, stopButton, stepButton, slider, configButton);
    return hbox;
  }

  public void setXMLLoaded(Boolean xmlLoaded){
    stopSimulation();
    this.xmlLoaded = xmlLoaded;
  }

  public boolean getXMLLoaded(){
    return this.xmlLoaded;
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
  }

  public boolean isSimPaused(){
    return simPaused;
  }

  private void stepSimulation(){
     stopSimulation();
     mySimulation.run();
     runSimulation();
  }
  public void runSimulation(){
    clearOldGrid();
    displayNewGrid();
    clearOldGraph();
    addGraph();
  }
  private void clearOldGrid() {
    gridWrapper.getChildren().clear();
  }
  private void clearOldGraph() {
    graphWrapper.getChildren().clear();
  }
  private void displayNewGrid(){
    int cellWidth = GRID_WIDTH / PARAM_COLS;
    int cellHeight = GRID_HEIGHT / PARAM_ROWS;

    for (int i = 0; i < PARAM_ROWS; i++){
      int startX = GRID_WIDTH - cellWidth*myModel.getWidth()/2;
      for (int j = 0; j < PARAM_COLS; j++) {
        String color0 = mySimulationParams.get("color0");
        String color1 = mySimulationParams.get("color1");
        String color2 = mySimulationParams.get("color2");

        VisualCell cell = new VisualCell(cellWidth*i+startX, cellHeight*j, cellWidth, cellHeight, myModel.getCell(i, j).getState(), color0, color1, color2);
        gridWrapper.getChildren().add(cell);
      }
    }
  }

  private void addGraph(){
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Time");
    final LineChart<Number,Number> lineChart =
            new LineChart<>(xAxis,yAxis);


    lineChart.setTitle("State count");
    for(int i = 0; i < allSeries.size(); i++){
      XYChart.Series curr = allSeries.get(i);
      curr.setName(labelList[i]);
      XYChart.Data<Number, Number> data = new XYChart.Data(lastX, myModel.numState(i));
      curr.getData().add(data);
      lineChart.getData().add(curr);

      String color = mySimulationParams.get("color" + i);
      style(allSeries.get(i).getNode(), data, color);
    }
    graphWrapper.getChildren().add(lineChart);
    lastX ++;
  }

  private void style(Node node, XYChart.Data<Number, Number> data, String color){
    String colorString = getRGBString(color);
    Node line = node.lookup(".chart-series-line");
    line.setStyle("-fx-stroke: " + colorString);

    Node fill = data.getNode().lookup(".chart-line-symbol");
    fill.setStyle("-fx-background-color: " + colorString + ", whitesmoke");
  }

  private String getRGBString(String colorString){
    Color color = Color.web(colorString);
    String ret =String.format( "rgba(%d, %d, %d, 1.0)",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    System.out.println("ret = " + ret);
    return ret;
  }
}

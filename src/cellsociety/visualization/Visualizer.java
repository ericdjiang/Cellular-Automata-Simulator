package cellsociety.visualization;

import cellsociety.Model;
import cellsociety.Simulation;
import cellsociety.xml.XMLException;
import cellsociety.xml.XMLGenerator;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.shape.Shape;


public class Visualizer {
  // Simulation model and parameters
  private Model myModel;
  private HashMap<String, String> mySimulationParams;
  private int PARAM_ROWS;
  private int PARAM_COLS;

  private Simulation mySimulation;

  public static final int GRID_WIDTH = 400;
  public static final int GRID_HEIGHT = 400;

  public static final int STAGE_HEIGHT = GRID_HEIGHT + 200;
  public static final int STAGE_WIDTH = GRID_WIDTH + 400;
  // Buttons
  private Button playButton;
  private Button stopButton;
  private Button stepButton;
  private Button configButton;
  private Button saveButton;

  private int lastX = 0;
  private ArrayList<XYChart.Series> allSeries = new ArrayList<>();
  private String[] memorizedStyles;
  // Simulation objects
  private Group gridWrapper = new Group();
  private Group graphWrapper = new Group();
  private Slider slider;

  private boolean clicked;
  private double clickedX;
  private double clickedY;

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
    this.clicked = false;
    initializeSeriesList();
  }

  private void initializeSeriesList(){
    for(int i = 0; i < labelList.length; i++){
      allSeries.add(new XYChart.Series<>());
    }
    memorizedStyles = new String[labelList.length];
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
    scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
    return scene;
  }

  public double getSimSpeed(){
    return slider.getValue();
  }


  public void saveSimulation () {
    stopSimulation();

    XMLGenerator xmlGenerator = new XMLGenerator(mySimulationParams, myModel);
    try {
      xmlGenerator.generateFile();
      Alert alert = new Alert(AlertType.INFORMATION, "File generated: " + xmlGenerator.getFilePath());
      alert.show();
    } catch(XMLException e){
      Alert alert = new Alert(AlertType.ERROR, e.getMessage());
      alert.show();
    }
  }


  private void handleMouseInput(double x, double  y){
    clicked = true;
    clickedX = x;
    clickedY = y;
  }

  // Making input panel to hold buttons and sliders
  private Node makeInputPanel () {
    playButton = makeButton("Start", event -> startSimulation());
    stopButton = makeButton("Stop", event -> stopSimulation());
    stepButton = makeButton("Step", event -> stepSimulation());
    saveButton = makeButton("Save", event -> saveSimulation());

    slider = new Slider();
    slider.setMin(100);
    slider.setMax(2000);
    slider.setShowTickLabels(true);

    configButton = makeButton("Select File", event -> setXMLLoaded(false));
    HBox hbox = new HBox(playButton, stopButton, stepButton, slider, configButton, saveButton);
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
    boolean up = true;
    String color0 = mySimulationParams.get("color0");
    String color1 = mySimulationParams.get("color1");
    String color2 = mySimulationParams.get("color2");

    boolean leftEdge;
    boolean rightEdge;
    for (int i = 0; i < PARAM_ROWS; i++){
      for (int j = 0; j < PARAM_COLS; j++) {
        double x;
        double y;
        Shape cell;
        if(mySimulationParams.get("cellShape").equals("triangle")){
          x = cellWidth*j/2;
          y = cellHeight*i;

          if(j == 0){
            leftEdge = true;
          }
          else{
            leftEdge = false;
          }
          if(j == PARAM_COLS-1){
            rightEdge = true;
          }
          else{
            rightEdge = false;
          }
          cell = new VisualCellTriangle(x, y, cellWidth, cellHeight, myModel.getCell(j, i).getState(), color0, color1, color2, up, leftEdge, rightEdge);
        }
        else{
          x = cellWidth * j;
          y = cellHeight * i;
          cell = new VisualCellRectangle(x, y, cellWidth, cellHeight, myModel.getCell(j, i).getState(), color0, color1, color2);
        }
        /*if(clicked){
          if(x < clickedX && clickedX  <  x + cellWidth && y < clickedY  && clickedY < y + cellHeight){
            myModel.getCell(i, j).increment();
            System.out.println("i + \" \" + j = " + i + " " + j);
          }
        }*/
        if(clicked && cell.contains(clickedX, clickedY)){
          System.out.println("i + \" \" + j = " + i + " " + j);
          clicked = false;
          myModel.getCell(j, i).increment();
        }
        gridWrapper.getChildren().add(cell);
        up = !up;
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


      style(allSeries.get(i).getNode(), data, i);
    }
    graphWrapper.getChildren().add(lineChart);
    lastX ++;
    Platform.runLater(() -> {
      for (Node node: lineChart.lookupAll(".chart-legend-item-symbol"))
        for (String styleClass: node.getStyleClass())
          if (styleClass.startsWith("series")) {
            final int i = Integer.parseInt(styleClass.substring(6));
            node.setStyle(memorizedStyles[i]);
            break;
          }
    });
  }

  private void style(Node node, XYChart.Data<Number, Number> data, int idx){
    String color = mySimulationParams.get("color" + idx);
    String colorString = getRGBString(color);
    Node line = node.lookup(".chart-series-line");
    line.setStyle("-fx-stroke: " + colorString);

    Node fill = data.getNode().lookup(".chart-line-symbol");
    fill.setStyle("-fx-background-color: " + colorString + ", whitesmoke");

    memorizedStyles[idx] = "-fx-background-color: " + colorString + ", whitesmoke";
  }

  private String getRGBString(String colorString){
    Color color = Color.web(colorString);
    String ret =String.format( "rgba(%d, %d, %d, 1.0)",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    return ret;
  }
}

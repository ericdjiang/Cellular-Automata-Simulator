package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VisualCellRectangle extends Rectangle{
  public VisualCellRectangle(double x, double y, double width, double height, String colorString){
    super(x, y, width, height);
    setStroke(Color.WHITE);
    Color color = Color.web(colorString, 1.0);
    setFill(color);
  }
}

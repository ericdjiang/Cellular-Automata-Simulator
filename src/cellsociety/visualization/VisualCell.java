package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VisualCell extends Rectangle {
  public VisualCell(double x, double y, double width, double height, int state){
    super(x, y, width, height);
    setColor(state);
    setStroke(Color.WHITE);
  }

  private void setColor(int state){
    switch(state){
      case 2:
        setFill(Color.RED);
        break;
      case 1:
        setFill(Color.GREEN);
        break;
      default:
        setFill(Color.BLACK);
        break;
    }
  }

}

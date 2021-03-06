package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class VisualCell extends Shape {
  private Color myColor0;
  private Color myColor1;
  private Color myColor2;

  public VisualCell(double x, double y, double width, double height, int state, String color0, String color1, String color2){
    myColor0 = Color.web(color0, 1.0);
    myColor1 = Color.web(color1, 1.0);
    myColor2 = Color.web(color2, 1.0);
    setColor(state);
  }
  private void setColor(int state){
    switch(state){
      case 2:
        setFill(myColor2);
        break;
      case 1:
        setFill(myColor1);
        break;
      default:
        setFill(myColor0);
        break;
    }
  }

}

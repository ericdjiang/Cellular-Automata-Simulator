package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VisualCellRectangle extends Rectangle{
  private Color myColor0;
  private Color myColor1;
  private Color myColor2;
  public VisualCellRectangle(double x, double y, double width, double height, String colorString){

    super(x, y, width, height);
    setStroke(Color.WHITE);
    Color color = Color.web(colorString, 1.0);
    setFill(color);
    /*myColor0 = Color.web(color0, 1.0);
    myColor1 = Color.web(color1, 1.0);
    myColor2 = Color.web(color2, 1.0);
    setColor(state);*/
  }/*
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
  }*/

}

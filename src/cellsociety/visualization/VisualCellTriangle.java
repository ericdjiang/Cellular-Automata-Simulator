package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class VisualCellTriangle extends Polygon{
  private Color myColor0;
  private Color myColor1;
  private Color myColor2;
  private double[] myPoints;
  private double x;
  private double y;
  private double width;
  private double height;
  private boolean up;
  private boolean leftEdge;
  private boolean rightEdge;
  public VisualCellTriangle(double x, double y, double width, double height, int state, String color0, String color1, String color2, boolean up, boolean leftEdge, boolean rightEdge){
    super();
    myPoints = new double[6];
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.up = up;
    this.leftEdge = leftEdge;
    this.rightEdge = rightEdge;
    generatePoints();
    setStroke(Color.WHITE);

    myColor0 = Color.web(color0, 1.0);
    myColor1 = Color.web(color1, 1.0);
    myColor2 = Color.web(color2, 1.0);
    setColor(state);
  }
  private void generatePoints(){
    double x1;
    double y1;
    double x2;
    double y2;
    double x3;
    double y3;

    if(up){
      x1 = width/2 + x;
      y1 = y;
      x2 = x;
      y2 = y+height;
      x3 = x + width;
      y3 = y + height;
      if(leftEdge){
        x2 = x1;
      }
      if(rightEdge){
        x3 = x1;
      }
    }else {
       x1 = x;
       y1 = y;
       x2 = x + width;
       y2 = y;
       x3 = width/2 + x;
      y3 = y + height;
      if(leftEdge){
        x1 = x3;
      }
      if(rightEdge){
        x2=x3;
      }
    }
    Double[] points = new Double[]{
            x1, y1,
            x2, y2,
            x3, y3
    };
    getPoints().addAll(points);

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
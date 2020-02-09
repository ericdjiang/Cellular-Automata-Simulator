package cellsociety.visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class VisualCellTriangle extends Polygon{

  private double[] myPoints;
  private double x;
  private double y;
  private double width;
  private double height;
  private boolean up;
  private boolean leftEdge;
  private boolean rightEdge;
  public VisualCellTriangle(double x, double y, double width, double height, String colorString, boolean up, boolean leftEdge, boolean rightEdge){
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
    Color color = Color.web(colorString, 1.0);

    setFill(color);
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

}

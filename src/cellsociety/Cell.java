package cellsociety;

public class Cell {
  private int state;
  private int x;
  private int y;
  public Cell(int state, int x, int y){
    this.state = state;
    this.x = x;
    this.y = y;
  }

  public int getState(){
    return this.state;
  }

  public void setState(int state){
    this.state = state;
  }

}

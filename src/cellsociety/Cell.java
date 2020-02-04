package cellsociety;

public class Cell {
  private int state;
  private int x;
  private int y;
  private int nextState;
  public Cell(int state, int x, int y){
    this.state = state;
    this.nextState = state;
    this.x = x;
    this.y = y;
  }

  public int getState(){
    return this.state;
  }

  public void setNextState(int nextState){
    this.nextState = nextState;
  }

  public int getX(){
    return x;
  }
  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void updateState(){
    state = nextState;
  }
}

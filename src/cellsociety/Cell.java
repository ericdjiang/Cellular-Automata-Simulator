package cellsociety;

public class Cell {
  private int state;
  private int x;
  private int y;
  private int nextState;
  public Cell(int state, int x, int y){
    this.state = state;
    this.x = x;
    this.y = y;
  }

  public int getState(){
    return this.state;
  }

  public void setNextState(int state){
    this.nextState = state;
  }

  public int getX(){
    return x;
  }
  public int getY() {
    return y;
  }
  public void updateState(){
    state = nextState;
  }
}

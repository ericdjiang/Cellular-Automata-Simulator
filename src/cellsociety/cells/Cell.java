package cellsociety.cells;

public class Cell {
  protected int state;
  protected int nextState;
  public Cell(int state){
    this.state = state;
    this.nextState = state;
  }

  public int getState(){
    return this.state;
  }

  public void setNextState(int nextState){
    this.nextState = nextState;
  }

  public void increment(int max){
    nextState++;
    if(nextState >= max){
      nextState = 0;
    }
  }

  public void updateState(){
    state = nextState;
  }
}

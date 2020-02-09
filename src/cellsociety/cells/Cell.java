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
    state++;
    nextState++;
    if(nextState >= max){
      state =  0;
      nextState = 0;
    }
  }

  public void updateState(){
    state = nextState;
  }
}

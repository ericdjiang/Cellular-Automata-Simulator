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

  public void increment(){
    System.out.println("increment");
    nextState++;
  }

  public void updateState(){
    state = nextState;
  }
}

package cellsociety.cells;

public class FishCell extends Cell {
    private int myBreedTime;
    private int myBreedTimeLeft;

    public FishCell(int state, int x, int y, int breedTime) {
        super(state, x, y);
        myBreedTime = breedTime;
        myBreedTimeLeft = myBreedTime;
    }

    public boolean breed(){
        if(myBreedTimeLeft == 0){
            myBreedTimeLeft = myBreedTime;
            return true;
        }
        return false;
    }

    public void turn(){
        myBreedTimeLeft--;
    }
}

package cellsociety.cells;

public class SharkCell extends FishCell{
    private int myBreedTime;
    private int myBreedTimeLeft;

    private int myStarveTime;
    private int turnsLeft;

    public SharkCell(int state, int x, int y, int breedTime, int starveTime) {
        super(state, x, y, breedTime);
        myBreedTime = breedTime;
        myBreedTimeLeft = myBreedTime;

        myStarveTime = starveTime;
        turnsLeft = starveTime;
    }

    public boolean dead(){
        return turnsLeft == 0;
    }
    public void eat(){
        turnsLeft = myStarveTime;
    }
}

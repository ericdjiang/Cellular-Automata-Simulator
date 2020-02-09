package cellsociety.cells;

public class PredatorPreyCell extends Cell {
    private int myBreedTime;
    private int myBreedTimeLeft;

    private int myStarveTime;
    private int myStarveTimeLeft;

    private boolean moved;

    public PredatorPreyCell(int state, int breedTime, int starveTime) {
        super(state);
        myBreedTime = breedTime;
        myBreedTimeLeft = myBreedTime;

        myStarveTime = starveTime;
        myStarveTimeLeft = starveTime;
        moved=false;
    }

    public boolean breed(){
        if(myBreedTimeLeft <= 0){
            myBreedTimeLeft = myBreedTime;
            return true;
        }
        return false;
    }

    @Override
    public void updateState(){
        if(state!=0){
            myBreedTimeLeft--;
        }
        if(state==2){
            myStarveTimeLeft--;
        }
        if(dead()){
            state = 0;
            myStarveTimeLeft=myStarveTime;
            myBreedTimeLeft=myBreedTime;
        }
        moved=false;
    }

    public boolean hasMoved(){
        return moved;
    }

    private void move(){
        moved=true;
    }

    public void eaten(PredatorPreyCell shark) {
        state = 2;
        move();
        myStarveTimeLeft = myStarveTime;
        myBreedTimeLeft=shark.getBreedTime();
    }

    public int getBreedTime(){
        return myBreedTimeLeft;
    }

    public int getStarveTime(){
        return myStarveTimeLeft;
    }

    public void movedInBy(PredatorPreyCell cell){
        state = cell.getState();
        move();
        myStarveTimeLeft = cell.getStarveTime();
        myBreedTimeLeft=cell.getBreedTime();
    }

    public boolean dead(){
        return myStarveTimeLeft <= 0;
    }
}

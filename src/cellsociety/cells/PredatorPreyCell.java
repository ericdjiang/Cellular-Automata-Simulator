package cellsociety;

public class PredatorPreyCell extends Cell {
    private int myBreedTime;
    private int myBreedTimeLeft;

    private int myStarveTime;
    private int turnsLeft;

    public PredatorPreyCell(int state, int x, int y, int breedTime, int starveTime) {
        super(state, x, y);
        myBreedTime = breedTime;
        myBreedTimeLeft = myBreedTime;

        myStarveTime = starveTime;
        turnsLeft = starveTime;
    }

    public boolean breed(){
        if(myBreedTimeLeft == 0){
            myBreedTimeLeft = myBreedTime;
            return true;
        }
        return false;
    }
    @Override
    public void updateState(){
        if(nextState!=state){
            eat();
            breed();
        }
    }

    public void turn(){
        if(state!=0){
            myBreedTimeLeft--;
        }
        if(state==2){
            turnsLeft--;
        }
        if(dead()){
            
        }
    }
    public boolean dead(){
        return turnsLeft == 0;
    }
    public void eat(){
        turnsLeft = myStarveTime;
    }
}

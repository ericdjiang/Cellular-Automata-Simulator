package cellsociety.simulations;

import cellsociety.*;
import cellsociety.cells.Cell;
import cellsociety.cells.PredatorPreyCell;

import java.util.ArrayList;
import java.util.Random;

public class PredatorPreySim extends Simulation {
    private int myBreedTime;
    private int myStarveTime;


    public PredatorPreySim(Model model, int breedTime, int starveTime) {
        super(model);
        myModel = model;
        myBreedTime = breedTime;
        myStarveTime = starveTime;
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                myModel.setCell(i,j,new PredatorPreyCell(myModel.getCell(i,j).getState(),myBreedTime,myStarveTime));
            }
        }
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                PredatorPreyCell cell =  (PredatorPreyCell) myModel.getCell(i,j);

                if(cell.getState() == 0){
                    continue;
                }
                if(cell.hasMoved()){
                    continue;
                }

                ArrayList<Cell> neighbors = myModel.getNeighbors(i,j);

                Cell newCell;

                if(cell.breed()){
                    switch (cell.getState()){
                        case 1:
                            newCell = newFishCell();
                            break;
                        case 2:
                            newCell = newSharkCell();
                            break;
                        default:
                            newCell = newEmptyCell();
                     }
                }else {
                    newCell=newEmptyCell();
                }

                if(cell.getState() == 2){

                    ArrayList<PredatorPreyCell> adjacentFish = new ArrayList<>();

                    for(Cell c: neighbors){
                        if(c.getState() == 1){
                            adjacentFish.add((PredatorPreyCell) c);
                        }
                    }

                    if(adjacentFish.size() > 0){
                        int idx = new Random().nextInt(adjacentFish.size());

                        adjacentFish.get(idx).eaten(cell);
                        myModel.setCell(i,j,newCell);

                        continue;
                    }
                }

                ArrayList<PredatorPreyCell> adjacentEmpties = new ArrayList<>();

                for(Cell c: neighbors){
                    if(c.getState() == 0){
                        adjacentEmpties.add((PredatorPreyCell) c);
                    }
                }

                if(adjacentEmpties.size() > 0){
                    int idx = new Random().nextInt(adjacentEmpties.size());
                    adjacentEmpties.get(idx).movedInBy(cell);
                    myModel.setCell(i,j,newCell);

                    continue;
                }
            }
        }
    }

    private Cell newFishCell(){
        return new PredatorPreyCell(1,myBreedTime,myStarveTime);
    }
    private Cell newSharkCell(){
        return new PredatorPreyCell(2,myBreedTime,myStarveTime);
    }
    private Cell newEmptyCell(){
        return new PredatorPreyCell(0,myBreedTime,myStarveTime);
    }
}

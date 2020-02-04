package cellsociety.simulations;

import cellsociety.*;

import java.util.ArrayList;
import java.util.Random;

public class PredatorPreySim extends Simulation {
    private Model myModel;
    private int myBreedTime;
    private int myStarveTime;
    ArrayList<ArrayList<Cell>> myGrid;
    ArrayList<int[]> myEmpties;
    ArrayList<int[]> myFish;
    ArrayList<int[]> mySharks;
    public PredatorPreySim(Model model, int breedTime, int starveTime) {
        super(model);
        myModel = model;
        myBreedTime = breedTime;
        myStarveTime = starveTime;
        myGrid = myModel.getGrid();
        myEmpties = new ArrayList<>();
        myFish = new ArrayList<>();
        mySharks = new ArrayList<>();
        for(int i = 0; i < myGrid.size(); i++) {
            for (int j = 0; j < myGrid.get(i).size(); j++) {
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    myEmpties.add(new int[]{cell.getX(), cell.getY()});
                }
                else if(cell.getState() == 1){
                    myFish.add(new int[]{cell.getX(), cell.getY()});
                    myGrid.get(i).set(j, new FishCell(cell.getState(), cell.getX(), cell.getY(), myBreedTime));
                }
                else if(cell.getState() == 2){
                    mySharks.add(new int[]{cell.getX(), cell.getY()});
                    myGrid.get(i).set(j, new SharkCell(cell.getState(), cell.getX(), cell.getY(), myBreedTime, myStarveTime));
                }
            }
        }
    }

    @Override
    protected ArrayList<Cell> getNeighbors(Cell cell){
        ArrayList<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        int[] xSteps = {0, 0, 1, -1};
        int[] ySteps = {1, -1, 0, 0};
        for(int i = 0; i < xSteps.length; i++){
            Cell neighbor = myModel.getCell(x+xSteps[i], y+ySteps[i]);
            if(neighbor != null)
                neighbors.add(neighbor);
        }
        return neighbors;
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myGrid.size(); i++){
            for(int j = 0; j < myGrid.get(i).size(); j++){
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    continue;
                }

                ArrayList<Cell> neighbors = getNeighbors(cell);

                int oldX = cell.getX();
                int oldY = cell.getY();
                int[] oldCoords = {oldX, oldY};

                if(cell.getState() == 2){
                    SharkCell sharkCell = (SharkCell) cell;

                    if(sharkCell.dead()){
                        myGrid.get(oldX).set(oldY, new Cell(0, oldX, oldY));
                        continue;
                    }

                    ArrayList<Cell> adjacentFish = new ArrayList<>();
                    for(Cell c: neighbors){
                        if(c.getState() == 1){
                            adjacentFish.add(c);
                        }
                    }

                    if(adjacentFish.size() > 0){
                        int idx = new Random().nextInt(adjacentFish.size());
                        int[] newCoords = {adjacentFish.get(idx).getX(), adjacentFish.get(idx).getY()};
                        myFish.remove(newCoords);

                        int newX = newCoords[0];
                        int newY = newCoords[1];

                        myGrid.get(newX).set(newY, cell);
                        mySharks.remove(oldCoords);

                        handleBreedAndEat(sharkCell, true);

                        sharkCell.setX(newX);
                        sharkCell.setY(newY);
                        sharkCell.turn();
                        continue;
                    }
                }

                ArrayList<Cell> adjacentEmpties = new ArrayList<>();

                for(Cell c: neighbors){
                    if(c.getState() == 0){
                        adjacentEmpties.add(c);
                    }
                }



                if(adjacentEmpties.size() > 0){
                    int idx = new Random().nextInt(adjacentEmpties.size());
                    int[] newCoords = {adjacentEmpties.get(idx).getX(), adjacentEmpties.get(idx).getY()};

                    int newX = newCoords[0];
                    int newY = newCoords[1];

                    myGrid.get(newX).set(newY, cell);
                    cell.setX(newX);
                    cell.setY(newY);

                    mySharks.remove(oldCoords);
                    myFish.remove(oldCoords);

                    FishCell fishCell = (FishCell) cell;
                    handleBreedAndEat(fishCell, false);

                    cell.setX(newX);
                    cell.setY(newY);
                    fishCell.turn();
                    continue;
                }
            }
        }
    }
    private void handleBreedAndEat(FishCell fishCell, boolean eat){
        if(!fishCell.breed()){
            return;
        }
        int oldX = fishCell.getX();
        int oldY = fishCell.getY();
        int[] oldCoords = {oldX, oldY};
        if(fishCell instanceof SharkCell){
            myGrid.get(oldX).set(oldY, new SharkCell(2, oldX, oldY, myBreedTime, myStarveTime));
            if(eat){
                ((SharkCell) fishCell).eat();
            }
            mySharks.add(oldCoords);
        }
        else{
            myGrid.get(oldX).set(oldY, new FishCell(1, oldX, oldY, myBreedTime));
            myFish.add(oldCoords);
        }

    }
}

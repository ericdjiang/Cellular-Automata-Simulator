package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;
import java.util.Random;

public class FireSim extends Simulation {
    private double myCatchProb;

    public FireSim(Model model, double catchProb) {
        super(model);
        myModel = model;
        myCatchProb = catchProb;
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
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                if(cell.getState() == 0){
                    continue;
                }
                if(cell.getState() == 2){
                    continue;
                }

                boolean adjacentBurn = false;

                ArrayList<Cell> neighbors = getNeighbors(cell);
                double randomVal = new Random().nextDouble();

                for(Cell c: neighbors){
                    if(c.getState() == 2){
                        adjacentBurn = true;
                    }
                }

                if(adjacentBurn && randomVal < myCatchProb){
                    cell.setNextState(2);
                }
            }
        }
    }
}

package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;
import java.util.Random;

public class FireSim extends Simulation {
    private Model myModel;
    private double myCatchProb;
    ArrayList<ArrayList<Cell>> myGrid;

    public FireSim(Model model, double catchProb) {
        super(model);
        myModel = model;
        myGrid = myModel.getGrid();
        myCatchProb = catchProb;
    }
/*
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
    }*/

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myGrid.size(); i++){
            for(int j = 0; j < myGrid.get(i).size(); j++){
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    continue;
                }
                if(cell.getState() == 2){
                    continue;
                }

                boolean adjacentBurn = false;

                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j, 4);
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

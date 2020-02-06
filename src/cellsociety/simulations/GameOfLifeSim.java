package cellsociety.simulations;

import cellsociety.cells.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;

public class GameOfLifeSim extends Simulation {

    public GameOfLifeSim(Model model) {
        super(model);
        myModel = model;
    }
    /*
    @Override
    protected ArrayList<Cell> getNeighbors(Cell cell){
        ArrayList<Cell> neighbors = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        int[] xSteps = {0, 0, 1, -1, 1, 1, -1, -1};
        int[] ySteps = {1, -1, 0, 0, 1, -1, 1, -1};
        for(int i = 0; i < xSteps.length; i++){
            Cell neighbor = myModel.getCell(x+xSteps[i], y+ySteps[i]);
            if(neighbor != null)
                neighbors.add(neighbor);
        }
        return neighbors;
    }*/

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j, 8);
                int count = 0;
                for(Cell c: neighbors){
                    count += c.getState();
                }

                if(count == 3){
                    cell.setNextState(1);
                }
                else if(count == 2){
                    cell.setNextState(cell.getState());
                }
                else
                    cell.setNextState(0);
            }
        }
    }

}

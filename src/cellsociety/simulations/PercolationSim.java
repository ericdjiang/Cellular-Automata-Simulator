package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;

public class PercolationSim extends Simulation {

    public PercolationSim(Model model) {
        super(model);
        myModel = model;
    }
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
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                if(cell.getState() == 0 || cell.getState() == 2){
                    continue;
                }
                ArrayList<Cell> neighbors = getNeighbors(cell);


                for(Cell c: neighbors){
                    if(c.getState() == 2){
                        cell.setNextState(2);
                        break;
                    }
                }
            }
        }
    }
}

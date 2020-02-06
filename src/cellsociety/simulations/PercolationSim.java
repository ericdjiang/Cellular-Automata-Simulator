package cellsociety.simulations;

import cellsociety.cells.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;

public class PercolationSim extends Simulation {

    public PercolationSim(Model model) {
        super(model);
        myModel = model;
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                if(cell.getState() == 0 || cell.getState() == 2){
                    continue;
                }
                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j, 8);


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

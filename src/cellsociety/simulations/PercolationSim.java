package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;

public class PercolationSim extends Simulation {
    private Model myModel;
    ArrayList<ArrayList<Cell>> myGrid;

    public PercolationSim(Model model) {
        super(model);
        myModel = model;
        myGrid = myModel.getGrid();
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myGrid.size(); i++){
            for(int j = 0; j < myGrid.get(i).size(); j++){
                Cell cell = myGrid.get(i).get(j);
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

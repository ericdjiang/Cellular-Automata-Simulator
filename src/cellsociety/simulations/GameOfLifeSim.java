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

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j);
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

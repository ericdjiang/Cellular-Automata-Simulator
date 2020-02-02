package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;

public class GameOfLifeSim extends Simulation {
    private Model myModel;
    ArrayList<ArrayList<Cell>> myGrid;

    public GameOfLifeSim(Model model) {
        this.myModel = model;
        myGrid = myModel.getGrid();
    }

    @Override
    public void findNewStates() {
        for(int i = 0; i < myGrid.size(); i++){
            for(int j = 0; j < myGrid.get(i).size(); j++){
                Cell cell = myGrid.get(i).get(j);
                ArrayList<Cell> neighbors = myModel.getNeighbors(cell);
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

    @Override
    public void setNewStates() {
        for(int i = 0; i < myGrid.size(); i++) {
            for (int j = 0; j < myGrid.get(i).size(); j++) {
                myGrid.get(i).get(j).updateState();
            }
        }
    }
}

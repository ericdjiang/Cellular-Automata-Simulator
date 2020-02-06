package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;
import java.util.Random;

public class SegregationSim extends Simulation {
    private Model myModel;
    private double myThreshold;
    ArrayList<ArrayList<Cell>> myGrid;
    ArrayList<int[]> myEmpties;

    public SegregationSim(Model model, double threshold) {
        super(model);
        myModel = model;
        myGrid = myModel.getGrid();
        myEmpties = new ArrayList<>();
        for(int i = 0; i < myGrid.size(); i++) {
            for (int j = 0; j < myGrid.get(i).size(); j++) {
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    myEmpties.add(new int[]{cell.getX(), cell.getY()});
                    System.out.println(cell);
                }
            }
        }
        myThreshold = threshold;
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myGrid.size(); i++){
            for(int j = 0; j < myGrid.get(i).size(); j++){
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    continue;
                }

                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j, 8);
                int similarCount = 0;
                int denominator = neighbors.size();

                for(Cell c: neighbors){
                    if(c.getState() == cell.getState()){
                        similarCount++;
                    }
                }

                double percentage = similarCount / (double)  denominator;

                if(percentage >= myThreshold){
                    continue;
                }
                System.out.println("myEmpties.size() = " + myEmpties.size());
                int idx = new Random().nextInt(myEmpties.size());

                int[] newLocation = myEmpties.remove(idx);
                int newX = newLocation[0];
                int newY = newLocation[1];

                myModel.getCell(newX, newY).setNextState(cell.getState());
                cell.setNextState(0);

                myEmpties.add(new int[]{cell.getX(), cell.getY()});
            }
        }
    }
}

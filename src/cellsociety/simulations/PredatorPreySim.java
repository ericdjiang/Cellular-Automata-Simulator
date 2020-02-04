package cellsociety.simulations;

import cellsociety.Cell;
import cellsociety.Model;
import cellsociety.Simulation;

import java.util.ArrayList;
import java.util.Random;

public class PredatorPreySim extends Simulation {
    private Model myModel;
    private double myThreshold;
    ArrayList<ArrayList<Cell>> myGrid;
    ArrayList<int[]> myEmpties;
    ArrayList<int[]> myFish;
    ArrayList<int[]> mySharks;
    public PredatorPreySim(Model model, double threshold) {
        super(model);
        myModel = model;
        myGrid = myModel.getGrid();
        myEmpties = new ArrayList<>();
        for(int i = 0; i < myGrid.size(); i++) {
            for (int j = 0; j < myGrid.get(i).size(); j++) {
                Cell cell = myGrid.get(i).get(j);
                if(cell.getState() == 0){
                    myEmpties.add(new int[]{cell.getX(), cell.getY()});
                }
                else if(cell.getState() == 1){
                    myFish.add(new int[]{cell.getX(), cell.getY()});
                }
                else if(cell.getState() == 2){
                    mySharks.add(new int[]{cell.getX(), cell.getY()});
                }
            }
        }
        myThreshold = threshold;
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

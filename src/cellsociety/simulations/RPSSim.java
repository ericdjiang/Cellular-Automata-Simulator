package cellsociety.simulations;

import cellsociety.Model;
import cellsociety.Simulation;
import cellsociety.cells.Cell;
import cellsociety.cells.PredatorPreyCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RPSSim extends Simulation {

    private int myMinThreshold;
    private int myAdditionalThreshold;

    private HashMap<Integer,ArrayList<Integer>> loseToMap;

    public RPSSim(Model model, int minThreshold, int maxThreshold) {
        super(model);
        myModel = model;
        myMinThreshold = minThreshold;
        myAdditionalThreshold = (maxThreshold-minThreshold)+1;
        ArrayList<Integer> l0= new ArrayList<>(1);
        ArrayList<Integer> l1= new ArrayList<>(1);
        ArrayList<Integer> l2= new ArrayList<>(1);

        l0.add(0);
        l1.add(1);
        l2.add(2);

        loseToMap.put(0,l1);
        loseToMap.put(1,l2);
        loseToMap.put(2,l0);
//        for(int i = 0; i < myModel.getHeight(); i++) {
//            for (int j = 0; j < myModel.getWidth(); j++) {
//                myModel.setCell(i,j,new PredatorPreyCell(myModel.getCell(i,j).getState(),myBreedTime,myStarveTime));
//            }
//        }
    }

    @Override
    protected void findNewStates() {
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                Cell cell = myModel.getCell(i, j);

                int thisThreshold = myMinThreshold+ new Random().nextInt(myAdditionalThreshold);

                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j);
                ArrayList<Integer> losingList= loseToMap.get(cell.getState());

                for(int l: losingList){
                    int total = 0;
                    {
                        for (Cell c : neighbors) {
                            if (c.getState() == l) total++;
                        }
                        if (total >= thisThreshold) {
                            cell.setNextState(l);
                            break;
                        }
                    }
                }
            }
        }
    }
}

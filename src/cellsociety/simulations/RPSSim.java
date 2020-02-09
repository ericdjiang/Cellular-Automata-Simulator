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
        createLoseToMap(3);
//        for(int i = 0; i < myModel.getHeight(); i++) {
//            for (int j = 0; j < myModel.getWidth(); j++) {
//                myModel.setCell(i,j,new PredatorPreyCell(myModel.getCell(i,j).getState(),myBreedTime,myStarveTime));
//            }
//        }
    }

    public RPSSim(Model model, int minThreshold, int maxThreshold, int numOfStates) {
        super(model);
        myModel = model;
        myMinThreshold = minThreshold;
        myAdditionalThreshold = (maxThreshold-minThreshold)+1;
        createLoseToMap(numOfStates);
//        for(int i = 0; i < myModel.getHeight(); i++) {
//            for (int j = 0; j < myModel.getWidth(); j++) {
//                myModel.setCell(i,j,new PredatorPreyCell(myModel.getCell(i,j).getState(),myBreedTime,myStarveTime));
//            }
//        }
    }

    private void createLoseToMap(int numOfStates){
        loseToMap = new HashMap<>();
        int numOfLoser = (numOfStates-1)/2;
        for(int i=0;i<numOfStates;i++){
            ArrayList<Integer> losers =new ArrayList<>();

            System.out.print("State "+i+" loses to: ");
            for(int j=0;j<numOfLoser;j++){
                System.out.print((i+(1+j))%numOfStates+" ");
                losers.add((i+(1+j))%numOfStates);
            }
            System.out.println();
            loseToMap.put(i,losers);
        }
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

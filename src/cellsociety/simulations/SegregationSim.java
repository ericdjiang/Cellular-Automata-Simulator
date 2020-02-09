package cellsociety.simulations;

import cellsociety.cells.Cell;
import cellsociety.Model;
import cellsociety.Simulation;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationSim extends Simulation {
    private double myThreshold;
    ArrayList<int[]> myEmpties;
    private Slider thresholdSlider;



    public SegregationSim(Model model, double threshold) {
        super(model);
        myModel = model;
        myEmpties = new ArrayList<>();
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                if(myModel.getCell(i,j).getState() == 0){
                    myEmpties.add(new int[]{i, j});
                }
            }
        }
        myThreshold = threshold;
    }

    @Override
    public HBox getExtraInputs(){
        thresholdSlider = new Slider();
        thresholdSlider.setMin(0);
        thresholdSlider.setMax(100);
        thresholdSlider.setShowTickLabels(true);
        thresholdSlider.setValue(myThreshold*100);
        HBox extraInputs=new HBox(thresholdSlider);
        return extraInputs;
    }

    @Override
    protected void findNewStates() {
        myThreshold=(thresholdSlider.getValue()/100);
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                Cell cell = myModel.getCell(i,j);
                if(cell.getState() == 0){
                    continue;
                }

                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j);
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

                myEmpties.add(new int[]{i, j});
            }
        }
    }
}

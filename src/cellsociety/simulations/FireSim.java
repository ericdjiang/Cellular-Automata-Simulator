package cellsociety.simulations;

import cellsociety.cells.Cell;
import cellsociety.Model;
import cellsociety.Simulation;
import cellsociety.cells.FireCell;
import cellsociety.cells.PredatorPreyCell;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class FireSim extends Simulation {
    private double myCatchProb;
    private int myBurnTime;
    private Slider catchProbSlider;
    private Slider burnTimeSlider;
    private int fontSize = 10;

    public FireSim(Model model, double catchProb) {
        super(model);
        myModel = model;
        myCatchProb = catchProb;
        myBurnTime=1;
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                myModel.setCell(i,j,new FireCell(myModel.getCell(i,j).getState(),myBurnTime));
            }
        }
    }

    public FireSim(Model model, double catchProb, int burnTime) {
        super(model);
        myModel = model;
        myCatchProb = catchProb;
        myBurnTime=burnTime;
        for(int i = 0; i < myModel.getHeight(); i++) {
            for (int j = 0; j < myModel.getWidth(); j++) {
                myModel.setCell(i,j,new FireCell(myModel.getCell(i,j).getState(),burnTime));
            }
        }
    }
/*
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
    }*/

    @Override
    public HBox getExtraInputs(){

        Text catchProbLabel = new Text();
        catchProbLabel.setFont(new Font(fontSize));
        catchProbLabel.setText("   Catch Probability");

        Text burnLabel = new Text();
        burnLabel.setFont(new Font(fontSize));
        burnLabel.setText("   Burn Time");

        catchProbSlider = new Slider();
        catchProbSlider.setMin(0);
        catchProbSlider.setMax(1);
        catchProbSlider.setShowTickLabels(true);
        catchProbSlider.setValue(myCatchProb);

        burnTimeSlider = new Slider();
        burnTimeSlider.setMin(1);
        burnTimeSlider.setMax(10);
        burnTimeSlider.setMajorTickUnit(1);
        burnTimeSlider.setShowTickLabels(true);
        burnTimeSlider.setValue(myBurnTime);
        burnTimeSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                burnTimeSlider.setValue(newVal.intValue()));

        VBox labeledProbSlider = new VBox(catchProbLabel,catchProbSlider);
        VBox labeledBurnSlider = new VBox(burnLabel,burnTimeSlider);

        HBox extraInputs=new HBox(labeledProbSlider, labeledBurnSlider);
        return extraInputs;
    }

    @Override
    protected void findNewStates() {
        myCatchProb=catchProbSlider.getValue();
        myBurnTime=(int) burnTimeSlider.getValue();
        for(int i = 0; i < myModel.getHeight(); i++){
            for(int j = 0; j < myModel.getWidth(); j++){
                FireCell cell = (FireCell) myModel.getCell(i,j);
                if(cell.getState() == 0){
                    continue;
                }
                if(cell.getState() == 2){
                    if(cell.burntOut()) {
                        cell.setNextState(0);
                    }
                    continue;
                }

                boolean adjacentBurn = false;

                ArrayList<Cell> neighbors = myModel.getNeighbors(i, j);
                double randomVal = new Random().nextDouble();

                for(Cell c: neighbors){
                    if(c.getState() == 2){
                        adjacentBurn = true;
                    }
                }

                if(adjacentBurn && randomVal < myCatchProb){
                    cell.burn(myBurnTime);
                }
            }
        }
    }
}

package cellsociety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class XMLParser {
  private String fname;

  public XMLParser(String fname){
    this.fname = fname;
  }

  public ArrayList<ArrayList<Cell>> generateGridFromXML(){
    ArrayList<ArrayList<Cell>> grid = new ArrayList <> ();

    for(int i = 0; i < 20; i++){
      grid.add(new ArrayList<Cell>());
      for(int j = 0; j < 20; j++){
        grid.get(i).add(new Cell(new Random().nextInt(3)));
      }
    }

    return grid;
  }
  public HashMap<String, String> getSimulationParams () {
    HashMap<String, String> simulationParams = new HashMap<>();
    return simulationParams;
  }
}

package cellsociety.visualization;

import java.util.ArrayList;
import java.util.List;

public class VisualizerWrapper {
  private List<Visualizer> myVisualizers = new ArrayList<>();

  public void addVisualizer(Visualizer myVisualizer){
    myVisualizers.add(myVisualizer);
  }
}

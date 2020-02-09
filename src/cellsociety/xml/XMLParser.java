package cellsociety.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLParser {
  private String gridASCII = "";
  private HashMap<String, String> simulationParams = new HashMap<>();
  private int myHeight;
  private int myWidth;

  private final List <String> PRESET_PARAMS = new ArrayList (Arrays.asList("gridHeight", "gridWidth", "gridValues"));

  public XMLParser(){
  }

  public void validateParams() throws XMLException{
    if(!simulationParams.containsKey("simName")){
      throw new XMLException("Invalid simulation type given. No Simulation specified");
    } else if (!validateGridValues()) {
      throw new XMLException("Invalid simulation parameters specified.");
    }

  }

  private boolean checkParamsExist(List<String> paramsToCheck){
    for (String param: paramsToCheck) {
      if(!simulationParams.containsKey(param)) return false;
    }
    return true;
  }

  private boolean validateGridValues(){
    if(simulationParams.get("assignmentType").equals("preset")){
      System.out.println(!simulationParams.get("gridValues").matches("[a-zA-Z3-9]+"));
        return(checkParamsExist(PRESET_PARAMS) &&
            !simulationParams.get("gridValues").matches("[a-zA-Z3-9]+") &&
            simulationParams.get("gridValues").length() == Integer.parseInt(simulationParams.get("gridWidth"))*Integer.parseInt(simulationParams.get("gridHeight"))
        );
      }

    return true;
  }

  public void initializeDocBuilder(File fname) {
    try{
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
          .newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document document = docBuilder.parse(fname);
      NodeList nodeList = document.getElementsByTagName("*");

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName()!="data") {
          /*if(node.getNodeName()=="gridValues"){
             gridASCII = node.getTextContent();
          }else{*/
            simulationParams.put(node.getNodeName(), node.getTextContent());
          //}
          /*if(node.getNodeName() == "gridHeight") {
            myHeight = Integer.parseInt(node.getTextContent());
          }
          if(node.getNodeName() == "gridWidth") {
            myWidth = Integer.parseInt(node.getTextContent());
          }*/
        }
      }
    } catch (SAXException | IOException | ParserConfigurationException | IllegalArgumentException e){
      System.out.println("asdlkjf");

      throw new XMLException(e, "Incorrect file type selected.");
    }
  }

  /*public ArrayList<ArrayList<Cell>> generateGridFromXML(){
    int stringIdx = 0;
    ArrayList<ArrayList<Cell>> grid = new ArrayList <> ();

    for(int i = 0; i < myHeight; i++){
      grid.add(new ArrayList<Cell>());
      for(int j = 0; j < myWidth; j++){
        int state = Character.getNumericValue(gridASCII.charAt(stringIdx));
        //grid.get(i).add(new Cell(new Random().nextInt(3), i, j));
        grid.get(i).add(new Cell(state, i, j));
        stringIdx++;
      }
    }

    return grid;
  }*/


  public HashMap<String, String> getSimulationParams (){
    return simulationParams;
  }

}


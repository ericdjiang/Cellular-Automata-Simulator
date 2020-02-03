package cellsociety.xml;

import cellsociety.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XMLParser {
  private String gridASCII = "";
  private HashMap<String, String> simulationParams = new HashMap<>();
  public XMLParser(){
  }

  public void initializeDocBuilder(File fname)throws SAXException, IOException,
      ParserConfigurationException {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
        .newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document document = docBuilder.parse(new File("C:\\Users\\edj9\\workspace308\\simulation_team16\\data\\fire_xml.XML"));
    NodeList nodeList = document.getElementsByTagName("*");

    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName()!="data") {
        if(node.getNodeName()=="gridValues"){
           gridASCII = node.getTextContent();
        } else {
          simulationParams.put(node.getNodeName(), node.getTextContent());
        }
      }
    }
  }

  public ArrayList<ArrayList<Cell>> generateGridFromXML(){
    ArrayList<ArrayList<Cell>> grid = new ArrayList <> ();

    for(int i = 0; i < 20; i++){
      grid.add(new ArrayList<Cell>());
      for(int j = 0; j < 20; j++){
        grid.get(i).add(new Cell(new Random().nextInt(2), i, j));
      }
    }

    return grid;
  }


  public HashMap<String, String> getSimulationParams (){
    return simulationParams;
  }

}


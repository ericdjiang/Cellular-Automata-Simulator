package cellsociety;

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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XMLParser {
  private String fname;

  public XMLParser(String fname){
    this.fname = fname;
    DOCUMENT_BUILDER = getDocumentBuilder();
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


  public HashMap<String, String> getSimulationParams () {
    HashMap<String, String> simulationParams = new HashMap<>();
    return simulationParams;
  }

  // Readable error message that can be displayed by the GUI
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  // keep only one documentBuilder because it is expensive to make and can reset it before parsing
  private final DocumentBuilder DOCUMENT_BUILDER;



  /**
   * Get data contained in this XML file as an object
   */
  public Game getGame (File dataFile) {
    Element root = getRootElement(dataFile);
    if (! isValidFile(root, Game.DATA_TYPE)) {
      throw new XMLException(ERROR_MESSAGE, Game.DATA_TYPE);
    }
    // read data associated with the fields given by the object
    Map<String, String> results = new HashMap<>();
    for (String field : Game.DATA_FIELDS) {
      results.put(field, getTextValue(root, field));
    }
    return new Game(results);
  }

  // get root element of an XML file
  private Element getRootElement (File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    }
    catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  // returns if this is a valid XML file for the specified object type
  private boolean isValidFile (Element root, String type) {
    return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
  }

  // get value of Element's attribute
  private String getAttribute (Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue (Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    }
    else {
      // FIXME: empty string or exception? In some cases it may be an error to not find any text
      return "";
    }
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder () {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }
}


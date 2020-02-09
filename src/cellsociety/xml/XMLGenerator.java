package cellsociety.xml;

import cellsociety.Model;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLGenerator {
  private HashMap<String, String> mySimulationParams;
  private final String OUTPUT_DIRECTORY_NAME = ".\\data\\";
  private File outputFilePath;
  private List<String> PARAMS_TO_SKIP = Arrays.asList("gridValues","state0", "state1", "state2");
  private Model myModel;

  public XMLGenerator(HashMap<String, String> mySimulationParams, Model myModel){
    this.mySimulationParams = mySimulationParams;
    this.myModel = myModel;
  }

  private File generateFilePath(){
    outputFilePath = new File(OUTPUT_DIRECTORY_NAME
        + mySimulationParams.get("simName")
        + " "
        + new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date())
        + ".xml");
    return outputFilePath;
  }

  public String getFilePath(){
    return outputFilePath.getName();
  }

  public void generateFile() {
    try{
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

      Document doc = documentBuilder.newDocument();

      // root element
      Element root = doc.createElement("data");
      doc.appendChild(root);

      mySimulationParams.put("assignmentType", "preset");
      for (String key: mySimulationParams.keySet()){
        if(!PARAMS_TO_SKIP.contains(key)){
          Element newLine = doc.createElement(key);
          newLine.appendChild(doc.createTextNode(mySimulationParams.get(key)));
          root.appendChild(newLine);
        }
       }

      storeGridValues(doc, root);

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource domSource = new DOMSource(doc);
    StreamResult streamResult = new StreamResult(generateFilePath());
    transformer.transform(domSource, streamResult);
    } catch ( ParserConfigurationException | TransformerException e){
      throw new XMLException("Error saving file", e);
    }
  }

  private void storeGridValues(Document doc, Element root) {
    String gridValues = "";
    for (int i = 0; i < myModel.getHeight(); i++){
      for(int j = 0; j < myModel.getWidth(); j++) {
        gridValues = gridValues + myModel.getCell(i, j).getState();
      }
    }
    Element gridValuesLine = doc.createElement("gridValues");
    gridValuesLine.appendChild(doc.createTextNode(gridValues));
    root.appendChild(gridValuesLine);
  }
}

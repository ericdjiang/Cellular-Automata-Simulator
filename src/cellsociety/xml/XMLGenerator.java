package cellsociety.xml;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

  public XMLGenerator(HashMap<String, String> mySimulationParams){
    this.mySimulationParams = mySimulationParams;
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

      for (String key: mySimulationParams.keySet()){
        Element newLine = doc.createElement(key);
        newLine.appendChild(doc.createTextNode(mySimulationParams.get(key)));
        root.appendChild(newLine);
      }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource domSource = new DOMSource(doc);
    StreamResult streamResult = new StreamResult(generateFilePath());
    transformer.transform(domSource, streamResult);
    } catch ( ParserConfigurationException | TransformerException e){
      throw new XMLException("Error saving file", e);
    }
  }
}

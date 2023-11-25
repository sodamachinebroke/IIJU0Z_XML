import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;

public class xPathiiju0z {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        Document document = parseXMLDocument("orarendiiju0z.xml");

        //Modifications begin here
        modifyTipusAttribute(document, "/iiju0z_orarend/ora[1]/@tipus", "Gyakorlat vagy nemtom"); //This boi changes the type of the course to the specified string.
        addOraadoElement(document, "/iiju0z_orarend/ora[2]", ":3"); //This one changes the name of the class to the specified string.
        modifyTolValue(document, "/iiju0z_orarend/ora[3]/idopont/tol", "22:00"); //These two change the beginning and end of the class.
        modifyTolValue(document, "/iiju0z_orarend/ora[3]/idopont/ig", "24:00");

        printModifiedXML(document); // Print the modified XML to the console
        writeXMLToFile(document, "orarendiiju0z1.xml");// Write the modified XML to a file
    }

    private static Document parseXMLDocument(String filename) throws ParserConfigurationException, IOException, SAXException { //Function to parse the input file so it's nice and neat.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(filename);
    }

    private static void modifyTipusAttribute(Document document, String xpathExpression, String newValue) throws XPathExpressionException { //This is where the fun begins.
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression modification = xPath.compile(xpathExpression);
        Node node = (Node) modification.evaluate(document, XPathConstants.NODE);
        
        if (node instanceof Attr) {
            Attr attribute = (Attr) node;
            attribute.setValue(newValue);
        }
    }
    
    private static void addOraadoElement(Document document, String xpathExpression, String oraadoContent) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression modification = xPath.compile(xpathExpression);
        Node oraNode = (Node) modification.evaluate(document, XPathConstants.NODE);
    
        Element newOraadoElement = document.createElement("oraado");
        newOraadoElement.setTextContent(oraadoContent);
        oraNode.appendChild(newOraadoElement);
    }
    
    private static void modifyTolValue(Document document, String xpathExpression, String newValue) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression modification = xPath.compile(xpathExpression);
        Node node = (Node) modification.evaluate(document, XPathConstants.NODE);
    
        node.setTextContent(newValue);
    } //This is where the fun ends:(

    private static void printModifiedXML(Document document) throws TransformerException { //PRinting modified orarend to the console
        Transformer transformer = createTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }

    private static void writeXMLToFile(Document document, String filename) throws TransformerException { //Writing it into a file
        Transformer transformer = createTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
        System.out.println("\nModified XML written to: " + filename);
    }

    private static Transformer createTransformer() throws TransformerConfigurationException { //The Allspark essentially. I mean it creates transformers like.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }
}

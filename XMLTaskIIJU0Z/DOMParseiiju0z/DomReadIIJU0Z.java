import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException; //There are import-ant

public class DomReadIIJU0Z {

    public static void main(String[] args) {
        File xmlFile = new File("XMLiiju0z.xml"); // First off, we need the file we're going to read
        Document doc = introduceFile(xmlFile); // This one makes the documentBuilderFactory and stuff. Looks nicer to
                                               // read, the function is below

        if (doc == null) { // If no doc, sadness ensues and you can't parse it. Too bad, program exits.
            System.out.println("The document is null");
            System.exit(-1);
        } else {
            processDocument(doc); // If doc good, then the fun begins. Let's start a function.
        }
    }

    private static void processDocument(Document doc) { // Document processing woohoo
        doc.getDocumentElement().normalize(); // Normalizing document so it complies with OSHA and DOM rules
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        System.out.println("<" + doc.getDocumentElement().getNodeName() +
                " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XSDiiju0z.xsd\">");

        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        String indent = "";
        listData(nodeList, indent);
        System.out.println("</" + doc.getDocumentElement().getNodeName() + ">"); // Formatting the thingy basically

        saveDocumentToFile(doc, "XMLiiju0zout.xml"); // This one saves the new document into the new file
    }

    private static void saveDocumentToFile(Document doc, String fileName) { // This one does a bunch of do-hickeys to
                                                                            // save the file. Ultimately it does save it
                                                                            // as specified in the function call
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            transformer.transform(new DOMSource(doc), new StreamResult(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document introduceFile(File xmlFile) { // As i said earlier, this one makes the DocumentBuilders
        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private static void listData(NodeList nodeList, String indent) { // This one lists everything. Essentially the most
                                                                     // important in terms of non-IO functions
        indent += "\t";

        if (nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && !node.getTextContent().trim().isEmpty()) {
                    processElementNode((Element) node, indent);
                } else if (node instanceof Text) {
                    String value = node.getNodeValue().trim();
                    if (!value.isEmpty()) {
                        System.out.println(indent + node.getTextContent());
                    }
                }
            }
        }
    }

    private static void processElementNode(Element element, String indent) {
        System.out.print(indent + "<" + element.getNodeName());
        if (element.hasAttributes()) {
            processAttributes(element);
        }
        System.out.println(">");

        NodeList nodeList_new = element.getChildNodes();
        listData(nodeList_new, indent);
        System.out.println(indent + "</" + element.getNodeName() + ">");
    }

    private static void processAttributes(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        for (int k = 0; k < attributes.getLength(); k++) {
            Node attribute = attributes.item(k);
            System.out.print(" " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
        }
    }
}

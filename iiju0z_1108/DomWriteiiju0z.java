import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DomWriteiiju0z {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse("orarendiiju0z.xml"); // Load the XML file

            Document outputDoc = factory.newDocumentBuilder().newDocument();// Create the output doc

            Element root = outputDoc.createElement("orarendiiju0z"); // Create a root element for the new doc
            outputDoc.appendChild(root);

            createTreeStructure(doc.getDocumentElement(), root, outputDoc, 0); // Print to the console
            printTreeStructure(root, 0);

            writeXMLToFile(outputDoc, "orarend1iiju0z.xml");// Write the tree structure to the output file

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTreeStructure(Element sourceElement, Element targetElement, Document outputDoc,
            int depth) { // Recursive function to create the tree structure
        Element newElement = outputDoc.createElement(sourceElement.getTagName());

        NamedNodeMap attributes = sourceElement.getAttributes();// Copy attributes from the source element to the target
                                                                // element
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            newElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
        }

        targetElement.appendChild(newElement);// Append the new element to the target element

        NodeList childNodes = sourceElement.getChildNodes(); // Copy child elements recursively
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                createTreeStructure((Element) childNode, newElement, outputDoc, depth + 1);
            }
        }

        if (sourceElement.hasChildNodes() && sourceElement.getChildNodes().getLength() == 1
                && sourceElement.getFirstChild().getNodeType() == Node.TEXT_NODE) {// Copy text
            newElement.setTextContent(sourceElement.getTextContent());
        }
    }

    
    private static void printTreeStructure(Element element, int depth) {// Function to print the tree structure to the console
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }

        System.out.println(indent.toString() + "<" + element.getTagName() + ">");
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(
                    indent.toString() + "  " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
        }

        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                printTreeStructure((Element) childNode, depth + 1);
            } else if (childNode.getNodeType() == Node.TEXT_NODE) {
                System.out.println(indent.toString() + "  " + childNode.getNodeValue().trim());
            }
        }

        System.out.println(indent.toString() + "</" + element.getTagName() + ">");
    }

    private static void writeXMLToFile(Document doc, String filename) throws Exception {
        javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory
                .newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(
                new java.io.File(filename));
        transformer.transform(source, result);
    }
}

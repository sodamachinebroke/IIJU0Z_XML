import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DomWriteiiju0z {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the XML file
            Document doc = builder.parse("orarendiiju0z.xml");

            // Create the output doc
            Document outputDoc = factory.newDocumentBuilder().newDocument();

            // Create a root element for the new doc
            Element root = outputDoc.createElement("orarendiiju0z");
            outputDoc.appendChild(root);

            // Function to recursively create the tree structure and print to the console
            createTreeStructure(doc.getDocumentElement(), root, outputDoc, 0);
            printTreeStructure(root, 0);

            // Write the tree structure to the output XML file
            writeXMLToFile(outputDoc, "orarend1iiju0z.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursive function to create the tree structure
    private static void createTreeStructure(Element sourceElement, Element targetElement, Document outputDoc, int depth) {
        Element newElement = outputDoc.createElement(sourceElement.getTagName());

        // Copy attributes from the source element to the target element
        NamedNodeMap attributes = sourceElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            newElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
        }

        // Append the new element to the target element
        targetElement.appendChild(newElement);

        // Copy child elements recursively
        NodeList childNodes = sourceElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode instanceof Element) {
                createTreeStructure((Element) childNode, newElement, outputDoc, depth + 1);
            }
        }

        // Copy text content
        if (sourceElement.hasChildNodes() && sourceElement.getChildNodes().getLength() == 1 && sourceElement.getFirstChild().getNodeType() == Node.TEXT_NODE) {
            newElement.setTextContent(sourceElement.getTextContent());
        }
    }

    // Function to print the tree structure to the console
    private static void printTreeStructure(Element element, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }

        System.out.println(indent.toString() + "<" + element.getTagName() + ">");
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(indent.toString() + "  " + attribute.getNodeName() + "=\"" + attribute.getNodeValue() + "\"");
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
        javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

        javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new java.io.File(filename));
        transformer.transform(source, result);
    }
}

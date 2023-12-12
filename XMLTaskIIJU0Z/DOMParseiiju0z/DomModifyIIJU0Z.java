import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomModifyIIJU0Z {
    public static void main(String[] args) {
        File xmlFile = new File("XMLiiju0z.xml");
        Document doc = parseXmlFile(xmlFile);

        if (doc == null) {
            System.out.println("The document is null");
            System.exit(-1);
        } else {
            doc.getDocumentElement().normalize();
        }

        /*This function applies the modifications*/
        modifyValues(doc.getDocumentElement());

        /*Print the modified XML to the screen*/ 
        printXml(doc);
    }

    /*Introduces file just like in the previous programs */
    public static Document parseXmlFile(File xmlFile) {
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


    /*This is where the magic happens*/
    public static void modifyValues(Element element) {
        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;

                /*Increase the prices of internet plans by 2000*/
                if ("internet".equals(childElement.getNodeName())) {
                    int currentPrice = Integer.parseInt(childElement.getElementsByTagName("price").item(0).getTextContent());
                    int newPrice = currentPrice + 2000;
                    childElement.getElementsByTagName("price").item(0).setTextContent(Integer.toString(newPrice));
                }

                /*Adds "lol" at the end of every email address lol*/
                if ("email".equals(childElement.getNodeName())) {
                    childElement.setTextContent(childElement.getTextContent() + "lol");
                }

                /*Changes every ZIP to 3333*/
                if ("ZIP".equals(childElement.getNodeName())) {
                    childElement.setTextContent("3333");
                }

                /*Reduces HD TV channel number by 15*/
                if ("tv".equals(childElement.getNodeName())) {
                    int currentHdNum = Integer.parseInt(childElement.getElementsByTagName("hd_num").item(0).getTextContent());
                    int newHdNum = currentHdNum - 15;
                    childElement.getElementsByTagName("hd_num").item(0).setTextContent(Integer.toString(newHdNum));
                }

                /*Adds "eeeeeeeee" at the beginning of every street name*/
                if ("str".equals(childElement.getNodeName())) {
                    childElement.setTextContent("eeeeeeeee" + childElement.getTextContent());
                }

                /*Increase plan_no by 1000, so everyone has thousands of plans ehehehehe*/
                if ("plan_no".equals(childElement.getNodeName())) {
                    int currentPlanNo = Integer.parseInt(childElement.getTextContent());
                    int newPlanNo = currentPlanNo + 1000;
                    childElement.setTextContent(Integer.toString(newPlanNo));
                }

                /* Actually apply these modifications to targeted elements*/ 
                modifyValues(childElement);
            }
        }
    }
    public static void printXml(Document doc) {
        try {
            /*Transformer factory let's go */
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            /*Printy printy :3*/
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class xPathiiju0z {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse("studentiiju0z.xml");
        document.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath(); 

        //Need to un-comment one of these to get the program to work.

        // String expression = "/class/student";
        // String expression = "//student[@id=02]";
        // String expression = "//student";
        // String expression = "/class/student[2]";
        // String expression = "/class/student[last()]";
        // String expression = "/class/student[last()-1]";
        // String expression = "/class/student[position()<=2]";
        // String expression = "/class/*";
        // String expression = "/class/student[@*]";
        // String expression = "//*";
        // String expression = "/class/student[20<kor]";
        // String expression = "/class/student/keresztnev | /class/student/vezeteknev";

        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            System.out.println("Current node: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Student ID: " + element.getAttribute("id"));
                System.out.println("Their first name: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Their surname: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Their nickname: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("Their age: " + element.getElementsByTagName("kor").item(0).getTextContent());
            }
        }
    }

}

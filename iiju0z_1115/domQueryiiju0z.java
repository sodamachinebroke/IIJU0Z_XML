import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.util.HashSet;
import java.util.Set;

public class domQueryiiju0z {

    private final XPathFactory xPathFactory;
    private final XPath xPath;

    private final Set<String> uniqueCourses;
    private final Set<String> uniqueTeachers;

    public domQueryiiju0z() {
        this.xPathFactory = XPathFactory.newInstance();
        this.xPath = xPathFactory.newXPath();
        this.uniqueCourses = new HashSet<>();
        this.uniqueTeachers = new HashSet<>();
    }

    public static void main(String[] args) {
        domQueryiiju0z domQuery = new domQueryiiju0z();
        Document xmlDoc = domQuery.loadXMLDocument("orarendiiju0z.xml");

        System.out.println("Kurzusok listaja:");
        domQuery.listUniqueCourses(xmlDoc);

        System.out.println("\nElso Ora peldany:");
        domQuery.printFirstInstance(xmlDoc);

        System.out.println("\nOktatok listaja:");
        domQuery.listUniqueCourseTeachers(xmlDoc);

        System.out.println("\nKomplex lekerdezes: Mely tantargyat tanitja Karácsony Zsolt?");
        domQuery.complexQuery(xmlDoc, "Karácsony Zsolt");
    }

    private Document loadXMLDocument(String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void listUniqueCourses(Document xmlDoc) {
        try {
            XPathExpression expr = xPath.compile("//targy");
            NodeList courses = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);

            for (int i = 0; i < courses.getLength(); i++) {
                String courseName = courses.item(i).getTextContent();
                if (uniqueCourses.add(courseName)) {
                    System.out.println(courseName);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void printFirstInstance(Document xmlDoc) {
        try {
            XPathExpression expr = xPath.compile("//ora[1]");
            Node firstInstance = (Node) expr.evaluate(xmlDoc, XPathConstants.NODE);
            if (firstInstance != null) {
                System.out.println(nodeToString(firstInstance));
            } else {
                System.out.println("No 'ora' element found.");
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void listUniqueCourseTeachers(Document xmlDoc) {
        try {
            XPathExpression expr = xPath.compile("//oktato");
            NodeList teachers = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);

            for (int i = 0; i < teachers.getLength(); i++) {
                String teacherName = teachers.item(i).getTextContent();
                if (uniqueTeachers.add(teacherName)) {
                    System.out.println(teacherName);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private void complexQuery(Document xmlDoc, String instructorName) {
        try {
            // Find all courses taught by a specific instructor
            XPathExpression expr = xPath.compile("//ora[oktato='" + instructorName + "']/targy");
            NodeList coursesByInstructor = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);
            for (int i = 0; i < coursesByInstructor.getLength(); i++) {
                System.out.println(coursesByInstructor.item(i).getTextContent());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private String nodeToString(Node node) {
        try {
            XPathExpression expr = xPath.compile("string()");
            return (String) expr.evaluate(node, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }
}

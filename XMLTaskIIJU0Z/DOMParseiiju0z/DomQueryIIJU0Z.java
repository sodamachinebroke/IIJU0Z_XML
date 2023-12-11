import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomQueryIIJU0Z {
    public static void main(String[] args) {
        File xmlFile = new File("XMLiiju0z.xml");
        Document doc = parseXmlFile(xmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();

            // Example queries
            querySubscribersWithMultiplePlans(doc);
            System.out.println("-------------------------------------------------------------");

            querySubscriberWithMaxBillPayment(doc);
        } else {
            System.out.println("The document is null");
            System.exit(-1);
        }
    }

    private static Document parseXmlFile(File xmlFile) {
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

    private static void querySubscribersWithMultiplePlans(Document doc) {
        NodeList subscribers = doc.getDocumentElement().getElementsByTagName("subscriber");
        for (int i = 0; i < subscribers.getLength(); i++) {
            Element subscriber = (Element) subscribers.item(i);
            NodeList plans = subscriber.getElementsByTagName("bill");
            if (plans.getLength() > 1) {
                listData(subscriber.getChildNodes(), "");
            }
        }
    }
    

    private static void querySubscriberWithMaxBillPayment(Document doc) {
        NodeList bills = doc.getDocumentElement().getElementsByTagName("bill");
        String maxSubscriberId = "";
        int maxPayment = Integer.MIN_VALUE;

        for (int i = 0; i < bills.getLength(); i++) {
            NodeList query = bills.item(i).getChildNodes();
            int payment = 0;

            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("price_sum")) {
                    payment += Integer.parseInt(query.item(j).getTextContent());
                }
            }

            if (payment > maxPayment) {
                maxPayment = payment;
                maxSubscriberId = bills.item(i).getAttributes().getNamedItem("bill_sub").getNodeValue();
            }
        }

        NodeList subscribers = doc.getDocumentElement().getElementsByTagName("subscriber");
        for (int i = 0; i < subscribers.getLength(); i++) {
            if (subscribers.item(i).getAttributes().getNamedItem("sub_id").getNodeValue().equals(maxSubscriberId)) {
                listData(subscribers.item(i).getChildNodes(), "");
                break;
            }
        }
    }

    public static void listData(NodeList nodeList, String indent){
        indent += "\t";

        if(nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && !node.getTextContent().trim().isEmpty()) {
                    System.out.print(indent + "<" + node.getNodeName());
                    if (node.hasAttributes()) {
                        for (int k = 0; k < node.getAttributes().getLength(); k++) {
                            Node attribute = node.getAttributes().item(k);
                            System.out.print(" "+attribute.getNodeName()+"=\""+attribute.getNodeValue()+"\"");
                        }
                        System.out.println(">");
                    }else {
                    	System.out.println(">");
                    }
                    	
                    NodeList nodeList_new = node.getChildNodes();
                    listData(nodeList_new, indent);
                    System.out.println(indent + "</" + node.getNodeName() + ">");
                } else if (node instanceof Text){
                    String value = node.getNodeValue().trim();
                    if (value.isEmpty()){
                        continue;
                    }
                    System.out.println(indent + node.getTextContent());
                }
            }
        }
    }
}


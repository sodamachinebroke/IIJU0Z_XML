import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
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

            /*Lists subscribers' sub_id and their first email if they have more than one plan*/
            querySubscribersWithMultiplePlans(doc); 

            /*Shows the subscriber with the highest bill to pay*/
            querySubscriberWithMaxBillPayment(doc); 

            /*Shows the plans on Optical Cable technology */
            queryInternetPlansOnOpticalCable(doc); 

            /*Shows the specified subscriber's main address */
            querySubscriberPlansAddress(doc, "0002"); 

            /*Lists every plan without a TV service*/
            queryPlansWithoutTV(doc);

            /*Lists every plan and the price_sum*/
            listPlansWithTotalPrice(doc); 

        } else {
            System.out.println("The document is null");
            System.exit(-1);
        }
    }


    /*DocumentBuilderFactory and other things */
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
    
        int maxPlans = 0;
        Element maxPlansSubscriber = null;
    
        for (int i = 0; i < subscribers.getLength(); i++) {
            Element subscriber = (Element) subscribers.item(i);
            String subId = subscriber.getAttribute("sub_id");
            int planCount = countPlansForSubscriber(doc, subId);
    
            
            if (planCount > maxPlans) {
                maxPlans = planCount;
                maxPlansSubscriber = subscriber;
            }
        }
    
        if (maxPlansSubscriber != null) {
            String subId = maxPlansSubscriber.getAttribute("sub_id");
            NodeList emails = maxPlansSubscriber.getElementsByTagName("email");
            String firstEmail = (emails.getLength() > 0) ? emails.item(0).getTextContent() : "No Email";
            System.out.println("Subscriber ID: " + subId + ", First Email: " + firstEmail);
        }
    }
    
    private static int countPlansForSubscriber(Document doc, String subId) {
        NodeList bills = doc.getDocumentElement().getElementsByTagName("bill");
        int planCount = 0;
    
        for (int i = 0; i < bills.getLength(); i++) {
            Element bill = (Element) bills.item(i);
            String billSub = bill.getAttribute("bill_sub");
    
            if (subId.equals(billSub)) {
                planCount++;
            }
        }
    
        return planCount;
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
                System.out.println("Highest paying customer: ");
                listData(subscribers.item(i).getChildNodes(), "");
                break;
            }
        }
    }


    private static void queryInternetPlansOnOpticalCable(Document doc) {
        NodeList internetPlans = doc.getDocumentElement().getElementsByTagName("internet");
        System.out.println("Internet plans on Optical cable:");
    
        for (int i = 0; i < internetPlans.getLength(); i++) {
            Element internetPlan = (Element) internetPlans.item(i);
            String technology = internetPlan.getElementsByTagName("technology").item(0).getTextContent();
            
            if (technology.equals("optical")) {
                String iId = internetPlan.getAttribute("i_id");
                System.out.println(iId);
            }
        }
        System.out.println("-------------------------------------------------------------");
    }
    private static void querySubscriberPlansAddress(Document doc, String targetSubId) {
        NodeList bills = doc.getDocumentElement().getElementsByTagName("bill");
    
        for (int i = 0; i < bills.getLength(); i++) {
            Element bill = (Element) bills.item(i);
            if (bill.getAttribute("bill_sub").equals(targetSubId)) {
                String planId = bill.getAttribute("bill_plan");
                Element plan = getPlanElementById(doc, planId);
    
                if (plan != null) {
                    displayAddress(plan);
                }
            }
        }
    }
    
    private static Element getPlanElementById(Document doc, String planId) {
        NodeList plans = doc.getDocumentElement().getElementsByTagName("plan");
    
        for (int i = 0; i < plans.getLength(); i++) {
            Element plan = (Element) plans.item(i);
            if (plan.getAttribute("p_id").equals(planId)) {
                return plan;
            }
        }
    
        return null;
    }
    
    private static void displayAddress(Element plan) {
        NodeList addressNodes = plan.getElementsByTagName("address");
        if (addressNodes.getLength() > 0) {
            Element address = (Element) addressNodes.item(0);
            String zip = address.getElementsByTagName("ZIP").item(0).getTextContent();
            String city = address.getElementsByTagName("city").item(0).getTextContent();
            String street = address.getElementsByTagName("str").item(0).getTextContent();
    
            System.out.println("Subscriber Address: ZIP " + zip + ", City: " + city + ", Street: " + street);
        }
    }
    
    
    private static void queryPlansWithoutTV(Document doc) {
        NodeList services = doc.getDocumentElement().getElementsByTagName("service");
    
        for (int i = 0; i < services.getLength(); i++) {
            Element service = (Element) services.item(i);
            if (!service.hasAttribute("tv_serv")) {
                String planId = service.getAttribute("serv_sum");
                System.out.println("Plan without TV: " + planId);
            }
        }
    }
    
    
    
    
    
    private static void listPlansWithTotalPrice(Document doc) {
        NodeList services = doc.getDocumentElement().getElementsByTagName("service");
    
        for (int i = 0; i < services.getLength(); i++) {
            Element service = (Element) services.item(i);
            String planId = service.getAttribute("serv_sum");
            int totalPrice = Integer.parseInt(service.getElementsByTagName("price_sum").item(0).getTextContent());
            System.out.println("Plan " + planId + " - Total Price: " + totalPrice);
        }
    }
    
    
    /*This one prints the questions onto the screen*/
    
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


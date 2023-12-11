import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class DomWriteIIJU0Z {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("telecommunications");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XSDiiju0z.xsd");
            document.appendChild(rootElement);

            processSubscribers(document, rootElement, "0001", Arrays.asList("piroskaneni@piroskaneni.hu", "piroskaburner@gmail.com", "piros@freemail.hu"), "+36466748449", "3467", "Ároktő", "Kossuth Lajos út 3");
            processSubscribers(document, rootElement, "0002", Arrays.asList("istvan@gmail.com", "akiraly@citromail.hu", "istvankiraly@allamalapitas.hu"), "+36204567892", "2435", "Nagylók", "Szent István út 1001");
            processSubscribers(document, rootElement, "0003", Arrays.asList("geza@gmail.com", "fejedelem@citromail.hu", "honfoglalas@magyarorszag.hu"), "+36204567893", "2434", "Hantos", "Kossuth Lajos út 2");

            processBills(document, rootElement, "0001", "34670001", 1);
            processBills(document, rootElement, "0001", "34670002", 2);
            processBills(document, rootElement, "0002", "24350001", 1);
            processBills(document, rootElement, "0003", "24340001", 1);

            processPlans(document, rootElement, "34670001", "3467", "Ároktő", "Kossuth Lajos út 3");
            processPlans(document, rootElement, "34670002", "3592", "Nemesbikk", "Posta utca 13");
            processPlans(document, rootElement, "24350001", "2435", "Nagylók", "Szent István út 1001");
            processPlans(document, rootElement, "24340001", "2220", "Vecsés", "Gárdony Géza utca 10");

            processServices(document, rootElement, "34670001", "5120", "Cu120", "5050", 9500);
            processServices(document, rootElement, "34670002", "5120", "Co500", "5050", 12500);
            processServices(document, rootElement, "24350001", "00", "Op1000", null, 19000);
            processServices(document, rootElement, "24340001", "5050", "Co500", "2030", 12000);

            processInternetTypes(document, rootElement, "Cu120", "copper", 120, 2000);
            processInternetTypes(document, rootElement, "Co500", "coaxial", 500, 5000);
            processInternetTypes(document, rootElement, "Op1000", "optical", 1000, 9000);

            processTelephoneTypes(document, rootElement, "5120", 5, 120, 3500);
            processTelephoneTypes(document, rootElement, "5050", 50, 50, 5000);
            processTelephoneTypes(document, rootElement, "00", -1, -1, 10000);

            processTVTypes(document, rootElement, "2030", 50, 30, 20, 2000);
            processTVTypes(document, rootElement, "5050", 100, 50, 50, 4000);

            printDocument(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processSubscribers(Document document, Element rootElement, String sub_id,
                                            List<String> emailList, String phone, String zip, String city, String str) {
        Element sub = document.createElement("subscriber");
        sub.setAttribute("sub_id", sub_id);

        Element emailE = document.createElement("email");
        for (String s : emailList) {
            Element temp = createElement(document, "email", s);
            emailE.appendChild(temp);
        }
        sub.appendChild(emailE);

        Element phoneE = createElement(document, "phone", phone);
        sub.appendChild(phoneE);

        Element addressE = document.createElement("address");
        Element zipE = createElement(document, "ZIP", zip);
        Element cityE = createElement(document, "city", city);
        Element strE = createElement(document, "str", str);

        addressE.appendChild(zipE);
        addressE.appendChild(cityE);
        addressE.appendChild(strE);

        sub.appendChild(addressE);
        rootElement.appendChild(sub);
    }

    private static void processBills(Document document, Element rootElement, String bill_sub, String bill_plan, int plan_no) {
        Element bill = document.createElement("bill");
        bill.setAttribute("bill_sub", bill_sub);
        bill.setAttribute("bill_plan", bill_plan);

        Element planNo = createElement(document, "plan_no", String.valueOf(plan_no));
        bill.appendChild(planNo);

        rootElement.appendChild(bill);
    }

    private static void processPlans(Document document, Element rootElement, String p_id, String zip, String city, String str) {
        Element plan = document.createElement("plan");
        plan.setAttribute("p_id", p_id);

        Element addressE = document.createElement("address");
        Element zipE = createElement(document, "ZIP", zip);
        Element cityE = createElement(document, "city", city);
        Element strE = createElement(document, "str", str);

        addressE.appendChild(zipE);
        addressE.appendChild(cityE);
        addressE.appendChild(strE);

        plan.appendChild(addressE);
        rootElement.appendChild(plan);
    }

    private static void processServices(Document document, Element rootElement, String serv_sum, String tel_serv, String i_serv, String tv_serv, int price_sum) {
        Element service = document.createElement("service");
        service.setAttribute("serv_sum", serv_sum);
        service.setAttribute("tel_serv", tel_serv);
        service.setAttribute("i_serv", i_serv);
        if (tv_serv != null) {
            service.setAttribute("tv_serv", tv_serv);
        }

        Element priceSum = createElement(document, "price_sum", String.valueOf(price_sum));
        service.appendChild(priceSum);

        rootElement.appendChild(service);
    }

    private static void processInternetTypes(Document document, Element rootElement, String i_id, String technology, int bwidth, int price) {
        Element internet = document.createElement("internet");
        internet.setAttribute("i_id", i_id);

        Element technologyE = createElement(document, "technology", technology);
        Element bwidthE = createElement(document, "bwidth", String.valueOf(bwidth));
        Element priceE = createElement(document, "price", String.valueOf(price));

        internet.appendChild(technologyE);
        internet.appendChild(bwidthE);
        internet.appendChild(priceE);

        rootElement.appendChild(internet);
    }

    private static void processTelephoneTypes(Document document, Element rootElement, String tel_id, int data_GB, int free_mins, int price) {
        Element telephone = document.createElement("telephone");
        telephone.setAttribute("tel_id", tel_id);

        Element dataGB = createElement(document, "data_GB", String.valueOf(data_GB));
        Element freeMins = createElement(document, "free_mins", String.valueOf(free_mins));
        Element priceE = createElement(document, "price", String.valueOf(price));

        telephone.appendChild(dataGB);
        telephone.appendChild(freeMins);
        telephone.appendChild(priceE);

        rootElement.appendChild(telephone);
    }

    private static void processTVTypes(Document document, Element rootElement, String tv_id, int ch_num, int sd_num, int hd_num, int price) {
        Element tv = document.createElement("tv");
        tv.setAttribute("tv_id", tv_id);

        Element chNum = createElement(document, "ch_num", String.valueOf(ch_num));
        Element sdNum = createElement(document, "sd_num", String.valueOf(sd_num));
        Element hdNum = createElement(document, "hd_num", String.valueOf(hd_num));
        Element priceE = createElement(document, "price", String.valueOf(price));

        tv.appendChild(chNum);
        tv.appendChild(sdNum);
        tv.appendChild(hdNum);
        tv.appendChild(priceE);

        rootElement.appendChild(tv);
    }

    private static Element createElement(Document document, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        return element;
    }

    private static void printDocument(Document document) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(document), new StreamResult("XMLiiju0z.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

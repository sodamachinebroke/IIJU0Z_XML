import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;

public class XsdValidation {
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance(); // parsing XML file
            dBF.setNamespaceAware(true);
            DocumentBuilder dB = dBF.newDocumentBuilder();
            org.w3c.dom.Document doc = dB.parse(new File("iiju0z_kurzusfelvetel.xml"));

            SchemaFactory schFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); // Create a schema
                                                                                                      // factory and set
                                                                                                      // the XSD schema
                                                                                                      // file
            Schema schema = schFactory.newSchema(new File("iiju0z_kurzusfelvetel.xsd"));

            Validator validator = schema.newValidator(); // Validator creation

            validator.validate(new StreamSource(new File("iiju0z_kurzusfelvetel.xml"))); // Validate the XML against the
                                                                                         // XSD

            System.out.println("XML is valid according to the XSD schema."); // if :3

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.err.println("XML is not valid according to the XSD schema."); // if not :3
        }
    }
}

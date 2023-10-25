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
            // Load and parse the XML file
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.parse(new File("iiju0z_kurzusfelvetel.xml"));

            // Create a schema factory and set the XSD schema file
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("iiju0z_kurzusfelvetel.xsd")); // Provide the path to your XSD file

            // Create a validator with the schema
            Validator validator = schema.newValidator();

            // Validate the XML against the XSD
            validator.validate(new StreamSource(new File("iiju0z_kurzusfelvetel.xml")));

            System.out.println("XML is valid according to the XSD schema.");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.err.println("XML is not valid according to the XSD schema.");
        }
    }
}

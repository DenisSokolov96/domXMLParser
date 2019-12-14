import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class Validate {

    public static void getValidateXML() throws IOException, SAXException {
        validateXML();
    }

    private static void validateXML() throws SAXException, IOException {

        SchemaFactory factory2 = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        File schemaLocation = new File("/home/denis/IntelliJIDEAProjects/xmlParser/src/main/resources/Univer.xsd");
        Schema schema = factory2.newSchema(schemaLocation);

        Validator validator = schema.newValidator();

        Source source = new StreamSource("/home/denis/IntelliJIDEAProjects/xmlParser/src/main/resources/UniverChange.xml");
        try {
            validator.validate(source);
            System.out.println("XML is valid.");
        }
        catch (SAXException ex) {
            System.out.println("XML is not valid.");
            System.out.println(ex.getMessage());
        }
    }
}
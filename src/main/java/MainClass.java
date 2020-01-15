import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;

public class MainClass {

    static StringBuilder str = new StringBuilder();
    private static Logger log = Logger.getLogger(MainClass.class);

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("/home/denis/IntelliJIDEAProjects/xmlParser/src/main/resources/Univer.xml"));

        //вывод xml
        setInfo(document);
        System.out.println(str);

        //add группу в ФАД; <group name="кадгр4" val="201"/></groups>
        Modification.addElementXML(document);

        //записать данных в UniverChange.xml
        writeDocument(document);

        //валидация
        Validate.getValidateXML();
    }

    public static void setInfo(Document document) throws TransformerFactoryConfigurationError, DOMException, IOException, SAXException {

        String element = "university";
        NodeList matchedElementsList = document.getElementsByTagName(element);
        if (matchedElementsList.getLength() == 0) {
            str.append("Тег " + element + " не был найден в файле."+"\n");
        }
        else {
            NamedNodeMap attributes = matchedElementsList.item(0).getAttributes();
            str.append("Университет "+attributes.getNamedItem("name").getTextContent()+"\n");
            Node foundedElement = matchedElementsList.item(0);
            if (foundedElement.hasChildNodes())
                printInfoAboutAllChildNodes(foundedElement.getChildNodes(), 0);
        }
        log.info("Вывод информации выполнен");
    }

    private static void printInfoAboutAllChildNodes(NodeList list, int t) {

        for (int i = 0; i < list.getLength(); i++) {
            int temp = t;
            Node node = list.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String textInformation = node.getNodeValue().replace("\n", "").trim();
                if(!textInformation.isEmpty()) {
                    String s = changeattribut(node.getNodeValue());
                    tab(s, t);
                }
            }
            else {
                String s = " ";
                if (changeattribut(node.getNodeName())!="Кафедра")
                s =changeattribut(node.getNodeName());

                tab(s,t);
                NamedNodeMap attributes = node.getAttributes();

                for (int k = 0; k < attributes.getLength(); k++)
                    if (attributes.item(k).getNodeName()=="val")
                        str.append(": "+changeattribut(attributes.item(k).getNodeValue())+" студент.");
                    else str.append(changeattribut(attributes.item(k).getNodeValue())+" ");

                if (s!="") str.append("\n");
            }
            if (node.hasChildNodes()) {
                t++;
                printInfoAboutAllChildNodes(node.getChildNodes(), t);
            }
            t = temp;
        }
    }

    public static String changeattribut(String string){

        switch (string){
            case "university": return "Университет";
            case "faculty": return "Факультет";
            case "department": return "Кафедра";
            case "group": return "группа";
            case "departments":
            case "facultys":
            case "groups": return "";
            default:return string;
        }
    }

    public static void tab(String s, int t){
        if (s!="") {
            for (int j = 0; j < t; j++)
                str.append("   ");
            str.append(s + " ");
        }
    }

    private static void writeDocument(Document document){
        try {

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(document);
            Result result = new StreamResult(new FileOutputStream("/home/denis/IntelliJIDEAProjects/xmlParser/src/main/resources/UniverChange.xml"));
            transformer.transform(source, result);

            log.info("Данные записанные");

        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
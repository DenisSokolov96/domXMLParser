import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Modification {

    public static void addElementXML(Document doc){
        addElement(doc);
    }

    private static void addElement(Document doc) {

        NodeList languages = doc.getElementsByTagName("groups");
        Element lang = null;
        for(int i=0; i<languages.getLength(); i++){
            lang = (Element) languages.item(i);
            Element paradigmElement = doc.createElement("group");

            NamedNodeMap a = languages.item(i).getParentNode().getAttributes();
            String s = a.item(0).getNodeValue();

            if (s.equalsIgnoreCase("Кафедра авиационных двигателей")){
                paradigmElement.setAttribute("name","кадгр4");
                paradigmElement.setAttribute("val","5");
                lang.appendChild(paradigmElement);
            }

        }
    }
}

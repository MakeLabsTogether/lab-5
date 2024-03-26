package XML;

import Organization.Organization;
import Organization.OrganizationCollection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class XmlCommands {
    OrganizationCollection collection;
    private static final String PATH = "C:\\Users\\Work\\IdeaProjects\\lab5\\src\\dataBase.xml";

    public XmlCommands(OrganizationCollection collection) {
        this.collection = collection;
    }

    /**
     * This method update xml file with received collection
     * by converting elements of collection to Element objects
     * and building document with this Element's
     *
     * @param list This is collection we need to write down to the xml file
     * @throws ParserConfigurationException This exception
     * @see XmlCommands#saveDocumentToFile(Document),
     * @see XML.XmlCommands#toXmlElement(Organization, org.w3c.dom.Document)
     */
    public void toSaveToXML(LinkedList<Organization> list) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element orgElement = doc.createElement("OrganizationCollection");
        for (Organization organization : list) {
            orgElement.appendChild(XmlCommands.toXmlElement(organization, doc));
        }
        doc.appendChild(orgElement);
        try {
            saveDocumentToFile(doc);
        } catch (TransformerException e) {
            System.out.println("Ошибка трансформера" + '\n' + e.getLocalizedMessage());
        }
    }

    /**
     * This private method used in toSaveToXML method,
     * it updates xml file with built document
     *
     * @param doc
     * @throws TransformerException
     * @see XmlCommands#toSaveToXML(LinkedList)
     */
    private void saveDocumentToFile(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(XmlCommands.PATH));
        transformer.transform(source, result);
    }

    /**
     * this private method used in toSaveToXML method,
     * it transforms organization elements to Element objects
     *
     * @param organization
     * @param doc
     * @return
     * @see XmlCommands#toSaveToXML(LinkedList)
     */
    private static Element toXmlElement(Organization organization, Document doc) {
        Element OrganizationElement = doc.createElement("organization");
        OrganizationElement.appendChild(doc.createElement("id"));
        OrganizationElement.getChildNodes().item(0).setTextContent(String.valueOf(organization.getId()));
        //сюда нужно добавлять теги новых элементов и их внутреннее содержание через следующие методы
        //если тег будет сложный, будем думать дальше:))))
        //OrganizationElement.appendChild(doc.createElement("tagname"));
        //OrganizationElement.getChildNodes().item(индекс).setTextContent("содержание");
        return OrganizationElement;
    }

    /**
     * This static method pars xml file and return LinkedList
     *
     * @return LinkedList that was parsed from XML file
     */
    public static LinkedList<Organization> toParse() {
        File db = new File(PATH);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = null;
        LinkedList<Organization> organizations = new LinkedList<>();
        try {
            document = dbf.newDocumentBuilder().parse(db);
        } catch (SAXException e) {
            System.out.println("sax");
        } catch (IOException e) {
            System.out.println("io");
        } catch (ParserConfigurationException e) {
            System.out.println("parse");
        }
        try {
            Node mainNode = document.getFirstChild();
            NodeList firstList = mainNode.getChildNodes();
            for (int i = 0; i < firstList.getLength(); i++) {
                if (firstList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                NodeList secondList = firstList.item(i).getChildNodes();
                int id;
                for (int o = 0; o < secondList.getLength(); o++) {
                    if (secondList.item(o).getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    //В этом свиче нужно добавлять по мере необходимости теги данных,
                    //которые необходимо прочитать
                    switch (secondList.item(o).getNodeName()) {
                        case "id":
                            id = Integer.parseInt(secondList.item(o).getTextContent());
                            break;
                    }
                }
//                  хс нужна ли эта проверка.. вроде бы нет
//                if(organizations.contains(new Organization())){
//                    continue;
//                }
                organizations.add(new Organization());
            }
        } catch (NullPointerException e) {
            System.out.println("Документ пустой");
            return new LinkedList<Organization>();
        }
        return organizations;
    }

}

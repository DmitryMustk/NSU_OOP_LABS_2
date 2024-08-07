package ru.nsu.dmustakaev.client;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XmlProcessor {

    public List<String> getUsernamesFromXML(String xmlString) {
        List<String> usernames;
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlString)));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");
            usernames = IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(node -> (Element) node)
                    .map(element -> {
                        Node nameNode = element.getElementsByTagName("name").item(0);
                        return nameNode != null ? nameNode.getTextContent() : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return usernames;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractXmlValue(String xml, String tagName) {
        int start = xml.indexOf("<" + tagName + ">") + tagName.length() + 2;
        int end = xml.indexOf("</" + tagName + ">");
        if (start < 0 || end < 0) return null;
        return xml.substring(start, end);
    }
}

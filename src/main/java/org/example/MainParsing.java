package org.example;

import org.example.model.People;
import org.example.model.Root;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainParsing {

    private static final String TAG_NAME_MAIN = "name";
    private static final String TAG_PEOPLE = "people";
    private static final String TAG_ELEMENT = "element";
    private static final String TAG_NAME = "name";
    private static final String TAG_AGE = "age";



    public static void main(String[] args) {

        Root root = new Root();

        Document doc = null;
        try {
            doc = buildDocument();
        } catch (Exception e) {
            System.out.println("Open parsing error" + e.toString());
            return;
        }

        Node rootNode = doc.getFirstChild();
        System.out.println("1 " + rootNode.getNodeName());

        NodeList rootChilds = rootNode.getChildNodes();


        String mainName = null;
        Node peopleNode = null;
        for (int i = 0; i < rootChilds.getLength(); i++){

            if(rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE)
               continue;

           System.out.println("2 " + rootChilds.item(i).getNodeName());
            switch (rootChilds.item(i).getNodeName()){
                case TAG_NAME_MAIN: {
                    mainName = rootChilds.item(i).getTextContent();
                    System.out.println("mainName " + mainName);
                    break;
                }
                case TAG_PEOPLE: {
                    peopleNode = rootChilds.item(i);
                    break;
                }
            }

        }


        if (peopleNode == null){
            return;
        }

        List<People> peopleList = parsPeople(peopleNode);

        root.setName(mainName);
        root.setPeople(peopleList);

        root.getPeople().stream().filter(people -> {
            return people.getAge() == 20;
        }).forEach(people -> {
            System.out.println("People " + people.toString());
        });

        System.out.println("Root " + root.toString());
    }

    private static  Document buildDocument() throws Exception{
        File file = new File("Test.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);
    }

    private static List<People> parsPeople(Node peopleNode){
        List<People> peopleList = new ArrayList<People>();
        NodeList peopleChilds = peopleNode.getChildNodes();
        for (int i = 0; i < peopleChilds.getLength(); i++) {

            if (peopleChilds.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            if (!peopleChilds.item(i).getNodeName().equals(TAG_ELEMENT)){
                continue;
            }

            People people = parsElement(peopleChilds.item(i));

            peopleList.add(people);
        }
        return peopleList;
    }

    private static People parsElement(Node elementNode){
        String name = "";
        int age = 0;
        NodeList elementChilds = elementNode.getChildNodes();
        for (int j = 0; j < elementChilds.getLength(); j++) {
            if (elementChilds.item(j).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }

            switch (elementChilds.item(j).getNodeName()){
                case TAG_NAME: {
                    name = elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_AGE: {
                    age = Integer.valueOf(elementChilds.item(j).getTextContent());
                    break;
                }
            }
        }
        People people = new People(name, age);
        return people;
    }
}

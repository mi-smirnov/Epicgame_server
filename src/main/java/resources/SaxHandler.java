package resources;

/**
 * Created by smike on 05.12.14.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import utils.ReflectionHelper;

public class SaxHandler extends DefaultHandler {
    private static String CLASSNAME = "class";
    private String element = null;
    private Object object = null;

    public void startDocument() throws SAXException {
        System.out.println("Start document");
    }

    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(!qName.equals(CLASSNAME)){
            element = qName;
        }
        else{
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            object = ReflectionHelper.createIntance(className);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(element != null){
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    public Object getObject(){
        return object;
    }
}


package resources;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by smike on 05.12.14.
 */
public class ResourceFactory {

    public static class ResourceFactoryHolder {
        public static final ResourceFactory HOLDER_INSTANCE = new ResourceFactory();
    }
    public static ResourceFactory getInstance(){
        return ResourceFactoryHolder.HOLDER_INSTANCE;
    }

    public Object get(String fileName){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            parser.parse(fileName, handler);
            return handler.getObject();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

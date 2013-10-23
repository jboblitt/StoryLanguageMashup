package com.jbslade.lang.mashup.parse;

import com.jbslade.lang.mashup.debug.MyLogger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Justin on 10/11/13. Notice that the same generic in ResourceParserXml is used in
 * declaring the objects in XmlContentHandler.  This is because the ResourceParserXml calls on
 * XmlContentHandler to return the parsed elements.  The objects must be the same.
 */
public class ResourceParserXml<T> extends ResourceParser
{
    private final XmlContentHandler m_saxContentHandler;
    private final ErrorHandler m_saxErrorHandler;

    public ResourceParserXml(XmlContentHandler<T> saxContentHandler, ErrorHandler saxErrorHandler)
    {
        m_saxContentHandler = saxContentHandler;
        m_saxErrorHandler = saxErrorHandler;
    }

    @Override
    public ArrayList<T> getResourceParsedElems(InputStream is)
    {
        try
        {
            //Prepare input source for xml parsing.
            InputSource source = new InputSource(is);
            source.setEncoding("ISO-8859-1");

            //Get instance of XMLReader from factory.
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            XMLReader m_xmlReader = saxParser.getXMLReader();

            // Set up my parsing handler to create objects while parsing the xml file.
            m_xmlReader.setContentHandler(m_saxContentHandler);

            // Add my error handler to print out convenient messages when finding formatting issues.
            m_xmlReader.setErrorHandler(m_saxErrorHandler);
            m_xmlReader.parse(source);
            return m_saxContentHandler.getParsedElems();
        }
        catch (ParserConfigurationException e)
        {
            MyLogger.logExceptionSevere(ResourceParserXml.class.getName(),
                    "getResourceParsedElems", null, e);
        }
        catch (SAXException e)
        {
            MyLogger.logExceptionSevere(ResourceParserXml.class.getName(),
                    "getResourceParsedElems", null, e);
        }
        catch (IOException e)
        {
            MyLogger.logExceptionSevere(ResourceParserXml.class.getName(),
                    "getResourceParsedElems", null, e);
        }

        return new ArrayList<T>();
    }
}

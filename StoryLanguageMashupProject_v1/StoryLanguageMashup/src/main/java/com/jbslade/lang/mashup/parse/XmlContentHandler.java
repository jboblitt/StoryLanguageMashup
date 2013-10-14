package com.jbslade.lang.mashup.parse;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;


/**
 * Created by Justin on 10/4/13.  This content handler is used by ResourceParserXml to parse
 * an xml resource into one or more objects.  Specify object T to specify the type of object
 * parsed from the xml resource.
 */
public abstract class XmlContentHandler<T> extends DefaultHandler
{
    public abstract ArrayList<T> getParsedElems();
}

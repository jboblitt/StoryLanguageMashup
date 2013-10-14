package com.jbslade.lang.mashup.parse;

import com.jbslade.lang.mashup.forms.SpiegelRssElement;
import com.jbslade.lang.mashup.debug.MyLogger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;

/**
 * Created by Justin on 10/5/13.
 */
public class XmlContentHandlerSpiegelRss extends XmlContentHandler<SpiegelRssElement>
{
    public static final String XML_TAG_ITEM = "item";
    public static final String XML_TAG_ITEM_TITLE = "title";
    public static final String XML_TAG_ITEM_DESCRIPTION = "description";
    public static final String XML_TAG_ITEM_PUBDATE = "pubDate";
    public static final String XML_TAG_ITEM_RESOURCE_URL = "guid";
    public static final String XML_TAG_ITEM_ENCLOSURE = "enclosure";

    public static final String XML_PARAM_ITEM_ENCLOSURE_TYPE = "type";
    public static final String XML_PARAM_ITEM_ENCLOSURE_URL = "url";

    private boolean m_isXmlTagItemTITLE = false;
    private boolean m_isXmlTagItemDESCRIPTION = false;
    private boolean m_isXmlTagItemPUBDATE = false;
    private boolean m_isXmlTagItemRESOURCE_URL = false;


    private ArrayList<SpiegelRssElement> m_spiegelElems;
    private SpiegelRssElement rssElem;

    public XmlContentHandlerSpiegelRss()
    {
        m_spiegelElems = new ArrayList<SpiegelRssElement>();
    }

    public ArrayList<SpiegelRssElement> getParsedElems()
    {
        return m_spiegelElems;
    }

    @Override
    public void startDocument() throws SAXException
    {
        m_spiegelElems.clear();
    }

    @Override
    public void endDocument() throws SAXException
    {
        MyLogger.logInfo(XmlContentHandlerSpiegelRss.class.getName(), "endDocument", null);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts)
            throws SAXException
    {
        if (qName.equals(XML_TAG_ITEM))
        {
            rssElem = new SpiegelRssElement();
        }
        else if (qName.equals(XML_TAG_ITEM_TITLE))
        {
            this.m_isXmlTagItemTITLE = true;
        }
        else if (qName.equals(XML_TAG_ITEM_DESCRIPTION))
        {
            this.m_isXmlTagItemDESCRIPTION = true;
        }
        else if (qName.equals(XML_TAG_ITEM_PUBDATE))
        {
            this.m_isXmlTagItemPUBDATE = true;
        }
        else if (qName.equals(XML_TAG_ITEM_RESOURCE_URL))
        {
            this.m_isXmlTagItemRESOURCE_URL = true;
        }
        else if (qName.equals(XML_TAG_ITEM_ENCLOSURE))
        {
            if (rssElem != null)
            {
                rssElem.setEnclosureType(atts.getValue(XML_PARAM_ITEM_ENCLOSURE_TYPE));
                rssElem.setEnclosureUrl(atts.getValue(XML_PARAM_ITEM_ENCLOSURE_URL));
            }
        }
        else
        {
            //don't care about these elements
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {

        //Reset flag at end of element.  If Item tag closes, add rss element to ArrayList
        if (qName.equals(XML_TAG_ITEM))
        {
            m_spiegelElems.add(rssElem);
            rssElem = null;
        }
        else if (qName.equals(XML_TAG_ITEM_TITLE))
        {
            m_isXmlTagItemTITLE = false;
        }
        else if (qName.equals(XML_TAG_ITEM_DESCRIPTION))
        {
            m_isXmlTagItemDESCRIPTION = false;
        }
        else if (qName.equals(XML_TAG_ITEM_PUBDATE))
        {
            m_isXmlTagItemPUBDATE = false;
        }
        else if (qName.equals(XML_TAG_ITEM_RESOURCE_URL))
        {
            this.m_isXmlTagItemRESOURCE_URL = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (rssElem != null)
        {
            if (m_isXmlTagItemTITLE)
            {
                String str = new String(ch, start, length);
                rssElem.setTitle(str);
            }
            else if (m_isXmlTagItemDESCRIPTION)
            {
                String str = new String(ch, start, length);
                rssElem.setDescription(str);
            }
            else if (m_isXmlTagItemPUBDATE)
            {
                String str = new String(ch, start, length);
                rssElem.setPubDate(str);
            }
            else if (m_isXmlTagItemRESOURCE_URL)
            {
                String str = new String(ch, start, length);
                rssElem.setResourceUrl(str);
            }
            else
            {
                //don't care
            }
        }


    }
}

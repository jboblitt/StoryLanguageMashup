package com.jbslade.lang.mashup.forms;

import android.widget.ImageView;
import android.widget.Toast;

import com.jbslade.lang.mashup.interfaces.ListBasicAdapterPopulateFields;

import java.io.Serializable;

/**
 * Created by Justin on 10/5/13.
 */
public class SpiegelRssElement implements Serializable, ListBasicAdapterPopulateFields
{
    private String m_title;
    private String m_description;
    private String m_pubDate;
    private String m_resourceUrl;
    private String m_enclosureType;
    private String m_enclosureUrl;

    public SpiegelRssElement()
    {

    }

    public String getTitle()
    {
        return m_title;
    }

    public void setTitle(String title)
    {
        m_title = title;
    }

    public String getDescription()
    {
        return m_description;
    }

    public void setDescription(String description)
    {
        m_description = description;
    }

    public String getPubDate()
    {
        return m_pubDate;
    }

    public void setPubDate(String pubDate)
    {
        m_pubDate = pubDate;
    }

    public String getEnclosureType()
    {
        return m_enclosureType;
    }

    public void setEnclosureType(String enclosureType)
    {
        m_enclosureType = enclosureType;
    }

    public String getEnclosureUrl()
    {
        return m_enclosureUrl;
    }

    public void setEnclosureUrl(String enclosureUrl)
    {
        m_enclosureUrl = enclosureUrl;
    }

    public String getResourceUrl()
    {
        return m_resourceUrl;
    }

    public void setResourceUrl(String resourceUrl)
    {
        m_resourceUrl = resourceUrl;
    }

    @Override
    public void populateIcon(ImageView icon)
    {
    }

    @Override
    public String getHeader1Text()
    {
        return getTitle();
    }

    @Override
    public String getHeader2aText()
    {
        return getPubDate();
    }

    @Override
    public String getHeader2bText()
    {
        return getDescription();
    }

    @Override
    public String toString()
    {
        String toRet = "";
        toRet += "Title:" + getTitle();
        toRet += "Publication Date:" + getPubDate();
        toRet += "Description:" + getDescription();
        toRet += "Resource Url:" + getResourceUrl();
        toRet += "Enclosure Type:" + getEnclosureType();
        toRet += "Enclosure Url:" + getEnclosureUrl();
        return toRet;
    }
}

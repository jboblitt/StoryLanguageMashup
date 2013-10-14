package com.jbslade.lang.mashup.forms;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Justin on 10/9/13.
 */
public class Story implements Serializable
{
    public static final String DELIMITER = "\\.";
    private ArrayList<String> m_nativeLangIndexedStory;
    private ArrayList<String> m_foreignLangIndexedStory;
    private String m_foreignText;

    public Story()
    {
        m_nativeLangIndexedStory = new ArrayList<String>();
        m_foreignLangIndexedStory = new ArrayList<String>();
    }

    public void addForeignLangSentenceArray(String[] paragraph)
    {
        for (String sentence : paragraph)
            m_foreignLangIndexedStory.add(sentence);
    }


    public String getNativeSentenceAtIndex(int ind)
    {
        return m_nativeLangIndexedStory.get(ind);
    }

    public String getForeignSentenceAtIndex(int ind)
    {
        return m_foreignLangIndexedStory.get(ind);
    }


    public void setForeignText(String foreignText)
    {
        m_foreignText = foreignText;
    }

    public String getForeignText()
    {
        return m_foreignText;
    }
}

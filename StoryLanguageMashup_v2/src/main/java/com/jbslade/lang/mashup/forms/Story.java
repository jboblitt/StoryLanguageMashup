package com.jbslade.lang.mashup.forms;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Justin on 10/9/13.
 */
public class Story implements Serializable
{
    public static final String DELIMITER = "(?<=[\\.?!])[\\s]";
    private ArrayList<String> m_nativeLangIndexedStory;
    private ArrayList<String> m_foreignLangIndexedStory;

    public Story()
    {
        //m_nativeLangIndexedStory = new ArrayList<String>();
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

    public String[] getForeignStringArray()
    {
        return m_foreignLangIndexedStory.toArray(new String[m_foreignLangIndexedStory.size()]);
    }

    public void setNativeStory(ArrayList<String> nativeStory)
    {
        m_nativeLangIndexedStory = nativeStory;
    }

    public int getStorySize()
    {
        return m_foreignLangIndexedStory.size();
    }
}

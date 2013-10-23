package com.jbslade.lang.mashup.adapter;

import android.widget.ImageView;

import com.jbslade.lang.mashup.forms.Story;
import com.jbslade.lang.mashup.interfaces.ListBasicAdapterControllerInterface;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Justin on 10/12/13.
 */
public class AdapterControllerStory implements ListBasicAdapterControllerInterface<String>
{
    private ArrayList<Integer> m_foreignIndicesShown;
    private ArrayList<Integer> m_nativeIndicesShown;

    private Story m_story;

    public AdapterControllerStory(Story story)
    {
        m_story = story;
        m_foreignIndicesShown = new ArrayList<Integer>();

        //Story all starts out in foreign language.  Set up foreign pool to contain all sentences.
        for (int i = 0; i < story.getStorySize(); i++)
            m_foreignIndicesShown.add(i);

        m_nativeIndicesShown = new ArrayList<Integer>();
    }

    private String getTextForSentenceIndex(int ind)
    {
        //Check in the pull of foreign languages for this index.
        if (m_foreignIndicesShown.contains(ind))
        {
            //Should return the foreign language at this index
            return m_story.getForeignSentenceAtIndex(ind);
        }
        else
        {
            //Should return the native language at this index
            return m_story.getNativeSentenceAtIndex(ind);
        }
    }

    public void requestPercentOfForeign(int requestedPercentageOfForeign, int maxValue)
    {
        int numForeignRequested =
                Math.round((float) requestedPercentageOfForeign * getCount() / maxValue);

        if (m_foreignIndicesShown.size() < numForeignRequested)
        {
            //Should move elements from native to foreign pool
            while (m_foreignIndicesShown.size() != numForeignRequested)
            {
                int randIndex = (int) (Math.random() * m_nativeIndicesShown.size());
                m_foreignIndicesShown.add(m_nativeIndicesShown.remove(randIndex));
            }
        }
        else if (m_foreignIndicesShown.size() > numForeignRequested)
        {
            //Should move from foreign to native pool.
            while (m_foreignIndicesShown.size() != numForeignRequested)
            {
                int randIndex = (int) (Math.random() * m_foreignIndicesShown.size());
                m_nativeIndicesShown.add(m_foreignIndicesShown.remove(randIndex));
            }
        }
    }

    @Override
    public boolean addElemsToList(ArrayList elems)
    {
        //We don't ever want to add sentences to this ListView adapter
        return false;
    }

    @Override
    public int getCount()
    {
        return m_story.getStorySize();
    }

    @Override
    public String getItem(int position)
    {
        //Each item in this list is actually a sentence from the story.
        return getTextForSentenceIndex(position);
    }

    @Override
    public String getHeader1Text(int position)
    {
        //Don't want to use Header1
        return null;
    }

    @Override
    public String getHeader2aText(int position)
    {
        //Don't want to use header 2a
        return null;
    }

    @Override
    public String getHeader2bText(int position)
    {
        //Header 2b should always be the smallest and most detailed text (good for a story).
        return getTextForSentenceIndex(position);
    }

    @Override
    public void populateIcon(ImageView icon)
    {

    }
}
